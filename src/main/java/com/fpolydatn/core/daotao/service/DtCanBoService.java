package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponseMessage;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author nguyenvv4
 */
public interface DtCanBoService {

    PageableObject<DtCanBoResponse> search(final DtFindCanBoRequest canBoRequest);

    boolean create(@Valid DtCreateCanBoRequest canBo);

    Boolean delete(String id, String coSoId);

    CanBo findById(String id);

    CanBo update(DtUpdateCanBoRequest canBoRequest);

    List<SimpleEntityProj> findAllSimpleEntityByTenCanBo(String id, int vaiTro);

    DtCanBoResponseMessage DtCanBoImport(MultipartFile file, String coSoId) throws IOException;

    void MauImportExcel(HttpServletResponse response, String typeExcel) throws IOException;

}
