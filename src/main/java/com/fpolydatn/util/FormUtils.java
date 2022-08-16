package com.fpolydatn.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpolydatn.api.PageableRequest;
import com.fpolydatn.infrastructure.interactor.PageableInteractor;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nguyenvv4
 */
@Slf4j
public class FormUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public <T> T convertToObject(Class<T> clazz, Object data) {
        Gson gson = new Gson();
        try {
            return MAPPER.readValue(gson.toJson(data), clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T extends PageableInteractor> T convert(Class<T> clazz, final PageableRequest data) {
        Gson gson = new Gson();
        try {
            T interactor = MAPPER.readValue(gson.toJson(data), clazz);
            interactor.setLimit(data.getSize());
            interactor.setOffset(data.getSize() * data.getPage());
            return interactor;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
