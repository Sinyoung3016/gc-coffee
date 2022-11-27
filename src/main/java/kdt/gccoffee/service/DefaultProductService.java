package kdt.gccoffee.service;

import kdt.gccoffee.model.Category;
import kdt.gccoffee.model.Product;
import kdt.gccoffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService{

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product creatProduct(String productName, Category category, long price) {
        Product newProduct = new Product(UUID.randomUUID(), productName, category, price);
        return productRepository.insert(newProduct);
    }

    @Override
    public Product creatProduct(String productName, Category category, long price, String descriptions) {
        Product newProduct = new Product(UUID.randomUUID(), productName, category, price, descriptions, LocalDateTime.now(), LocalDateTime.now());
        return productRepository.insert(newProduct);
    }
}
