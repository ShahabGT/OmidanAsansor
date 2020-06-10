package ir.shahabazimi.omidanasansor.data;



import ir.shahabazimi.omidanasansor.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("request.php")
    Call<GeneralResponse> SendRequest(
            @Field("name") String name,
            @Field("number") String number,
            @Field("address") String address);
}


