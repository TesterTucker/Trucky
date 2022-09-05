package com.frist.turkey.utils

object AppConstant {

    const val EMAIL_PATTERN =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]{2,3})\$"
    const val NUMBER_PATTERN = "^[0-9]*$"
    const val NAME_PATTERN = "^[A-Za-z0-9]"

    const val MEDIUM_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
    const val STRONG_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$"
    const val GALLERY = 1111
    val IMAGE_MAX_SIZE = 1024
    const val VIDEO = 3333
    const val PDF = 1113
    const val CAMERA = 2222

    /*
        const val MatisseLibVideo = 10004
        const val CAMERA_CONSTANT = 10002
        const val AUDIO_CONSTANT = 10006
        const val VIDEO_CONSTANT = 10003*/
    const val MatisseLib = 10001


    //Media Type
    const val MEDIA_TYPE_IMAGE = 1
    const val MEDIA_TYPE_VIDEO = 2

    const val fromLoginFragment = 1
    const val fromForgotPassword = 2
    const val fromSignUpFragment = 3
    const val fromOtpFragment = 4
    const val fromSignUp = 5
    const val fromNewForgotPassword = 6
    const val fromCreatePassword = 7
    const val fromSignUpDetailsFragment = 8
    const val jobDescriptionFragment = 9

    const val FROM_WHERE = "from"
    const val FROM_PAYMENT = "PaymentSuccess"

    const val PERSISTABLE_PREFERENCE_NAME = "rental_app"

    val SEND: String = "send"
    val RESEND: String = "resend"

    const val REQUEST_CAMERA_IMAGE_CODE = 102
    const val REQUEST_GALLERY_eIMAGE_CODE = 103
    const val REQUEST_CODE_SELECT_DOCUMENTS = 121




    //Intents Constants
    const val IS_MOBILE_VERIFIED = "is_mobile_verified"
    const val IS_FORGOT_PASSWORD = "is_forgot_password"
    const val EXTRA_COUNTRY_CODE = "country_code"
    const val EXTRA_MOBILE_NUMBER = "mobile_number"

    const val SPLASH_TIME_OUT: Long = 1000

}