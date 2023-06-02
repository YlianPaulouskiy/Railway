package by.itacademy.railway.configuration;

import by.itacademy.railway.entity.*;
import by.itacademy.railway.entity.embedded.OrderInfo;
import by.itacademy.railway.entity.embedded.SeatInfo;
import by.itacademy.railway.entity.embedded.TrainInfo;
import by.itacademy.railway.entity.embedded.WagonInfo;
import by.itacademy.railway.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TestConfig {

    @Bean
    public CommandLineRunner dataLoader(@Autowired StationRepository stationRepo,
                                        @Autowired TrainRepository trainRepo, @Autowired RoleRepository roleRepo,
                                        @Autowired PassengerRepository passengerRepo, @Autowired UserRepository userRepo) {
        return args -> {
            var mnsStation = createStation("Minsk-Passenger");
            var grdStation = createStation("Grodno");
            var mnsInstituteCulture = createStation("Minsk Institute of Culture");
            var brnStation = createStation("Baranovichi-Poleskie");

            stationRepo.save(mnsStation);
            stationRepo.save(grdStation);
            stationRepo.save(mnsInstituteCulture);
            stationRepo.save(brnStation);

            var mns = createRouteStation(
                    LocalDateTime.of(2023, 6, 1, 23, 51),
                    null,
                    mnsStation);
            var grd = createRouteStation(
                    null,
                    LocalDateTime.of(2023, 6, 2, 6, 22),
                    grdStation);
            var insCult = createRouteStation(
                    LocalDateTime.of(2023, 6, 1, 12, 44),
                    null,
                    mnsInstituteCulture);
            var brn = createRouteStation(
                    null,
                    LocalDateTime.of(2023, 6, 1, 6, 58),
                    brnStation);

            var mnsGrd = createRoute(mns, grd);
            var mnsBrn = createRoute(insCult, brn);

            var train1 = createTrain("MNS-GRD11", TrainType.INTERREGIONAL, mnsGrd,
                    createWagon((short) 1, WagonType.RESERVED,
                            createSeat((short) 1, SeatType.LOWER, createTicket(13.76)),
                            createSeat((short) 2, SeatType.UPPER, createTicket(13.76)),
                            createSeat((short) 3, SeatType.LOWER, createTicket(13.76)),
                            createSeat((short) 4, SeatType.UPPER, createTicket(13.76))),
                    createWagon((short) 2, WagonType.COUPE,
                            createSeat((short) 1, SeatType.LOWER, createTicket(16.4)),
                            createSeat((short) 2, SeatType.UPPER, createTicket(16.4)),
                            createSeat((short) 3, SeatType.LOWER, createTicket(16.4)),
                            createSeat((short) 4, SeatType.UPPER, createTicket(16.4))));
            var train2 = createTrain("MNS-DRN11", TrainType.REGIONAL, mnsBrn,
                    createWagon((short) 1, WagonType.RESERVED,
                            createSeat((short) 1, SeatType.LOWER, createTicket(13.76)),
                            createSeat((short) 2, SeatType.LOWER, createTicket(13.76)),
                            createSeat((short) 3, SeatType.LOWER, createTicket(13.76)),
                            createSeat((short) 4, SeatType.LOWER, createTicket(13.76))),
                    createWagon((short) 2, WagonType.COUPE,
                            createSeat((short) 1, SeatType.LOWER, createTicket(16.4)),
                            createSeat((short) 2, SeatType.LOWER, createTicket(16.4)),
                            createSeat((short) 3, SeatType.LOWER, createTicket(16.4)),
                            createSeat((short) 4, SeatType.LOWER, createTicket(16.4))));
            trainRepo.save(train1);
            trainRepo.save(train2);

            var admin = createRole("ADMIN");
            var user = createRole("USER");

            roleRepo.save(admin);
            roleRepo.save(user);

            var max = createPassenger("Maxin", "Yarmosh", "Konstantinovich",
                    Gender.MALE, DocumentType.PASSPORT, "AB2944725");
            var kate = createPassenger("Kate", "Yarmoshyk", "Valeryevna",
                    Gender.FEMALE, DocumentType.DRIVER_LICENSE, "22428191421");

            passengerRepo.save(max);
            passengerRepo.save(kate);

            var tr1w1t1 = train1.getWagons().get(0).getSeats().get(0).getTicket();
            tr1w1t1.setPassenger(max);
            var tr1w2t2 = train1.getWagons().get(1).getSeats().get(0).getTicket();
            tr1w2t2.setPassenger(kate);
            var tr2w1t1 = train2.getWagons().get(0).getSeats().get(0).getTicket();
            tr2w1t1.setPassenger(max);
            var tr2w2t2 = train2.getWagons().get(1).getSeats().get(0).getTicket();
            tr2w2t2.setPassenger(kate);

            var order1 = createOrder("33123456712389", OrderStatus.NEED_PAY, null, tr1w1t1);
            var order2 = createOrder("12454218711213", OrderStatus.NEED_PAY, null, tr1w2t2);
            var order3 = createOrder("11285268129124", OrderStatus.PAYED,
                    LocalDateTime.of(2023, 5, 31, 17, 15), tr2w1t1);
            var order4 = createOrder("84376753492101", OrderStatus.PAYED,
                    LocalDateTime.of(2023, 5, 31, 17, 15), tr2w2t2);

            List<Passenger> passengers = new ArrayList<>(List.of(max, kate));

            var maxim = createUser("Max", "White", "maxik@gmail.com",
                    "qwerty123", admin, passengers, order1, order2);
            var katrine = createUser("Katrine", "YouAre", "kate99@gamil.com",
                    "qwerty123", user, passengers, order3, order4);

            userRepo.save(maxim);
            userRepo.save(katrine);
        };
    }

    private User createUser(String name, String lastName, String email, String password,
                            Role role, List<Passenger> passengers, Order... orders) {
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
        user.addOrders(orders);
        return user;
    }

    private Order createOrder(String no, OrderStatus status, LocalDateTime payedTime, Ticket... tickets) {
        var order = Order.builder()
                .orderInfo(OrderInfo.builder()
                        .no(no)
                        .status(status)
                        .payedTime(payedTime)
                        .build())
                .build();
        order.addTickets(tickets);
        return order;
    }

    private Passenger createPassenger(String name, String lastName, String middleName,
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

    private Role createRole(String role) {
        return Role.builder()
                .role(role)
                .build();
    }

    private Train createTrain(String code, TrainType trainType, Route route, Wagon... wagons) {
        var train = Train.builder()
                .trainInfo(TrainInfo.builder()
                        .code(code)
                        .type(trainType)
                        .build())
                .route(route)
                .build();
        train.addWagons(wagons);
        return train;
    }

    private Wagon createWagon(Short no, WagonType wagonType, Seat... seats) {
        var wagon = Wagon.builder()
                .wagonInfo(WagonInfo.builder()
                        .no(no)
                        .type(wagonType)
                        .build())
                .build();
        wagon.addSeats(seats);
        return wagon;
    }

    private Seat createSeat(Short no, SeatType seatType, Ticket ticket) {
        var seat = Seat.builder()
                .seatInfo(SeatInfo.builder()
                        .no(no)
                        .type(seatType)
                        .build())
                .build();
        seat.setTicket(ticket);
        return seat;
    }

    private Ticket createTicket(Double cost) {
        return Ticket.builder()
                .cost(cost)
                .build();
    }

    private Route createRoute(RouteStation... routeStations) {
        Route route = new Route();
        route.addRouteStation(routeStations);
        return route;
    }

    private RouteStation createRouteStation(LocalDateTime departureTime, LocalDateTime arrivalTime, Station station) {
        return RouteStation.builder()
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .station(station)
                .build();
    }

    private Station createStation(String name) {
        return Station.builder()
                .name(name)
                .build();
    }

}
