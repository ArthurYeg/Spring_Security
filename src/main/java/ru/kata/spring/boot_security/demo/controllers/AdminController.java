package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", userService.listUser ());
        return "admin";
    }

    @GetMapping("delete/{id}")
    public String deleteUser (@PathVariable("id") int id) {
        userService.removeUser (id);
        return "redirect:/admin";
    }

    @GetMapping("update/{id}")
    public String updateUserForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getRoleList());
        return "update";
    }

    @PostMapping("/update")
    public String updateUser (@ModelAttribute("user") User user, @RequestParam("roleList") List<String> role) {
        user.setRoles(userService.getSetOfRoles(role));
        userService.updateUser (user);
        return "redirect:/admin";
    }

    @GetMapping("add")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getRoleList());
        return "add";
    }

    @PostMapping("/add")
    public String addUser (@ModelAttribute("user") User user, @RequestParam(value = "role", required = false) List<String> role) {
        if (role != null && !role.isEmpty()) {
            Collection<Role> roleList = userService.getSetOfRoles(role);
            user.setRoles(roleList);
        }
        userService.addUser (user);
        return "redirect:/admin";
    }
}
