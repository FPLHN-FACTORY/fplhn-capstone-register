package com.fpolydatn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Data
@Table
@Entity
public class MonDatn implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID monDatnId;

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenMonDatn;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID chuyenNganhId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type= EntityProperties.TYPE_ID)
    private UUID coSoId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column
    private Date updateAt;
}
