package com.fpolydatn.service.impl;

import com.fpolydatn.repository.SinhVienRepository;
import com.fpolydatn.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HangNT
 */
@Service
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;

}