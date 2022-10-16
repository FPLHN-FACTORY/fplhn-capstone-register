package com.fpolydatn.entity;

import com.fpolydatn.infrastructure.constant.EntityProperties;
import com.fpolydatn.infrastructure.constant.TrangThaiSinhVien;
import com.fpolydatn.entity.base.IsBelongToPeriod;
import com.fpolydatn.entity.base.MultiTenantEntity;
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
@Table(name = "sinh_vien")
@Entity
public class SinhVien extends MultiTenantEntity
        implements Serializable, IsBelongToPeriod {

    @Column(length = EntityProperties.LENGTH_NAME, nullable = false)
    @Nationalized
    private String tenSinhVien;

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String maSinhVien;

    @Column(length = EntityProperties.LENGTH_PHONE, nullable = false)
    private String soDienThoai;

    @Column(length = EntityProperties.LENGTH_EMAIL, nullable = false)
    private String email;

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String khoa;

    @Column(nullable = false)
    private TrangThaiSinhVien trangThai = TrangThaiSinhVien.DU_DIEU_KIEN;

    @Column(length = EntityProperties.LENGTH_ID)
    private String nhomId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String monChuongTrinhId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String monDatnId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String chuyenNganhId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String dotDangKyId;

    @Column
    private Long thoiDiemRoiNhomGanNhat;
}
