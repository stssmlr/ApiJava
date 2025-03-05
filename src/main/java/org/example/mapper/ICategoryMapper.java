package org.example.mapper;

import org.example.dto.category.CategoryItemDTO;
import org.example.entites.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {
    @Mapping(source = "creationTime", target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CategoryItemDTO toDto(CategoryEntity category);

    List<CategoryItemDTO> toDto(List<CategoryEntity> categories);
}
