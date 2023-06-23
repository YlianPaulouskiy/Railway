package by.itacademy.railway.service;

import by.itacademy.railway.dto.station.StationReadDto;
import by.itacademy.railway.dto.station.StationStringDto;
import by.itacademy.railway.mapper.StationMapper;
import by.itacademy.railway.repository.StationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    public boolean remove(Integer id) {
        stationRepository.deleteById(id);
        return !stationRepository.existsById(id);
    }

}
