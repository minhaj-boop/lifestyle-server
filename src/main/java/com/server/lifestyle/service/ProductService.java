package com.server.lifestyle.service;

import com.server.lifestyle.exceptions.ProductException;
import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.Seller;
import com.server.lifestyle.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest req, Seller seller);
    void deleteProduct(Long productId) throws ProductException;
    Product updateProduct(Long productId, Product product) throws ProductException;
    Product findProductById(Long productId) throws ProductException;
    List<Product> searchProducts(String keyword);
    Page<Product> getAllProducts(
            String category,
            String brand,
            String colors,
            String size,
            Double minPrice,
            Double maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );

    List<Product> getProductsBySellerId(Long sellerId);
}
