package com.server.lifestyle.controller;

import com.server.lifestyle.model.HomeCategory;
import com.server.lifestyle.model.HomePage;
import com.server.lifestyle.service.HomeCategoryService;
import com.server.lifestyle.service.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeCategoryController {
    private final HomeCategoryService homeCategoryService;
    private final HomePageService homeService;

    @PostMapping("/category/create")
    public ResponseEntity<HomePage> createHomeCategory(@RequestBody List<HomeCategory> homeCategories) {

        List<HomeCategory> categories = homeCategoryService.createCategories(homeCategories);
        HomePage home = homeService.createHomePageData(categories);
        return new ResponseEntity<>(home, HttpStatus.OK);

    }

    @GetMapping("/category/get")
    public ResponseEntity<List<HomeCategory>> getHomeCategory() {
        List<HomeCategory> categories = homeCategoryService.getAllHomeCategories();
        return ResponseEntity.ok(categories);
    }

    @PatchMapping("/category/update/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long id, @RequestBody HomeCategory homeCategory) throws Exception {
        HomeCategory updateCategory = homeCategoryService.updateHomeCategory(homeCategory, id);
        return ResponseEntity.ok(updateCategory);
    }

}
