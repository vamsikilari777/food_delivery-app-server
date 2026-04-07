package com.brundhavan.foodapi.services;

import com.brundhavan.foodapi.IO.FoodRequest;
import com.brundhavan.foodapi.IO.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {

    String uploadFile(MultipartFile file);
    FoodResponse addProduct(FoodRequest request, MultipartFile file);
    List<FoodResponse> getProductList();
    FoodResponse getProductById(String id);
    void deleteProductById(String id);

}
