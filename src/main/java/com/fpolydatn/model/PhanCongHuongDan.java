package com.fpolydatn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(PhanCongHuongDanId.class)
public class PhanCongHuongDan implements Serializable {

    @Id
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID giangVienId;

    @Id
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID monDatnId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column
    private Date updateAt;
}
