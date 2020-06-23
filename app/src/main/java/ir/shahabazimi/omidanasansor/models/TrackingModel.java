package ir.shahabazimi.omidanasansor.models;

import com.google.gson.annotations.SerializedName;

public class TrackingModel {

//                    "user_id": "3",
//                    "user_name": "عملیمراد",
//                    "user_code": "452356",
//                    "user_number": "09355452356",
//                    "user_invite": "1",
//                    "user_address": "",
//                    "user_reg": "2020-06-03 08:56:08",
//                    "user_status": "2",
//                    "point_amount": "2",
//                    "wallet_amount": "0"

//                        "user_bday": "1370/2/8",
//                        "user_reason": "1",
//                        "user_reg": "2020-06-14 09:00:16",
//                        "point_amount": "0",
//                        "wallet_amount": "1000000"

    @SerializedName("point_amount")
    private String pointAmount;

    @SerializedName("buy_id")
    private String buyId;

    @SerializedName("user_bday")
    private String userBday;

    @SerializedName("user_reason")
    private String userReason;

    @SerializedName("wallet_amount")
    private String walletAmount;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("user_code")
    private String userCode;

    @SerializedName("user_invite")
    private String userInvite;

    @SerializedName("user_number")
    private String userNumber;

    @SerializedName("user_reg")
    private String userReg;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public String getUserReg() {
        return userReg;
    }

    public String getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(String pointAmount) {
        this.pointAmount = pointAmount;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public String getUserInvite() {
        return userInvite;
    }

    public String getUserBday() {
        return userBday;
    }

    public String getUserReason() {
        return userReason;
    }

    public String getBuyId() {
        return buyId;
    }
}
