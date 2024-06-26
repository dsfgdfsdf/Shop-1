# Comments on AuthProviderConfig.java

## Imported libraries and classes
```java
package com.barda_petrenco.shop_electronic.config;

// Import UserService from the service package
import com.barda_petrenco.shop_electronic.service.UserService;

// Import annotations and classes from Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

# Comments on SecurityConfig.java

## Imported libraries and classes
```java
package com.barda_petrenco.shop_electronic.config;

// Import Role from the domain package
import com.barda_petrenco.shop_electronic.domain.Role;

// Import annotations and classes from Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
==============================================================================================================================================================================================
# Comments on BucketController.java

## Imported libraries and classes
```java
package com.barda_petrenco.shop_electronic.controllers;

// Import DTO and service for working with the cart
import com.barda_petrenco.shop_electronic.dto.BucketDTO;
import com.barda_petrenco.shop_electronic.service.BucketService;

// Import annotations from Swagger to document the API
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// Import logger from SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Import annotations and classes from Spring Framework
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
===============================================================================================================================================================================================

/**
 * ErrorControllerAdvice.java
 *
 * This class serves as a global controller advice to handle exceptions across all controllers.
 */

package com.barda_petrenco.shop_electronic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global controller advice to handle exceptions across all controllers.
 */
@ControllerAdvice
public class ErrorControllerAdvice {

    /**
     * Exception handler method to handle all exceptions of type Exception.
     * Returns the name of the error view with the error message.
     *
     * @param exception The exception thrown
     * @param model     The Spring MVC model to add attributes
     * @return The name of the error view template
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception exception, Model model) {
        // Get the error message from the exception, or set a default message if null
        String errorMessage = (exception != null) ? exception.getMessage() : "Unknown Error";
        // Add the error message attribute to the model for the error view to display
        model.addAttribute("errorMessage", errorMessage);
        // Return the name of the error view template to display the error message
        return "error";
    }
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller class for serving images stored in a directory.
 */
@Controller
@Tag(name = "Image Controller", description = "Controller for serving images")
public class ImageController {

    // Directory path where images are stored
    private final Path imagePath = Paths.get("src/main/resources/static/images");

    /**
     * Retrieves and serves an image file by its filename.
     *
     * @param filename The name of the image file to serve
     * @return ResponseEntity containing the image file as a Resource
     * @throws RuntimeException if the file cannot be read or served
     */
    @Operation(summary = "Serve image", description = "Serves an image file by its filename")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully served image"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Path file = imagePath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                // Serve the image file with appropriate headers
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // Throw an exception if the file cannot be read
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (Exception e) {
            // Throw a runtime exception if there's an error serving the file
            throw new RuntimeException("Could not serve file: " + filename, e);
        }
    }
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for handling main application routes.
 */
@Controller
public class MainController {

    /**
     * Handles requests to the root and "/" paths, directing to the index view.
     *
     * @return The name of the index view template
     */
    @RequestMapping({"", "/"})
    public String index() {
        return "index";
    }

    /**
     * Handles requests to the "/login" path, directing to the login view.
     *
     * @return The name of the login view template
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles requests to the "/login-error" path, directing to the login view with a login error attribute.
     *
     * @param model The Spring MVC model to add attributes
     * @return The name of the login view template
     */
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        // Add loginError attribute to the model to indicate login error
        model.addAttribute("loginError", true);
        return "login";
    }
}
===============================================================================================================================================================================================
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

/**
 * Controller class for handling operations related to products.
 */
@Controller
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Controller for handling product operations")
public class ProductController {
    
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves and displays the list of all products.
     *
     * @param model The Spring MVC model to add attributes
     * @return The name of the products view template
     */
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

    /**
     * Adds a product to the authenticated user's bucket.
     *
     * @param id         The ID of the product to add to the bucket
     * @param principal  The authenticated user's principal object
     * @return Redirects to the product list view after adding the product to the bucket
     */
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
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling speaker-related pages.
 */
@Controller
public class SpeakerController {

    /**
     * Displays the speaker page.
     *
     * @return The name of the speaker view template
     */
    @GetMapping("/speaker")
    public String showSpeaker1Page() {
        return "speaker";
    }

    /**
     * Displays the speaker1 page.
     *
     * @return The name of the speaker1 view template
     */
    @GetMapping("/speaker1")
    public String showSpeaker2Page() {
        return "speaker1";
    }

    /**
     * Displays the speaker3 page.
     *
     * @return The name of the speaker3 view template
     */
    @GetMapping("/speaker3")
    public String showSpeaker3Page() {
        return "speaker3";
    }
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.controllers;

import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.UserDTO;
import com.barda_petrenco.shop_electronic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;

/**
 * Controller class for handling user-related operations.
 */
@Controller
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Controller for handling user operations")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves and displays the list of all users.
     *
     * @param model The Spring MVC model to add attributes
     * @return The name of the userList view template
     */
    @Operation(summary = "Get user list", description = "Fetches the list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched user list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "userList";
    }

    /**
     * Displays the form for creating a new user.
     *
     * @param model The Spring MVC model to add attributes
     * @return The name of the user view template for creating a new user
     */
    @Operation(summary = "Create new user", description = "Displays form to create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully displayed new user form",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user";
    }

    /**
     * Handles saving a new user.
     *
     * @param dto    The UserDTO object containing user data from the form
     * @param result The Spring MVC BindingResult to check for validation errors
     * @param model  The Spring MVC model to add attributes
     * @return Redirects to the user list view if user is successfully saved, otherwise returns to the user view for correction
     */
    @Operation(summary = "Save new user", description = "Saves a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved new user"),
            @ApiResponse(responseCode = "400", description = "Validation errors occurred")
    })
    @PostMapping("/new")
    public String saveUser(UserDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", dto);
            return "user";
        }
        if (userService.save(dto)) {
            return "redirect:/users";
        } else {
            model.addAttribute("user", dto);
            return "user";
        }
    }

    /**
     * Retrieves and displays the profile of the authenticated user.
     *
     * @param model     The Spring MVC model to add attributes
     * @param principal The authenticated user's principal object
     * @return The name of the profile view template
     * @throws RuntimeException if the user is not authenticated or not found
     */
    @Operation(summary = "Get user profile", description = "Fetches the profile of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched user profile",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You aren't authorized");
        }
        User user = userService.findByName(principal.getName());
        if (user == null){
            throw new RuntimeException("User not found");
        }
        UserDTO dto = UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", dto);
        return "profile";
    }

    /**
     * Handles updating the profile of the authenticated user.
     *
     * @param dto       The UserDTO object containing updated user data from the form
     * @param result    The Spring MVC BindingResult to check for validation errors
     * @param model     The Spring MVC model to add attributes
     * @param principal The authenticated user's principal object
     * @return Redirects to the profile view if the user profile is successfully updated, otherwise returns to the profile view for correction
     * @throws RuntimeException if the user is not authenticated or not authorized to update the profile
     */
    @Operation(summary = "Update user profile", description = "Updates the profile of the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user profile"),
            @ApiResponse(responseCode = "400", description = "Validation errors occurred"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto, BindingResult result, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), dto.getUsername())) {
            throw new RuntimeException("You aren't authorized");
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()
                && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())) {
            result.rejectValue("password", "error.user", "Passwords do not match");
            model.addAttribute("user", dto);
            return "profile";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", dto);
            return "profile";
        }
        userService.updateUserProfile(dto);
        return "redirect:/users/profile";
    }
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.dao;

import com.barda_petrenco.shop_electronic.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Product entities.
 * Extends JpaRepository which provides basic CRUD operations for Product entities.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.dao;

import com.barda_petrenco.shop_electronic.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Bucket entities.
 * Extends JpaRepository which provides basic CRUD operations for Bucket entities.
 */
public interface BucketRepository extends JpaRepository<Bucket, Long> {
}

===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.dao;

import com.barda_petrenco.shop_electronic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for User entities.
 * Extends JpaRepository which provides basic CRUD operations for User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves the first user with the given name.
     *
     * @param name The name of the user to find
     * @return The User entity if found, null otherwise
     */
    User findFirstByName(String name);
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.dto;

import com.barda_petrenco.shop_electronic.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for displaying details of products in a bucket.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetailDTO {
 private String title; // The product's name
 private Long productId; // Product ID
 private BigDecimal price; // Product price
 private BigDecimal amount; // Product quantity
 private Double sum; // Sum of product cost

 /**
 * Constructor to create BucketDetailDTO from a Product object.
 *
 * @param product The Product object to create DTO from
 */
 public BucketDetailDTO(Product product) {
 this.title = product.getTitle(); // Set the product name
 this.productId = product.getId(); // Set the product ID
 this.price = product.getPrice(); // Set the price of the product
 this.amount = new BigDecimal(1.0); // Set the quantity of the product (here the default is 1.0)
 this.sum = Double.valueOf(product.getPrice().toString()); // Calculate the amount of product cost
 }
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.dto;

import com.barda_petrenco.shop_electronic.domain.Bucket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for representing a bucket with its details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDTO {
 private int amountProducts; // Number of products in the cart
 private Double sum; // Total cost of products in the cart
 private List<BucketDetailDTO> bucketDetails; // Details of the products in the cart

 /**
 * Constructor for creating a BucketDTO.
 *
 * @param amountProducts The number of products in the bucket
 * @param sum The total sum of product prices in the bucket
 * @param bucketDetails List of product details in the bucket
 */
 public BucketDTO(int amountProducts, Double sum, List<BucketDetailDTO> bucketDetails) {
 this.amountProducts = amountProducts;
 this.sum = sum;
 this.bucketDetails = bucketDetails;
 }

 /**
 * Builder class for BucketDTO.
 */
 public static class BucketDTOBuilder {
 private List<BucketDetailDTO> bucketDetails = new ArrayList<>(); // List of details of products in cart

 /**
 * Adds a product detail to the bucket.
 *
 * @param bucketDetailDTO The product detail to add
 * @return The builder instance
 */
 public BucketDTOBuilder bucketDetail(BucketDetailDTO bucketDetailDTO) {
 this.bucketDetails.add(bucketDetailDTO);
 return this;
 }

 /**
 * Builds the BucketDTO object.
 *
 * @return The constructed BucketDTO
 */
 public BucketDTO build() {
 return new BucketDTO(bucketDetails.size(), calculateSum(), bucketDetails);
 }

 /**
 * Calculates the total sum of product prices in the bucket.
 *
 * @return The total sum of product prices
 */
 private Double calculateSum() {
 return bucketDetails.stream()
 .map(BucketDetailDTO::getSum)
 .mapToDouble(Double::doubleValue)
 .sum();
 }
 }

 /**
 * Aggregates the amount of products and the total sum of prices in the bucket.
 */
 public void aggregate() {
 this.amountProducts = bucketDetails.size();
 this.sum = bucketDetails.stream()
 .map(BucketDetailDTO::getSum)
 .mapToDouble(Double::doubleValue)
 .sum();
 }
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for representing product data.
 */
@Data
@NoArgsConstructor
public class ProductDTO {
 private Long id; // Product ID
 private String title; // The product's name
 private BigDecimal price; // Product price
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing user data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
 private String username; // Username
 private String password; // User password
 private String matchingPassword; // Confirm user password
 private String email; // User email
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.mapper;

import com.barda_petrenco.shop_electronic.domain.Product;
import com.barda_petrenco.shop_electronic.dto.ProductDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting between Product and ProductDTO objects.
 */
@Mapper
public interface ProductMapper {

 ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

 /**
 * Converts Product entity to ProductDTO.
 *
 * @param product Product entity to convert
 * @return ProductDTO object
 */
 @InheritInverseConfiguration
 ProductDTO fromProduct(Product product);

 /**
 * Converts a list of ProductDTO objects to a list of Product entities.
 *
 * @param productDTOS List of ProductDTO objects
 * @return List of Product entities
 */
 List<Product> toProductList(List<ProductDTO> productDTOS);

 /**
 * Converts a list of Product entities to a list of ProductDTO objects.
 *
 * @param products List of Product entities
 * @return List of ProductDTO objects
 */
 List<ProductDTO> fromProductList(List<Product> products);

}
===============================================================================================================================================================================================
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

/**
 * Service interface for managing shopping buckets.
 */
public interface BucketService {

    /**
     * Creates a new shopping bucket for the user with the specified products.
     *
     * @param user       User entity representing the owner of the bucket
     * @param productIds List of product IDs to add to the bucket
     * @return Bucket entity representing the created bucket
     */
    @Operation(summary = "Creating a bucket", description = "Creates a cart for the user with the specified products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bucket created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Bucket.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    Bucket createBucket(User user, List<Long> productIds);

    /**
     * Adds the specified products to the existing bucket.
     *
     * @param bucket     Bucket entity to add products to
     * @param productIds List of product IDs to add to the bucket
     */
    @Operation(summary = "Adding products to bucket", description = "Adds the specified products to the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products added successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Bucket not found",
                    content = @Content)
    })
    void addProducts(Bucket bucket, List<Long> productIds);

    /**
     * Removes the specified products from the bucket.
     *
     * @param bucket     Bucket entity to remove products from
     * @param productIds List of product IDs to remove from the bucket
     */
    @Transactional
    @Operation(summary = "Removing products from the bucket", description = "Removes the specified products from the bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products removed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Bucket or products not found",
                    content = @Content)
    })
    void removeProducts(Bucket bucket, List<Long> productIds);

    /**
     * Retrieves the bucket associated with the given username.
     *
     * @param name Username of the user whose bucket is to be retrieved
     * @return BucketDTO representing the user's bucket details
     */
    @Operation(summary = "Get bucket by user", description = "Retrieves the bucket associated with the given user name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bucket retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BucketDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User or bucket not found",
                    content = @Content)
    })
    BucketDTO getBucketByUser(String name);

    /**
     * Removes the specified product from the user's bucket.
     *
     * @param productId ID of the product to be removed from the bucket
     * @param username  Username of the user whose bucket is to be updated
     */
    @Operation(summary = "Remove product from user's bucket", description = "Removes the specified product from the user's bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product removed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product or bucket not found",
                    content = @Content)
    })
    void removeFromUserBucket(Long productId, String username);
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.dao.BucketRepository;
import com.barda_petrenco.shop_electronic.dao.ProductRepository;
import com.barda_petrenco.shop_electronic.dao.UserRepository;
import com.barda_petrenco.shop_electronic.domain.Bucket;
import com.barda_petrenco.shop_electronic.domain.Product;
import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.BucketDTO;
import com.barda_petrenco.shop_electronic.dto.BucketDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(BucketServiceImpl.class);

    // Constructor for Dependency Injection
    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService, UserRepository userRepository) {
        this.bucketRepository = bucketRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    // Create a new bucket for a user with specified product IDs
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsById(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    // Helper method to fetch products by their IDs
    private List<Product> getCollectRefProductsById(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getReferenceById)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    // Add products to an existing bucket
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        Set<Long> existingProductIds = products.stream().map(Product::getId).collect(Collectors.toSet());
        List<Product> newProductList = new ArrayList<>(products);

        productIds.stream()
                .filter(productId -> !existingProductIds.contains(productId))
                .map(productRepository::getReferenceById)
                .forEach(newProductList::add);

        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    // Remove products from a bucket
    public void removeProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        if (products != null) {
            products.removeIf(product -> productIds.contains(product.getId()));
            bucket.setProducts(products);
            bucketRepository.save(bucket);
        }
    }

    @Override
    // Get bucket details for a user by username
    public BucketDTO getBucketByUser(String username) {
        User user = userService.findByName(username);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO(); // Return an empty DTO if user or bucket is null
        }
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if (detail == null) {
                detail = new BucketDetailDTO();
                detail.setAmount(BigDecimal.ONE);
                detail.setSum(Double.valueOf(product.getPrice().toString()));
                mapByProductId.put(product.getId(), detail);
            } else {
                detail.setAmount(detail.getAmount().add(BigDecimal.ONE));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate(); // Aggregate totals for the bucket
        logger.debug("Bucket details for user {}: {}", username, bucketDTO);
        return bucketDTO;
    }

    @Override
    @Transactional
    // Remove a specific product from a user's bucket by productId and username
    public void removeFromUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user != null && user.getBucket() != null) {
            Bucket bucket = user.getBucket();
            removeProducts(bucket, Collections.singletonList(productId));
        }
    }
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.dto.ProductDTO;

import java.util.List;

public interface ProductService {

 // Get a list of all products as a DTO
 List<ProductDTO> getAll();

 //Add a product to the user's cart by product ID and username
 void addToUserBucket(Long productId, String username);

}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.dao.ProductRepository;
import com.barda_petrenco.shop_electronic.domain.Bucket;
import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.ProductDTO;
import com.barda_petrenco.shop_electronic.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

 private final ProductMapper mapper = ProductMapper.MAPPER;
 private final ProductRepository productRepository;
 private final UserService userService;
 private final BucketService bucketService;

 // Constructor for dependency injection
 public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService) {
 this.productRepository = productRepository;
 this.userService = userService;
 this.bucketService = bucketService;
 }

 @Override
 // Get a list of all products as a DTO
 public List<ProductDTO> getAll() {
 return mapper.fromProductList(productRepository.findAll());
 }

 @Override
 @Transactional
 //Add a product to the user's cart by product ID and username
 public void addToUserBucket(Long productId, String username) {
 // Find user by name
 User user = userService.findByName(username);
 if (user == null) {
 throw new RuntimeException("User not found - " + username);
 }

 // Get the user's cart
 Bucket bucket = user.getBucket();
 if (bucket == null) {
 // If the user does not have a cart, create a new one and add the product to it
 Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
 user.setBucket(newBucket);
 userService.save(user); // Save user with new cart
 } else {
 // If the user already has a cart, add the product to the existing cart
 bucketService.addProducts(bucket, Collections.singletonList(productId));
 }
 }
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService { // Extends the interface for security

 // Save user based on DTO
 boolean save(UserDTO userDTO);

 // Save user
 void save(User user);

 // Get a list of all users as a DTO
 List<UserDTO> getAll();

 // Find user by name
 User findByName(String name);

 // Update user data based on DTO
 void update(UserDTO userDTO);

 // Transactionally update user profile based on DTO
 @Transactional
 void updateUserProfile(UserDTO dto);
}
===============================================================================================================================================================================================
package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.dao.UserRepository;
import com.barda_petrenco.shop_electronic.domain.Role;
import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

 private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;

 @Autowired
 public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
 this.userRepository = userRepository;
 this.passwordEncoder = passwordEncoder;
 }

 @Override
 //Save user based on DTO
 public boolean save(UserDTO userDTO) {
 // Check that the passwords match
 if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
 throw new RuntimeException("Passwords are not equal");
 }

 // Create a user object
 User user = User.builder()
 .name(userDTO.getUsername())
 .password(passwordEncoder.encode(userDTO.getPassword()))
 .email(userDTO.getEmail())
 .role(Role.CLIENT) // Set the default client role
 .build();

 // Saving the user to the repository
 userRepository.save(user);
 return true;
 }

 @Override
 //Save user
 public void save(User user) {
 userRepository.save(user);
 }

 @Override
 // Getting a list of all users as a DTO
 public List<UserDTO> getAll() {
 return userRepository.findAll().stream()
 .map(this::toDto)
 .collect(Collectors.toList());
 }

 @Override
 // Load user by name for Spring Security
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 User user = userRepository.findFirstByName(username);
 if (user == null) {
 throw new UsernameNotFoundException("User not found with name " + username);
 }

 // Forming a list of roles for Spring Security
 List<GrantedAuthority> roles = new ArrayList<>();
 roles.add(new SimpleGrantedAuthority(user.getRole().name()));

 // Return a UserDetails object for authentication and authorization
 return new org.springframework.security.core.userdetails.User(
 user.getName(),
 user.getPassword(),
 roles
 );
 }

 // Convert the User entity to a DTO
 private UserDTO toDto(User user) {
 return UserDTO.builder()
 .username(user.getName())
 .email(user.getEmail())
 .build();
 }

 @Override
 // Find user by name
 public User findByName(String name) {
 return userRepository.findFirstByName(name);
 }

 @Override
 // Update user data (not yet implemented)
 public void update(UserDTO userDTO) {
 // The method is left empty because its implementation is not provided
 }

 @Override
 @Transactional
 // Transactional user profile update based on DTO
 public void updateUserProfile(UserDTO dto) {
 // Find the saved user by name
 User savedUser = userRepository.findFirstByName(dto.getUsername());
 if (savedUser == null) {
 throw new RuntimeException("User not found by name " + dto.getUsername());
 }

 // Flag to track changes
 boolean isChanged = false;

 // If the password is set and differs from the current one, update it
 if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
 savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
 isChanged = true;
 }

 // If the email is different from the current one, update it
 if (!Objects.equals(dto.getEmail(), savedUser.getEmail())) {
 savedUser.setEmail(dto.getEmail());
 isChanged = true;
 }

 // If changes have been made, save the user
 if (isChanged) {
 userRepository.save(savedUser);
 }
 }
}
===============================================================================================================================================================================================
-- Sequence for the users table
DROP SEQUENCE IF EXISTS user_seq;
create sequence user_seq start 1 increment 1;

-- Users table
DROP TABLE IF EXISTS users CASCADE;
create table users
(
 id int8 not null,
 archive boolean not null,
 email varchar(255),
 name varchar(255),
 password varchar(255),
 role varchar(255),
 bucket_id int8,
 primary key (id)
);

-- Sequence for the buckets table
DROP SEQUENCE IF EXISTS bucket_seq;
create sequence bucket_seq start 1 increment 1;

-- Buckets table
DROP TABLE IF EXISTS buckets CASCADE;
create table buckets
(
 id int8 not null,
 user_id int8,
 primary key (id)
);

-- Relationship between buckets and users tables
alter table buckets
 add constraint buckets_fk_user
 foreign key (user_id) references users(id);

-- Relationship between the users and buckets tables
alter table users
 add constraint users_fk_bucket
 foreign key (bucket_id) references buckets(id);

-- Sequence for the categories table
DROP SEQUENCE IF EXISTS category_seq;
create sequence category_seq start 1 increment 1;

-- Categories table
DROP TABLE IF EXISTS categories CASCADE;
create table categories
(
 id int8 not null,
 title varchar(255),
 primary key (id)
);

-- Sequence for the products table
DROP SEQUENCE IF EXISTS product_seq;
create sequence product_seq start 1 increment 1;

-- Products table
DROP TABLE IF EXISTS products CASCADE;
create table products
(
 id int8 not null,
 price numeric(19, 2),
 title varchar(255),
 primary key (id)
);

-- Relationship between the products and categories tables through the products_categories intermediate table
DROP TABLE IF EXISTS products_categories CASCADE;
create table products_categories
(
 product_id int8 not null,
 category_id int8 not null,
 primary key (product_id, category_id)
);

-- Link products_categories_fk_category
alter table products_categories
 add constraint products_categories_fk_category
 foreign key (category_id) references categories(id);

-- Link products_categories_fk_products
alter table products_categories
 add constraint products_categories_fk_products
 foreign key (product_id) references products(id);

-- Table of products in the basket (buckets_products)
DROP TABLE IF EXISTS buckets_products CASCADE;
create table buckets_products
(
 bucket_id int8 not null,
 product_id int8 not null,
 primary key (bucket_id, product_id)
);

-- Link buckets_products_fk_products
alter table buck
===============================================================================================================================================================================================
-- Insert user data
INSERT INTO users (id, archive, email, name, password, role, bucket_id)
VALUES (1, false, 'mukolabarda777@gmail.com', 'admin', 'admin', 'ADMIN', null);

-- Restart user_seq sequence
ALTER SEQUENCE user_seq RESTART WITH 2;
===============================================================================================================================================================================================
-- Inserting product data
INSERT INTO products (id, price, title)
VALUES
 (1, 8420, 'Speaker system 2.1 Logitech Z625 400 W 3.5 mm Black'),
 (2, 2399, 'Speaker system Genius SP-HF500B'),
 (3, 2267, 'Tracer Tumba 2.1 Bluetooth speaker system'),
 (4, 1966, 'Tracer 2.1 Hi-Cube RGB Flow Bluetooth speaker system'),
 (5, 2185, 'Speaker system Microlab M-590 (11) Black / Silver');

-- Restart the product_seq sequence
ALTER SEQUENCE product_seq RESTART WITH 6;
===============================================================================================================================================================================================spring:
 jpa:
 hibernate:
 ddl-auto: validate # Validates existing database schema (does not change the structure)
 show-sql: true # Output SQL queries to the console for debugging
 datasource:
 url: jdbc:postgresql://localhost:5432/shop_electronic # URL to connect to PostgreSQL
 username: admin # Database username
 password: admin # Database user password
 flyway:
 baseline-on-migrate: true # Create a baseline migration if one is missing
server:
 port: 8012 # Port on which the application runs
file:
 upload-dir: uploads # Directory for uploading files, relative to the application root
