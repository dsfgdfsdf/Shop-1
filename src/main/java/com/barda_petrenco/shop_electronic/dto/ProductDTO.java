package com.barda_petrenco.shop_electronic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private BigDecimal price;
}
