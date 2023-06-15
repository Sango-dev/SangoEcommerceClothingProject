package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.CategoryDto;
import ua.khpi.diploma.sangoecommerceclothingproject.exception.CustomException;
import ua.khpi.diploma.sangoecommerceclothingproject.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public String showCategories(Model model) {
        List<CategoryDto> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "adminCategoryList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-category")
    public String addCategory(Model model) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("category", categoryDto);
        model.addAttribute("add", true);
        return "category";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value="/add-category", params="submit")
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto, Model model) {
        String pageReturn = "category";
        String title = categoryDto.getTitle().trim();
        categoryDto.setTitle(title);
        if (title.isBlank()) {
            model.addAttribute("add", true);
            return incorrectInputDataCategory(categoryDto, model, "Назва категорії не заповнена!", pageReturn);
        }
        if (categoryService.findCategoryByTitle(title) != null) {
            model.addAttribute("add", true);
            return incorrectInputDataCategory(categoryDto, model, "Ця назва категорії вже використовується!", pageReturn);
        }
        categoryService.saveCategoryDto(categoryDto);
        return "redirect:/category/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/update-category")
    @SneakyThrows
    public String updateCategory(
            @PathVariable String id,
            Model model
    ) {
        CategoryDto categoryDto = categoryService.findCategoryById(id);
        if (categoryDto == null) {
            throw new CustomException("Категорії з даним id не існує!");
        }
        model.addAttribute("category", categoryDto);
        model.addAttribute("update", true);
        return "category";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value="/{id}/update-category", params="submit")
    public String updateCategory(@ModelAttribute("category") CategoryDto categoryDto, Model model) {
        String pageReturn = "category";
        String title = categoryDto.getTitle().trim();
        categoryDto.setTitle(title);
        if (title.isBlank()) {
            model.addAttribute("update", true);
            return incorrectInputDataCategory(categoryDto, model, "Назва категорії не заповнена!", pageReturn);
        }
        if (categoryService.findCategoryByTitle(title) != null) {
            model.addAttribute("update", true);
            return incorrectInputDataCategory(categoryDto, model, "Ця назва категорії вже використовується!", pageReturn);
        }
        categoryService.updateCategoryDto(categoryDto);
        return "redirect:/category/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete-category")
    public String deleteCategory(
            @PathVariable String id,
            Model model
    ) {
        categoryService.deleteCategoryById(id);
        return "redirect:/category/list";
    }

    private String incorrectInputDataCategory(CategoryDto categoryDto, Model model, String errorMessage, String page) {
        model.addAttribute("category", categoryDto);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorFlag", true);
        return page;
    }
}
