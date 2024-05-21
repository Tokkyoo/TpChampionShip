package com.example.tpchampionship.repository;

import com.example.tpchampionship.models.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionShipRepository extends JpaRepository<Championship, Long> {


    List<Championship> findChampionshipsByTeamsId(Long teamId);

    List<Championship> findByNameContaining(String name);
}
