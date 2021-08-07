package projectname.auth.controllers.json.responses;

import java.util.List;

import projectname.auth.enums.Role;
import projectname.auth.enums.UserState;
import projectname.user.models.User;

public class RegisterResponse{
    public String id;
    public String name;
    public String lastName;
    public String email;
    public UserState state;
    public List<Role> roles;
    public String accessToken;
    public String refreshToken;
    public String response;
    
    public RegisterResponse (User user) {
        this.initUser(user);
    }

    public RegisterResponse (User user, String response) {
        this.initUser(user);
        this.response = response;
    }

    public RegisterResponse (User user, LoginResponse loginResponse) {
        this.initUser(user);
        this.accessToken = loginResponse.accessToken;
        this.refreshToken = loginResponse.refreshToken;
    }

    private void initUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.state = user.getState();
        this.roles = user.getRoles();
    }
}
