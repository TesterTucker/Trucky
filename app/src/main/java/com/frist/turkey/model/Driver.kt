package com.frist.turkey.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Driver(
    var driverName: String? = null,
    var driverMobileNumber: String? = null,
    var driverLicenseNo: String? = null,
    var DriverAadhaarNo: String? = null,
    var driverAddress: String? = null,
    var driverDetailsId: String? = null,
    var timeStamp: @RawValue Any? = null,
) : Parcelable

data class TruckDetail(
    var driverName: String? = null,
    var truckBrand: String? = null,
    var truckRate: String? = null,
    var DelearPurchaseDate: String? = null,
    var truckNumber: String? = null,
    var truckPurchaseDate: String? = null,
    var truckChaiseNumber: String? = null,
    var truckNoOfTyre: String? = null,
    var InsurenceCompanyName: String? = null,
    var InsurenceDate: String? = null,
    var InsurenceRenewalDate: String? = null,
    var InsurenceRenewalDay: String? = null,
    var InsurenceRenewalMonth: String? = null,
    var InsurenceNo: String? = null,
    var FitnessCompanyName: String? = null,
    var FitnessDate: String? = null,
    var FitnessRenewalDate: String? = null,
    var FitnessNumber: String? = null,
    var FitnessRenewalDays: String? = null,
    var FitnessRenewalMonth: String? = null,
    var TaxCompanyName: String? = null,
    var TaxDate: String? = null,
    var TaxRenewalDate: String? = null,
    var TaxNumber: String? = null,
    var TaxDay: String? = null,
    var TaxMonth: String? = null,
    var PermitCompanyname: String? = null,
    var PermitDate: String? = null,
    var PermitRenwalDate: String? = null,
    var PermitNumber: String? = null,
    var PermitRenwalDay: String? = null,
    var PermitRenwalMonth: String? = null,
    var truckDetailsId: String? = null,

    )

data class TyreDetail(
    var tyreTruckNumber: String? = null,
    var tyreBrand: String? = null,
    var NoOfTyre: String? = null,
)