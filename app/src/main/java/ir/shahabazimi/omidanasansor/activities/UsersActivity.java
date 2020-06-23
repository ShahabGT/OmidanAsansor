package ir.shahabazimi.omidanasansor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.adapters.StatsAdapter;
import ir.shahabazimi.omidanasansor.adapters.UsersAdapter;
import ir.shahabazimi.omidanasansor.classes.Utils;
import ir.shahabazimi.omidanasansor.data.RetrofitClient;
import ir.shahabazimi.omidanasansor.models.StatsResponse;
import ir.shahabazimi.omidanasansor.models.TrackingModel;
import ir.shahabazimi.omidanasansor.models.TrackingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class UsersActivity extends AppCompatActivity {

    private LinearLayout loading;
    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private SearchView search;
    private MaterialCardView filter;
    private MaterialTextView filterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        search = findViewById(R.id.user_search);
        filter = findViewById(R.id.user_filter);
        filterText = findViewById(R.id.user_filter_text);
        recyclerView = findViewById(R.id.user_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loading = findViewById(R.id.users_loading);
        loading.setVisibility(View.VISIBLE);
        getData();

        filter.setOnClickListener(v->{
            if(filterText.getText().toString().equals("تمامی کاربران")){
                filterText.setText("کاربران ثبت نام شده");
                adapter.update(1);
            }else if(filterText.getText().toString().equals("کاربران ثبت نام شده")){
                filterText.setText("کاربران خریدار");
                adapter.update(2);


            }else if(filterText.getText().toString().equals("کاربران خریدار")){
                filterText.setText("تمامی کاربران");
                adapter.update(0);
            }


        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.search(newText);
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (dy >0) {
                    // Scroll Down
                    if (filter.isShown()) {
                        filter.setVisibility(View.GONE);
                    }
                }
                else if (dy <0) {
                    // Scroll Up
                    if (!filter.isShown()) {
                        filter.setVisibility(View.VISIBLE);
                    }
                }
            }

        });
    }
    private void getData(){
        RetrofitClient.getInstance().getApi().GetUsers()
                .enqueue(new Callback<TrackingResponse>() {
                    @Override
                    public void onResponse(Call<TrackingResponse> call, Response<TrackingResponse> response) {
                        loading.setVisibility(View.GONE);
                        filter.setVisibility(View.VISIBLE);
                        if(response.isSuccessful() && response.body()!=null){
                            adapter = new UsersAdapter(UsersActivity.this,response.body().getData());
                            recyclerView.setAdapter(adapter);
                        }else{
                            Toast.makeText(UsersActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<TrackingResponse> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        filter.setVisibility(View.VISIBLE);
                        Toast.makeText(UsersActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
    }

}