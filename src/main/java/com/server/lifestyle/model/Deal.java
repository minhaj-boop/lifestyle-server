package com.server.lifestyle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private Integer discount;

    @OneToOne
    private HomeCategory homeCategory;
}
