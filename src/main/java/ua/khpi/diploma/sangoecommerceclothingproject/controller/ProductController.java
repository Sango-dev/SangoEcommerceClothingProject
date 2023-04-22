package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.BrandDto;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.CategoryDto;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductInstanceDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.ProductInstancePage;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Color;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Gender;
import ua.khpi.diploma.sangoecommerceclothingproject.service.BrandService;
import ua.khpi.diploma.sangoecommerceclothingproject.service.CategoryService;
import ua.khpi.diploma.sangoecommerceclothingproject.service.ProductInstanceService;
import ua.khpi.diploma.sangoecommerceclothingproject.service.ShopCartService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductInstanceService productInstanceService;
    private final ShopCartService cartService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false, defaultValue = "0") int page) {

        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        ProductInstancePage productInstancePageResponse = productInstanceService.findAllProductInstances(pageable);

        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("productInstances", productInstancePageResponse.getProductInstanceDtos());
        model.addAttribute("totalPages", productInstancePageResponse.getTotalPages());
        model.addAttribute("currentPage", page);
        return "productInstancesd";
    }

    @GetMapping(value = "/filter/list")
    public String findAllProductInstancesWithFilters(
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<Gender> genders,
            @RequestParam(required = false) List<Color> colors,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) String sort,
            Model model) {

        List<BrandDto> brandDtos = brandService.findAll();
        if (brands == null) {
            brands = brandDtos.stream().map(brandDto -> brandDto.getTitle()).collect(Collectors.toList());
        }

        List<CategoryDto> categoryDtos = categoryService.findAll();
        if (categories == null) {
            categories = categoryDtos.stream().map(categoryDto -> categoryDto.getTitle()).collect(Collectors.toList());
        }

        if (sizes == null) {
            sizes = Arrays.asList("XS", "S", "M", "L", "XL", "XLL", "3XL", "29", "30", "31", "32", "33", "34");
        }

        if (colors == null) {
           colors = Arrays.asList(Color.BLACK, Color.BLUE, Color.BROWN, Color.GOLDCOLORED, Color.GREY, Color.GREEN, Color.MULTI, Color.ORANGE, Color.PINK, Color.PURPLE, Color.RED, Color.SILVERCOLORED, Color.TURQUOISE, Color.WHITE, Color.YELLOW);
        }

        if (genders == null) {
            genders = Arrays.asList(Gender.FEMALE, Gender.MALE);
        }

            List<ProductInstanceDto> list = productInstanceService.findAllProductInstancesWithFilters(
                    colors, brands, categories, genders, sizes, sort);



        model.addAttribute("productInstances", list);
        model.addAttribute("brands", brandDtos);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("totalPages", 0);
        model.addAttribute("currentPage", 0);
        return "productInstancesd";
    }

    @GetMapping("/search-by-param")
    public String searchByParam(Model model, @RequestParam("str") String param) {
        List<CategoryDto> categoryDtos = categoryService.findAll();
        List<BrandDto> brandDtos = brandService.findAll();
        final List<ProductInstanceDto> list = productInstanceService.findAllProductInstancesBySearchParam(param);
        model.addAttribute("productInstances", list);
        model.addAttribute("brands", brandDtos);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("totalPages", 0);
        model.addAttribute("currentPage", 0);
        return "productInstancesd";
    }

    @GetMapping("/{id}/info")
    public String productInstanceInfo(@PathVariable String id, Model model) throws Exception {
        ProductInstanceDto productInstanceDto = productInstanceService.findProductInstanceById(id);
        model.addAttribute("productInstance", productInstanceDto);
        return "productInstanceInfo";
    }


    @PostMapping("/{id}/add-to-cart")
    public String attachToCart(@PathVariable String id,
                               @RequestParam(required = true) String size,
                               Principal principal,
                               HttpServletRequest request) {
        String link = request.getHeader("Referer");
        cartService.attachToShopCart(id, size, principal.getName());
        return "redirect:" + link;
    }


}
