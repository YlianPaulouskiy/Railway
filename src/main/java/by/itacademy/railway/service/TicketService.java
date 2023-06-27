package by.itacademy.railway.service;

import by.itacademy.railway.repository.TicketRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public boolean remove(@NotNull(message = "Ticket id can't be null") Long id) {
        setNullToLinkedSeat(id);
        ticketRepository.deleteById(id);
        return !ticketRepository.existsById(id);
    }

    /**
     * Устанавливает значение поля ticket у Seat в null для удаляемого билета
     *
     * @param id идентификационный номер билета
     */
    private void setNullToLinkedSeat(Long id) {
        ticketRepository.findById(id).ifPresent(ticket -> ticket.getSeat().setTicket(null));
    }

}
