package com.example.tpchampionship.controllers;


import com.example.tpchampionship.exception.ResourceNotFoundException;
import com.example.tpchampionship.models.Championship;
import com.example.tpchampionship.models.Team;
import com.example.tpchampionship.repository.ChampionShipRepository;
import com.example.tpchampionship.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired

    private TeamRepository teamRepository;

    @Autowired
    private ChampionShipRepository championShipRepository;

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = new ArrayList<Team>();

        teamRepository.findAll().forEach(teams::add);

        if (teams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(teams, HttpStatus.OK);
    }


    @GetMapping("/championships/{championshipId}/teams")
    public ResponseEntity<List<Team>> getAllTeamsByChampionShipId(@PathVariable(value = "championshipId") Long championshipId) {
        if (!championShipRepository.existsById(championshipId)) {
            throw new ResourceNotFoundException("Not found Championship with id = " + championshipId);
        }

        List<Team> teams = teamRepository.findTeamByChampionshipsId(championshipId);
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTagsById(@PathVariable(value = "id") Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Team with id = " + id));

        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @GetMapping("/teams/{teamId}/championships")
    public ResponseEntity<List<Championship>> getAllChampionshipsByTeamId(@PathVariable(value = "teamId") Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("Not found Team  with id = " + teamId);
        }

        List<Championship> championships = championShipRepository.findChampionshipsByTeamsId(teamId);
        return new ResponseEntity<>(championships, HttpStatus.OK);
    }

    @PostMapping("/championships/{championshipId}/team")
    public ResponseEntity<Team> addTeam(@PathVariable(value = "championshipId") Long championshipId, @RequestBody Team teamRequest) {
        return championShipRepository.findById(championshipId).map(championship -> {
            Team newTeam = new Team(teamRequest.getName(), teamRequest.getCreationDate());
            championship.addTeam(newTeam);
            championShipRepository.save(championship);
            return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
        }).orElseThrow(() -> new ResourceNotFoundException("Championship not found with id = " + championshipId));
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") long id, @RequestBody Team teamRequest) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeamId " + id + "not found"));

        team.setName(teamRequest.getName());
        team.setCreationDate(teamRequest.getCreationDate());

        return new ResponseEntity<>(teamRepository.save(team), HttpStatus.OK);
    }

    @DeleteMapping("/championships/{championshipId}/teams/{teamId}")
    public ResponseEntity<HttpStatus> deleteTeamFromTutorial(@PathVariable(value = "championshipId") Long championshipId, @PathVariable(value = "teamId") Long teamId) {
        Championship championship = championShipRepository.findById(championshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ChampionShip with id = " + championshipId));

        championship.removeTeam(teamId);
        championShipRepository.save(championship);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @DeleteMapping("/teams/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
        teamRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
