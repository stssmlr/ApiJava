package org.example.service;

import lombok.AllArgsConstructor;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.entites.CategoryEntity;
import org.example.mapper.ICategoryMapper;
import org.example.repository.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;
    private final FileService fileService;

    public List<CategoryItemDTO> getList() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    @Override
    public CategoryItemDTO getCategoryById(Integer categoryId) {
        var entity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.categoryItemDTO(entity);
    }

    @Override
    public CategoryItemDTO create(CategoryCreateDTO model) {
        // Map DTO to Entity
        CategoryEntity entity = categoryMapper.categoryEntityByCategoryCreateDTO(model);
        entity.setCreationTime(LocalDateTime.now());

        // Handle the image file if present
        if (model.getImageFile() != null ) {
            String imageFileName = fileService.load(model.getImageFile());
            if (!imageFileName.isEmpty()) {
                entity.setImage(imageFileName);
            }
        }

        // Save the entity
        categoryRepository.save(entity);

        // Map back to DTO and return
        return categoryMapper.categoryItemDTO(entity);
    }

    @Override
    public CategoryItemDTO edit(CategoryEditDTO dto) {
        // Find existing category by ID
        CategoryEntity entity = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update fields if non-null and non-blank
        if (dto.getName() != null && !dto.getName().isBlank()) {
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            entity.setDescription(dto.getDescription());
        }

        // Handle image replacement if new image is provided
        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            String newImageFileName = fileService.replace(entity.getImage(), dto.getImageFile());
            entity.setImage(newImageFileName);
        }


        // Save updated category entity
        categoryRepository.save(entity);

        // Return updated category as DTO
        return categoryMapper.categoryItemDTO(entity);
    }

    public void delete(Integer categoryId) {
        CategoryEntity entity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(entity);
    }
}