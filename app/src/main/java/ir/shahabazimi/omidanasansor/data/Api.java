package ir.shahabazimi.omidanasansor.data;



import ir.shahabazimi.omidanasansor.models.GeneralResponse;
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
            @Field("title") String title
    );
}


