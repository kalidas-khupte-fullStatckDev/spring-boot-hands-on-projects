package com.ecommerce.project.controller.admin;

import com.ecommerce.project.dtos.ProductDTO;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//http://localhost:8080/api/admin/products/category/1/add
@RestController
@RequestMapping("api/admin/products/")
public class ProductAdminController {

    @Autowired
    private ProductService productService;

    @PostMapping("category/{categoryId}/add")
    public ResponseEntity<ProductDTO> addProduct( @PathVariable Long categoryId, @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.addProduct(categoryId, productDTO), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @PutMapping("update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO product) {
        return new ResponseEntity<>(productService.updateProduct(productId, product), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PutMapping("update/image/{productId}")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) {
        return new ResponseEntity<>(productService.updateProductImage(productId, image), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @DeleteMapping("delete/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
