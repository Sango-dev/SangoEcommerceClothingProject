package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductInstanceRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.SizeClothRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.SizeClothDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.SizeClothMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.SizeCloth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SizeClothServiceImpl implements SizeClothService {
    private final SizeClothRepository sizeClothRepository;
    private final ProductInstanceRepository productInstanceRepository;
    private final SizeClothMapper sizeClothMapper = SizeClothMapper.MAPPER;

    @Override
    public List<String> findAllNotAddedSizeClothByProductInstance(String id) {
        List<SizeClothDto> sizes = sizeClothMapper.fromSizeClothList(sizeClothRepository.findAllByProductInstance(productInstanceRepository.findById(id).get()));
        boolean sizeXS = false;
        boolean sizeS = false;
        boolean sizeM = false;
        boolean sizeL = false;
        boolean sizeXL = false;
        boolean sizeXXL = false;
        boolean sizeXXXL = false;
        for (SizeClothDto size : sizes) {
            if (size.getSize().equals("XS")) {
                sizeXS = true;
            }
            if (size.getSize().equals("S")) {
                sizeS = true;
            }
            if (size.getSize().equals("M")) {
                sizeM = true;
            }
            if (size.getSize().equals("L")) {
                sizeL = true;
            }
            if (size.getSize().equals("XL")) {
                sizeXL = true;
            }
            if (size.getSize().equals("XXL")) {
                sizeXXL = true;
            }
            if (size.getSize().equals("3XL")) {
                sizeXXXL = true;
            }
        }

        List<String> addSizeList = new ArrayList<>();
        if (sizeXS == false) {
            addSizeList.add("XS");
        }
        if (sizeS == false) {
            addSizeList.add("S");
        }
        if (sizeM == false) {
            addSizeList.add("M");
        }
        if (sizeL == false) {
            addSizeList.add("L");
        }
        if (sizeXL == false) {
            addSizeList.add("XL");
        }
        if (sizeXXL == false) {
            addSizeList.add("XXL");
        }
        if (sizeXXXL == false) {
            addSizeList.add("3XL");
        }

        return addSizeList;
    }

    @Override
    public List<String> findAllAddedSizeClothByProductInstance(String id) {
        List<String> addSizeList = new ArrayList<>();
        List<SizeClothDto> sizes = sizeClothMapper.fromSizeClothList(sizeClothRepository.findAllByProductInstance(productInstanceRepository.findById(id).get()));
        return sizes.stream()
                .map(sizeClothDto -> sizeClothDto.getSize())
                .collect(Collectors.toList());
    }

    @Override
    public void addNewSizeToProductInstanceCloth(String prodId, String size) {
        ProductInstanceCloth productInstanceCloth = productInstanceRepository.findById(prodId).get();
        SizeCloth sizeCloth = new SizeCloth();
        sizeCloth.setSize(size);
        sizeCloth.setProductInstance(productInstanceCloth);
        sizeClothRepository.save(sizeCloth);
        productInstanceCloth.getSizes().add(sizeCloth);
        productInstanceRepository.save(productInstanceCloth);
    }

    @Override
    @Transactional
    public void removeSizeFromProductInstanceCloth(String prodId, String size) {
        ProductInstanceCloth productInstanceCloth = productInstanceRepository.findById(prodId).get();
        productInstanceCloth.getSizes().removeIf(sizeCloth -> sizeCloth.getSize().equals(size));
        sizeClothRepository.deleteBySizeAndProductInstance(size, productInstanceCloth);
        productInstanceRepository.save(productInstanceCloth);
    }
}
