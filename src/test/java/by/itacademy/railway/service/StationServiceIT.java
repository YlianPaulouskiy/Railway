package by.itacademy.railway.service;

import by.itacademy.railway.annotation.IT;
import by.itacademy.railway.dto.station.StationReadDto;
import by.itacademy.railway.dto.station.StationStringDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class StationServiceIT {

    private final StationService stationService;
    private static final Integer DEFAULT_SIZE = 4;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private static final Integer REMOVE_STATION_ID = 1;
    private StationStringDto createStationDto;

    @BeforeEach
    void setUp() {
        createStationDto = StationStringDto.builder()
                .name("Vitebsk")
                .build();
    }

    @Test
    void findAllTest() {
        List<StationReadDto> stations = stationService.findAll();
        assertThat(stations).hasSize(DEFAULT_SIZE);
        List<String> stationNames = stations.stream().map(StationReadDto::getName).collect(Collectors.toList());
        assertThat(stationNames).containsExactlyInAnyOrder("Minsk-Passenger", "Grodno", "Minsk Institute of Culture", "Baranovichi-Poleskie");
    }

    @Test
    @Rollback
    void createTest() {
        assertThat(stationService.findAll()).hasSize(DEFAULT_SIZE);
        var stationDto = stationService.create(createStationDto);
        assertThat(stationDto).isPresent();
        assertThat(stationService.findAll()).hasSize(CREATED_SIZE);
    }

    @Test
    @Rollback
    void removeTest() {
        assertThat(stationService.findAll()).hasSize(DEFAULT_SIZE);
        boolean isRemove = stationService.remove(REMOVE_STATION_ID);
        assertThat(isRemove).isTrue();
        assertThat(stationService.findAll()).hasSize(REMOVED_SIZE);
    }

}
