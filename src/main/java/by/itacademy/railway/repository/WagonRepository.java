package by.itacademy.railway.repository;

import by.itacademy.railway.entity.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WagonRepository extends JpaRepository<Wagon, Long> {
}
