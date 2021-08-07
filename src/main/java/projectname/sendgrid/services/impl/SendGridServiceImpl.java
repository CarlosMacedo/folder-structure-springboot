package projectname.sendgrid.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendGridServiceImpl {
    @Value("${config.activate.sendgrid}") private boolean sendgridActivated;

    public void sendConfirmationEmailOfAccount(String email, String code) {
        if (!sendgridActivated)
            System.out.println("[Sendgrid disabled] Sending confirmation email. Your code is: " + code);
        else
            this.sendConfirmationEmailOfAccountSendGrid(email, code);
    }

    private void sendConfirmationEmailOfAccountSendGrid(String email, String code) {
    }

    public void sendWasItYouWhoEntered(String email) {
        if (!sendgridActivated)
            System.out.println("[Sendgrid disabled - send whenever refreshtoken] If it wasn't you, click here.");
        else
            this.sendWasItYouWhoEnteredSendGrid(email);
    }

    private void sendWasItYouWhoEnteredSendGrid(String email) {
    }

    public void sendForgotPassword(String email, String code) {
        if (!sendgridActivated)
            System.out.println("[Sendgrid disabled] Click here to change your password. Code: " + code);
        else
            this.sendForgotPasswordSendGrid(email);
    }

    private void sendForgotPasswordSendGrid(String email) {
    }
    
}
