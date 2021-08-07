package projectname.logger.services.impl;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.auth.models.AuthUser;
import projectname.logger.enums.RequestExecutionTime;
import projectname.logger.models.AccessLog;
import projectname.logger.repositories.AccessLogBD;

@Service
public class AccessLogServiceImpl {
    @Autowired AccessLogBD accessLogBD;

    public void createAccessLog(AuthUser user, HttpServletRequest request, HttpServletResponse response) {
        Long endpointRuntime = this.getEndpointRuntime(request);
        String endpoint = request.getServletPath();
        String ip = this.getRemoteIp(request);
        Integer statusCode = response.getStatus();
        Date now = new Date();

        this.accessLogBD.createAccessLog(new AccessLog(
            user.getId(),
            now,
            endpoint,
            statusCode,
            ip,
            endpointRuntime
        ));
    }
    
    private Long getEndpointRuntime(HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute(RequestExecutionTime.START_TIME_ENDPOINT.toString());
        Long finalTime = System.currentTimeMillis();
        return finalTime - startTime;
    }

    private String getRemoteIp(HttpServletRequest request) {
		String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
		if (ip.equals("0:0:0:0:0:0:0:1")) return "127.0.0.1";
		return ip;
	}
}
