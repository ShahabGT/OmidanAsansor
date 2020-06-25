package ir.shahabazimi.omidanasansor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    private TextInputEditText points, wallet,invite,bday,limit1,limit2,limit3;
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
        limit1 = findViewById(R.id.settings_limit1);
        limit2 = findViewById(R.id.settings_limit2);
        limit3 = findViewById(R.id.settings_limit3);
        getSettings();

        wallet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                wallet.removeTextChangedListener(this);

                String value = wallet.getText().toString();


                if (!value.equals("")) {
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        wallet.setText("");
                    }


                    String str = wallet.getText().toString().replaceAll(",", "");
                    wallet.setText(Utils.moneySeparator(str));
                    wallet.setSelection(wallet.getText().toString().length());

                }
                wallet.addTextChangedListener(this);
            }
        });
        points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                points.removeTextChangedListener(this);

                String value = points.getText().toString();


                if (!value.equals("")) {
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        points.setText("");
                    }


                    String str = points.getText().toString().replaceAll(",", "");
                    points.setText(Utils.moneySeparator(str));
                    points.setSelection(points.getText().toString().length());

                }
                points.addTextChangedListener(this);
            }
        });
        invite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                invite.removeTextChangedListener(this);

                String value = invite.getText().toString();


                if (!value.equals("")) {
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        invite.setText("");
                    }


                    String str = invite.getText().toString().replaceAll(",", "");
                    invite.setText(Utils.moneySeparator(str));
                    invite.setSelection(invite.getText().toString().length());

                }
                invite.addTextChangedListener(this);
            }
        });


        reg.setOnClickListener(v -> {
            Utils.hideKeyboard(SettingsActivity.this);
            String w = wallet.getText().toString().replace(",","").trim();
            String p = points.getText().toString().replace(",","").trim();
            String i = invite.getText().toString().replace(",","").trim();
            String b = bday.getText().toString();
            String l1 = limit1.getText().toString();
            String l2 = limit2.getText().toString();
            String l3 = limit3.getText().toString();

            if (p.isEmpty() || w.isEmpty()|| i.isEmpty()|| b.isEmpty() || l1.isEmpty() || l2.isEmpty() || l3.isEmpty()) {
                Toast.makeText(this, "لطفا فرم را تکمیل کنید!", Toast.LENGTH_SHORT).show();

            } else {
                if (Utils.checkInternet(SettingsActivity.this))
                    setSettings(p, w,i,b,l1,l2,l3);
                else
                    Toast.makeText(SettingsActivity.this, "لطفا دسترسی به اینترنت را بررسی کنید!", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setSettings(String points, String wallet,String invite,String bday, String l1,String l2, String l3) {
        reg.setEnabled(false);
        reg.setText("...");
        RetrofitClient.getInstance().getApi().SetSettings(points, wallet,invite,bday,l1,l2,l3)
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
                            points.setText(Utils.moneySeparator(response.body().getData().get(1).getTitlesAmount()));
                            invite.setText(Utils.moneySeparator(response.body().getData().get(2).getTitlesAmount()));
                            bday.setText(response.body().getData().get(3).getTitlesAmount());
                            limit1.setText(response.body().getData().get(4).getTitlesAmount());
                            limit2.setText(response.body().getData().get(5).getTitlesAmount());
                            limit3.setText(response.body().getData().get(6).getTitlesAmount());
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