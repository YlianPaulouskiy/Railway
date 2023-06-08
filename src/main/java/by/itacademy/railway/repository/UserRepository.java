package by.itacademy.railway.repository;

import by.itacademy.railway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    Optional<User> login(String email, String password);

}
