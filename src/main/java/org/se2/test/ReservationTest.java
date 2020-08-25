package org.se2.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.se2.hausarbeit.model.dao.ReservationDao;
import org.se2.hausarbeit.model.entity.Car;
import org.se2.hausarbeit.model.entity.Reservation;
import org.se2.hausarbeit.process.exception.DatabaseException;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ReservationTest {

    private static ReservationDao dao = null;

    @BeforeEach
    public void daoSetup() throws DatabaseException {
        dao = new ReservationDao();
    }

    @Test
    public void reservationRoundTripTest() throws DatabaseException {
        // get a car
        Car car = dao.search("C".split(" "), 1).get(0).getCar();

        // add Reservation
        dao.addReservation(car.getId(), 1);
        // get Reservation
        Stream<Reservation> stream = dao.getReservationsForUser(1).stream().filter(r -> r.getCar().getId() == car.getId());
        assertTrue(stream.count() == 1);
        // delete Reservation
        dao.deleteReservation(dao.getReservationsForUser(1).stream().filter(r -> r.getCar().getId() == car.getId()).iterator().next().getId());
        assertTrue(dao.getReservationsForUser(1).stream().filter(r -> r.getCar().getId() == car.getId()).count() == 0);
    }
}
