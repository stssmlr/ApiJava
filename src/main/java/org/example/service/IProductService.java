package org.example.service;

import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductEditDTO;
import org.example.dto.product.ProductItemDTO;

import java.util.List;

public interface IProductService {
    List<ProductItemDTO> getList();
    ProductItemDTO getById(Integer productId);
    ProductItemDTO create(ProductCreateDTO dto);
    ProductItemDTO edit(Integer id, ProductEditDTO dto);
    void delete(Integer productId);
}
