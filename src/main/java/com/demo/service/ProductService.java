package com.demo.service;

import com.demo.model.ProductDTO;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDto);

    ProductDTO getProduct(long productId);

    ProductDTO updateProduct(ProductDTO productDto);

    void deleteProduct(long productId);
}
