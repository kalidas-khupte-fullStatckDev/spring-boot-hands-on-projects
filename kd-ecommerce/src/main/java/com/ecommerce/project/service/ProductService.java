package com.ecommerce.project.service;

import com.ecommerce.project.api.response.model.ProductResponse;
import com.ecommerce.project.dtos.ProductDTO;
import com.ecommerce.project.model.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts();

    ProductResponse getCategoryWiseAllProducts(Long categoryId);

    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct( Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);


    ProductDTO updateProductImage(Long productId, MultipartFile image);
}
