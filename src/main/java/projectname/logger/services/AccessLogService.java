package projectname.logger.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.auth.models.AuthUser;
import projectname.logger.services.impl.AccessLogServiceImpl;

@Service
public class AccessLogService {
    @Autowired AccessLogServiceImpl accessLogServiceImpl;

    public void createEndpointAccessHistory(AuthUser user, HttpServletRequest request, HttpServletResponse response) {        
        this.accessLogServiceImpl.createAccessLog(user, request, response);
    }
}
