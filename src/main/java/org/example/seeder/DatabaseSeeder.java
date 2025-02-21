package org.example.seeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.entites.CategoryEntity;
import org.example.entites.ProductEntity;
import org.example.entites.ProductImageEntity;
import org.example.repository.ICategoryRepository;
import org.example.repository.IProductImageRepository;
import org.example.repository.IProductRepository;
import org.example.service.FileService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;
    private final IProductImageRepository productImageRepository;
    private final FileService fileService;

    @PostConstruct
    public void seed() {
        if (categoryRepository.count() == 0) {

            var electronics = fileService.load("https://laluna.com.ua/image/cache/catalog/easyphoto/2811202310_photo_2023-11-28_14-27-25-crop-720x1080.jpg");
            var clothing = fileService.load("https://tse1.mm.bing.net/th?id=OIP.7tGu3cNzCxDnOnER2ysmRQHaE8&w=316&h=316&c=7");
            var books = fileService.load("https://tse4.mm.bing.net/th?id=OIP.PJ0PyRXOXD-2gqlfs4RG-wHaHa&w=474&h=474&c=7");
            var home_kitchen = fileService.load("https://tse4.mm.bing.net/th?id=OIP.6IDTDVYInw1c0qXj6Gb2pwHaE8&w=316&h=316&c=7");
            var toys = fileService.load("https://tse2.mm.bing.net/th?id=OIP.OYSdNG2zvrhP7Mn8DwoGMAHaHa&w=474&h=474&c=7");

            List<CategoryEntity> categories = List.of(
                    createCategory("Electronics", electronics, "Devices and gadgets"),
                    createCategory("Clothing", clothing, "Apparel for men and women"),
                    createCategory("Books", books, "Various genres of books"),
                    createCategory("Home & Kitchen", home_kitchen, "Household essentials"),
                    createCategory("Toys", toys, "Toys and games for kids")
            );
            categoryRepository.saveAll(categories);
        }

        if (productRepository.count() == 0) {
            seedProducts();
        }
    }

    private CategoryEntity createCategory(String name, String image, String description) {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setImage(image);
        category.setDescription(description);
        category.setCreationTime(LocalDateTime.now());
        return category;
    }

    private void seedProducts() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        if (categories.isEmpty()) return;

        ProductEntity laptop = createProduct("Laptop", "High-performance laptop", 1200.0, categories.get(0));
        ProductEntity shirt = createProduct("T-Shirt", "Cotton t-shirt", 20.0, categories.get(1));
        ProductEntity novel = createProduct("Fantasy Novel", "Bestselling fantasy novel", 15.0, categories.get(2));

        productRepository.saveAll(List.of(laptop, shirt, novel));

        seedProductImages(laptop, List.of("laptop1.jpg", "laptop2.jpg"));
        seedProductImages(shirt, List.of("shirt1.jpg", "shirt2.jpg"));
        seedProductImages(novel, List.of("novel1.jpg"));
    }

    private ProductEntity createProduct(String name, String description, Double price, CategoryEntity category) {
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setCreationTime(LocalDateTime.now());
        return product;
    }

    private void seedProductImages(ProductEntity product, List<String> imageUrls) {
        List<ProductImageEntity> images = imageUrls.stream()
                .map(url -> createProductImage(url, product))
                .toList();
        productImageRepository.saveAll(images);
    }

    private ProductImageEntity createProductImage(String imageUrl, ProductEntity product) {
        ProductImageEntity image = new ProductImageEntity();
        image.setImageUrl(imageUrl);
        image.setPriority(1);
        image.setProduct(product);
        return image;
    }


}