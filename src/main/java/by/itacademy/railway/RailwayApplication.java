package by.itacademy.railway;

import by.itacademy.railway.entity.*;
import by.itacademy.railway.entity.embedded.OrderInfo;
import by.itacademy.railway.entity.embedded.SeatInfo;
import by.itacademy.railway.entity.embedded.TrainInfo;
import by.itacademy.railway.entity.embedded.WagonInfo;
import by.itacademy.railway.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class RailwayApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(RailwayApplication.class);
        initDb(context);
        // TODO: 28.06.2023 доделать сервисы и поиск всех элементов для работы с меню, т.е. поиск заказов, passengers по ид юсера  
    }

    private static void initDb(ConfigurableApplicationContext context) {
        var mnsStation = createStation("Minsk-Passenger");
        var grdStation = createStation("Grodno");
        var mnsInstituteCulture = createStation("Minsk Institute of Culture");
        var brnStation = createStation("Baranovichi-Poleskie");
        saveStations(context, mnsStation, mnsInstituteCulture, grdStation, brnStation);

        var train1 = createTrain("MNS-GRD11", TrainType.INTERREGIONAL);
        var train2 = createTrain("MNS-DRN11", TrainType.REGIONAL);

        saveTrains(context, train1, train2);

        var mns = createRouteStation(
                LocalDateTime.of(2023, 6, 1, 23, 51),
                null, mnsStation, train1);
        var grd = createRouteStation(
                null, LocalDateTime.of(2023, 6, 2, 6, 22),
                grdStation, train1);
        var insCult = createRouteStation(
                LocalDateTime.of(2023, 6, 1, 12, 44),
                null, mnsInstituteCulture, train2);
        var brn = createRouteStation(
                null, LocalDateTime.of(2023, 6, 1, 6, 58),
                brnStation, train2);
        saveRouteStation(context, mns, grd, insCult, brn);

        var wagon1 = createWagon((short) 1, WagonType.RESERVED, train1);
        var wagon2 = createWagon((short) 2, WagonType.COUPE, train1);
        var wagon3 = createWagon((short) 1, WagonType.RESERVED, train2);
        var wagon4 = createWagon((short) 2, WagonType.COUPE, train2);

        saveWagons(context, wagon1, wagon2, wagon3, wagon4);

        var admin = createRole("ADMIN");
        var user = createRole("USER");
        saveRoles(context, admin, user);

        var max = createPassenger("Maxin", "Yarmosh", "Konstantinovich",
                Gender.MALE, DocumentType.PASSPORT, "AB2944725");
        var kate = createPassenger("Kate", "Yarmoshyk", "Valeryevna",
                Gender.FEMALE, DocumentType.DRIVER_LICENSE, "22428191421");
        savePassengers(context, max, kate);

        List<Passenger> passengers = new ArrayList<>(List.of(max, kate));
        var maxim = createUser("Max", "White", "maxik@gmail.com",
                "qwerty123", admin, passengers);
        var katrine = createUser("Katrine", "YouAre", "kate99@gamil.com",
                "qwerty123", user, passengers);
        saveUsers(context, maxim, katrine);

        var order1 = createOrder("33123456712389", OrderStatus.NEED_PAY, maxim, null);
        var order2 = createOrder("12454218711213", OrderStatus.NEED_PAY, katrine, null);
        var order3 = createOrder("11285268129124", OrderStatus.PAYED, maxim,
                LocalDateTime.of(2023, 5, 31, 17, 15));
        var order4 = createOrder("84376753492101", OrderStatus.PAYED, katrine,
                LocalDateTime.of(2023, 5, 31, 17, 15));
        saveOrders(context, order1, order2, order3, order4);

        var ticket1 = createTicket(mns, grd, order1, max);
        var ticket2 = createTicket(mns, grd, order2, kate);
        var ticket3 = createTicket(insCult, brn, order3, max);
        var ticket4 = createTicket(insCult, brn, order4, kate);
        saveTickets(context, ticket1, ticket2, ticket3, ticket4);

        saveSeats(context,
                createSeat((short) 1, SeatType.LOWER, 13.76, ticket1, wagon1),
                createSeat((short) 2, SeatType.UPPER, 13.76, null, wagon1),
                createSeat((short) 3, SeatType.LOWER, 13.76, null, wagon1),
                createSeat((short) 4, SeatType.UPPER, 13.76, null, wagon1),
                createSeat((short) 1, SeatType.LOWER, 16.4, ticket2, wagon2),
                createSeat((short) 2, SeatType.UPPER, 16.4, null, wagon2),
                createSeat((short) 3, SeatType.LOWER, 16.4, null, wagon2),
                createSeat((short) 4, SeatType.UPPER, 16.4, null, wagon2),
                createSeat((short) 1, SeatType.LOWER, 13.76, ticket3, wagon3),
                createSeat((short) 2, SeatType.LOWER, 13.76, null, wagon3),
                createSeat((short) 3, SeatType.LOWER, 13.76, null, wagon3),
                createSeat((short) 4, SeatType.LOWER, 13.76, null, wagon3),
                createSeat((short) 1, SeatType.LOWER, 16.4, ticket4, wagon3),
                createSeat((short) 2, SeatType.LOWER, 16.4, null, wagon3),
                createSeat((short) 3, SeatType.LOWER, 16.4, null, wagon3),
                createSeat((short) 4, SeatType.LOWER, 16.4, null, wagon3));
    }

    private static void saveTickets(ConfigurableApplicationContext context, Ticket... tickets) {
        var ticketRepository = context.getBean(TicketRepository.class);
        for (Ticket ticket : tickets) {
            ticketRepository.save(ticket);
        }
    }

    private static void saveOrders(ConfigurableApplicationContext context, Order... orders) {
        var orderRepo = context.getBean(OrderRepository.class);
        for (Order order : orders) {
            orderRepo.save(order);
        }
    }

    private static void saveUsers(ConfigurableApplicationContext context, User... users) {
        var userRepo = context.getBean(UserRepository.class);
        for (User user : users) {
            userRepo.save(user);
        }
    }

    private static void saveRoles(ConfigurableApplicationContext context, Role... roles) {
        var roleRepo = context.getBean(RoleRepository.class);
        for (Role role : roles) {
            roleRepo.save(role);
        }
    }

    private static void savePassengers(ConfigurableApplicationContext context, Passenger... passengers) {
        var passengerRepo = context.getBean(PassengerRepository.class);
        for (Passenger passenger : passengers) {
            passengerRepo.save(passenger);
        }
    }

    private static void saveStations(ConfigurableApplicationContext context, Station... stations) {
        var stationRepo = context.getBean(StationRepository.class);
        for (Station station : stations) {
            stationRepo.save(station);
        }
    }

    private static void saveTrains(ConfigurableApplicationContext context, Train... trains) {
        var trainRepo = context.getBean(TrainRepository.class);
        for (Train train : trains) {
            trainRepo.save(train);
        }
    }

    private static void saveWagons(ConfigurableApplicationContext context, Wagon... wagons) {
        var wagonRepo = context.getBean(WagonRepository.class);
        for (Wagon wagon : wagons) {
            wagonRepo.save(wagon);
        }
    }

    private static void saveRouteStation(ConfigurableApplicationContext context, RouteStation... routeStations) {
        var routeStationRepo = context.getBean(RouteStationRepository.class);
        for (RouteStation routeStation : routeStations) {
            routeStationRepo.save(routeStation);
        }
    }

    private static void saveSeats(ConfigurableApplicationContext context, Seat... seats) {
        var seatRepo = context.getBean(SeatRepository.class);
        for (Seat seat : seats) {
            seatRepo.save(seat);
        }
    }

    private static User createUser(String name, String lastName, String email, String password,
                                   Role role, List<Passenger> passengers) {
        var user = User.builder()
                .name(name)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();
        user.setRole(role);
        for (Passenger passenger : passengers) {
            user.addPassengers(passenger);
        }
        return user;
    }

    private static Order createOrder(String no, OrderStatus status, User user, LocalDateTime payedTime) {
        var order = Order.builder()
                .orderInfo(OrderInfo.builder()
                        .no(no)
                        .status(status)
                        .registrationTime(LocalDateTime.now())
                        .payedTime(payedTime)
                        .build())
                .build();
        order.setUser(user);
        return order;
    }

    private static Passenger createPassenger(String name, String lastName, String middleName,
                                             Gender gender, DocumentType documentType, String documentNo) {
        return Passenger.builder()
                .name(name)
                .lastName(lastName)
                .middleName(middleName)
                .gender(gender)
                .document(documentType)
                .documentNo(documentNo)
                .build();
    }

    private static Role createRole(String role) {
        return Role.builder()
                .role(role)
                .build();
    }

    private static Train createTrain(String code, TrainType trainType) {
        return Train.builder()
                .trainInfo(TrainInfo.builder()
                        .code(code)
                        .type(trainType)
                        .build())
                .build();
    }

    private static Wagon createWagon(Short no, WagonType wagonType, Train train) {
        var wagon = Wagon.builder()
                .wagonInfo(WagonInfo.builder()
                        .no(no)
                        .type(wagonType)
                        .build())
                .build();
        wagon.setTrain(train);
        return wagon;
    }

    private static Seat createSeat(Short no, SeatType seatType, Double cost, Ticket ticket, Wagon wagon) {
        var seat = Seat.builder()
                .seatInfo(SeatInfo.builder()
                        .no(no)
                        .type(seatType)
                        .build())
                .cost(cost)
                .build();
        seat.setWagon(wagon);
        if (ticket != null) {
            seat.setTicket(ticket);
        }
        return seat;
    }

    private static Ticket createTicket(RouteStation from, RouteStation to, Order order, Passenger passenger) {
        var ticket = Ticket.builder().build();
        ticket.setFrom(from);
        ticket.setTo(to);
        ticket.setOrder(order);
        ticket.setPassenger(passenger);
        return ticket;
    }

    private static RouteStation createRouteStation(LocalDateTime departureTime, LocalDateTime arrivalTime, Station station, Train train) {
        var routeStation = RouteStation.builder()
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .build();
        routeStation.setStation(station);
        routeStation.setTrain(train);
        return routeStation;
    }

    private static Station createStation(String name) {
        return Station.builder()
                .name(name)
                .build();
    }

}
