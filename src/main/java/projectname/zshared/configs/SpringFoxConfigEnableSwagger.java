package projectname.zshared.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;

@Configuration
public class SpringFoxConfigEnableSwagger {                                    
    @Bean
    public Docket api() { 
        //swagger-url: http://localhost:{{port}}/v1/swagger-ui.html                                 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
          .ignoredParameterTypes(AuthenticationPrincipal.class)
          .enableUrlTemplating(true);  
    }
}
