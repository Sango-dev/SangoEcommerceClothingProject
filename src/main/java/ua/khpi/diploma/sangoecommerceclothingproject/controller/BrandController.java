package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.BrandDto;
import ua.khpi.diploma.sangoecommerceclothingproject.service.BrandService;

@Controller
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-brand")
    public String addBrand(Model model) {
        BrandDto brandDto = new BrandDto();
        model.addAttribute("brand", brandDto);
        return "addBrand";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value="/add-brand", params="submit")
    public String addBrand(@ModelAttribute("brand") BrandDto brandDto, Model model) {
        String pageReturn = "addBrand";
        String title = brandDto.getTitle().trim();
        brandDto.setTitle(title);
        if (title.isBlank()) {
            return incorrectInputDataBrand(brandDto, model, "Назва бренду не заповнена!", pageReturn);
        }
        if (brandService.findBrandByTitle(title) != null) {
            return incorrectInputDataBrand(brandDto, model, "Ця назва бренду вже використовується!", pageReturn);
        }
        brandService.saveBrandDto(brandDto);
        return "redirect:/users/profile";
    }

    private String incorrectInputDataBrand(BrandDto brandDto, Model model, String errorMessage, String page) {
        model.addAttribute("brand", brandDto);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorFlag", true);
        return page;
    }

}
