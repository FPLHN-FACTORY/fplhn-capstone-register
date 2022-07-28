package com.fpolydatn.model;

import lombok.Data;
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

@Data
@Table
@Entity
@IdClass(ChuyenNganhGiangVienId.class)
public class ChuyenNganhGiangVien implements Serializable {

    @Id
    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID giangVienId;

    @Id
    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID chuyenNganhId;
}
