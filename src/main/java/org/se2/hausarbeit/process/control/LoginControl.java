package org.se2.hausarbeit.process.control;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.model.Navigator;
import org.se2.hausarbeit.model.dao.BaseDao;
import org.se2.hausarbeit.model.dao.UserDao;
import org.se2.hausarbeit.model.dto.LoginDTO;
import org.se2.hausarbeit.model.dto.OperationFeedback;
import org.se2.hausarbeit.model.entity.User;
import org.se2.hausarbeit.process.exception.DatabaseException;
import org.se2.hausarbeit.process.exception.NoSuchUserOrPasswordException;
import org.se2.hausarbeit.services.util.PAGE;
import org.se2.hausarbeit.services.util.SESSIONATTRIBUT;

public class LoginControl {
    public OperationFeedback login(LoginDTO dto) {
        OperationFeedback feedback = null;
        try {
            User user = new UserDao().getByEmailAndPassword(dto.getEmail(), dto.getPassword());
            if(user != null) {
                UI.getCurrent().getSession().setAttribute(SESSIONATTRIBUT.USER, user);
                UI.getCurrent().navigate(PAGE.SEARCH);
                feedback = new OperationFeedback(true, null);
            }
        }
        catch (DatabaseException | NoSuchUserOrPasswordException e) {
            feedback = new OperationFeedback(false, e.getMessage());
        }
        return feedback;
    }
}
