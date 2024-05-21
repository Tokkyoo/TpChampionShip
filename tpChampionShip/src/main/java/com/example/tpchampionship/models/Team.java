package com.example.tpchampionship.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name cannot be empty")
    @NotNull(message = " name cannot not be null")
    private String name;


    private LocalDate creationDate;

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Team(String name, LocalDate creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "teams")
    @JsonIgnore
    private Set<Championship> championships = new HashSet<>();

    @OneToMany(mappedBy = "team1")
    private List<Game> gamesTeam1;

    @OneToMany(mappedBy = "team2")
    private List<Game> gamesTeam2;

    public Team(){

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

    public Set<Championship> getChampionships() {
        return championships;
    }

    public void setChampionships(Set<Championship> championships) {
        this.championships = championships;
    }
}
