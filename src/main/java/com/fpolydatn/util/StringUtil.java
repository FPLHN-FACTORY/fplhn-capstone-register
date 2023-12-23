package com.fpolydatn.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;

public final class StringUtil {

    public static String removeDiacritics(String text) {
        text = text.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A")
                .replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E")
                .replaceAll("[ÌÍỊỈĨ]", "I")
                .replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O")
                .replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U")
                .replaceAll("[ỲÝỴỶỸ]", "Y")
                .replaceAll("[Đ]", "D");
        return text;
    }

    public static String toJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Convert parameterMap to JSON string
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            // Handle exceptions, e.g., JSON serialization errors
            e.printStackTrace();
            return "{}"; // Return empty JSON object on failure
        }
    }

}
