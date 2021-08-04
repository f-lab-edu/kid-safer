package com.flab.kidsafer.controller;

import com.flab.kidsafer.config.auth.LoginUser;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/block")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public void blockUser(@PathVariable int userId, @LoginUser SessionUser user) {
        adminValidation(user);
        adminService.blockUser(userId);
    }

    @DeleteMapping
    public void unblockUser(@PathVariable int userId, @LoginUser SessionUser user) {
        adminValidation(user);
        adminService.unblockUser(userId);
    }

    public void adminValidation(SessionUser user) {
        if (user.getType() != UserType.ADMIN) {
            throw new OperationNotAllowedException();
        }
    }

}
