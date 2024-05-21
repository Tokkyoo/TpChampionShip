package com.example.tpchampionship.controllers;


import com.example.tpchampionship.exception.ResourceNotFoundException;
import com.example.tpchampionship.models.Championship;
import com.example.tpchampionship.repository.ChampionShipRepository;
import com.example.tpchampionship.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ChampionShipController {


    @Autowired
    ChampionShipRepository championShipRepository;

    @GetMapping("/championships")
    public ResponseEntity<List<Championship>> getAllChampionships(@RequestParam(required = false) String name){
        List<Championship> championships = new ArrayList<Championship>();

        if(name == null)
            championShipRepository.findAll().forEach(championships::add);
        else
            championShipRepository.findByNameContaining(name).forEach(championships::add);

        if(championships.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

        return new ResponseEntity<>(championships, HttpStatus.OK);

    }

    @GetMapping("/championships/{id}")
    public ResponseEntity<Championship> getChampionShipById(@PathVariable("id") long id) {
        Championship championship = championShipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Championship with id = " + id));

        return new ResponseEntity<>(championship, HttpStatus.OK);
    }

    @PostMapping("/championships")
    public ResponseEntity<Championship> createChampionShip(@RequestBody Championship championship) {
        Championship _championship = championShipRepository.save(new Championship(championship.getName(), championship.getStartDate(), championship.getEndDate(), championship.getWonPoint(), championship.getLostPoint(), championship.getDrawPoint()));
        return new ResponseEntity<>(_championship, HttpStatus.CREATED);
    }

    @PutMapping("/championships/{id}")
    public ResponseEntity<Championship> updateChampionship(@PathVariable("id") long id, @RequestBody Championship championship) {
        Championship _championship = championShipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ChampionShip with id = " + id));

        _championship.setName(championship.getName());
        _championship.setStartDate(championship.getStartDate());
        _championship.setEndDate(championship.getEndDate());
        _championship.setWonPoint(championship.getWonPoint());
        _championship.setLostPoint(championship.getLostPoint());
        _championship.setDrawPoint(championship.getDrawPoint());

        return new ResponseEntity<>(championShipRepository.save(_championship), HttpStatus.OK);
    }


    @DeleteMapping("/champsionships/{id}")
    public ResponseEntity<HttpStatus> deleteChampionship(@PathVariable("id") long id) {
        championShipRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/champsionships")
    public ResponseEntity<HttpStatus> deleteAllChampionships() {
        championShipRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
