package com.fpolydatn.model;

import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author phongtt35
 */

@Data
@Table
@Entity
public class DotDangKy implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID dotDangKyId;

    @Column(length = 100, nullable = false)
    @Nationalized
    private String tenDotDangKy;

    @Column(nullable = false)
    private Date ngayBatDau;

    @Column(nullable = false)
    private Date ngayKetThuc;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID hocKyId;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID coSoId;
}
