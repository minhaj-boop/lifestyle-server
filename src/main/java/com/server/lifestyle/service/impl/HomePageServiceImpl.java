package com.server.lifestyle.service.impl;

import com.server.lifestyle.domain.HomeCategorySection;
import com.server.lifestyle.model.Deal;
import com.server.lifestyle.model.HomeCategory;
import com.server.lifestyle.model.HomePage;
import com.server.lifestyle.repository.DealRepository;
import com.server.lifestyle.service.HomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HomePageServiceImpl implements HomePageService {

    private final DealRepository dealRepository;

    @Override
    public HomePage createHomePageData(List<HomeCategory> allCategories) {
        List<HomeCategory> gridCategories = allCategories.stream()
                .filter(category ->
                        category.getHomeCategorySection() == HomeCategorySection.GRID)
                .collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category ->
                        category.getHomeCategorySection() == HomeCategorySection.SHOP_BY_CATEGORY)
                .collect(Collectors.toList());

        List<HomeCategory> electronicsCategories = allCategories.stream()
                .filter(category ->
                        category.getHomeCategorySection() == HomeCategorySection.ELECTRIC_CATEGORY)
                .collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream()
                .filter(category ->
                        category.getHomeCategorySection() == HomeCategorySection.DEALS)
                .collect(Collectors.toList());

        List<Deal> createdDeals = new ArrayList<>();

        if(dealRepository.findAll().isEmpty()) {
            List<Deal> deals = allCategories.stream()
                    .filter(category -> category.getHomeCategorySection() == HomeCategorySection.DEALS)
                    .map(category -> new Deal(null, 10, category ))
                    .collect(Collectors.toList());
            createdDeals = dealRepository.saveAll(deals);
        } else  {
            createdDeals = dealRepository.findAll();
        }

        HomePage home = new HomePage();
        home.setGrid(gridCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electronicsCategories);
        home.setDeals(createdDeals);
        home.setDealCategories(dealCategories);
        return home;
    }
}
