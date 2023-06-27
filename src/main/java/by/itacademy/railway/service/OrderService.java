package by.itacademy.railway.service;

import by.itacademy.railway.entity.Order;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.repository.OrderRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketService ticketService;

    @Transactional
    public boolean remove(@NotNull(message = "Order id can't be null") Long id) {
        deleteTicketsWhichLinkedAtOrder(id);
        orderRepository.deleteById(id);
        return !orderRepository.existsById(id);
    }

    /**
     * Удаляет ссылающие билеты на текущий заказ
     *
     * @param id идентификационный номер заказа
     */
    private void deleteTicketsWhichLinkedAtOrder(Long id) {
        orderRepository.findById(id).ifPresent(order -> order.getTickets()
                .stream()
                .map(Ticket::getId)
                .forEach(ticketService::remove));
    }

}
