package com.server.lifestyle.controller;

import com.server.lifestyle.exceptions.ProductException;
import com.server.lifestyle.model.Product;
import com.server.lifestyle.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;


    @GetMapping("/get/by-id/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search/by-query")
    public ResponseEntity<List<Product>> searchProductByQuery(@RequestParam(required = false) String query) throws ProductException {
        List<Product> products = productService.searchProducts(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) Integer pageNumber) throws ProductException {
        return new ResponseEntity<>(
                productService.getAllProducts(category, brand, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber ), HttpStatus.OK
        );
    }
}
