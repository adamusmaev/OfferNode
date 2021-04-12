package com.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "characteristics")
    private Collection<Offer> offers;

}
