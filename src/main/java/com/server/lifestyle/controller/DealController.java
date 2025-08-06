package com.server.lifestyle.controller;

import com.server.lifestyle.model.Deal;
import com.server.lifestyle.response.ApiResponse;
import com.server.lifestyle.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deal/")
public class DealController {
    private final DealService dealService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Deal>> getAllDeals() {
        List<Deal> deals = dealService.getAllDeals();
        return new ResponseEntity<>(deals, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) {
        Deal createdDeal = dealService.createDeal(deal);
        return new ResponseEntity<>(createdDeal, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal deal) throws Exception {
        Deal updatedDeal = dealService.updateDeal(deal, id);
        return new ResponseEntity<>(updatedDeal, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id) throws Exception {
        dealService.deleteDeal(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully deleted deal");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
