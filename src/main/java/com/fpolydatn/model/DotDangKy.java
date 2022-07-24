package com.fpolydatn.model;

import lombok.Builder;
import lombok.Data;

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
    private UUID dotDangKyId;

    @Column(nullable = false)
    private String tenDotDangKy;

    @Column(nullable = false)
    private Date ngayBatDau;

    @Column(nullable = false)
    private Date ngayKetThuc;

    @Column(nullable = false)
    private UUID hocKyId;

    @Column(nullable = false)
    private UUID coSoId;
}
