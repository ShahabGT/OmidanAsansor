package ir.shahabazimi.omidanasansor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.adapters.StatsAdapter;
import ir.shahabazimi.omidanasansor.classes.Utils;
import ir.shahabazimi.omidanasansor.data.RetrofitClient;
import ir.shahabazimi.omidanasansor.models.StatsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity {

    private RecyclerView yearRecycler;
    private StatsAdapter yearAdapter;
    private TextInputEditText year;
    private MaterialButton check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        init();


    }


    private void init() {

        year = findViewById(R.id.stats_search_year);
        check = findViewById(R.id.stats_check);

        yearRecycler = findViewById(R.id.stats_year_recycler);
        yearRecycler.setLayoutManager(new LinearLayoutManager(this));

        onClicks();

    }

    private void onClicks() {


        check.setOnClickListener(w -> {
            if (year.getText().toString().isEmpty() || year.getText().toString().length() < 4 ||
                    !year.getText().toString().startsWith("139") || year.getText().toString().startsWith("140")) {
                Toast.makeText(this, "لطفا مقدار سال را وارد کنید", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Utils.checkInternet(this)) {
                Utils.hideKeyboard(StatsActivity.this);
                getYearStats(year.getText().toString());
            } else {
                Toast.makeText(this, "لطفا اتصال به اینترنت را بررسی کنید", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void getYearStats(String year) {
        check.setEnabled(false);
        check.setText("...");
        String eYear = String.valueOf(Integer.valueOf(year) + 621);
        RetrofitClient.getInstance().getApi()
                .GetStats(eYear)
                .enqueue(new Callback<StatsResponse>() {
                    @Override
                    public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                        check.setEnabled(true);
                        check.setText("ثبت");
                        if (response.isSuccessful() && response.body() != null && response.body().getData()!=null) {
                            yearAdapter = new StatsAdapter(StatsActivity.this, response.body().getData(), year);
                            yearRecycler.setAdapter(yearAdapter);
                        } else {
                            Toast.makeText(StatsActivity.this, "خط! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<StatsResponse> call, Throwable t) {
                        check.setEnabled(true);
                        check.setText("ثبت");
                        Toast.makeText(StatsActivity.this, "خط! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
