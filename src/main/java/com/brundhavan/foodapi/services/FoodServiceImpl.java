package com.brundhavan.foodapi.services;

import com.brundhavan.foodapi.Entities.FoodEntity;
import com.brundhavan.foodapi.IO.FoodRequest;
import com.brundhavan.foodapi.IO.FoodResponse;
import com.brundhavan.foodapi.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;

// Image Stored via s3 bucket related code

//    @Autowired
//    private S3Client s3Client;
//    @Value("${aws.region}")
//    private String region;
//    private final String bucketName = "milkies-milk";
//
//    @Override
//    public String uploadFile(MultipartFile file) {
//        try {
//            // Generate unique file name
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//            // Create put request
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(fileName)
//                    .contentType(file.getContentType())
//                    .build();
//
//            // Upload to S3
//            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
//
//            // Return the file URL
//            return String.format("https://%s.s3.%s.amazonaws.com/%s",
//                    bucketName, region, fileName);
//
//        } catch (IOException e) {
//            throw new RuntimeException("Error uploading file to S3", e);
//        }
//    }


// Image Stored via in project directory related code

    // Folder path inside project
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // Create folder if not exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Save file
            File destinationFile = new File(uploadDir + fileName);
            file.transferTo(destinationFile);

            // Return file path (or URL later)
            return "/uploads/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file locally", e);
        }
    }

    @Override
    public FoodResponse addProduct(FoodRequest request, MultipartFile file) {
        FoodEntity newProductEntity = converToEntity(request);
        String imageUrl = uploadFile(file);
        newProductEntity.setImageUrl(imageUrl);
        newProductEntity = foodRepository.save(newProductEntity);

        return  converToResponse(newProductEntity);
    }
    @Override
    public List<FoodResponse> getProductList() {

        List<FoodEntity> listOfProductEntity = foodRepository.findAll();
        return listOfProductEntity.stream().map((object)-> converToResponse(object)).collect(Collectors.toList());
    }

    @Override
    public FoodResponse getProductById(String id) {
        FoodEntity singleProduct = foodRepository.findById(id).get();
        return converToResponse(singleProduct);
    }

    ///  this is for s3 bucket file deleting method

//    public boolean deleteFile(String fileName){
//        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileName)
//                .build();
//        s3Client.deleteObject(deleteObjectRequest);
//        return true;
//    }

    ///  this is for local file deleting method
    public boolean deleteFile(String fileName) {

        // Path to uploads folder
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File file = new File(uploadDir + fileName);

        // Check if file exists
        if (file.exists()) {
            return file.delete(); // true if deleted
        }

        return false; // file not found
    }
    @Override
    public void deleteProductById(String id) {
        FoodEntity productEntity = foodRepository.findById(id).get();
        String imageUrl = productEntity.getImageUrl();
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        boolean isFileDeleted = deleteFile(fileName);
        if(isFileDeleted) {

            foodRepository.deleteById(id);
        }
    }
    private FoodEntity converToEntity(FoodRequest request){

        return FoodEntity.builder()
                .prodName(request.getName())
                .prodDescription(request.getDescription())
                .prodCategory(request.getCategory())
                .prodPrice(request.getPrice())
                .build();
    }

    private FoodResponse converToResponse(FoodEntity entity){
        return  FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getProdName())
                .category(entity.getProdCategory())
                .description(entity.getProdDescription())
                .price(entity.getProdPrice())
                .imageUrl(entity.getImageUrl())
                .build();
    }


}
