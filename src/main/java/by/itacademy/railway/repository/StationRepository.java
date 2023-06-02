package by.itacademy.railway.repository;

import by.itacademy.railway.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {

    Optional<Station> findStationByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);

}
