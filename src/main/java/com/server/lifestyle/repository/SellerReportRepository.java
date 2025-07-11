package com.server.lifestyle.repository;

import com.server.lifestyle.model.Seller;
import com.server.lifestyle.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    SellerReport findBySellerId(Long sellerId);

}
