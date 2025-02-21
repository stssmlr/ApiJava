package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductEditDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.entites.ProductEntity;
import org.example.entites.CategoryEntity;
import org.example.mapper.IProductMapper;
import org.example.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IProductMapper productMapper;
    private final CategoryService categoryService; // для отримання категорії за id

    @Override
    public List<ProductItemDTO> getList() {
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    public ProductItemDTO getById(Integer productId) {
        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(entity);
    }

    @Override
    public ProductItemDTO create(ProductCreateDTO model) {
        var p = new ProductEntity();
        var cat = new CategoryEntity();
        cat.setId(model.getCategoryId());
        p.setName(model.getName());
        p.setDescription(model.getDescription());
        p.setPrice(model.getPrice());
        p.setCreationTime(LocalDateTime.now());
        p.setCategory(cat);
        productRepository.save(p);
        return null;
    }


    @Override
    public ProductItemDTO edit(ProductEditDTO dto) {
        ProductEntity entity = productRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        productRepository.save(entity);
        return productMapper.toDto(entity);
    }

    @Override
    public void delete(Integer productId) {
        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(entity);
    }
}
