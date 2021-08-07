package projectname.logger.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import projectname.logger.middlewares.AccessLogMiddleware;

@Configuration
public class MiddlewaresConfig implements WebMvcConfigurer {
    @Autowired protected AccessLogMiddleware accessLogMiddleware;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		  registry.addInterceptor(this.accessLogMiddleware);
    }
}
