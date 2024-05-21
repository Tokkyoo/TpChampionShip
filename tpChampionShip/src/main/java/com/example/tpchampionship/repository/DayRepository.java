package com.example.tpchampionship.repository;

import com.example.tpchampionship.models.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, Long> {
    List<Day> findDayByChampionshipId(Long id);
}
