package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.BrandDto;
import ua.khpi.diploma.sangoecommerceclothingproject.exception.CustomException;
import ua.khpi.diploma.sangoecommerceclothingproject.service.BrandService;

import java.util.List;

@Controller
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list")
    public String showBrands(Model model) {
        List<BrandDto> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "adminBrandList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-brand")
    public String addBrand(Model model) {
        BrandDto brandDto = new BrandDto();
        model.addAttribute("brand", brandDto);
        model.addAttribute("add", true);
        return "brand";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value="/add-brand", params="submit")
    public String addBrand(@ModelAttribute("brand") BrandDto brandDto, Model model) {
        String pageReturn = "addBrand";
        String title = brandDto.getTitle().trim();
        brandDto.setTitle(title);
        if (title.isBlank()) {
            model.addAttribute("add", true);
            return incorrectInputDataBrand(brandDto, model, "Назва бренду не заповнена!", pageReturn);
        }
        if (brandService.findBrandByTitle(title) != null) {
            model.addAttribute("add", true);
            return incorrectInputDataBrand(brandDto, model, "Ця назва бренду вже використовується!", pageReturn);
        }
        brandService.saveBrandDto(brandDto);
        return "redirect:/brand/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/update-brand")
    @SneakyThrows
    public String updateBrand(
            @PathVariable String id,
            Model model
    ) {
        BrandDto brandDto = brandService.findBrandById(id);
        if (brandDto == null) {
            throw new CustomException("Бренд з даним id не існує!!!");
        }
        model.addAttribute("brand", brandDto);
        model.addAttribute("update", true);
        return "brand";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value="/{id}/update-brand", params="submit")
    public String updateCategory(@ModelAttribute("brand") BrandDto brandDto, Model model) {
        String pageReturn = "brand";
        String title = brandDto.getTitle().trim();
        brandDto.setTitle(title);
        if (title.isBlank()) {
            model.addAttribute("update", true);
            return incorrectInputDataBrand(brandDto, model, "Назва категорії не заповнена!", pageReturn);
        }
        if (brandService.findBrandByTitle(title) != null) {
            model.addAttribute("update", true);
            return incorrectInputDataBrand(brandDto, model, "Ця назва категорії вже використовується!", pageReturn);
        }
        brandService.updateBrandDto(brandDto);
        return "redirect:/brand/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete-brand")
    public String deleteBrand(
            @PathVariable String id,
            Model model
    ) {
        brandService.deleteBrandById(id);
        return "redirect:/brand/list";
    }

    private String incorrectInputDataBrand(BrandDto brandDto, Model model, String errorMessage, String page) {
        model.addAttribute("brand", brandDto);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorFlag", true);
        return page;
    }

}
