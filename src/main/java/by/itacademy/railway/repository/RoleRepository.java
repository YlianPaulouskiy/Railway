package by.itacademy.railway.repository;

import by.itacademy.railway.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByRole(String role);

    void deleteByRole(String role);

    Optional<Role> findByRole(String role);

}
