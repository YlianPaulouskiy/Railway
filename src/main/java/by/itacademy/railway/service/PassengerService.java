package by.itacademy.railway.service;

import by.itacademy.railway.dto.PassengerReadDto;
import by.itacademy.railway.dto.PassengerStringDto;
import by.itacademy.railway.mapper.PassengerMapper;
import by.itacademy.railway.repository.PassengerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

// TODO: 08.06.2023 доделать логирование и валидацию
@Service
@Validated
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public boolean create(@Valid PassengerStringDto passengerStringDto) {
        passengerRepository.save(passengerMapper.toModel(passengerStringDto));
        return passengerRepository.existsByDocumentAndDocumentNo(passengerStringDto.getDocument(),
                passengerStringDto.getDocumentNo());
    }

    public boolean remove(PassengerReadDto passengerReadDto) {
        passengerRepository.deleteByDocumentAndDocumentNo(passengerReadDto.getDocument(),
                passengerReadDto.getDocumentNo());
        return !passengerRepository.existsByDocumentAndDocumentNo(passengerReadDto.getDocument(),
                passengerReadDto.getDocumentNo());
    }

}
