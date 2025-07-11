package com.server.lifestyle.service;

import com.server.lifestyle.model.Seller;
import com.server.lifestyle.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
