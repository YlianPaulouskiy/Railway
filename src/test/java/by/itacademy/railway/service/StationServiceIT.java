package by.itacademy.railway.service;

import by.itacademy.railway.RailwayApplication;
import by.itacademy.railway.dto.StationDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RailwayApplication.class)
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
public class StationServiceIT {

    private final StationService stationService;
    private static final Integer DEFAULT_SIZE = 4;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private static final String STATION_NAME = "Grodno";
    private StationDto createStationDto;
    private StationDto removeStationDto;

    @BeforeEach
    void setUp() {
        createStationDto = StationDto.builder()
                .name("Vitebsk")
                .build();
        removeStationDto = StationDto.builder()
                .name(STATION_NAME)
                .build();
    }


    @Test
    void findAllTest() {
        List<StationDto> stations = stationService.findAll();
        assertThat(stations).hasSize(DEFAULT_SIZE);
        List<String> stationNames = stations.stream().map(StationDto::getName).collect(Collectors.toList());
        System.out.println(stationNames);
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
        var list = stationService.findAll();
        assertThat(stationService.findAll()).hasSize(DEFAULT_SIZE);
        boolean isRemove = stationService.remove(createStationDto);
        assertThat(isRemove).isTrue();
        list = stationService.findAll();
        assertThat(stationService.findAll()).hasSize(REMOVED_SIZE);
        assertThat(stationService.findStationByName(removeStationDto.getName())).isEmpty();
    }
}
