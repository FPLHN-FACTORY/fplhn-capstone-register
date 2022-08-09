package com.fpolydatn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@Table
@Entity
public class CanBo implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID canBoId;

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenCanBo;

    @Column(length = EntityProperties.LENGTH_ACCOUNT, nullable = false)
    private String tenTaiKhoan;

    @Column(length = EntityProperties.LENGTH_PHONE, nullable = false)
    private String soDienThoai;

    @Column(length = EntityProperties.LENGTH_EMAIL, nullable = false)
    private String emailFpt;

    @Column(length = EntityProperties.LENGTH_EMAIL, nullable = false)
    private String emailFe;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID coSoId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID vaiTroId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column
    private Date updateAt;
}