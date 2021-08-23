package projectname.auth.configs;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import projectname.auth.enums.UserState;
import projectname.auth.exceptions.UserIdCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.services.AuthService;
import projectname.auth.services.AuthServiceAsync;
import projectname.user.models.User;
import projectname.user.services.UserService;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	@Value("${server.jwt.secretKey}") private String SECRET;  
	@Value("${config.activation.by.email.is.required}") private boolean ACTIVATION_BY_EMAIL_IS_REQUIRED;
	@Autowired UserService userService;
	@Autowired AuthService authService;
	@Autowired AuthServiceAsync authServiceAsync;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (this.checkJWTToken(request, response)) {
				Claims claims = this.validateToken(request);
				if (claims.get("authorities") != null) {
					this.setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}
	}	



	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}
	 
	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	private void setUpSpringAuthentication(Claims claims) {		
		try {
			String userId = claims.getSubject();
			Date iat = claims.getIssuedAt();

			User user = this.userService.getUserById(userId);
			this.authServiceAsync.setLoginAt(user, new Date());
			user = this.keepGoing(user, iat);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				user,
				null,
				user.getRoles().stream().map(role -> role.toString()).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
			);

			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (UserIdCannotBeNullException | UserNotFoundException e) {
			this.logger.info("TOKEN: User not found (#404)");			
			SecurityContextHolder.clearContext();
		} catch (Exception e) {
			this.logger.info("TOKEN: User not found (#500)");
			SecurityContextHolder.clearContext();
		}
	}

	private User keepGoing(User user, Date iat) {
		user = this.waitingValidateEmail(user);
		user = this.userIsActive(user);
		user = this.passwordChangedAfterThisTokenWasGenerated(user, iat);

		return user;
	}

	private User passwordChangedAfterThisTokenWasGenerated(User user, Date iat) {
		if (user == null) return null;
		if (user.getLastChangedPassword() == null) return user;
		if (user.getLastChangedPassword().after(iat)) return null;
		return user;
	}

	private User userIsActive(User user) {
		if (user == null) return null;
		return user.isActive()? user : null;
	}

	private User waitingValidateEmail(User user) {
		if (user == null) return null;
		if (ACTIVATION_BY_EMAIL_IS_REQUIRED && user.getState() == UserState.WAITING_VALIDATE_EMAIL) 
			return null;
		return user;
	}
}
