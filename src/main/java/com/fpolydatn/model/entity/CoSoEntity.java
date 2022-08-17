package com.fpolydatn.model.entity;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.model.base.PrimaryEntity;
import lombok.Getter;
import lombok.Setter;
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
@Table(name = "co_so")
@Entity
public class CoSoEntity extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String maCoSo;

    @Column(length = EntityProperties.LENGTH_NAME_SHORT, nullable = false)
    @Nationalized
    private String tenCoSo;

}