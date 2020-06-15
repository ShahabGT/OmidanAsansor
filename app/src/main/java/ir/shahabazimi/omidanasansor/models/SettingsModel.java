package ir.shahabazimi.omidanasansor.models;

import com.google.gson.annotations.SerializedName;

public class SettingsModel {


    @SerializedName("titles_id")
    private String titlesId;

    @SerializedName("titles_name")
    private String titlesName;

    @SerializedName("titles_amount")
    private String titlesAmount;


    public String getTitlesId() {
        return titlesId;
    }

    public String getTitlesName() {
        return titlesName;
    }

    public String getTitlesAmount() {
        return titlesAmount;
    }
}
