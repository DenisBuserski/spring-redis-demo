package com.demo.service;

import com.demo.config.RedisCacheConfig;
import com.demo.model.Product;
import com.demo.model.ProductDTO;
import com.demo.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final RedisCacheConfig redisCacheConfig;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, RedisCacheConfig redisCacheConfig) {
        this.productRepository = productRepository;
        this.redisCacheConfig = redisCacheConfig;
    }

    @PostConstruct
    public void init() {
        System.out.println("Injected Cache Name: " + redisCacheConfig.getProductCache());
    }

    @CachePut(value = "#{redisCacheConfig.getProductCache()}", key = "#result.id()")
    public ProductDTO createProduct(ProductDTO productDto) {
        Product product = Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .build();

        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice());
    }

    @Cacheable(value = "#{redisCacheConfig.getProductCache()}", key = "#productId")
    public ProductDTO getProduct(long productId) {
        log.info("Get product by id: {} from the DB", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Cannot find product with id " + productId));
        return new ProductDTO(product.getId(), product.getName(), product.getPrice());
    }


    @CachePut(value = "#{redisCacheConfig.getProductCache()}", key = "#result.id()")
    public ProductDTO updateProduct(ProductDTO productDto) {
        Product product = productRepository.findById(productDto.id()).orElseThrow(() -> new IllegalArgumentException("Cannot find product with id " + productDto.id()));
        product.setName(productDto.name());
        product.setPrice(productDto.price());

        Product updatedProduct = productRepository.save(product);
        return new ProductDTO(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getPrice());
    }

    @CacheEvict(value = "#{redisCacheConfig.getProductCache()}", key = "#productId")
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }
}
