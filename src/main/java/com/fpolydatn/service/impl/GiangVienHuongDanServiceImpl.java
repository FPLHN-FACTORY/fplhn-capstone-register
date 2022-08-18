package com.fpolydatn.service.impl;

import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.service.GiangVienHuongDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HangNT
 */
@Service
public class GiangVienHuongDanServiceImpl implements GiangVienHuongDanService {

    @Autowired
    private GiangVienHuongDanRepository giangVienHuongDanRepository;
}
