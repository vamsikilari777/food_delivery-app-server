package com.brundhavan.foodapi.controller;

import com.brundhavan.foodapi.IO.FoodRequest;
import com.brundhavan.foodapi.IO.FoodResponse;
import com.brundhavan.foodapi.services.FoodService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private final FoodService foodService;
    private final ObjectMapper objectMapper;



    public FoodController(FoodService productImageService, ObjectMapper objectMapper) {
        this.foodService = productImageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/add")
    public FoodResponse addProduct(@RequestPart("food") String foodString,
                                   @RequestPart("file") MultipartFile file) {

        ObjectMapper objectMapper = new ObjectMapper();
        FoodRequest request = null;

        try {
            request = objectMapper.readValue(foodString, FoodRequest.class);
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        }

        FoodResponse response = foodService.addProduct(request, file);
        return response;
    }
    @GetMapping("/foods-list")
    public List<FoodResponse> getProductList(){
        return foodService.getProductList();
    }

    @GetMapping("/{id}")
    public FoodResponse getProductById(@PathVariable String id){
        return foodService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable String id){
        foodService.deleteProductById(id);
    }
}
