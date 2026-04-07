package com.brundhavan.foodapi.repository;

import com.brundhavan.foodapi.Entities.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<FoodEntity, String> {
}
