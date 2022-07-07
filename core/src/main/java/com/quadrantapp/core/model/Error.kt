package com.quadrantapp.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Error(
    var code: String,
    var message: String
) : Parcelable{
    companion object {
        // CIAM Error Code
        const val ERROR_OTP_MAX_ATTEMPT_REACHED_ON_VALIDATE_OTP = 4011
        const val ERROR_OTP_MAX_ATTEMPT_REACHED_ON_VALIDATE_MSISDN = 4010
        const val ERROR_UNREGISTERED_EMAIL_ON_VALIDATE_EMAIL = 4013
        const val ERROR_GET_OTP = 4013

        // MW Error Code
        const val UNAUTHORIZED = "132"
        const val PAYMENT_UNDER_60_SECS = "123"
        const val PAYMENT_BALANCE_ON_PROCESS = "140"
        const val UNKNOWN_SERVICE_CODE = "151"
        const val CANCELLED_ACCOUNT_VALIDATE_MSISDN = "142"
        const val PROMO_CODE_INVALID = "144"
        const val PROMO_CODE_EXPIRED = "145"
        const val NOT_FOUND = "213"
        const val ADD_MEMBER_GENERAL = "301"
        const val ADD_MEMBER_ALREADY_HAS_FAMILY = "302"
        const val ADD_MEMBER_BEING_INVITED = "303"
        const val INVALID_OTP = "141"
        const val INVALID_OTP_VALIDATE_NUMBER_DDBRI = "111"
        const val REQUEST_LIMIT_REACHED = "122"
        const val UNKNOWN_REGIST_STATUS = "160"
        const val INCOMPLETE_ONLINE_REGIST = "161"
        const val EXPIRED_ONLINE_REGIST = "162"
        const val NOT_ACTIVATED_YET = "163"
        const val UNKNOWN_SUBS_ACCESS = "159"
        const val MISSION_VOUCHER_OUT_OF_STOCK = "212"
        const val ERROR_DDBRI_INITIALIZATION_DATA_1 = "4001"
        const val ERROR_DDBRI_INITIALIZATION_DATA_2 = "4002"
        const val ERROR_DDBRI_INITIALIZATION_DATA_3 = "4003"

        //XL Satu Lite regisration error
        const val MAX_NIK_REGIST_REACHED = "164"
        const val INVALID_NIK_OR_KK = "165"
        const val INVALID_PUK = "166"
        const val MAX_VALIDATE_PUK_ATTEMPT = "167"
        const val MAX_FAILED_REGIS_REACHED = "168"

        //XL Home Fiber registration pairing error
        const val HOME_FIBER_CANNOT_PAIR = "170"
        const val HOME_FIBER_NOT_ELIGIBLE = "171"
        const val HOME_FIBER_IS_NOT_CONVERGENCE = "174"

        // Troubleshoot Device info
        const val ACS_MODEM_UNDETECTED = "214"

        //PRIO ACTIVATION ERROR INPUT
        const val ICCID_INVALID = "304"
        const val REG_NO_INVALID = "305"

        // FE Error Code
        const val NO_INTERNET = "900"
        const val FE_SOURCE = "800"
        const val NO_DATA = "801"
        const val NO_CACHE = "802"

        const val SDK_MSISDN_LOGIN_NOT_PREPAID_NUMBER_DETECTED = "700"
        const val FE_MSISDN_LOGIN_AXIS_NUMBER_DETECTED = "701"
        const val FE_MSISDN_LOGIN_NUMBER_ALREADY_EXIST = "702"
        const val JAILBROKEN_DETECTED = "703"
        const val SDK_LOGIN_FORM_MAX_OTP_ATTEMPT_REACHED = "704"
        const val SDK_LOGIN_FORM_VALIDATE_MSISDN = "705"
        const val SDK_LOGIN_FORM_VALIDATE_EMAIL = "706"
        const val SDK_OTP_METHOD_NO_ALTERNATE_NUMBER = "707"
        const val SDK_OTP_FORM_REQUEST_OTP = "708"
        const val SDK_OTP_FORM_VALIDATE_OTP = "709"
        const val SDK_OTP_FORM_MAX_OTP_ATTEMPT_REACHED = "710"
        const val SDK_LOGIN_FORM_EMAIL_NOT_REGISTERED = "711"
        const val API_MANUALLOGIN_LOGIN = "712"
        const val API_AUTOLOGIN_GET_ACCESS_TOKEN = "713"
        const val XLAPI_AUTOLOGIN_GET_SSO_TOKEN = "714"
        const val DB_MANUALLOGIN_GETALLSESSIONS = "715"
        const val API_AUTOLOGIN_NON_PREPAID_NUMBER_DETECTED = "716"
        const val API_AUTOLOGIN_AXIS_NUMBER_DETECTED = "717"
        const val SDK_AUTOLOGIN_VALIDATE_SSO_TOKEN = "718"
        const val API_AUTOLOGIN_LOGIN = "719"
        const val SDK_AUTOEXTEND_VALIDATE_MSISDN = "720"
        const val SDK_AUTOEXTEND_VALIDATE_EMAIL = "721"
        const val DB_SPLASHSCREEN_GETALLSESSIONS = "722"
        const val SDK_XENDIT_TOKENIZATION_ERROR = "921"
        const val SDK_XENDIT_SERVER_ERROR = "922"
        const val SDK_XENDIT_INVALID_API_KEY = "923"
        const val SDK_XENDIT_REQUEST_FORBIDDEN_ERROR = "924"
        const val SDK_XENDIT_GENERAL = "929"
        const val SDK_CREATE_SINGLE_USE_TOKEN = "930"
        const val SDK_CREATE_MULTIPLE_USE_TOKEN = "931"
        const val SDK_CREATE_3DS_AUTHENTICATION_TOKEN = "932"
        const val API_PAYMENT_TOPUP_PURCHASE = "933"
        const val API_PAYMENT_BILL_DEPOSIT = "934"
        const val API_BILL_SET_PAYMENT_METHOD = "935"
        const val API_BILL_GET_PAYMENT_METHOD = "936"
        const val API_SET_LOCKUNLOCK_UNDER_15_MINUTES = "135"

        //CP BUNDLING
        const val COUPON_NOT_AVAILABLE = "135"

        //PIN
        const val PIN_NOTVALID_LENGTH6 = "1711"
        const val PIN_NOTVALID_NUMBERONLY = "1712"
        const val PIN_NOTVALID_SAMENUMBER = "1713"
        const val PIN_NOTVALID_ASCENDING = "1714"
        const val PIN_NOTVALID_DESCENDING = "1715"
        const val PIN_NOTVALID_DATEFORMAT = "1716"
        const val PIN_NOTVALID_BLOCKEDPIN = "1717"

        //SHAREQUOTA
        const val SHARE_QUOTA_INTERNET_FAIL = "250"
        const val SHARE_QUOTA_ACCUMULATE_FAIL ="251"
        const val SHARE_QUOTA_SHARE_FAIL = "252"

        //Refresh Network
        const val REFRESH_NETWORK_REACH_500_TPS = "121"
        const val REFRESH_NETWORK_REACH_MAX = "122"
        const val REFRESH_NETWORK_INTERVAL_TIME = "111"
    }

    enum class Source(
        val source: String
    ) {
        FE("FE"),
        API("API"),
        DB("DB"),
        SDK("SDK"),
        XLAPI("API XL");
    }
}