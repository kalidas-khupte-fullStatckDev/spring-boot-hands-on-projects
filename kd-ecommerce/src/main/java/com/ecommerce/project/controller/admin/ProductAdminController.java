package com.ecommerce.project.controller.admin;

import com.ecommerce.project.dtos.ProductDTO;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/admin/products/")
public class ProductAdminController {

    @Autowired
    private ProductService productService;

    @PostMapping("category/{categoryId}/add")
    public ResponseEntity<ProductDTO> addProduct( @PathVariable Long categoryId, @RequestBody @Valid ProductDTO productDTO) {
        return new ResponseEntity<>(productService.addProduct(categoryId, productDTO), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @PutMapping("update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDTO product) {
        return new ResponseEntity<>(productService.updateProduct(productId, product), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PutMapping("update/image/{productId}")
    public ResponseEntity<Object> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) {
        try {
            return new ResponseEntity<>(productService.updateProductImage(productId, image), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
