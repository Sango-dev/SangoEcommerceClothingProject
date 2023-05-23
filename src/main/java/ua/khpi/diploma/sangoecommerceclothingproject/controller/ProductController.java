package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.*;
import ua.khpi.diploma.sangoecommerceclothingproject.exception.CustomException;
import ua.khpi.diploma.sangoecommerceclothingproject.model.ProductInstancePage;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Color;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Gender;
import ua.khpi.diploma.sangoecommerceclothingproject.service.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductInstanceService productInstanceService;
    private final SizeClothService sizeClothService;
    private final ShopCartService cartService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ReviewService reviewService;
    private final ProductClothService productService;

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
        return "productInstances";
    }

    @GetMapping( "/filter/list")
    public String findAllProductInstancesWithFilters(
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<Gender> genders,
            @RequestParam(required = false) List<Color> colors,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) String sort,
            Model model) {

        if (sort.equals("non-sort") && brands == null && categories == null && genders == null && colors == null && sizes == null) {
            return "redirect:/product/list";
        }

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

        if (list.isEmpty()) {
            model.addAttribute("noMatch", true);
        }

        model.addAttribute("productInstances", list);
        model.addAttribute("brands", brandDtos);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("totalPages", 0);
        model.addAttribute("currentPage", 0);
        return "productInstances";
    }

    @GetMapping("/search-by-param")
    public String searchByParam(Model model, @RequestParam("str") String param) {
        if (param.isBlank()) {
            return "redirect:/product/list";
        }
        List<CategoryDto> categoryDtos = categoryService.findAll();
        List<BrandDto> brandDtos = brandService.findAll();
        final List<ProductInstanceDto> list = productInstanceService.findAllProductInstancesBySearchParam(param);
        if (list.isEmpty()) {
            model.addAttribute("noMatch", true);
        }
        model.addAttribute("productInstances", list);
        model.addAttribute("brands", brandDtos);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("totalPages", 0);
        model.addAttribute("currentPage", 0);
        return "productInstances";
    }

    @GetMapping("/{id}/info")
    @SneakyThrows
    public String productInstanceInfo(@PathVariable String id, Model model) {
        ProductInstanceDto productInstanceDto = productInstanceService.findProductInstanceById(id);
        if (productInstanceDto.getAvailable() == false) {
            throw new CustomException("Даний екземпляр товару недоступний!");
        }
        List<ReviewDto> reviews = reviewService.findAllReviewsByProductInstanceId(id);
        if (reviews.isEmpty()) {
            model.addAttribute("rate", 0);
        } else {
            int avgRate = reviews.stream().mapToInt(reviewDto -> reviewDto.getRate()).sum() / reviews.size();
            model.addAttribute("reviews", reviews);
            model.addAttribute("rate", avgRate);
        }
        model.addAttribute("productInstance", productInstanceDto);
        return "productInstanceInfo";
    }

    @GetMapping("/admin-list")
    public String listForAdmin(Model model) {
        List<ProductDto> list = productInstanceService.findAllProductsWithInstances();
        model.addAttribute("products", list);
        return "adminList";
    }

    @GetMapping("/admin-list/search-by-code")
    public String searchByCode(Model model, @RequestParam("code") String productCode) {
        if (productCode.isBlank()) {
            model.addAttribute("noMatch", true);
        } else {
            ProductDto product = productInstanceService.findProductWithInstancesByProductCode(productCode.trim().toLowerCase());
            if (product == null) {
                model.addAttribute("noMatch", true);
            } else {
                model.addAttribute("products", Collections.singletonList(product));
            }
        }
        model.addAttribute("isSearch", true);
        return "adminList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-product")
    public String addProd(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("product", productDto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "addProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/add-product", params = "submit")
    public String addProd(
            @ModelAttribute("product") ProductDto productDto,
            BindingResult bindingResult,
            Model model
    ) {
        String pageReturn = "addProduct";
        if (bindingResult.hasErrors()) {
            String errorMessage = "Необхідно правильно заповнити поля!";
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("errorFlag", true);
            return pageReturn;
        }
        if (productDto.getProductCode().isBlank()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Код товару не заповнений!", pageReturn);
        }
        if (productDto.getTitle().isBlank()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Назва товару не заповнена!", pageReturn);
        }
        if (productDto.getDescription().isBlank()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Опис товару не заповнений!", pageReturn);
        }
        if (productDto.getComposition().isBlank()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Склад товару не заповнений!", pageReturn);
        }
        if (productDto.getPrice() == null) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Необхідно правильно записати ціну!", pageReturn);
        }
        if (productService.findFirstByProductCode(productDto.getProductCode()) != null) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Цей код товару вже використовується!", pageReturn);
        }
        productService.addProduct(productDto);
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/add-product-instance")
    public String addProdInstance(Model model) {
        ProductInstanceDto productInstanceDto = new ProductInstanceDto();
        model.addAttribute("product", productInstanceDto);
        return "productInstanceAdd";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{id}/add-product-instance", params = "submit")
    public String addProdInstance(
            Model model,
            @PathVariable("id") String prodId,
            @ModelAttribute("product") ProductInstanceDto productInstanceDto,
            BindingResult bindingResult) {

        String pageReturn = "productInstanceAdd";

        if (bindingResult.hasErrors()) {
            return pageReturn;
        }

        if (productInstanceDto.getLinkOfMainPicture().isBlank() ||
                productInstanceDto.getLinkOfBackPicture().isBlank() ||
                productInstanceDto.getLinkOfFrontPicture().isBlank()) {
            return incorrectInputDataProductInstance(productInstanceDto, model, "Посилання на зображення не заповнено!", pageReturn);
        }
        if (productInstanceDto.getColorDefinition().isBlank()) {
            return incorrectInputDataProductInstance(productInstanceDto, model, "Опис кольору не заповнено!", pageReturn);
        }
        productInstanceService.addProductInstance(prodId, productInstanceDto);
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit-product")
    public String editProd(Model model, @PathVariable("id") String prodId) {
        ProductDto productDto = productService.getProductDtoById(prodId);
        if (productDto != null) {
            model.addAttribute("product", productDto);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return "productEdit";
        }
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{id}/edit-product", params = "submit")
    public String editProd(@PathVariable("id") String prodId, @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model) {
        String pageReturn = "productEdit";
        if (bindingResult.hasErrors()) {
            String errorMessage = "Необхідно правильно заповнити поля!";
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("errorFlag", true);
            return pageReturn;
        }
        if (productDto.getTitle().isBlank()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Назва товару не заповнена!", pageReturn);
        }
        if (productDto.getDescription().isBlank()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Опис товару не заповнений!", pageReturn);
        }
        if (productDto.getComposition().isBlank()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Склад товару не заповнений!", pageReturn);
        }
        if (productDto.getPrice() == null) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return incorrectInputData(productDto, model, "Необхідно правильно записати ціну!", pageReturn);
        }
        productService.updateProduct(prodId, productDto);
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit-product-instance")
    public String editProdInstance(Model model, @PathVariable("id") String prodId) {
        ProductInstanceDto productInstanceDto = productInstanceService.findProductInsClothById(prodId);
        if (productInstanceDto != null) {
            model.addAttribute("product", productInstanceDto);
            return "productInstanceEdit";
        }
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{id}/edit-product-instance", params = "submit")
    public String editProdInstance(@PathVariable("id") String prodId, @ModelAttribute("product") ProductInstanceDto productInstanceDto, BindingResult bindingResult, Model model) {

        String pageReturn = "productInstanceEdit";

        if (bindingResult.hasErrors()) {
            return pageReturn;
        }

        if (productInstanceDto.getLinkOfMainPicture().isBlank() ||
                productInstanceDto.getLinkOfBackPicture().isBlank() ||
                productInstanceDto.getLinkOfFrontPicture().isBlank()) {
            return incorrectInputDataProductInstance(productInstanceDto, model, "Посилання на зображення не заповнено!", pageReturn);
        }

        if (productInstanceDto.getColorDefinition().isBlank()) {
            return incorrectInputDataProductInstance(productInstanceDto, model, "Опис кольору не заповнено!", pageReturn);
        }

        productInstanceService.updateProductInstance(prodId, productInstanceDto);
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/add-size")
    public String addSizeToProdInstance(Model model, @PathVariable("id") String prodId) {
        List<String> addSizeList = sizeClothService.findAllNotAddedSizeClothByProductInstance(prodId);

        if (addSizeList != null) {
            model.addAttribute("sizes", addSizeList);
            model.addAttribute("add", true);
            model.addAttribute("prodId", prodId);
            return "sizes";
        }
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/adding-size", params = "submit")
    public String addSizeToProdInstance(@RequestParam("prodId") String prodId, @RequestParam("size") String size, Model model) {
        sizeClothService.addNewSizeToProductInstanceCloth(prodId, size);
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/remove-size")
    public String removeSizeFromProdInstance(Model model, @PathVariable("id") String prodId) {
        List<String> removeSizeList = sizeClothService.findAllAddedSizeClothByProductInstance(prodId);

        if (removeSizeList != null) {
            model.addAttribute("sizes", removeSizeList);
            model.addAttribute("add", false);
            model.addAttribute("prodId", prodId);
            return "sizes";
        }
        return "redirect:/product/admin-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/removing-size", params = "submit")
    public String removeSizeFromProdInstance(@RequestParam("prodId") String prodId, @RequestParam("size") String size, Model model) {
        sizeClothService.removeSizeFromProductInstanceCloth(prodId, size);
        return "redirect:/product/admin-list";
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete-product")
    public String deleteCategory(
            @PathVariable String id,
            Model model
    ) {
        productService.deleteProductById(id);
        return "redirect:/product/admin-list";
    }

    private String incorrectInputData(ProductDto productDto, Model model, String errorMessage, String page) {
        model.addAttribute("product", productDto);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorFlag", true);
        return page;
    }

    private String incorrectInputDataProductInstance(ProductInstanceDto productInstanceDto, Model model, String errorMessage, String page) {
        model.addAttribute("product", productInstanceDto);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorFlag", true);
        return page;
    }
}
