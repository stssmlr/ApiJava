package org.example.dto.product;

import lombok.Data;

@Data
public class ProductEditDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer categoryId;
}
