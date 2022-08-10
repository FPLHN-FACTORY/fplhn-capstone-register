package com.fpolydatn.model.domain;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.model.base.MultiTenantEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class CanBo extends MultiTenantEntity implements Serializable {

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

    @Column
    private VaiTro vaiTro;

}