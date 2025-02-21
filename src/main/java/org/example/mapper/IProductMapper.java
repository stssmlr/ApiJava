package org.example.mapper;

import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductEditDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.entites.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    ProductItemDTO toDto(ProductEntity entity);
    ProductEntity fromCreateDto(ProductCreateDTO dto);
    ProductEntity fromEditDto(ProductEditDTO dto);
    List<ProductItemDTO> toDto(List<ProductEntity> entities);
}
