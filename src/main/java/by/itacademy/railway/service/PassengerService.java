package by.itacademy.railway.service;

import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.dto.passenger.PassengerStringDto;
import by.itacademy.railway.mapper.PassengerMapper;
import by.itacademy.railway.repository.PassengerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: 08.06.2023 доделать логирование
@Service
@Validated
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Transactional(readOnly = true)
    public List<PassengerReadDto> findAllByUserId(Long id) {
        return passengerRepository.findAllByUserId(id)
                .stream()
                .map(passengerMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<PassengerReadDto> create(@Valid PassengerStringDto passengerStringDto) {
        return Optional.ofNullable(
                passengerMapper.toReadDto(
                        passengerRepository.save(
                                passengerMapper.toModel(passengerStringDto))));
    }

    @Transactional
    public boolean remove(Long id) {
        passengerRepository.deleteById(id);
        return !passengerRepository.existsById(id);
    }

}
