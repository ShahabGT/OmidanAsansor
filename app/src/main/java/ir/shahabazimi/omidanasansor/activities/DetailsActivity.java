package ir.shahabazimi.omidanasansor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.adapters.DetailsAdapter;
import ir.shahabazimi.omidanasansor.data.RetrofitClient;
import ir.shahabazimi.omidanasansor.models.DetailsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String month,year,eyear;
    private DetailsAdapter adapter;
    private LinearLayout loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();

    }

    private void init(){
        loading = findViewById(R.id.details_loading);
        loading.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.details_recycler);

        Bundle b = getIntent().getExtras();
        if(b==null){
            Toast.makeText(this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else{
            month = b.getString("month","");
            year = b.getString("year","");
            eyear = b.getString("eyear","");
        }

        getData();
    }

    private void getData(){

        RetrofitClient.getInstance().getApi()
                .GetDetails(eyear,month)
                .enqueue(new Callback<DetailsResponse>() {
                    @Override
                    public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                        loading.setVisibility(View.GONE);

                        if(response.isSuccessful()&& response.body()!=null){

                            adapter = new DetailsAdapter(response.body().getData());
                            recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                            recyclerView.setAdapter(adapter);
                        }else{
                            Toast.makeText(DetailsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailsResponse> call, Throwable t) {
                        Toast.makeText(DetailsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
    }
}
