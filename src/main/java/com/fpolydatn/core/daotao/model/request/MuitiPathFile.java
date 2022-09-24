package com.fpolydatn.core.daotao.model.request;

import lombok.Data;

import javax.persistence.Transient;

/**
 * @author Vinhnv
 */
@Data
public class MuitiPathFile {

    @Transient
    private MuitiPathFile file;
}
