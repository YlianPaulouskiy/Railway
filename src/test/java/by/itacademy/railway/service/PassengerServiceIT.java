package by.itacademy.railway.service;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.passenger.PassengerStringDto;
import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import by.itacademy.railway.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class PassengerServiceIT {

    private final PassengerService passengerService;
    private final PassengerRepository passengerRepository;
    private static final Integer DEFAULT_SIZE = 2;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private PassengerStringDto createPassengerDto;
    private static final Long REMOVE_ID = 1L;

    @BeforeEach
    void setUp() {
        createPassengerDto = PassengerStringDto.builder()
                .name("Nastya")
                .lastName("Kadirkylova")
                .middleName("Rystamovna")
                .gender(Gender.FEMALE)
                .document(DocumentType.PASSPORT)
                .documentNo("BB12323412")
                .build();
    }

    @Test
    @Rollback
    void createTest() {
        assertThat(passengerRepository.findAll()).hasSize(DEFAULT_SIZE);
        var dtoOptional = passengerService.create(createPassengerDto);
        assertThat(dtoOptional).isPresent();
        assertThat(passengerRepository.findAll()).hasSize(CREATED_SIZE);
    }

    @Test
    @Rollback
    void removeTest() {
        assertThat(passengerRepository.findAll()).hasSize(DEFAULT_SIZE);
        boolean isRemove = passengerService.remove(REMOVE_ID);
        assertThat(isRemove).isTrue();
        assertThat(passengerRepository.findAll()).hasSize(REMOVED_SIZE);
    }

}
