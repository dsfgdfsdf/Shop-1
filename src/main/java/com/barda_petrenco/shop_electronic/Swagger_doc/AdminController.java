package com.barda_petrenco.shop_electronic.Swagger_doc;
import com.barda_petrenco.shop_electronic.dto.UserDTO;
import com.barda_petrenco.shop_electronic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Controller for managing admin users")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @Operation(summary = "View all users", description = "This method allows the administrator to view all users or add a new one")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAll();
    }
}
