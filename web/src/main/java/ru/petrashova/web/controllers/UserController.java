package ru.petrashova.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.petrashova.web.models.User;
import ru.petrashova.web.services.UserService;

import javax.security.sasl.AuthenticationException;



@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id:\\d+}")
    String getUser (@PathVariable("id") int id, Model model) throws AuthenticationException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser.getRoles().stream().allMatch(r-> r.getRole().equals("ROLE_USER")) && !(currentUser.getId() == id)){
            throw new AuthenticationException();
        }
        model.addAttribute("user", userService.getUser(id));
        return "/user/show_user";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
