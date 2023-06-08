package by.itacademy.railway.service;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.station.StationDto;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class StationServiceIT {

    private final StationService stationService;
    private static final Integer DEFAULT_SIZE = 4;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private static final String STATION_NAME = "Grodno";
    private static final String EXCEPTION_MESSAGE = "Station name is required";
    private StationDto createStationDto;

    @BeforeEach
    void setUp() {
        createStationDto = StationDto.builder()
                .name("Vitebsk")
                .build();
    }

    @Test
    void findAllTest() {
        List<StationDto> stations = stationService.findAll();
        assertThat(stations).hasSize(DEFAULT_SIZE);
        List<String> stationNames = stations.stream().map(StationDto::getName).collect(Collectors.toList());
        assertThat(stationNames).containsExactlyInAnyOrder("Minsk-Passenger", "Grodno", "Minsk Institute of Culture", "Baranovichi-Poleskie");
    }

    @Test
    void findByNameTest() {
        var optionalStation = stationService.findStationByName(STATION_NAME);
        assertThat(optionalStation).isPresent();
        optionalStation.ifPresent(stationDto -> assertThat(stationDto.getName()).isEqualTo(STATION_NAME));
    }

    @Test
    @Rollback
    void createTest() {
        assertThat(stationService.findAll()).hasSize(DEFAULT_SIZE);
        boolean isCreate = stationService.create(createStationDto);
        assertThat(isCreate).isTrue();
        assertThat(stationService.findAll()).hasSize(CREATED_SIZE);
        assertThat(stationService.findStationByName(createStationDto.getName())).isPresent();
    }

    @Test
    @Rollback
    void removeTest() {
        assertThat(stationService.findAll()).hasSize(DEFAULT_SIZE);
        boolean isRemove = stationService.remove(STATION_NAME);
        assertThat(isRemove).isTrue();
        assertThat(stationService.findAll()).hasSize(REMOVED_SIZE);
        assertThat(stationService.findStationByName(STATION_NAME)).isEmpty();
    }

    @Test
    void validationTest() {
        createStationDto.setName("");
        ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> stationService.create(createStationDto));
        assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE));
        assertThrows(ConstraintViolationException.class, () -> stationService.remove(""));
    }

}
