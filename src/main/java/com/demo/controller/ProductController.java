package com.demo.controller;

import com.demo.model.ProductDTO;
import com.demo.service.CacheService;
import com.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final CacheService cacheService;

    @Autowired
    public ProductController(ProductService productService, CacheService cacheService) {
        this.productService = productService;
        this.cacheService = cacheService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody ProductDTO productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductDTO getProduct(@PathVariable long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ProductDTO updateProduct(@RequestBody ProductDTO productDto) {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
    }

    @DeleteMapping("/clear")
    public void clearCache() {
        cacheService.clearCache();
    }
}
