package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.product.ProductEditDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductCreateDTO;
import org.example.entites.CategoryEntity;
import org.example.entites.ProductEntity;
import org.example.entites.ProductImageEntity;
import org.example.mapper.IProductMapper;
import org.example.repository.IProductImageRepository;
import org.example.repository.IProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private IProductRepository productRepository;
    private FileService fileService;
    private IProductImageRepository productImageRepository;
    private IProductMapper productMapper;

    public List<ProductItemDTO> getAllProducts() {
        var list = productRepository.findAll();
        return productMapper.toDto(list);
    }

    public ProductItemDTO getProductById(Integer id) {
        return productMapper.toDto(productRepository.findById(id).orElse(null));
    }

    public ProductEntity createProduct(ProductCreateDTO product) {
        var entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setCreationTime(LocalDateTime.now());
        var cat = new CategoryEntity();
        cat.setId(product.getCategoryId());
        entity.setCategory(cat);

        productRepository.save(entity);

        int priority = 1;
        for (var img : product.getImages()) {
            var imageName = fileService.load(img);
            var img1 = new ProductImageEntity();
            img1.setPriority(priority);
            img1.setName(imageName);
            img1.setProduct(entity);
            productImageRepository.save(img1);
            priority++;
        }
        return entity;
    }

    public ProductItemDTO edit(ProductEditDTO model) {
        var p = productRepository.findById(model.getId());

        if (p.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        try {
            var product = p.get();
            var imagesDb = product.getImages();

            // Видаляємо фото, які відсутні в новому списку
            for (var image : imagesDb) {
                if (model.getImages().stream().noneMatch(img -> img.getName().equals(image.getName()))) {
                    fileService.remove(image.getName());
                    productImageRepository.delete(image);
                }
            }

            // Оновлення даних продукту
            var cat = new CategoryEntity();
            cat.setId(model.getCategoryId());
            product.setName(model.getName());
            product.setDescription(model.getDescription());
            product.setPrice(model.getPrice());
            product.setCategory(cat);
            productRepository.save(product);

            // Оновлюємо або додаємо нові фото
            int priority = 1;
            for (var imgDto : model.getImages()) {
                var existingImage = imagesDb.stream()
                        .filter(img -> img.getName().equals(imgDto.getName()))
                        .findFirst();
                if (existingImage.isPresent()) {
                    existingImage.get().setPriority(priority);
                } else {
                    // Завантажуємо нове фото
                    var fileName = fileService.load(imgDto.getName()); // Очікується URL або файл
                    var newImage = new ProductImageEntity();
                    newImage.setName(fileName);
                    newImage.setPriority(priority);
                    newImage.setProduct(product);
                    productImageRepository.save(newImage);
                }
                priority++;
            }
            return productMapper.toDto(product);

        } catch (Exception ex) {
            System.out.println("Edit product is problem: " + ex.getMessage());
            return null;
        }
    }


    public boolean deleteProduct(Integer id) {
        var res = productRepository.findById(id);
        if (res.isEmpty()) {
            return false;
        }

        var product = res.get();
        var images = product.getImages();

        // Видалення всіх зображень продукту, якщо вони є
        if (images != null && !images.isEmpty()) {
            for (var image : images) {
                fileService.remove(image.getName());
            }
            productImageRepository.deleteAll(images);
        }

        // Видалення самого продукту
        productRepository.delete(product);

        return true;
    }

}