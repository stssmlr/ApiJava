package org.example.controller;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryItemDTO> getAllCategories() {
        return categoryService.getList();
    }

    // Створити нову категорію
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> create(@ModelAttribute CategoryCreateDTO model) {
        var result = categoryService.create(model);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Оновити категорію (з можливістю зміни зображення)
    @PutMapping("/{id}")
    public CategoryItemDTO edit(@PathVariable int id,
                                @RequestBody CategoryEditDTO dto,
                                @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        // Встановлюємо id категорії для редагування
        dto.setId(id);
        return categoryService.edit(dto);
    }

    // Отримати категорію за її id
    @GetMapping("/{id}")
    public CategoryItemDTO getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        categoryService.delete(id);
    }
}