package com.ecommerce.project.dtos;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;

    @NotEmpty(message ="Product Name must not be Empty" )
    @Size(min = 3, message = "Product Name must be at-least of 3 character length")
    private String productName;

    @Size(min = 10, message = "Product description must be at-least of 10 character length")
    private String description;

    private String image;
    private Integer quantity;

    private double price;
    private double specialPrice;
    private double discount;
}
