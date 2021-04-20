package com.controllers;

import com.detailsrequestmodels.CategoryDetailsRequestModel;
import com.entities.Category;
import com.services.CategoryService;
import com.transfers.CategoryTransfer;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j
@RequestMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    @ResponseBody
    public List<CategoryTransfer> findCategory() {
        List<CategoryTransfer> categoryTransfers = new ArrayList<>();
        for (Category c : categoryService.findAllCategory()) {
            CategoryTransfer categoryTransferTmp = new CategoryTransfer(c);
            categoryTransfers.add(categoryTransferTmp);
        }
        return categoryTransfers;
    }

    @PostMapping()
    public void addCategory(@RequestBody CategoryDetailsRequestModel categoryDRM) {
        Category category = new Category();
        category.setName(categoryDRM.getName());
        categoryService.saveCategory(category);
        log.info("Add category");
    }
}
