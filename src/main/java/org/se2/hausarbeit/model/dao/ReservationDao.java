package org.se2.hausarbeit.model.dao;

import org.se2.hausarbeit.model.entity.Car;
import org.se2.hausarbeit.model.entity.Reservation;
import org.se2.hausarbeit.process.exception.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDao extends BaseDao {
    public ReservationDao() throws DatabaseException {}

    public ArrayList<Reservation> search(String[] input, int userId) throws DatabaseException {
        ArrayList<Reservation> result = null;
        try {
            String sql = "SELECT * FROM public.auto AS a " +
                    "LEFT OUTER JOIN " +
                    "(SELECT ba.benutzer, ba.auto, ba.id AS reservid FROM public.b_reserv_a AS ba WHERE benutzer=?) AS ba " +
                    "ON ba.auto = a.id " +
                    "WHERE to_tsvector('german', COALESCE(marke,'') || ' ' || COALESCE(modell,'') || ' ' || COALESCE(to_char(baujahr,'YYYYMMDD')) || ' ' || COALESCE(beschreibung,'')) " +
                    "@@ to_tsquery('german', ?)";
            // add suffix
            for(int i=0; i<input.length; i++) {
                if(!input[i].equals("")) input[i] += ":*";
            }
            ResultSet rs = this.setSQL(sql).setInt(userId).setString(String.join(" & ", input)).executeQuerry();
            result = this.getReservationFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Fehler beim Ausführen der Datenbankanfrage");
        }
        return result;
    }

    public ArrayList<Reservation> getReservationsForUser(int userId) throws DatabaseException {
        ArrayList<Reservation> result = new ArrayList<>();
        try {
            String sql = "SELECT a.marke, a.modell, a.beschreibung, a.id, a.baujahr, ba.id AS reservid FROM public.auto AS a " +
                    "INNER JOIN public.b_reserv_a AS ba ON a.id = ba.auto " +
                    "WHERE ba.benutzer = ?";
            result = this.getReservationFromResultSet(this.setSQL(sql).setInt(userId).executeQuerry());
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Fehler beim Ausführen der Datenbankanfrage");
        }
        return result;
    }

    public void addReservation(int carId, int userId) throws DatabaseException {
        String sql = "INSERT INTO public.b_reserv_a (auto, benutzer) VALUES (?,?)";
        this.setSQL(sql).setInt(carId).setInt(userId).executeUpdate();
    }

    public void deleteReservation(int reservationId) throws DatabaseException {
        String sql = "DELETE FROM public.b_reserv_a AS ba WHERE ba.id=?";
        this.setSQL(sql).setInt(reservationId).executeUpdate();
    }

    private ArrayList<Reservation> getReservationFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Reservation> result = new ArrayList<>();
        while(rs.next()) {
            Reservation reservation = new Reservation();
            Car car = new Car();
            car.setBrand(rs.getString("marke"));
            car.setModel(rs.getString("modell"));
            car.setDescription(rs.getString("beschreibung"));
            car.setId(rs.getInt("id"));
            car.setYearOfProduction(rs.getDate("baujahr").toLocalDate());
            int reservationId = rs.getInt("reservid");
            // column was null
            if(reservationId == 0) {
                reservation.setReserved(false);
            }
            else {
                reservation.setReserved(true);
                reservation.setId(reservationId);
            }
            reservation.setCar(car);
            result.add(reservation);
        }
        return result;
    }
}
