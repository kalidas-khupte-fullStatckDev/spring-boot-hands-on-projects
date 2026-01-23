package com.ecommerce.project.controller.general;

import com.ecommerce.project.api.response.model.CategoryResponse;
import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/public/")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("categories/view")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_CATEGORY_ID, required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstant.SORT_ORDER_DIR, required = false) String sortOrder){
        CategoryResponse fetchedCategoriesRes = categoryService.getCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(fetchedCategoriesRes, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
