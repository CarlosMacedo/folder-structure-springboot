package projectname.auth.controllers.json.payloads;

public class LoginPayload {
    private String email;
    public String password;

    public LoginPayload() {

    }

    public LoginPayload(RegisterPayload registerPayload) {
        this.setEmail(registerPayload.email);
        this.password = registerPayload.password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null)
            this.email = email.toLowerCase().trim();
        else  
            this.email = email;  
    }
}
