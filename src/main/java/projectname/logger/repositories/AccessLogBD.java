package projectname.logger.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.logger.models.AccessLog;
import projectname.logger.repositories.impl.AccessLogRepository;

@Service
public class AccessLogBD {
    @Autowired AccessLogRepository accessLogRepository;

    public void createAccessLog(AccessLog accessLog) {
        this.accessLogRepository.save(accessLog);
    }
}
