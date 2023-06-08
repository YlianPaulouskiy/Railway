package by.itacademy.railway.service;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.dto.passenger.PassengerStringDto;
import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import by.itacademy.railway.mapper.PassengerMapper;
import by.itacademy.railway.repository.PassengerRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IT
@RequiredArgsConstructor
public class PassengerServiceIT {

    private final PassengerService passengerService;
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private static final Integer DEFAULT_SIZE = 2;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private PassengerStringDto createPassengerDto;
    private PassengerReadDto removePassengerDto;

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
        var optPassenger = passengerRepository.findById(1L);
        optPassenger.ifPresent(passenger -> removePassengerDto = passengerMapper.toReadDto(passenger));
    }

    @Test
    @Rollback
    void createTest() {
        assertThat(passengerRepository.findAll()).hasSize(DEFAULT_SIZE);
        boolean isCreate = passengerService.create(createPassengerDto);
        assertThat(isCreate).isTrue();
        assertThat(passengerRepository.findAll()).hasSize(CREATED_SIZE);
    }

    @Test
    @Rollback
    void removeTest() {
        assertThat(passengerRepository.findAll()).hasSize(DEFAULT_SIZE);
        boolean isRemove = passengerService.remove(removePassengerDto);
        assertThat(isRemove).isTrue();
        assertThat(passengerRepository.findAll()).hasSize(REMOVED_SIZE);
    }

    @Test
    void validationTest() {
        createPassengerDto.setName(null);
        assertThrows(ConstraintViolationException.class, () -> passengerService.create(createPassengerDto));
        createPassengerDto.setName("Name");
        createPassengerDto.setLastName("");
        assertThrows(ConstraintViolationException.class, () -> passengerService.create(createPassengerDto));
        createPassengerDto.setLastName("LAStName");
        createPassengerDto.setMiddleName("");
        assertThrows(ConstraintViolationException.class, () -> passengerService.create(createPassengerDto));
        createPassengerDto.setMiddleName("midName");
        createPassengerDto.setGender(null);
        assertThrows(ConstraintViolationException.class, () -> passengerService.create(createPassengerDto));
        createPassengerDto.setGender(Gender.MALE);
        createPassengerDto.setDocument(null);
        assertThrows(ConstraintViolationException.class, () -> passengerService.create(createPassengerDto));
        createPassengerDto.setDocument(DocumentType.STUDENT_ID_CARD);
        createPassengerDto.setDocumentNo(" ");
        assertThrows(ConstraintViolationException.class, () -> passengerService.create(createPassengerDto));
    }
}
