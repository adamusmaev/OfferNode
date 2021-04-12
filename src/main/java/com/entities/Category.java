package com.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL)
    private Offer offer;
}
