package com.fpolydatn.infrastructure.constant;

public class Constants {

    private Constants() {
    }

    public static final String ENCODING_UTF8 = "UTF-8";

    public class UrlPath {

        private UrlPath() {
        }

        public static final String URL_API_CAN_BO_LIST = "/can-bo/list";
        public static final String URL_API_CAN_BO_ADD = "/can-bo/add";

    }

    public class FileProperties {
        private FileProperties() {
        }

        public static final String PROPERTIES_APPLICATION = "application.properties";
        public static final String PROPERTIES_VALIDATION = "messages.properties";
    }

}
