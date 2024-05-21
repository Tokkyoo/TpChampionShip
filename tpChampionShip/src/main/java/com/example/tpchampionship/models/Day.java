package com.example.tpchampionship.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "days")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    public Long getId() {
        return id;
    }

    public Day(String number) {
        this.number = number;
    }

    public Day() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    @OneToMany(mappedBy = "day", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Game> games;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Championship championship;
}
