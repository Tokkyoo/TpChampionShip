package com.example.tpchampionship.controllers;


import com.example.tpchampionship.exception.ResourceNotFoundException;
import com.example.tpchampionship.models.Day;
import com.example.tpchampionship.models.Game;
import com.example.tpchampionship.repository.ChampionShipRepository;
import com.example.tpchampionship.repository.DayRepository;
import com.example.tpchampionship.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DayController {

    @Autowired
    ChampionShipRepository championShipRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    DayRepository dayRepository;

    @GetMapping("/days")
    public ResponseEntity<List<Day>> getAllDays()
    {
        List<Day> days = new ArrayList<Day>();

        dayRepository.findAll().forEach(days::add);

        return new ResponseEntity<>(days, HttpStatus.OK);
    }


    @GetMapping("/day/{championshipId}/game")
    public ResponseEntity<List<Day>> getAllDayByChampionshipId(@PathVariable(value = "championshipId") Long championshipId) {

        if (!championShipRepository.existsById(championshipId)) {
            throw new ResourceNotFoundException("Not found Day with id = " + championshipId);
        }

        List<Day> days = dayRepository.findDayByChampionshipId(championshipId);
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    @GetMapping("/day/{dayId}")
    public ResponseEntity<Day> getDayByDayId(@PathVariable(value = "dayId") Long dayId) {

        Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Game with id = " + dayId));

        return new ResponseEntity<>(day, HttpStatus.OK);
    }

    @PostMapping("/days")
    public ResponseEntity<Day> addDay(@RequestBody Day day) {
        try {

            Day newDay = dayRepository.save(day);
            return new ResponseEntity<>(newDay, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/days/{id}")
    public ResponseEntity<Day> updateDay(@PathVariable("id") Long id, @RequestBody Day dayDetails) {
        Day day = dayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Day not found with id: " + id));

        day.setNumber(dayDetails.getNumber());


        final Day updatedDay = dayRepository.save(day);
        return new ResponseEntity<>(updatedDay, HttpStatus.OK);
    }

    @DeleteMapping("/days/{id}")
    public ResponseEntity<HttpStatus> deleteDayById(@PathVariable("id") Long id) {
        try {
            dayRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
