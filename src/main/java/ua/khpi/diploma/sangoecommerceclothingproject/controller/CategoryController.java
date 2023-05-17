package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.CategoryDto;
import ua.khpi.diploma.sangoecommerceclothingproject.service.CategoryService;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-category")
    public String addCategory(Model model) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("category", categoryDto);
        return "addCategory";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value="/add-category", params="submit")
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto, Model model) {
        String pageReturn = "addCategory";
        String title = categoryDto.getTitle().trim();
        categoryDto.setTitle(title);
        if (title.isBlank()) {
            return incorrectInputDataCategory(categoryDto, model, "Назва категорії не заповнена!", pageReturn);
        }
        if (categoryService.findCategoryByTitle(title) != null) {
            return incorrectInputDataCategory(categoryDto, model, "Ця назва категорії вже використовується!", pageReturn);
        }
        categoryService.saveCategoryDto(categoryDto);
        return "redirect:/users/profile";
    }

    private String incorrectInputDataCategory(CategoryDto categoryDto, Model model, String errorMessage, String page) {
        model.addAttribute("category", categoryDto);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorFlag", true);
        return page;
    }

}
