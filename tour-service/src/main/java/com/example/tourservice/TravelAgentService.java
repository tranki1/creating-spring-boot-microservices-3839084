package com.example.tourservice;

import com.example.tourservice.utilities.TourRepository;
import org.springframework.stereotype.Service;

@Service
public class TravelAgentService {
    private  TourRepository tourRepository;

    public TravelAgentService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }
    public void displayTours() {
        tourRepository.findAll().stream().forEach(System.out::println);
    }

    public void displayToursBy(Boolean isKidFriendly) {
        tourRepository.findByType(isKidFriendly).stream()
                .forEach(System.out::println);
    }
}
