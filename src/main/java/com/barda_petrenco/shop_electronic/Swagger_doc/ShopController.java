package com.barda_petrenco.shop_electronic.Swagger_doc;
import com.barda_petrenco.shop_electronic.dto.ProductDTO;
import com.barda_petrenco.shop_electronic.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Shop Controller", description = "Controller for store management")
public class ShopController {
    private final ProductService productService;

    @Autowired
    public ShopController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/products")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get product list", description = "This method returns a list of all available products")
    public List<ProductDTO> getProducts() {
        return productService.getAll();
    }
}
