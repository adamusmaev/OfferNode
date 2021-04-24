package com.transfers;

import com.entities.Category;
import lombok.Data;

@Data
public class CategoryTransfer {

    private Integer id;
    private String name;

    public CategoryTransfer(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
