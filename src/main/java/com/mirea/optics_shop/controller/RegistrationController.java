package com.mirea.optics_shop.controller;

import com.mirea.optics_shop.dto.UserDto;
import com.mirea.optics_shop.exception.UserAlreadyExistsException;
import com.mirea.optics_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/signup")
    public String register(final Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String userRegistration(final @Valid UserDto userDto, final BindingResult bindingResult, final Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userDto);
            return "signup";
        }
        try {
            userService.register(userDto);
        } catch (UserAlreadyExistsException e){
            bindingResult.rejectValue("email", "userDto.email","Аккаунт с такой почтой уже существует.");
            model.addAttribute("registrationForm", userDto);
            return "signup";
        }
        return "redirect:/index";
    }
}