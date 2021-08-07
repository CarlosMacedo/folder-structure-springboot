package projectname.auth.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import projectname.auth.enums.Role;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

@EnableWebSecurity
@Configuration
@Order(1)
public class WebSecurityConfigEndpointsAuthorization extends WebSecurityConfigurerAdapter {
    @Autowired private JWTAuthorizationFilter jWTAuthorizationFilter;

    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.csrf().disable()	
			.addFilterAfter(this.jWTAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)		
			.authorizeRequests()
			.antMatchers("/signup").permitAll()
			.antMatchers("/signin").permitAll()
			.antMatchers("/refresh-token").permitAll()
			.antMatchers("/change-password").permitAll()
			.antMatchers("/forgot-password/{email}").permitAll()
			.antMatchers("/forgot-password/{email}/{randomId}").permitAll()
			.antMatchers("/resend-confirmation-email/{email}").permitAll()
			.antMatchers("/activate-email/{email}/{randomId}").permitAll()
			.antMatchers("/terms-of-service/**").permitAll()
			.antMatchers("/status").hasAnyAuthority(Role.ADMIN.toString(), Role.PROVIDER.toString())
			.antMatchers(HttpMethod.POST, "/contact/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.headers()
			.xssProtection()
			.and()
			.contentSecurityPolicy("script-src 'self'");;
	}	
}
