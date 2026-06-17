package com.pluralsight.sneakerdrops.service;

import com.pluralsight.sneakerdrops.data.BrandRepository;
import com.pluralsight.sneakerdrops.data.SneakerRepository;
import com.pluralsight.sneakerdrops.models.Sneaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SneakerService {

    private final SneakerRepository sneakerRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public SneakerService(SneakerRepository sneakerRepository, BrandRepository brandRepository) {
        this.sneakerRepository = sneakerRepository;
        this.brandRepository = brandRepository;
    }

    public List<Sneaker> search(Integer releaseYear, String brand, Double minPrice, String sort) {
        List<Sneaker> results = new ArrayList<>(sneakerRepository.findAll().stream()
                .filter(s -> releaseYear == null || s.getReleaseYear() == releaseYear)
                .filter(s -> brand == null || s.getBrand().getName().equalsIgnoreCase(brand))
                .filter(s -> minPrice == null || s.getPrice() >= minPrice)
                .toList());
        if ("price".equalsIgnoreCase(sort)) {
            results.sort(Comparator.comparingDouble(Sneaker::getPrice).reversed());
        } else if ("model".equalsIgnoreCase(sort)) {
            results.sort(Comparator.comparing(Sneaker::getModel));
        }
        return results;
    }

    public Sneaker byId(long id) {
        return sneakerRepository.findById(id).orElse(null);
    }
}