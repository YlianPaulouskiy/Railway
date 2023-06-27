package by.itacademy.railway.service;

import by.itacademy.railway.dto.station.StationReadDto;
import by.itacademy.railway.dto.station.StationStringDto;
import by.itacademy.railway.entity.RouteStation;
import by.itacademy.railway.mapper.StationMapper;
import by.itacademy.railway.repository.RouteStationRepository;
import by.itacademy.railway.repository.StationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// TODO: 01.06.2023 добавить логирование
@Validated
public class StationService {

    private final StationRepository stationRepository;
    private final RouteStationRepository routeStationRepository;
    private final RouteStationService routeStationService;
    private final StationMapper stationMapper;

    @Transactional(readOnly = true)
    public List<StationReadDto> findAll() {
        return stationMapper.toListDto(stationRepository.findAll());
    }

    @Transactional
    public Optional<StationReadDto> create(@Valid StationStringDto stationStringDto) {
        return Optional.ofNullable(
                stationMapper.toDto(
                        stationRepository.save(
                                stationMapper.toEntity(stationStringDto))));
    }

    @Transactional
    public boolean remove(@NotNull(message = "Station id can't be null") Integer id) {
        deleteRouteStationWhichLinkedAtStation(id);
        stationRepository.deleteById(id);
        return !stationRepository.existsById(id);
    }

    /**
     * Удаляет пункты маршрута которые ссылаются на текущую станцию
     *
     * @param id идентификационный номер станции
     */
    private void deleteRouteStationWhichLinkedAtStation(Integer id) {
        stationRepository.findById(id).ifPresent(station -> station.getRouteStations()
                .stream()
                .map(RouteStation::getId)
                .forEach(routeStationService::remove));
    }

}
