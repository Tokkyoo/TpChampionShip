package com.example.tpchampionship.controllers;

import com.example.tpchampionship.exception.ResourceNotFoundException;
import com.example.tpchampionship.models.Championship;
import com.example.tpchampionship.models.Game;
import com.example.tpchampionship.models.Team;
import com.example.tpchampionship.repository.ChampionShipRepository;
import com.example.tpchampionship.repository.DayRepository;
import com.example.tpchampionship.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    ChampionShipRepository championShipRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    DayRepository dayRepository;

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames()
    {
        List<Game> games = new ArrayList<Game>();

        gameRepository.findAll().forEach(games::add);

        return new ResponseEntity<>(games, HttpStatus.OK);
    }


    @GetMapping("/day/{dayId}/game")
    public ResponseEntity<List<Game>> getAllGameByDayId(@PathVariable(value = "dayId") Long dayId) {

        if (!dayRepository.existsById(dayId)) {
            throw new ResourceNotFoundException("Not found Day with id = " + dayId);
        }

        List<Game> games = gameRepository.findGamesByDayId(dayId);
        return new ResponseEntity<>(games, HttpStatus.OK);

    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getGameByGameId(@PathVariable(value = "gameId") Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Game with id = " + gameId));

        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("/games")
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        try {
            // Enregistrer le jeu dans la base de données
            Game newGame = gameRepository.save(game);
            return new ResponseEntity<>(newGame, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable("id") Long id, @RequestBody Game gameDetails) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + id));


        game.setTeam1Point(gameDetails.getTeam1Point());
        game.setTeam2point(gameDetails.getTeam2point());

        final Game updatedGame = gameRepository.save(game);
        return ResponseEntity.ok(updatedGame);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<HttpStatus> deleteGameById(@PathVariable("id") Long id) {
        try {
            gameRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





}
