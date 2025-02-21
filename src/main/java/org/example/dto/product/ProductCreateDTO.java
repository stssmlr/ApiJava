package org.example.dto.product;

import lombok.Data;

@Data
public class ProductCreateDTO {
    private String name;
    private String description;
    private Double price;
    private Integer categoryId;  // ID категорії
}
