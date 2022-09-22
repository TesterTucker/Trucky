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

@Parcelize
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
    var timeStamp: @RawValue Any? = null,
    var truckModel:String?=null

    ): Parcelable

data class TyreDetail(
    var tyreTruckNumber: String? = null,
    var tyreBrand: String? = null,
    var NoOfTyre: String? = null,
)


@Parcelize
data class ClientDetail(
    var clientName:String?=null,
    var typefClient:String?=null,
    var ClientMobileNumber:String?=null,
    var ClientCompany:String?=null,
    var ClientAddress:String?=null,
    var clientDetailsId:String?=null,
    var timeStamp: @RawValue Any? = null,
    var clientNameBank:String?=null,
    var branchName:String?=null,
    var accountNumber:String?=null,
    var ifscCode:String?=null
):Parcelable


data class MarketVehicle(
        var vehicletype:String?=null,
        var date:String?=null,
        var GrNo:String?=null,
        var EBillNo:String?=null,
        var VehicleOwner:String?=null,
        var MobileNumber:String?=null,
        var AccountNumber:String?=null,
        var IfscNumber:String?=null,
        var TruckNumber:String?=null,
        var Chaise_Number:String?=null,
        var DriverName:String?=null,
        var DriverNo:String?=null,
        var ConsignorName:String?=null,
        var ConsignorNo:String?=null,
        var ProductName:String?=null,
        var fixedPerTonee:String?=null,
        var RateWeight:String?=null,
        var ActualRate:String?=null,
        var RateGst:String?=null,
        var Total:String?=null,
        var RateGiven:String?=null,
        var Total_rateGiven:String?=null,
        var Gst_RateGiven:String?=null,
        var Commission_Charge:String?=null,
        var GuideCharge:String?=null,
        var Advance_Amount:String?=null,
        var Advance_Amount_paid:String?=null,
        var AdvancePercentageDeduction:String?=null,
        var toatal_Advance_Amount_Paid:String?=null,
        var NooftimesDieselPaid:String?=null,
        var PumpName:String?=null,
        var DieselRate:String?=null,
        var PerLiter:String?=null,
        var DieselAmount:String?=null,
        var OtherExpenses:String?=null,
        var Remark:String?=null,
        var marketVehicleId: String? = null,
        var timeStamp: @RawValue Any? = null,
)

data class BrokerVehicle(
    var vehicletype:String?=null,
    var date:String?=null,
    var BrokerName:String?=null,
    var EBillNo:String?=null,
    var VehicleOwner:String?=null,
    var MobileNumber:String?=null,
    var AccountNumber:String?=null,
    var IfscNumber:String?=null,
    var TruckNumber:String?=null,
    var Chaise_Number:String?=null,
    var DriverName:String?=null,
    var DriverNo:String?=null,
    var ConsignorName:String?=null,
    var ConsignorNo:String?=null,
    var ProductName:String?=null,
    var fixedPerTonee:String?=null,
    var RateWeight:String?=null,
    var ActualRate:String?=null,
    var RateGst:String?=null,
    var Total:String?=null,
    var PaidBy:String?=null,
    var PaidByNumber:String?=null,
    var BrokerVehicleId: String? = null,
    var timeStamp: @RawValue Any? = null,
)