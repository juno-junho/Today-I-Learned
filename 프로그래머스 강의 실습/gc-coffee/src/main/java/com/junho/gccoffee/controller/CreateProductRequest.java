package com.junho.gccoffee.controller;

import com.junho.gccoffee.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String description) {
}
