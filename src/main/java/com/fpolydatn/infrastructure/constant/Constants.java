package com.fpolydatn.infrastructure.constant;

public class Constants {

    private Constants() {
    }

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String TEXT_CSV = "text/csv";

    public static final String HEADER_KEY = "Content-Disposition";

    public static final String DEFAULT_PASSWORD = "$2a$12$JGEFQf1mRIKD.Mbg6WXSt.2MiKzplrG/Ok8qgqmDonc0Z5.0beGEi";

    public static final String DEFAULT_PASSWORD_ENCODE = "123456aA@";

    public static final String DEFAULT_AVATAR = "avatar_default.png";

    public class UrlPath {

        private UrlPath() {
        }

        public static final String URL_API_SEARCH_ABSENT = "/admin/absent/search";
        public static final String URL_API_EXPORT_SEARCH_ABSENT = "/mem-ber/absent/export";
        public static final String URL_API_INSERT_ABSENT = "/landlord/absent/add";

        public static final String URL_API_INSERT_FEEDBACK = "/landlord/feedback/add";
        public static final String URL_API_SEARCH_FEEDBACK = "/admin/feedback/search";

        public static final String URL_API_SEARCH_POSITION = "/manager-service/position/search";
        public static final String URL_API_SEARCH_EMPLOYEE = "/manager-service/employee/search";
        public static final String URL_API_GET_ONE_EMPLOYEE = "/manager-service/employee/get-one";
        public static final String URL_API_REMOVE_EMPLOYEE = "/admin/employee/remove";
        public static final String URL_API_UPDATE_EMPLOYEE = "/admin/employee/update";
        public static final String URL_API_ADD_EMPLOYEE = "/admin/employee/add";
        public static final String URL_API_EXPORT_SEARCH_EMPLOYEE = "/mem-ber/employee/export";

        public static final String URL_API_LIST_SERVICE = "/manager-service/service/list";
        public static final String URL_API_SEARCH_SERVICE = "/manager-service/service/search";
        public static final String URL_API_DETAIL_SERVICE = "/manager-service/detail-service/get-one";

        public static final String URL_API_SEARCH_NOTIFICATION = "/tenant/notification/search";
        public static final String URL_API_ADD_NOTIFICATION = "/admin/notification/add";

        public static final String URL_API_SEARCH_REQUEST_SERVICE = "/manager-service/request-service/search";
        public static final String URL_API_UPDATE_REQUEST_SERVICE = "/landlord/request-service/update";
        public static final String URL_API_GET_ONE_REQUEST_SERVICE = "/manager-service/request-service/get-one";
        public static final String URL_API_LIST_STATUS_REQUEST_SERVICE = "/manager-service/request-service/list";

        public static final String URL_API_SEARCH_VEHICLE_CARD = "/manager-service/vehicle-card/search";
        public static final String URL_API_DETAIL_VEHICLE_CARD = "/manager-service/vehicle-card/get-one";
        public static final String URL_API_UPDATE_VEHICLE_CARD = "/manager-service/vehicle-card/update";
        public static final String URL_API_REMOVE_VEHICLE_CARD = "/manager-service/vehicle-card/remove";
        public static final String URL_API_LIST_STATUS_VEHICLE_CARD = "/manager-service/vehicle-card/list";
        public static final String URL_API_SEARCH_RESIDENT_CARD = "/admin/resident-card/search";
        public static final String URL_API_UPDATE_RESIDENT_CARD = "/admin/resident-card/update";
        public static final String URL_API_REMOVE_RESIDENT_CARD = "/admin/resident-card/remove";
        public static final String URL_API_ADD_RESIDENT_CARD = "/admin/resident-card/add";

        public static final String URL_API_SEARCH_APARTMENT = "/admin/apartment/search";
        public static final String URL_API_ADD_APARTMENT_OWNER = "/admin/apartment/add/owner";
        public static final String URL_API_ADD_A_APARTMENT = "/admin/a/apartment/add";
        public static final String URL_API_ADD_APARTMENT_RESIDENT = "/admin/apartment/add/resident";
        public static final String URL_API_DISABLE_APARTMENT = "/admin/resident/remove-person";
        public static final String URL_API_SEARCH_APARTMENT_RESIDENT = "/admin/apartment/search/resident";
        public static final String URL_API_EXPORT_APARTMENT = "/mem-ber/apartment/export";
        public static final String URL_API_FLOOR_LIST = "/admin/floor/list";
        public static final String URL_API_BLOCK_LIST = "/admin/block/list";
        public static final String URL_API_ROOM_NUMBER_SEARCH = "/admin/room-number/search";
        public static final String URL_API_UPDATE_RESIDENT = "/admin/resident/update";
        public static final String URL_API_VALIDATE_OWNER = "/admin/validate/owner";
        public static final String URL_API_VALIDATE_RESIDENT = "/admin/validate/resident";

        public static final String URL_API_SEARCH_DETAIL_SUB_SERVICE = "/tenant/detail_sub_service/search";
        public static final String URL_API_SEARCH_REASON_DETAIL_SUB_SERVICE = "/tenant/reason_detail_sub_service/search";
        public static final String URL_API_SERVICE_REQUEST_PRICE = "/landlord/service_request/price";
        public static final String URL_API_ADD_SERVICE_REQUEST = "/landlord/service_request/add";
        public static final String URL_API_TYPE_APARTMENT = "/landlord/type_apartment";
        public static final String URL_API_LIST_SERVICE_REQUEST = "/landlord/service_request/list";
        public static final String URL_API_ADD_RESIDENT_CARD_CLIENT = "/landlord/resident_card/add";
        public static final String URL_API_ADD_VEHICLE_CARD_CLIENT = "/landlord/vehicle_card/add";
        public static final String URL_API_STATUS_SERVICE_REQUEST = "/landlord/status_request";

        public static final String URL_API_LIST_NOTIFICATION = "/landlord/notifications";
        public static final String URL_API_LIST_NOTIFICATION_PRIVATE = "/landlord/notifications/private";
        public static final String URL_API_LIST_NOTIFICATION_NUMBER = "/landlord/notifications/numbers";
        public static final String URL_API_UDPATE_NOTIFICATION = "/landlord/update-status";
        public static final String URL_API_FEE_SERVICE = "/admin/fee-apartment/service";
        public static final String URL_API_FEE_TOTAL_CARD = "/admin/fee-apartment/total-card";
        public static final String URL_API_FEE_APARTMENT_TOTAL = "/admin/fee-apartment/total";
        public static final String URL_API_VEHICLE_BY_ACCOUNT_ID_AND_VEHICLE_TYPE = "/manager-service/vehicle-card/search/account-id/vehicle-id";
        public static final String URL_API_FEE_APARTMENT_SEARCH_RESIDENT_CARD = "/admin/fee-apartment/search/resident-card";
        public static final String URL_API_FEE_APARTMENT_SEARCH_VEHICLE_CARD = "/admin/fee-apartment/search/vehicle-card";
        public static final String URL_API_UPDATE_STATUS_FEE_APARTMENT = "/admin/fee-apartment/update";

        public static final String URL_API_LOGIN = "/login";
        public static final String URL_API_PROFILE = "/member/account/profile";
        public static final String URL_API_UPDATE_PROFILE_ACCOUNT = "/tenant/update/profile";
        public static final String URL_API_FORWARD_PASSWORD = "/forward-password";
        public static final String URL_API_RESET_PASSWORD = "/reset-password";
        public static final String URL_API_CHANGE_PASSWORD = "/change-password";

        public static final String URL_API_DETAIL_ACCOUNT = "/tenant/detail/account";
        public static final String URL_API_CHANGE_PASSWORD_APP = "/tenant/change-password";
        public static final String URL_API_DEPENDENT_PERSON_APP = "/tenant/dependent-person";
        public static final String URL_API_MANAGEMENT_PERSON_APP = "/tenant/management-person";
        public static final String URL_API_DETAIL_BUILDING_APP = "/tenant/detail-building";
        public static final String URL_API_REQUEST_SERVICE_REGISTER_APP = "/landlord/request-service/list";
        public static final String URL_API_VEHICLE_APP = "/tenant/vehicle-by-account-id";

        public static final String URL_API_DASHBOARD_NUMBER = "/manager-service/dashboard/dashboardNumber";
        public static final String URL_API_DASHBOARD_DASHBOARD_YEARLY_TOTAL_REVENUE = "/manager-service/dashboard/yearlyTotalRevenue";
        public static final String URL_API_DASHBOARD_DASHBOARD_YEARLY_COUNT_SERVICE_REQUEST = "/manager-service/dashboard/yearlyCountServiceRequest";
        public static final String URL_API_DASHBOARD_DASHBOARD_MONTHLY_ACCOUNT = "/manager-service/dashboard/monthlyAccount";
        public static final String URL_API_DASHBOARD_DASHBOARD_TYPE_APARTMENT_ACCOUNT = "/manager-service/dashboard/typeApartmentAccount";
        public static final String URL_API_DASHBOARD_DASHBOARD_MONTHLY_VEHICLE = "/manager-service/dashboard/monthlyVehicle";
        public static final String URL_API_DASHBOARD_DASHBOARD_NUMBER_OF_USER_SERVICE_REQUEST = "/manager-service/dashboard/numberOfUseServiceRequest";

        public static final String URL_API_UPDATE_REQUEST_SERVICE_APP = "/landlord/request-service/update-app";
        public static final String URL_API_NOTIFICATION_FEE_APARTMENT = "/tenant/notification-fee-apartment";
        public static final String URL_API_LIST_HOURS = "/tenant/list-hour";

    }

    public class FileProperties {
        private FileProperties() {
        }

        public static final String PROPERTIES_APPLICATION = "application.properties";
        public static final String PROPERTIES_VALIDATION = "messages.properties";
    }

    public class ResidentCard {
        private ResidentCard() {
        }

        public static final String PRICE = "50000";
    }

    public class GeneralSerivce {
        public GeneralSerivce() {
        }

        public static final String FEE_GENERAL_SERVICE = "5000";
    }

    public class NotificationFeeName {
        public NotificationFeeName() {
        }

        public static final String FEE_GENERAL_NAME = "Dịch vụ quản lí chung";
        public static final String FEE_VEHICLE_NAME = "Phí gửi xe";
        public static final String FEE_APARTMENT_CARD_NAME = "Phí thẻ căn hộ đăng kí thêm";
    }
}
