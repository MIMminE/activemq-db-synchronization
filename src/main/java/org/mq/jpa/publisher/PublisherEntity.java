package org.mq.jpa.publisher;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

@Entity
@Table(name="authlog")
@NoArgsConstructor
@Data
public class PublisherEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hostname", length = 32, nullable = false)
    private String hostname;

    @Column(name = "UserName", length = 64, nullable = false)
    private String userName;

    @Column(name = "TimeStamp", nullable = false)
    private Date timestamp;

    @Column(name = "Result", nullable = false)
    private int result;

    @Column(name = "Reason", length = 200)
    private String reason;

    @Column(name = "cli_mac", length = 40, nullable = false)
    private String cliMac;

    @Column(name = "nas_id", length = 40, nullable = false)
    private String nasId;

    @Column(name = "version", length = 64)
    private String version;

    @Column(name = "cli_ip", nullable = false)
    private int cliIp;

    @Column(name = "fail_code", length = 8)
    private String failCode;

    @Column(name = "sync_flag")
    private Boolean syncFlag;
}
