package org.example.controller;

import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductEditDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Отримати всі продукти
    @GetMapping
    public List<ProductItemDTO> getAllProducts() {
        return productService.getList();
    }

    // Отримати продукт за id
    @GetMapping("/{id}")
    public ProductItemDTO getById(@PathVariable int id) {
        return productService.getById(id);
    }

    // Створити новий продукт
    @PostMapping
    public ProductItemDTO create(@RequestBody ProductCreateDTO dto) {
        return productService.create(dto);
    }

    // Оновити продукт
    @PutMapping("/{id}")
    public ProductItemDTO edit(@PathVariable int id, @RequestBody ProductEditDTO dto) {
        dto.setId(id);
        return productService.edit(dto);
    }

    // Видалити продукт
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        productService.delete(id);
    }
}
