package org.example.mapper;

import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryCreateDTO;
import org.example.entites.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {
    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryCreateDTO(CategoryCreateDTO dto);

    @Mapping(target = "dateCreated", source = "creationTime", dateFormat = "dd.MM.yyyy HH:mm:ss")
    CategoryItemDTO categoryItemDTO(CategoryEntity category);

    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryEditDto(CategoryEditDTO dto);

    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryEntityByCategoryCreateDTO(CategoryCreateDTO category);

    List<CategoryItemDTO> toDto(List<CategoryEntity> categories);
}