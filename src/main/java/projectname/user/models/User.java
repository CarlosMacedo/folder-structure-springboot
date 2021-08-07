package projectname.user.models;

import java.util.Date;
import java.util.List;

import projectname.auth.enums.Role;
import projectname.auth.enums.UserState;
import projectname.auth.exceptions.RefreshTokenCannotBeNullException;
import projectname.auth.models.AuthUser;

public class User extends AuthUser {
    public User(
        String name,
        String lastName,
        String email, 
        String password, 
        UserState state,
        List<Role> roles,
        boolean active,
        String whoCreatedUserId,
        Date createAt, 
        String refreshToken
    ) throws RefreshTokenCannotBeNullException {
        super(name, lastName, email, password, state, roles, active, whoCreatedUserId, createAt, refreshToken);
    }
}
