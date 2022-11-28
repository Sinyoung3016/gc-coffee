package kdt.gccoffee.service;

import kdt.gccoffee.model.Category;
import kdt.gccoffee.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProduct();

    Product creatProduct(String productName, Category category, long price);

    Product creatProduct(String productName, Category category, long price, String descriptions);
}
