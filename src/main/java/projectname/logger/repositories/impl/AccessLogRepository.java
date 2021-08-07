package projectname.logger.repositories.impl;

import org.springframework.data.mongodb.repository.MongoRepository;

import projectname.logger.models.AccessLog;

public interface AccessLogRepository extends MongoRepository<AccessLog, String>{
    
}
