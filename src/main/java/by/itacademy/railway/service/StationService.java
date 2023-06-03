package by.itacademy.railway.service;

import by.itacademy.railway.dto.StationDto;
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
// TODO: 01.06.2023 добавить валидацию и логирование
@Validated
public class StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Transactional(readOnly = true)
    public Optional<StationDto> findStationByName(@NotBlank(message = "Station name is required") String name) {
        return stationRepository.findStationByName(name).map(stationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<StationDto> findAll() {
        return stationMapper.toListDto(stationRepository.findAll());
    }

    @Transactional
    public boolean create(@Valid StationDto stationDto) {
        stationRepository.save(stationMapper.toEntity(stationDto));
        return stationRepository.existsByName(stationDto.getName());
    }

    // TODO: 02.06.2023 доделать
    @Transactional
    public boolean remove(@Valid StationDto stationDto) {
        stationRepository.deleteByName(stationDto.getName());
//        stationRepository.deleteById(2); // работает
        return !stationRepository.existsByName(stationDto.getName());
    }

}
