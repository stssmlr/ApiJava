package org.example.dto.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductEditDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer categoryId;
    private List<MultipartFile> images;
}
