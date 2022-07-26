package com.fpolydatn.model;

import lombok.Data;

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
    private UUID giangVienId;

    @Id
    private UUID chuyenNganhId;
}
