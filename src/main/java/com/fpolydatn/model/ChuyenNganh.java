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
public class ChuyenNganh implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = 36)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID chuyenNganhId;

    @Column(length = 100, nullable = false)
    @Nationalized
    private String tenChuyenNganh;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID chuyenNganhMeId;

    @Column(length = 36, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID coSoId;
}
