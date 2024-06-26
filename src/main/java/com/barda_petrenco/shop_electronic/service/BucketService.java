package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.domain.Bucket;
import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.BucketDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



public interface BucketService {

    @Operation(summary = "Creating a bucket", description = "Creates a cart for the user with the specified products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bucket created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Bucket.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    Bucket createBucket(User user, List<Long> productIds);

    @Operation(summary = "Adding products to bucket", description = "Adds the specified products to the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products added successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Bucket not found",
                    content = @Content)
    })
    void addProducts(Bucket bucket, List<Long> productIds);

    @Transactional
    @Operation(summary = "Removing products from the bucket", description = "Removes the specified products from the bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products removed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Bucket or products not found",
                    content = @Content)
    })
    void removeProducts(Bucket bucket, List<Long> productIds);

    @Operation(summary = "Get bucket by user", description = "Retrieves the bucket associated with the given user name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bucket retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BucketDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User or bucket not found",
                    content = @Content)
    })
    BucketDTO getBucketByUser(String name);

    @Operation(summary = "Remove product from user's bucket", description = "Removes the specified product from the user's bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product removed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product or bucket not found",
                    content = @Content)
    })
    void removeFromUserBucket(Long productId, String username);
}
