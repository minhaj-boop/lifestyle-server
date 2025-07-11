package com.server.lifestyle.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SellerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @OneToOne
    Seller seller;

    private double totalEarnings = 0.0;

    private double totalSales = 0.0;

    private double totalRefunds = 0.0;

    private double totalTax = 0.0;

    private double netEarnings = 0.0;

    private Integer totalOrders = 0;

    private Integer cancelledOrders = 0;

    private Integer totalTransactions = 0;
}
