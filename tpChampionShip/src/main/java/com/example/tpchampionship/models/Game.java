package com.example.tpchampionship.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int team1Point;
    private int team2point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Team team1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Team team2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Day day;

    public Game(int team1Point, int team2point) {
        this.team1Point = team1Point;
        this.team2point = team2point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTeam1Point() {
        return team1Point;
    }

    public void setTeam1Point(int team1Point) {
        this.team1Point = team1Point;
    }

    public int getTeam2point() {
        return team2point;
    }

    public void setTeam2point(int team2point) {
        this.team2point = team2point;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Game(){

    }



}
