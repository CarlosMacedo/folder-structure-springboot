package projectname.auth.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@Order(2)
public class WebSecurityConfigCorsAndIgnoringEndpoints extends WebSecurityConfigurerAdapter {
	@Value("${server.allowedOrigins}") private String allowedOrigins;

	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(		
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**",
			"/swagger-resources/**"
		);
    }

	@Bean
    CorsConfigurationSource corsConfigurationSource() {
		List<String> origins = new ArrayList<String>(Arrays.asList(this.allowedOrigins.split(",")));
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
