package com.fpolydatn.model;

import com.fpolydatn.infrastructure.constant.EntityProperties;
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
@Table
@Entity
public class HocKy extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_NAME_SHORT, nullable = false)
    @Nationalized
    private String tenHocKy;
}
