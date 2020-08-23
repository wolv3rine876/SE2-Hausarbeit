package org.se2.hausarbeit.model.dao;

import org.se2.hausarbeit.model.entity.User;
import org.se2.hausarbeit.process.exception.DatabaseException;
import org.se2.hausarbeit.process.exception.NoSuchUserOrPasswordException;
import org.se2.hausarbeit.process.exception.UserAlreadyRegisteredException;
import org.se2.hausarbeit.services.util.ROLE;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends BaseDao {

    public UserDao() throws DatabaseException {}

    public User getByEmailAndPassword(String email, String password) throws DatabaseException, NoSuchUserOrPasswordException {
        User user = null;
        try {
            ResultSet rs = this.setSQL("SELECT * FROM public.benutzer as b WHERE b.email=? AND b.passwort=? AND b.rolle=?")
                    .setString(email)
                    .setString(password)
                    .setString(ROLE.BENUTZER)
                    .executeQuerry();
            if(rs.next()) {
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("vorname"));
                user.setLastname(rs.getString("name"));
                user.setPassword(rs.getString("passwort"));
                user.setRole(rs.getString("rolle"));
                user.setId(rs.getInt("id"));
            }
            // invalid email or pw
            else {
                throw new NoSuchUserOrPasswordException("Falsche Zugangsdaten");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Fehler beim Ausführen der Datenbankanfrage");
        }
        return user;
    }
    public boolean checkIfEmailExists(String email) throws DatabaseException, UserAlreadyRegisteredException {
        boolean result = true;
        try {
            ResultSet rs = new UserDao().setSQL("SELECT id FROM public.benutzer AS b WHERE b.email=?")
                                        .setString(email)
                                        .executeQuerry();
            if(rs.next()) throw new UserAlreadyRegisteredException("E-Mail Adresse bereits registriert");
            else result = false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Fehler beim Ausführen der Datenbankanfrage");
        }
        return result;
    }
    public void registerUser(User user) throws DatabaseException {
        int id = new UserDao().setSQL("INSERT INTO public.benutzer (vorname, name, rolle, email, passwort) VALUES (?,?,?,?,?)")
                    .setString(user.getName())
                    .setString(user.getLastname())
                    .setString(user.getRole())
                    .setString(user.getEmail())
                    .setString(user.getPassword())
                    .executeUpdate("id");
        user.setId(id);
    }
}
