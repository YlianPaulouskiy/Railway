package by.itacademy.railway.service;

import by.itacademy.railway.dto.order.OrderReadDto;
import by.itacademy.railway.entity.Order;
import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.mapper.OrderMapper;
import by.itacademy.railway.repository.OrderRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketService ticketService;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<OrderReadDto> findAllByUserId(Long id) {
        return orderRepository.findAllByUserId(id).stream().map(orderMapper::toReadDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<OrderReadDto> findAllByUserId(Long id, Pageable pageable) {
        return orderRepository.findAllByUserId(id, pageable).map(orderMapper::toReadDto);
    }

    @Transactional(readOnly = true)
    public Optional<OrderReadDto> findById(@NotNull(message = "Order id can't be null") Long id) {
        return orderRepository.findById(id).map(orderMapper::toReadDto);
    }

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
