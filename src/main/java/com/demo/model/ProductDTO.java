package com.demo.model;

import java.math.BigDecimal;

public record ProductDTO(long id, String name, BigDecimal price) {
}
