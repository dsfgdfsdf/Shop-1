package com.barda_petrenco.shop_electronic.controllers;

import com.barda_petrenco.shop_electronic.dto.ProductDTO;
import com.barda_petrenco.shop_electronic.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Controller for handling product operations")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get product list", description = "Fetches the list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched product list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public String list(Model model) {
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }

    @Operation(summary = "Add product to bucket", description = "Adds a product to the authenticated user's bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added product to bucket"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal != null) {
            productService.addToUserBucket(id, principal.getName());
        }
        return "redirect:/products";
    }
}
