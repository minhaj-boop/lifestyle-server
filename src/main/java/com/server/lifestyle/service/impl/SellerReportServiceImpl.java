package com.server.lifestyle.service.impl;

import com.server.lifestyle.model.Seller;
import com.server.lifestyle.model.SellerReport;
import com.server.lifestyle.repository.SellerReportRepository;
import com.server.lifestyle.service.SellerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {

    private final SellerReportRepository sellerReportRepository;

    @Override
    public SellerReport getSellerReport(Seller seller) {
        SellerReport sellerReport = sellerReportRepository.findBySellerId(seller.getId());
        if(sellerReport == null) {
            SellerReport newReport = new SellerReport();
            newReport.setId(seller.getId());
            newReport.setSeller(seller);
            return sellerReportRepository.save(newReport);
        }
        return sellerReport;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }
}
