package com.junho.gccoffee.service;

import com.junho.gccoffee.model.Category;
import com.junho.gccoffee.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price);

    Product createProduct(String productName, Category category, long price, String description);
}
