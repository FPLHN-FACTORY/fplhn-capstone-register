package com.fpolydatn.core.daotao.service;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.model.request.DtCreateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtFindCanBoRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateCanBoRequest;
import com.fpolydatn.core.daotao.model.request.FindDtSinhVienTheoDotRequest;
import com.fpolydatn.core.daotao.model.response.DtCanBoResponse;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.infrastructure.projection.SimpleEntityProj;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author nguyenvv4
 */
public interface DtCanBoService {

    PageableObject<DtCanBoResponse> search(final DtFindCanBoRequest canBoRequest);

    CanBo create(@Valid DtCreateCanBoRequest canBo);

    public CanBo createcb(CanBo canBo);

    Boolean delete(String id);

    CanBo findById(String id);

    CanBo update(DtUpdateCanBoRequest canBoRequest);

    List<SimpleEntityProj> findAllSimpleEntityByTenCanBo(String id, int vaiTro);


    void MauImportExcel(HttpServletResponse response, String typeExcel, FindDtSinhVienTheoDotRequest request) throws IOException;

    RestApiException DtCanBoImport(MultipartFile file, String coSoId) throws IOException;


}
