package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.UserDto;
import ua.khpi.diploma.sangoecommerceclothingproject.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/new")
    public String addNewUser(UserDto user, Model model) {
        String pageReturn = "registration";
        if (user.getFirstName().isBlank()) {
            return incorrectInputData(user, model, "Ім'я не заповнено!", pageReturn);
        }
        if (user.getLastName().isBlank()) {
            return incorrectInputData(user, model, "Прізвище не заповнено!", pageReturn);
        }
        if (user.getNickName().isBlank()) {
            return incorrectInputData(user, model, "Нік не заповнено!", pageReturn);
        }
        if (user.getEmail().isBlank()) {
            return incorrectInputData(user, model, "Пошта не заповнено!", pageReturn);
        }
        if (user.getPhone().isBlank()) {
            return incorrectInputData(user, model, "Номер телефона не заповнено!", pageReturn);
        }
        if (user.getPassword().isBlank() || user.getMatchingPassword().isBlank()) {
            return incorrectInputData(user, model, "Пароль не заповнено!", pageReturn);
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            return incorrectInputData(user, model, "Паролі не співпадають!!!", pageReturn);
        }
        if (userService.findFirstByNickName(user.getNickName()) != null) {
            return incorrectInputData(user, model, "Цей логін вже використовується!", pageReturn);
        }
        if (userService.findFirstByEmail(user.getEmail()) != null) {
            return incorrectInputData(user, model, "Ця пошта вже використовується!", pageReturn);
        }
        if (userService.findFirstByPhone(user.getPhone()) != null) {
            return incorrectInputData(user, model, "Цей номер телефона вже використовується!", pageReturn);
        }

        userService.save(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return "redirect:/users";
            }
        }
        return "redirect:/login";
    }

    private String incorrectInputData(UserDto userDTO, Model model, String errorMessage, String page) {
        model.addAttribute("user", userDTO);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorFlag", true);
        return page;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String visitProfile(Model model, Principal principal) {
        final UserDto userDto = userService.getUserDtoByNickName(principal.getName());
        model.addAttribute("user", userDto);
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public String updatePassword(
            Principal principal,
            Model model,
            @ModelAttribute("user") UserDto user,
            @RequestParam("password") String password,
            @RequestParam("matchPassword") String matchPassword
            ) {

        String pageReturn = "profile";
        if (password != null && matchPassword != null) {
            if (password.isBlank() || matchPassword.isBlank()) {
                return incorrectInputData(user, model, "Необхідно правильно записати пароль!", pageReturn);
            }
            if (!password.equals(matchPassword)) {
                return incorrectInputData(user, model,  "Паролі не співпадають!", pageReturn);
            }

            user.setPassword(password);
        }

        userService.updatePassword(user);
        return "redirect:/users/profile";
    }
}
