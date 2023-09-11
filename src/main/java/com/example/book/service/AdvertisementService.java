package com.example.book.service;

import com.example.book.domain.AdvertisementDetails;
import com.example.book.domain.AdvertisementHandling;
import com.example.book.entity.Advertisement;
import com.example.book.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final ModelMapper mapper;

    public List<AdvertisementDetails> viewCurrentAdvertisements(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy 'at' hh:mm a");
        List<Advertisement> advertisements = advertisementRepository.findAll();
        List<AdvertisementDetails> mapped_advertisements = new ArrayList<>();

        for (Advertisement advertisement : advertisements){
            AdvertisementDetails advertisementDetails = mapper.map(advertisement, AdvertisementDetails.class);
            String start_day = advertisement.getStart_date().format(formatter);
            String expired_day = advertisement.getEnd_date().format(formatter);
            advertisementDetails.setDuration(start_day + " - " + expired_day);
            mapped_advertisements.add(advertisementDetails);
        }

        return mapped_advertisements;
    }

    public void saveAdvertisement(AdvertisementHandling advertisementHandling){
        // Format date from String
        String start_day = advertisementHandling.getStart_day();
        String end_day = advertisementHandling.getEnd_day();
        LocalDate startDay = LocalDate.parse(start_day.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDay = LocalDate.parse(end_day.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        //Format time from String
        String start_time = advertisementHandling.getStart_time();
        String end_time = advertisementHandling.getEnd_time();
        LocalTime startTime = LocalTime.parse(start_time.trim(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(end_time.trim(), DateTimeFormatter.ofPattern("HH:mm"));
        // Set seconds component to zero
        startTime = startTime.withSecond(0);
        endTime = endTime.withSecond(0);
        //Merge time and date
        LocalDateTime startDateTime = startDay.atTime(startTime);
        LocalDateTime endDateTime = endDay.atTime(endTime);
        // Mapping object
        Advertisement advertisement = mapper.map(advertisementHandling, Advertisement.class);
        advertisement.setStart_date(startDateTime);
        advertisement.setEnd_date(endDateTime);
        advertisementRepository.save(advertisement);
    }

    public void cancelAdvertisement(Integer advertisement_id){
        Optional<Advertisement> existing_advertisement = advertisementRepository.findById(advertisement_id);

        if (existing_advertisement.isPresent()){
            Advertisement advertisement = existing_advertisement.get();
            advertisementRepository.delete(advertisement);
        }
    }
}
