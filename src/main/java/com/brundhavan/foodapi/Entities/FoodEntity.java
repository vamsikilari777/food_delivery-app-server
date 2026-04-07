package com.brundhavan.foodapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products_details")
public class FoodEntity {

    private String id;
    private String prodName;
    private String prodDescription;
    private double prodPrice;
    private String prodCategory;
    private String imageUrl;

}

