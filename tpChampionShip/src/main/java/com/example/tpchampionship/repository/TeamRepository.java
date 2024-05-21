package com.example.tpchampionship.repository;

import com.example.tpchampionship.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findTeamByChampionshipsId(Long championShipId);
}
