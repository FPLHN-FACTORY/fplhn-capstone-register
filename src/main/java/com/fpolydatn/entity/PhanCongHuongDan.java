package com.fpolydatn.entity;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author phongtt35
 */
@Getter
@Setter
@Table(name = "phan_cong_huong_dan")
@Entity
@IdClass(PhanCongHuongDanId.class)
public class PhanCongHuongDan implements Serializable {

    @Id
    @Column(length = EntityProperties.LENGTH_ID)
    private String giangVienId;

    @Id
    @Column(length = EntityProperties.LENGTH_ID)
    private String monDatnId;

}
