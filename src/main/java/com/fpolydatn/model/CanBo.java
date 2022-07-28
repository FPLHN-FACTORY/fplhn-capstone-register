package com.fpolydatn.model;

import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Data
@Table
@Entity
public class CanBo implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID canBoId;

    @Column(length = 100, nullable = false)
    @Nationalized
    private String tenCanBo;

    @Column(length = 20, nullable = false)
    private String tenTaiKhoan;

    @Column(length = 20, nullable = false)
    private String soDienThoai;

    @Column(length = 50, nullable = false)
    private String emailFpt;

    @Column(length = 50, nullable = false)
    private String emailFe;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID coSoId;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID vaiTroId;
}