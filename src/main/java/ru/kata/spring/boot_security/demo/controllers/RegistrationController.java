package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.HashSet;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private RoleServiceImpl roleServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/registration")
    public String hello(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleServiceImpl.getRoleList());
        return "registration";
    }

    @PostMapping("/registration")
    public String adminRegistration(@ModelAttribute("user") User user,
                                    @RequestParam("roles") List<String> rolesId,
                                    Model model) {

        List<Role> roles = roleServiceImpl.getSetOfRoles(rolesId);
        user.setRoles(new HashSet<>(roles));


        userServiceImpl.addUser(user);

        return "redirect:/admin";
    }
}