package com.thymeleafhomework.thhomework;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
     
    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }
     
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
         
        UserRepository.save(user);
        model.addAttribute("users", UserRepository.findAll());
        return "redirect:/index";
        }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
    User user = UserRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
     
    model.addAttribute("user", user);
    return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
    if (result.hasErrors()) {
        user.setId(id);
        return "update-user";
    }
         
    UserRepository.save(user);
    model.addAttribute("users", UserRepository.findAll());
    return "redirect:/index";
}
     
@GetMapping("/delete/{id}")
public String deleteUser(@PathVariable("id") long id, Model model) {
    User user = UserRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
      UserRepository.delete(user);
    model.addAttribute("users", UserRepository.findAll());
    return "index";
}
 
}