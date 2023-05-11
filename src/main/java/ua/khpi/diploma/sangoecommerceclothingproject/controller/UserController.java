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
        String page = "registration";
        if (user.getFirstName().isBlank()) {
            return incorrectInputData(user, model, "Ім'я відсутнє!", page);
        }
        if (user.getLastName().isBlank()) {
            return incorrectInputData(user, model, "Прізвище відсутнє!", page);
        }
        if (user.getNickName().isBlank()) {
            return incorrectInputData(user, model, "Нік відсутній!", page);
        }
        if (user.getEmail().isBlank()) {
            return incorrectInputData(user, model, "Пошта відсутня!", page);
        }
        if (user.getPhone().isBlank()) {
            return incorrectInputData(user, model, "Номер телефона відсутній!", page);
        }
        if (user.getPassword().isBlank() || user.getMatchingPassword().isBlank()) {
            return incorrectInputData(user, model, "Пароль відсутній!", page);
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            return incorrectInputData(user, model, "Паролі не співпадають!!!", page);
        }
        if (userService.findFirstByNickName(user.getNickName()) != null) {
            return incorrectInputData(user, model, "Цей логін вже використовується!", page);
        }
        if (userService.findFirstByEmail(user.getEmail()) != null) {
            return incorrectInputData(user, model, "Ця пошта вже використовується!", page);
        }
        if (userService.findFirstByPhone(user.getPhone()) != null) {
            return incorrectInputData(user, model, "Цей номер телефона вже використовується!", page);
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
    public String updateProfile(@RequestParam("password") String password,
                                @RequestParam("matchPassword") String matchPassword,
                                @ModelAttribute("user") UserDto user,
                                Principal principal, Model model) {
        int sz = 3;
        return "null";
       /* String page = "profile";
        if (userDTO.getPassword().isBlank() || userDTO.getMatchingPassword().isBlank()) {
            return incorrectInputData(userDTO, model, "Password is blank!!!", page);
        }
        if (!userDTO.getPassword().equals(userDTO.getMatchingPassword())) {
            return incorrectInputData(userDTO, model, "Passwords are not matching!!!", page);
        }
        userService.updatePassword(userDTO);
        LOGGER.info("Password updation has been successfully done, User {}", userDTO.getNickName());
        return "redirect:/users/profile";*/
    }

   /* @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public String updatePassword(@ModelAttribute("user") UserDTO userDTO, Principal principal, Model model) {
        String page = "profile";
        if (userDTO.getPassword().isBlank() || userDTO.getMatchingPassword().isBlank()) {
            return incorrectInputData(userDTO, model, "Password is blank!!!", page);
        }
        if (!userDTO.getPassword().equals(userDTO.getMatchingPassword())) {
            return incorrectInputData(userDTO, model, "Passwords are not matching!!!", page);
        }
        userService.updatePassword(userDTO);
        LOGGER.info("Password updation has been successfully done, User {}", userDTO.getNickName());
        return "redirect:/users/profile";
    }*/
}
