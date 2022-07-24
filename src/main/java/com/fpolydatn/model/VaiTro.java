package com.fpolydatn.model;

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
@Table
@Entity
public class VaiTro implements Serializable {

    @Id
    @GeneratedValue
    private UUID vaiTroId;

    @Column(nullable = false)
    private String tenVaiTro;
}
