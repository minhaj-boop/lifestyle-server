package com.server.lifestyle.service.impl;

import com.server.lifestyle.model.Deal;
import com.server.lifestyle.model.HomeCategory;
import com.server.lifestyle.repository.DealRepository;
import com.server.lifestyle.repository.HomeCategoryRepository;
import com.server.lifestyle.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;

    @Override
    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) {
        HomeCategory homeCategory = homeCategoryRepository.findById(deal.getHomeCategory().getId()).orElse(null);

        Deal newDeal = new Deal();
        newDeal.setHomeCategory(homeCategory);
        newDeal.setDiscount(deal.getDiscount());

        return dealRepository.save(newDeal);
    }

    @Override
    public Deal updateDeal(Deal deal, Long id) throws Exception {
        Deal oldDeal = dealRepository.findById(id).orElse(null);
        HomeCategory homeCategory = homeCategoryRepository.findById(deal.getHomeCategory().getId()).orElse(null);

        if(oldDeal != null) {
            if(deal.getDiscount() != null) {
                oldDeal.setDiscount(deal.getDiscount());
            }
            if(deal.getHomeCategory() != null) {
                oldDeal.setHomeCategory(homeCategory);
            }
            return dealRepository.save(oldDeal);
        }
        throw new Exception("Deal not found!");
    }

    @Override
    public void deleteDeal(Long dealId) throws Exception {
        Deal oldDeal = dealRepository.findById(dealId).orElseThrow(()-> new Exception("Deal not found!"));
        dealRepository.delete(oldDeal);
    }
}
