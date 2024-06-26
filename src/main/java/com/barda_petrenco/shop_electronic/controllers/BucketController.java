package com.barda_petrenco.shop_electronic.controllers;

import com.barda_petrenco.shop_electronic.dto.BucketDTO;
import com.barda_petrenco.shop_electronic.service.BucketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Tag(name = "Bucket Controller", description = "Controller for handling bucket operations")
public class BucketController {
    private final BucketService bucketService;
    private final Logger logger = LoggerFactory.getLogger(BucketController.class);

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @Operation(summary = "Get bucket details", description = "Fetches the bucket details for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched bucket",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BucketDTO.class))),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Bucket not found for the user")
    })
    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            logger.debug("Fetching bucket for user: {}", username);
            BucketDTO bucketDTO = bucketService.getBucketByUser(username);
            if (bucketDTO != null) {
                model.addAttribute("bucket", bucketDTO);
            } else {
                model.addAttribute("bucket", new BucketDTO());
                logger.debug("Bucket is empty for user: {}", username);
            }
        } else {
            model.addAttribute("bucket", new BucketDTO());
            logger.debug("No authenticated user found.");
        }
        return "bucket";
    }

    @Operation(summary = "Remove product from bucket", description = "Removes a product from the authenticated user's bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed product from bucket"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Product not found in the user's bucket")
    })
    @PostMapping("/bucket/remove/{productId}")
    public String removeProductFromBucket(@PathVariable Long productId, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            String username = principal.getName();
            bucketService.removeFromUserBucket(productId, username);
            logger.debug("Removed product {} from user {}'s bucket", productId, username);
            redirectAttributes.addFlashAttribute("message", "Product removed successfully.");
        } else {
            logger.warn("Unauthenticated user tried to remove product {}", productId);
            redirectAttributes.addFlashAttribute("error", "You must be logged in to remove products from the bucket.");
        }
        return "redirect:/bucket";
    }
}
