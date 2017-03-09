package jpastart.authlog;

import jpastart.common.InetAddressConverter;

import javax.persistence.*;
import java.net.InetAddress;
import java.util.Date;

@Entity
@Table(name = "auth_log")
public class AuthLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    @Convert(converter = InetAddressConverter.class)
    private InetAddress ipAddress;
    private Date timestamp;
    private boolean success;

    public AuthLog() {
    }

    public AuthLog(String userId, InetAddress ipAddress, Date timestamp, boolean success) {
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
        this.success = success;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }
}
