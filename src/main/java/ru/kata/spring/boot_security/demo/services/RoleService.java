package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Collection;

public interface RoleService {

    Collection<Role> getRoleList();
    Role getRoleById(int id);
}