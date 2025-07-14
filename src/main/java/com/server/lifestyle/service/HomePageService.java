package com.server.lifestyle.service;

import com.server.lifestyle.model.HomeCategory;
import com.server.lifestyle.model.HomePage;

import java.util.List;

public interface HomePageService {
    HomePage createHomePageData(List<HomeCategory> allCategories);
}
