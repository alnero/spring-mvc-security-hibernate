package com.example.project.controller;

import com.example.project.model.User;
import com.example.project.model.UserAuthority;
import com.example.project.security.SuccessUserHandler;
import com.example.project.service.UserAuthorityService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private UserService userService;
    private UserAuthorityService userAuthorityService;
    private SuccessUserHandler successUserHandler;

    @Autowired
    public UsersController(UserService userService, UserAuthorityService userAuthorityService, SuccessUserHandler successUserHandler) {
        this.userService = userService;
        this.userAuthorityService = userAuthorityService;
        this.successUserHandler = successUserHandler;
    }

    @GetMapping()
    public String listUsers(ModelMap model) {
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, ModelMap model) {
        User authenticatedUser = successUserHandler.currentAuthenticatedUser();
        Long authenticatedUserId = authenticatedUser.getId();
        String userAuthority = authenticatedUser.getAuthorities().iterator().next().getAuthority();
        if (UserAuthority.Role.ADMIN.name().equals(userAuthority) && !id.equals(authenticatedUserId)) {
            User user = userService.getById(id);
            model.addAttribute("user", user);
            return "user";
        }
        if (id.equals(authenticatedUserId)) {
            model.addAttribute("user", authenticatedUser);
            return "user";
        } else {
            return "redirect:/users/" + authenticatedUserId;
        }
    }

    @GetMapping("/add")
    public String addPage(@ModelAttribute User user) {
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute User user, @Validated String role) {
        UserAuthority userAuthority = userAuthorityService.getUserAuthorityByName(role);
        user.setUserAuthority(userAuthority);
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam long id, ModelMap model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute User user, @Validated String role) {
        UserAuthority userAuthority = userAuthorityService.getUserAuthorityByName(role);
        user.setUserAuthority(userAuthority);
        userService.edit(user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id) {
        userService.delete(userService.getById(id));
        return "redirect:/users";
    }
}
