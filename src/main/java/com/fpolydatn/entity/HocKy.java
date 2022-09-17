package com.fpolydatn.entity;

import com.fpolydatn.entity.base.HasPeriod;
import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.entity.base.PrimaryEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author phongtt35
 */
@Getter
@Setter
@Table(name = "hoc_ky")
@Entity
@ToString
public class HocKy extends PrimaryEntity
        implements Serializable, HasPeriod {

    @Column(length = EntityProperties.LENGTH_NAME_SHORT, nullable = false)
    @Nationalized
    private String tenHocKy;

    @Column(nullable = false)
    private Long ngayBatDau;

    @Column(nullable = false)
    private Long ngayKetThuc;
}
