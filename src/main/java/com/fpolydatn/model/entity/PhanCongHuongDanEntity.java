package com.fpolydatn.model.entity;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author phongtt35
 */
@Getter
@Setter
@Table(name = "phan_cong_huong_dan")
@Entity
@IdClass(PhanCongHuongDanId.class)
public class PhanCongHuongDanEntity implements Serializable {

    @Id
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID giangVienId;

    @Id
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID monDatnId;

}
