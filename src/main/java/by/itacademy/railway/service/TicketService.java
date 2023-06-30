package by.itacademy.railway.service;

import by.itacademy.railway.entity.Order;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.repository.OrderRepository;
import by.itacademy.railway.repository.TicketRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public boolean remove(@NotNull(message = "Ticket id can't be null") Long id) {
        setNullToLinkedSeat(id);
        var ticketOpt = ticketRepository.findById(id);
        ticketRepository.deleteById(id);
        deleteOrderWhichLinkedAtTicket(ticketOpt);
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

    /**
     * Удаляет заказ, если у него нету больше билетов которые на него ссылаются
     *
     * @param ticketOpt обертка билета, заказ которого нужно проверить
     */
    private void deleteOrderWhichLinkedAtTicket(Optional<Ticket> ticketOpt) {
        if (ticketOpt.isPresent()) {
            orderRepository.flush(); // чтобы в кэше ничего не осталось
            var orderOpt = orderRepository.findById(ticketOpt.get().getOrder().getId());
            if (orderOpt.isPresent() && orderOpt.get().getTickets().isEmpty()) {
                orderRepository.deleteById(orderOpt.get().getId());
            }
        }
    }

}
