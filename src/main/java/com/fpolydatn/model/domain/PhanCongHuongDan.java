package com.fpolydatn.model.domain;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.model.base.AuditEntity;
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
@Table
@Entity
@IdClass(PhanCongHuongDanId.class)
public class PhanCongHuongDan extends AuditEntity implements Serializable {

    @Id
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID giangVienId;

    @Id
    @Column(length = EntityProperties.LENGTH_ID)
    @Type(type = EntityProperties.TYPE_ID)
    private UUID monDatnId;

}
