package com.ecommerce.project.controller.general;

import com.ecommerce.project.api.response.model.ProductResponse;
import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/public/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products/byKeyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY_PRODUCT_ID, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_ORDER_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(productService.getProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder), HttpStatusCode.valueOf(HttpStatus.FOUND.value()));
    }

    @GetMapping("products/all")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY_PRODUCT_ID, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_ORDER_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping("categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getCategoryWiseAllProducts(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BY_PRODUCT_ID, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_ORDER_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(productService.getCategoryWiseAllProducts(categoryId, pageNumber, pageSize, sortBy, sortOrder), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }


}
