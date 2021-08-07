package projectname.auth.models;

import java.util.Date;
import java.util.List;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import org.apache.commons.lang3.StringUtils;

import projectname.auth.enums.Role;
import projectname.auth.enums.UserState;
import projectname.auth.exceptions.RefreshTokenCannotBeNullException;
import projectname.auth.helpers.ActivationEmail;

@Document("user")
public class AuthUser {
    @Id private String id;
    private String name;
    private String lastName;
    @Indexed(unique = true) private String email;
    private String password;
    private UserState state;
    private List<Role> roles;
    private boolean active;
    private String whoCreatedUserId;
    private Date createAt;
    private Date loginAt;
    private Date lastChangedPassword;
    private String forgotPasswordCode;
    @Indexed(unique = true) private String refreshToken;
    private ActivationEmail activationEmail;

    public AuthUser(
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
        this.setName(name);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setState(state);
        this.setRoles(roles);
        this.setActive(active);
        this.setWhoCreatedUserId(whoCreatedUserId);
        this.setCreateAt(createAt);
        this.setRefreshToken(refreshToken);
    }

    public String getId() {
        return this.id;
    }

    public AuthUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AuthUser setName(String name) {
        if (name == null) return this;
        this.name = StringUtils.capitalize(name.toLowerCase().trim()); 
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthUser setLastName(String lastName) {
        if (lastName == null) return this;
        this.lastName = StringUtils.capitalize(lastName.toLowerCase().trim());
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public AuthUser setEmail(String email) {
        if (email == null) return this;
        this.email = email.toLowerCase().trim();
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public AuthUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserState getState() {
        return state;
    }

    public AuthUser setState(UserState state) {
        this.state = state;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public AuthUser setRoles(List<Role> roles) {
        if (roles == null) return this;
        this.roles = (roles.contains(Role.USER))? Arrays.asList(Role.USER) : roles;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public AuthUser setActive(boolean active) {
        this.active = active;
        return this;
    }

    public String getWhoCreatedUserId() {
        return whoCreatedUserId;
    }

    public AuthUser setWhoCreatedUserId(String whoCreatedUserId) {
        this.whoCreatedUserId = whoCreatedUserId;
        return this;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public AuthUser setCreateAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public Date getLoginAt() {
        return this.loginAt;
    }

    public AuthUser setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
        return this;
    }

    public Date getLastChangedPassword() {
        return this.lastChangedPassword;
    }

    public AuthUser setLastChangedPassword(Date lastChangedPassword) {
        this.lastChangedPassword = lastChangedPassword;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthUser setRefreshToken(String refreshToken) throws RefreshTokenCannotBeNullException {
        if (refreshToken == null) throw new RefreshTokenCannotBeNullException(null);
        this.refreshToken = refreshToken;
        return this;
    }

    public ActivationEmail getActivationEmail() {
        return activationEmail;
    }

    public AuthUser setActivationEmail(ActivationEmail activationEmail) {
        this.activationEmail = activationEmail;
        return this;
    }

    public String getForgotPasswordCode() {
        return forgotPasswordCode;
    }

    public AuthUser setForgotPasswordCode(String forgotPasswordCode) {
        this.forgotPasswordCode = forgotPasswordCode;
        return this;
    }
}