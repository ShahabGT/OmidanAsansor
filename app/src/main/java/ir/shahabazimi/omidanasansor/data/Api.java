package ir.shahabazimi.omidanasansor.data;



import ir.shahabazimi.omidanasansor.models.DetailsResponse;
import ir.shahabazimi.omidanasansor.models.GeneralResponse;
import ir.shahabazimi.omidanasansor.models.SettingsResponse;
import ir.shahabazimi.omidanasansor.models.StatsResponse;
import ir.shahabazimi.omidanasansor.models.TrackingResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("register.php")
    Call<GeneralResponse> register(
            @Field("name") String name,
            @Field("number") String number,
            @Field("bday") String bday,
            @Field("invite") String invite,
            @Field("reason") String reason);

    @FormUrlEncoded
    @POST("buy.php")
    Call<GeneralResponse> buy(
            @Field("user_id") String userId,
            @Field("amount") String amount,
            @Field("wallet") String wallet,
            @Field("pay") String pay,
            @Field("title") String title,
            @Field("invite") String invite
    );

    @FormUrlEncoded
    @POST("search.php")
    Call<GeneralResponse> search(
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("getstats.php")
    Call<StatsResponse> GetStats(
            @Field("year") String year);

    @FormUrlEncoded
    @POST("getdetails.php")
    Call<DetailsResponse> GetDetails(
            @Field("year") String year,
            @Field("month") String month);

    @GET("getusers.php")
    Call<TrackingResponse> GetUsers();

    @FormUrlEncoded
    @POST("updatepoints.php")
    Call<GeneralResponse> UpdatePoints(
            @Field("user_id") String userId,
            @Field("point") String point);

    @GET("settings.php")
    Call<SettingsResponse> GetSettings();

    @FormUrlEncoded
    @POST("setsettings.php")
    Call<GeneralResponse> SetSettings(
            @Field("points") String points,
            @Field("wallet") String wallet,
            @Field("invite") String invite,
            @Field("bday") String bday,
            @Field("limit1") String limit1,
            @Field("limit2") String limit2,
            @Field("limit3") String limit3
    );
}


