package com.fpolydatn.service;

import com.fpolydatn.model.ChuyenNganh;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Vinhnv
 */
public interface ChuyenNganhService {

    List<ChuyenNganh> getList (int offset , int size);

}
