package com.server.lifestyle.controller;

import com.server.lifestyle.exceptions.ProductException;
import com.server.lifestyle.exceptions.SellerException;
import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.Seller;
import com.server.lifestyle.request.CreateProductRequest;
import com.server.lifestyle.service.ProductService;
import com.server.lifestyle.service.SellerService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/products")
public class SellerProductController {

    private final SellerService sellerService;
    private final ProductService productService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Product>> getProductsBySellerId(@RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);

        List<Product> products = productService.getProductsBySellerId(seller.getId());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestHeader("Authorization") String jwt, @RequestBody CreateProductRequest  req) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        Product product = productService.createProduct(req, seller);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) throws Exception {
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) throws ProductException {

        Product updateProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);

    }
}
