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

// TODO: 08.06.2023 доделать логирование
@Service
@Validated
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Transactional
    public boolean create(@Valid PassengerStringDto passengerStringDto) {
        passengerRepository.save(passengerMapper.toModel(passengerStringDto));
        return passengerRepository.existsByDocumentAndDocumentNo(passengerStringDto.getDocument(),
                passengerStringDto.getDocumentNo());
    }

    @Transactional
    public boolean remove(@NotNull(message = "PassengerReadDto can't be null") PassengerReadDto passengerReadDto) {
        passengerRepository.deleteByDocumentAndDocumentNo(passengerReadDto.getDocument(),
                passengerReadDto.getDocumentNo());
        return !passengerRepository.existsByDocumentAndDocumentNo(passengerReadDto.getDocument(),
                passengerReadDto.getDocumentNo());
    }

}
