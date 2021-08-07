package projectname.logger.middlewares;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import projectname.auth.models.AuthUser;
import projectname.logger.enums.RequestExecutionTime;
import projectname.logger.services.AccessLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AccessLogMiddleware implements HandlerInterceptor {  
    @Autowired AccessLogService accessLogService;

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(RequestExecutionTime.START_TIME_ENDPOINT.toString(), System.currentTimeMillis());
        return true;
	}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();        
        boolean authIsValid = auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof AuthUser;
        
        if (authIsValid) this.accessLogService.createEndpointAccessHistory(
            (AuthUser) auth.getPrincipal(),
            request,
            response
        );
    }
}
