package by.itacademy.railway.repository;

import by.itacademy.railway.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
