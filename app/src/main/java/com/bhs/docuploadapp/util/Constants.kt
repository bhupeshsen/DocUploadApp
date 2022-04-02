package com.bhs.docuploadapp.util



object PrefsConstant{
        const val IS_LOGGED_IN_USER = "IS_LOGGED_IN_USER"
        const val IS_SOCIAL_LOGIN = "IS_SOCIAL_LOGIN"
        const val USER_DATA= "user_data"
        const val IS_GUEST_USER = "IS_GUEST_USER"
        const val IS_COMPLETE_PROFILE_DONE= "is_complete_profile_done"
        const val HEIGHT_OF_VIEW= "height_of_view"
        const val FCM_TOKEN= "fcm_token"
        const val APPLY_FILTER_MODEL= "apply_filter_model"
        const val IS_PROFILE_UPDATED= "is_profile_updated"

}

object DateConstant{
        const val SHOW_DATE_FORMAT = "MMM dd, yyyy"
        const val API_DATE_FORMAT = "yyyy-MM-dd"
        const val SHOW_TIME_FORMAT = "hh:mm a"
        const val SERVER_API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        const val SERVER_CHAT_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val SERVER_API_DATE_FORMAT_TWO = "yyyy-MM-dd HH:mm:ss"
        const val SERVER_UTC_TIMEZONE = "UTC"
        const val SHOW_DATE_WITH_DAY_FORMAT = "MMM dd, EEE hh:mm aa"
        const val SHOW_DATE_WITH_DAY_FORMAT_TWO = "MMM dd, EEE, hh:mm aa"
        const val SHOW_DATE_WITH_DAY_FORMAT_DATE = "MMM dd"
        const val SHOW_DATE_WITH_DAY_FORMAT_DATE_TIME_TWO= "MMM dd , hh:mm aa"
        const val SHOW_DATE_WITH_DAY_FORMAT_DATE_TIME = "hh:mm aa"
        const val SHOW_MMM_DD_FORMAT = "MMM dd"
        const val SHOW_EEE_MMM_DD_DATE_FORMAT = "EEE, MMM dd"

}

object Constants {
        const val LIMIT = "5"
        const val LIMIT_TEN = "10"
        const val timeMillisOf2days = 1000 * 60 * 60 *48
        const val SESSION_MIN_DURATION = 15 //min
        const val timeMillisOf15Min = 1000 * 60 * SESSION_MIN_DURATION
        const val PDF_SIZE = 10
        const val CAMERA_PERMISSION_REQUEST_CODE = 111
        const val GALLERY_PERMISSION_REQUEST_CODE = 112
        const val DOC_PERMISSION_REQUEST_CODE = 113
        const val LOGIN = "Login"
        const val SIGNUP = "Signup"
        const val DELAY_MILLIS = 1000L
        const val LEFT_CHAT = "left"
        const val CHAT_MESSAGE = "CHAT_MESSAGE"
        const val BIO_MAX_LENGTH = 250

}

object SocialPlatformConstants {
        const val GOOGLE = "google"
        const val FACEBOOK = "facebook"
        const val LINKEDIN = "linkedin"
}

object PaymentForConstants {
        const val COURSE = "course"
        const val ELIBRARY = "elibrary"
}

object CourseReportStatusConstants {
        const val COMPLETED = "Completed"
        const val CANCELLED = "Cancelled"
        const val FAILED = "Failed"
        const val PENDING = "Pending"
}



object PaymentStatusConstants {
        const val COMPLETED = "Completed"
        const val CANCELLED = "Cancelled"
        const val FAILED = "Failed"
        const val PENDING = "Pending"
}

object CurrencyTypeConstants {
        const val CURRENCY_TYPE = "USD"
}

object GenderConstants {
        const val MALE = "male"
        const val FEMALE = "female"
}

object StarRatingConstants {
        const val RATING_TWO = "2"
        const val RATING_THREE = "3"
        const val RATING_FOUR = "4"
        const val RATING_FIVE = "5"
}