package projectname.logger.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
@CompoundIndexes({
    @CompoundIndex(name = "user-date", def = "{'userId' : 1, 'date': -1}"),
    @CompoundIndex(name = "user-endpoint-status", def = "{'userId' : 1, 'endpoint': 1, 'responseStatusCode': 1}"),
})
public class AccessLog {
    @Id private String id;
    private String userId;
	private Date date;
	private String endpoint;
    private Integer responseStatusCode;
	private String ipAddress;
	private Long executionTime;


    public AccessLog(String userId, Date date, String endpoint, Integer responseStatusCode, String ipAddress, Long executionTime) {
        this.setUserId(userId);
        this.setDate(date);
        this.setEndpoint(endpoint);
        this.setResponseStatusCode(responseStatusCode);
        this.setIpAddress(ipAddress);
        this.setExecutionTime(executionTime);
    }

    public String getUserId() {
        return this.userId;
    }

    public AccessLog setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Date getDate() {
        return this.date;
    }

    public AccessLog setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public AccessLog setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }

    public AccessLog setResponseStatusCode(Integer responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
        return this;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public AccessLog setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public Long getExecutionTime() {
        return this.executionTime;
    }

    public AccessLog setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
        return this;
    }

}
