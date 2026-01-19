package com.ecommerce.project.controller.general;

import com.ecommerce.project.api.response.model.ProductResponse;
import com.ecommerce.project.model.Product;
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
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword) {
        return new ResponseEntity<>(productService.getProductsByKeyword(keyword), HttpStatusCode.valueOf(HttpStatus.FOUND.value()));
    }

    @GetMapping("products/all")
    public ResponseEntity<ProductResponse> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping("categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getCategoryWiseAllProducts(@PathVariable Long categoryId) {
        return new ResponseEntity<>(productService.getCategoryWiseAllProducts(categoryId), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }


}
