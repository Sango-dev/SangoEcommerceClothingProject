package ua.khpi.diploma.sangoecommerceclothingproject.service;

import java.util.List;

public interface SizeClothService {
    List<String> findAllNotAddedSizeClothByProductInstance(String id);
    List<String> findAllAddedSizeClothByProductInstance(String id);
    void addNewSizeToProductInstanceCloth(String prodId, String size);
    void removeSizeFromProductInstanceCloth(String prodId, String size);
}
