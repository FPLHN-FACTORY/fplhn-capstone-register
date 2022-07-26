package com.fpolydatn.service.impl;

import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.service.HocKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vinhnv
 */
@Service
public class HocKyServiceImpl implements HocKyService {

    @Autowired
    private HocKyRepository hocKyRepository ;
}
