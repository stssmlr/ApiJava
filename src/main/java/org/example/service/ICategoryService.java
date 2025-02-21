package org.example.service;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;


import java.util.List;

public interface ICategoryService {

    List<CategoryItemDTO> getList();
    CategoryItemDTO getCategoryById(Integer categoryId);
    CategoryItemDTO create(CategoryCreateDTO model);
    CategoryItemDTO edit(CategoryEditDTO model);
    void delete(Integer categoryId);
}