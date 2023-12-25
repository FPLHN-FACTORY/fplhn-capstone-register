package com.fpolydatn.core.common;

import org.springframework.stereotype.Service;

public interface IUseCase<I, O> {

    O execute(final I input);
}
