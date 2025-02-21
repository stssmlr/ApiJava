package org.example.dto.product;

import lombok.Data;

@Data
public class ProductItemDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String categoryName; // Назва категорії
    private String imageUrl;     // URL зображення
}
