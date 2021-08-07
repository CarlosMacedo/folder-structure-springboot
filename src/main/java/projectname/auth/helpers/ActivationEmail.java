package projectname.auth.helpers;

import java.util.Date;

public class ActivationEmail {
    public Date send;
    public String randomId;

    public ActivationEmail(Date send, String randomId) {
        this.send = send;
        this.randomId = randomId;
    }
}
