package com.example.explorecalijpa.repo;

import com.example.explorecalijpa.model.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.explorecalijpa.model.Tour;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Integer> {
    List<Tour> findByDifficulty(Difficulty difficulty);

    List<Tour> findByTourPackageCode(String tourPackageCode);
}