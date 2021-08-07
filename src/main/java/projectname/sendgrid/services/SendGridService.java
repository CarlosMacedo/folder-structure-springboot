package projectname.sendgrid.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.helpers.ActivationEmail;
import projectname.sendgrid.services.impl.SendGridServiceImpl;
import projectname.sendgrid.services.validations.SendGridValidateOrThrow;
import projectname.user.models.User;
import projectname.zshared.utils.Strings;

@Service
public class SendGridService {
    @Autowired SendGridValidateOrThrow sendGridValidateOrThrow;
    @Autowired SendGridServiceImpl sendGridServiceImpl;

    public ActivationEmail sendConfirmationEmailOfAccount(User user) throws UserCannotBeNullException, EmailCannotBeNullException, InvalidEmailException {
        this.sendGridValidateOrThrow.object(user);
        boolean hasCode = user.getActivationEmail() != null && user.getActivationEmail().randomId != null;
        String code = hasCode? user.getActivationEmail().randomId : Strings.generateRandomString(60);
        this.sendGridServiceImpl.sendConfirmationEmailOfAccount(user.getEmail(), code);
        return new ActivationEmail(new Date(), code);
    } 
    
    public void sendWasItYouWhoEntered(User user) throws UserCannotBeNullException, EmailCannotBeNullException, InvalidEmailException {
        this.sendGridValidateOrThrow.object(user);
        this.sendGridServiceImpl.sendWasItYouWhoEntered(user.getEmail());
    }

    public String sendForgotPassword(User user) throws UserCannotBeNullException, EmailCannotBeNullException, InvalidEmailException {
        this.sendGridValidateOrThrow.object(user);
        boolean hasCode = user.getForgotPasswordCode() != null;
        String code = hasCode? user.getForgotPasswordCode() : Strings.generateRandomString(60);
        this.sendGridServiceImpl.sendForgotPassword(user.getEmail(), code);
        return code;
    }
}
