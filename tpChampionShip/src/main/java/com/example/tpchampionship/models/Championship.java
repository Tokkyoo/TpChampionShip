package com.example.tpchampionship.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "championships")
public class Championship {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name cannot be empty")
    @NotNull(message = " name cannot not be null")
    private String name;

    @NotNull(message = "startDate cannot be null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat()
    private LocalDate startDate;

    @NotNull(message = "startDate cannot be null")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat()
    private LocalDate endDate;


    private int wonPoint;
    private int lostPoint;
    private int drawPoint;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "TeamChampionShips",
            joinColumns = {@JoinColumn(name = "teamchampionship_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")})
    private Set<Team> teams = new HashSet<>();

    public Championship(String name, LocalDate startDate, LocalDate endDate, int wonPoint, int lostPoint, int drawPoint) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.wonPoint = wonPoint;
        this.lostPoint = lostPoint;
        this.drawPoint = drawPoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getWonPoint() {
        return wonPoint;
    }

    public void setWonPoint(int wonPoint) {
        this.wonPoint = wonPoint;
    }

    public int getLostPoint() {
        return lostPoint;
    }

    public void setLostPoint(int lostPoint) {
        this.lostPoint = lostPoint;
    }

    public int getDrawPoint() {
        return drawPoint;
    }

    public void setDrawPoint(int drawPoint) {
        this.drawPoint = drawPoint;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Championship() {

    }

    public void addTeam(Team team) {
        this.teams.add(team);
        team.getChampionships().add(this);
    }

    public void removeTeam(long teamId) {
        Team team = this.teams.stream().filter(t -> t.getId() == teamId).findFirst().orElse(null);
        if (team != null) {
            this.teams.remove(team);
            team.getChampionships().remove(this);
        }


    }

    @OneToMany(mappedBy = "championship", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Day> days;

}
