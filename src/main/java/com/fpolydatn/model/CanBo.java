package com.fpolydatn.model;

import lombok.Builder;
import lombok.Data;

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
@Builder
@Table
@Entity
public class CanBo implements Serializable {

    @Id
    @GeneratedValue
    private UUID canBoId;

    @Column(nullable = false)
    private String tenCanBo;

    @Column(nullable = false)
    private String tenTaiKhoan;

    @Column(nullable = false)
    private String soDienThoai;

    @Column(nullable = false)
    private String emailFpt;

    @Column(nullable = false)
    private String emailFe;

    @Column
    private UUID coSoId;

    @Column
    private UUID vaiTroId;
}