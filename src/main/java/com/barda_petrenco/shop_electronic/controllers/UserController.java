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

@Controller
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Controller for handling user operations")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
