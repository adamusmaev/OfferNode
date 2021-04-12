package com.transfers;

import com.entities.Category;
import lombok.Data;

@Data
public class CategoryTransfer {

    private String name;

    public CategoryTransfer(Category category) {
       this.name = category.getName();
    }
}
