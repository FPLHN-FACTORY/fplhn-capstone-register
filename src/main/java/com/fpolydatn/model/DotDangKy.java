package com.fpolydatn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Data
@Table
@Entity
public class DotDangKy implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID dotDangKyId;

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenDotDangKy;

    @Column(nullable = false)
    private Date ngayBatDau;

    @Column(nullable = false)
    private Date ngayKetThuc;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID hocKyId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID coSoId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column
    private Date updateAt;
}
