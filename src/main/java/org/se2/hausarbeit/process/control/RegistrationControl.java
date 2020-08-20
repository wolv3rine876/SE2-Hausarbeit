package org.se2.hausarbeit.process.control;

import org.se2.hausarbeit.model.dao.UserDao;
import org.se2.hausarbeit.model.dto.OperationFeedback;
import org.se2.hausarbeit.model.dto.RegistrationDTO;
import org.se2.hausarbeit.model.entity.User;
import org.se2.hausarbeit.process.exception.DatabaseException;
import org.se2.hausarbeit.process.exception.UserAlreadyRegisteredException;
import org.se2.hausarbeit.services.util.ROLE;

public class RegistrationControl {

    public OperationFeedback register(RegistrationDTO dto) {
        OperationFeedback feedback = null;
        try {
            User user = new User(dto.getName(), dto.getLastname(), ROLE.BENUTZER, dto.getEmail(), dto.getPassword());
            UserDao dao = new UserDao();
            if(!dao.checkIfEmailExists(user.getEmail())) {
                dao.registerUser(user);
                feedback = new OperationFeedback(true, "Benutzer registriert");
            }
        }
        catch (DatabaseException | UserAlreadyRegisteredException e) {
            e.printStackTrace();
            feedback = new OperationFeedback(false, e.getMessage());
        }
        return feedback;
    }
}
