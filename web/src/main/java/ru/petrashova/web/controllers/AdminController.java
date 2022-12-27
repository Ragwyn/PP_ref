package ru.petrashova.web.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.petrashova.web.models.User;
import ru.petrashova.web.services.RoleService;
import ru.petrashova.web.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService usersService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService usersService, RoleService roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Model model){
        model.addAttribute("user", usersService.getAllUsers());
        return "admin/show_all";
    }
    @GetMapping("/new_user")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("role", roleService.getAll());
        return "admin/new_user";
    }
    @GetMapping("/user/{id}")
    public String showUser(@PathVariable("id") int id, Model model){
        model.addAttribute("user", usersService.getUser(id));
        return "admin/show_user";
    }
    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "admin/new_user";

        usersService.save(user);
        return "redirect:/admin";
    }
    @GetMapping("/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", usersService.getUser(id));
        model.addAttribute("role", roleService.getAll());
        return "admin/edit";
    }
    @PatchMapping("/user/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "admin/edit";

        usersService.update(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable("id") int id){
        usersService.delete(id);
        return "redirect:/admin";
    }
}