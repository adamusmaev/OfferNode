package com.services;

import com.entities.Category;
import com.repo.CategoryRepo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Iterable<Category> findAllCategory() {
        return categoryRepo.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepo.save(category);
    }

    public void deleteCategory(Category category) {
        Category categoryTmp = categoryRepo.findById(category.getId()).orElse(null);
        if (categoryTmp == null) log.error("Category is not in the database");
        else categoryRepo.delete(category);
    }

    public Category findCategoryById(Integer id) {
        return categoryRepo.findById(id).orElse(null);
    }
}
