package by.itacademy.railway.service;

import by.itacademy.railway.entity.Ticket;
import by.itacademy.railway.repository.RouteStationRepository;
import by.itacademy.railway.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RouteStationService {

    private final RouteStationRepository routeStationRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    @Transactional
    public boolean remove(Long id) {
        deleteTicketsWhichLinkedAtRouteStation(id);
        routeStationRepository.deleteById(id);
        return !routeStationRepository.existsById(id);
    }

    /**
     * Удаляет билеты которые ссылаются на этот пункт маршрута
     *
     * @param id идентификационный номер пункта маршрута
     */
    private void deleteTicketsWhichLinkedAtRouteStation(Long id) {
        routeStationRepository.findById(id).ifPresent(routeStation -> {
            routeStation.getFromTickets().stream().map(Ticket::getId).forEach(ticketService::remove);
            routeStation.getToTickets().stream().map(Ticket::getId).forEach(ticketService::remove);
        });
    }

}
