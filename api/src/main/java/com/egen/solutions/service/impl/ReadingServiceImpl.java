package com.egen.solutions.service.impl;

import com.egen.solutions.dataobject.Reading;
import com.egen.solutions.dataobject.Vehicle;
import com.egen.solutions.exception.ResourceNotFoundException;
import com.egen.solutions.repository.ReadingRepository;
import com.egen.solutions.repository.VehicleRepository;
import com.egen.solutions.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReadingServiceImpl implements ReadingService {


    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    VehicleRepository vechicleRepository;

    public List<Reading> listAll() {
        return readingRepository.listAll();
    }

    public Reading getById(String readingId) {
        Reading existingReading = readingRepository.getById(readingId);
        if(existingReading == null)
        {
            throw new ResourceNotFoundException("Reading with id " + readingId + " doesn't exist.");
        }
        return existingReading;
    }

    @Transactional
    public Reading create(Reading readingObj) {
        if (readingObj != null && readingObj.getVin() != null) {
            Vehicle existingVehicle = vechicleRepository.getById(readingObj.getVin());
            if (existingVehicle != null) {
                 readingRepository.create(readingObj);
            }
        }
        return readingObj;
    }

    @Transactional
    public Reading update(String readingId, Reading readingObj) {
        Reading existingReading = readingRepository.getById(readingId);
        if (existingReading == null) {
            throw new ResourceNotFoundException("Reading with id " + readingId + " doesn't exist.");
        }
        return readingRepository.update(readingObj);
    }

    @Transactional
    public void delete(String readingId) {
        Reading existingReading = readingRepository.getById(readingId);
        if (existingReading == null) {
            throw new ResourceNotFoundException("Vehicle with id " + readingId + " doesn't exist.");
        }
        readingRepository.delete(existingReading);
    }

}