package kdt.gccoffee.controller;

import kdt.gccoffee.model.Category;

public record CreateProductRequest(String productName, Category category, long price, String descriptions) {
}
