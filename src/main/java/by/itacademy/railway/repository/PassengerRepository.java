package by.itacademy.railway.repository;

import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    boolean existsByDocumentAndDocumentNo(DocumentType document, String documentNo);

    void deleteByDocumentAndDocumentNo(DocumentType document, String documentNo);
}
