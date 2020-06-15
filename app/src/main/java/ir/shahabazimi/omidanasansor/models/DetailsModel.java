package ir.shahabazimi.omidanasansor.models;

import com.google.gson.annotations.SerializedName;

public class DetailsModel {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("user_code")
    private String userCode;

    @SerializedName("user_number")
    private String userNumber;

    @SerializedName("user_invite")
    private String userInvite;

    @SerializedName("userAddress")
    private String user_address;

    @SerializedName("user_reg")
    private String userReg;

    @SerializedName("user_status")
    private String userStatus;

    @SerializedName("buy_id")
    private String buyId;

    @SerializedName("buy_title")
    private String buyTitle;

    @SerializedName("buy_price")
    private String buyPrice;

    @SerializedName("buy_wallet")
    private String buyWallet;

    @SerializedName("buy_pay")
    private String buyPay;

    @SerializedName("buy_date")
    private String buyDate;

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

    public String getUserInvite() {
        return userInvite;
    }

    public String getUser_address() {
        return user_address;
    }

    public String getUserReg() {
        return userReg;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getBuyId() {
        return buyId;
    }

    public String getBuyTitle() {
        return buyTitle;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public String getBuyWallet() {
        return buyWallet;
    }

    public String getBuyPay() {
        return buyPay;
    }

    public String getBuyDate() {
        return buyDate;
    }
}
