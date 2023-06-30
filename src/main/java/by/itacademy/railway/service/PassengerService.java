package by.itacademy.railway.service;

import by.itacademy.railway.dto.passenger.PassengerReadDto;
import by.itacademy.railway.dto.passenger.PassengerStringDto;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.mapper.PassengerMapper;
import by.itacademy.railway.repository.PassengerRepository;
import by.itacademy.railway.repository.TicketRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final TicketService ticketService;
    private final PassengerMapper passengerMapper;

    @Transactional(readOnly = true)
    public List<PassengerReadDto> findAllByUserId(Long id) {
        return passengerRepository.findAllByUserId(id).stream()
                .map(passengerMapper::toReadDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PassengerReadDto> findAllByUserId(Long id, Pageable pageable) {
        return passengerRepository.findAllByUserId(id, pageable)
                .map(passengerMapper::toReadDto);
    }

    @Transactional
    public Optional<PassengerReadDto> create(@Valid PassengerStringDto passengerStringDto) {
        return Optional.ofNullable(
                passengerMapper.toReadDto(
                        passengerRepository.save(
                                passengerMapper.toModel(passengerStringDto))));
    }

    @Transactional
    public Optional<PassengerReadDto> update(@NotNull(message = "Passenger id can't be null")Long id, @Valid PassengerStringDto passengerStringDto) {
        var passenger = passengerMapper.toModel(passengerStringDto);
        passenger.setId(id);
        passengerRepository.save(passenger);
        passengerRepository.flush();
        return Optional.of(passengerMapper.toReadDto(passenger));
    }

    @Transactional
    public boolean remove(@NotNull(message = "Passenger id can't be null") Long id) {
        deleteTicketWhichLinkedAtPassenger(id);
        passengerRepository.deleteById(id);
        return !passengerRepository.existsById(id);
    }

    /**
     * Удаляет ссылающие билеты на текущего пассажира
     *
     * @param id идентификационный номер пассажира
     */
    private void deleteTicketWhichLinkedAtPassenger(Long id) {
        passengerRepository.findById(id).ifPresent(passenger -> passenger.getTickets()
                .stream()
                .map(Ticket::getId)
                .forEach(ticketService::remove));
    }

}
