package by.itacademy.railway;

import by.itacademy.railway.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RailwayApplicationTests {

    @Test
    void contextLoads(@Autowired StationRepository stationRepository) {
        stationRepository.findAll().forEach(System.out::println);
    }

}
