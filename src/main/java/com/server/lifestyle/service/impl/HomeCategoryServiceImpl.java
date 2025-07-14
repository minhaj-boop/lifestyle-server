package com.server.lifestyle.service.impl;

import com.server.lifestyle.model.HomeCategory;
import com.server.lifestyle.repository.HomeCategoryRepository;
import com.server.lifestyle.service.HomeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategoryService {

    private final HomeCategoryRepository homeCategoryRepository;


    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {
        if(homeCategoryRepository.findAll().isEmpty()) {
            return homeCategoryRepository.saveAll(homeCategories);
        }
        return  homeCategoryRepository.findAll();
    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory category, Long id) throws Exception {
        HomeCategory oldHomeCategory = homeCategoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Category not found")
                );
        if(category.getImage() != null) {
            oldHomeCategory.setImage(category.getImage());
        }
        if(category.getCategoryId() != null) {
            oldHomeCategory.setCategoryId(category.getCategoryId());
        }
        return homeCategoryRepository.save(oldHomeCategory);
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }
}
