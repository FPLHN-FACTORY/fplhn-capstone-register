package com.fpolydatn.core.common;

import com.fpolydatn.infrastructure.session.FpolyDatnSession;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author thangncph26123
 */
@Component
public final class CommonHelper {

    private final FpolyDatnSession session;
    private final DotDangKyRepository dotDangKyRepository;

    @Autowired
    public CommonHelper(FpolyDatnSession session,
                        @Qualifier(DotDangKyRepository.NAME)
                            DotDangKyRepository dotDangKyRepository) {
        this.session = session;
        this.dotDangKyRepository = dotDangKyRepository;
    }

    public String getIdDotDangKyHienTai() {
        String coSoId = session.getCoSoId();
        return dotDangKyRepository.findDotDangKyID(coSoId, DateTimeUtil.convertDateToTimeStampSecond());
    }
}
