package ir.shahabazimi.omidanasansor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.classes.Utils;
import ir.shahabazimi.omidanasansor.data.RetrofitClient;
import ir.shahabazimi.omidanasansor.models.GeneralResponse;
import ir.shahabazimi.omidanasansor.models.SettingsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    private TextInputEditText points, wallet,invite,bday;
    private MaterialButton reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
        reg = findViewById(R.id.settings_reg);
        points = findViewById(R.id.settings_point);
        wallet = findViewById(R.id.settings_wallet);
        invite = findViewById(R.id.settings_invite);
        bday = findViewById(R.id.settings_bday);
        getSettings();

        reg.setOnClickListener(v -> {
            Utils.hideKeyboard(SettingsActivity.this);
            String w = wallet.getText().toString().replace(",","").trim();
            String p = points.getText().toString();
            String i = invite.getText().toString();
            String b = bday.getText().toString();

            if (p.isEmpty() || w.isEmpty()|| i.isEmpty()|| b.isEmpty()) {
                Toast.makeText(this, "لطفا فرم را تکمیل کنید!", Toast.LENGTH_SHORT).show();

            } else {
                if (Utils.checkInternet(SettingsActivity.this))
                    setSettings(p, w,i,b);
                else
                    Toast.makeText(SettingsActivity.this, "لطفا دسترسی به اینترنت را بررسی کنید!", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setSettings(String points, String wallet,String invite,String bday) {
        reg.setEnabled(false);
        reg.setText("...");
        RetrofitClient.getInstance().getApi().SetSettings(points, wallet,invite,bday)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        reg.setEnabled(true);
                        reg.setText("ثبت");
                        if (response.isSuccessful() && response.body() != null && response.body().getMessage().equals("success")) {
                            Toast.makeText(SettingsActivity.this, "با موفیقت ثبت شد!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SettingsActivity.this, "خطا1! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        reg.setEnabled(true);
                        reg.setText("ثبت");
                        Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }
                });


    }


    private void getSettings() {
        RetrofitClient.getInstance().getApi().GetSettings()
                .enqueue(new Callback<SettingsResponse>() {
                    @Override
                    public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().getData().isEmpty()) {
                            wallet.setText(Utils.moneySeparator(response.body().getData().get(0).getTitlesAmount()));
                            points.setText(response.body().getData().get(1).getTitlesAmount());
                            invite.setText(response.body().getData().get(2).getTitlesAmount());
                            bday.setText(response.body().getData().get(3).getTitlesAmount());
                        } else {
                            Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<SettingsResponse> call, Throwable t) {
                        Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });


    }
}