package com.fpolydatn.core.daotao.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;

/**
 * @author huynq
 */
@Data
@Getter
@Setter
public class MuitiPathFile {

    @Transient
    private MultipartFile file;

}
