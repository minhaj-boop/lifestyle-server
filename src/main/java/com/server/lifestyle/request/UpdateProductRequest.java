package com.server.lifestyle.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateProductRequest {
    private String title;
    private String description;
    private String color;
    private double mrPrice;
    private double sellingPrice;
    private List<String> images;
    private List<String> sizes;
    private String category3; // Optional: for changing category
    private String stock;
}
