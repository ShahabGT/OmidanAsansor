package ir.shahabazimi.omidanasansor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.classes.ConfirmInterface;
import ir.shahabazimi.omidanasansor.classes.Utils;
import ir.shahabazimi.omidanasansor.data.RetrofitClient;
import ir.shahabazimi.omidanasansor.dialogs.ConfirmDialog;
import ir.shahabazimi.omidanasansor.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyActivity extends AppCompatActivity {

    private TextInputEditText search,name,price;
    private MaterialButton reg;
    private String cName = "";
    private String cNumber = "";
    private String cId = "";
    private String cWallet = "";
    private String cCode = "";
    private LinearLayout loading;
    private Spinner reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        init();

    }

    private void init(){
        reason = findViewById(R.id.service_reason);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reason, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reason.setAdapter(adapter);
        search = findViewById(R.id.service_code);
        name = findViewById(R.id.service_name);
        price = findViewById(R.id.service_price);
        reg = findViewById(R.id.service_reg);
        loading = findViewById(R.id.service_loading);

        onClicks();
    }

    private void onClicks(){

        reg.setOnClickListener(w->{
            Utils.hideKeyboard(BuyActivity.this);
            String p = price.getText().toString().trim().replace(",","");
            String t = reason.getSelectedItemPosition()+1+"";

            if (p.isEmpty() || t.isEmpty()) {

                Toast.makeText(this, "لطفا مشخصات خرید را کامل کنید", Toast.LENGTH_SHORT).show();
            } else if (cId.isEmpty()) {
                Toast.makeText(this, "لطفا شماره مشتری را وارد کنید", Toast.LENGTH_SHORT).show();

            } else {

                ConfirmDialog dialog = new ConfirmDialog(BuyActivity.this, cCode, cName, cWallet, p, new ConfirmInterface() {
                    @Override
                    public void onClick(String amount, String wallet, String pay) {
                        if(Utils.checkInternet(BuyActivity.this))
                            buy(amount, wallet, pay,t);
                        else
                            Toast.makeText(BuyActivity.this, "لطفا دسترسی به اینترنت را بررسی کنید!", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                price.removeTextChangedListener(this);

                String value = price.getText().toString();


                if (!value.equals("")) {
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        price.setText("");
                    }


                    String str = price.getText().toString().replaceAll(",", "");
                    price.setText(Utils.moneySeparator(str));
                    price.setSelection(price.getText().toString().length());

                }
                price.addTextChangedListener(this);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    searchCode(s.toString());
                } else {
                    cName = "";
                    cNumber = "";
                    cId = "";
                    cWallet = "";
                    cCode = "";
                    name.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    searchCode(s.toString());

                } else {
                    cName = "";
                    cNumber = "";
                    cId = "";
                    cWallet = "";
                    cCode = "";
                    name.setText("");

                }
            }
        });
    }

    private void searchCode(String code) {
        Utils.hideKeyboard(BuyActivity.this);

        cName = "";
        cNumber = "";
        cId = "";
        cWallet = "";
        cCode = "";


        RetrofitClient.getInstance().getApi().search(code)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if(response.body().getMessage().equals("success")) {
                                cName = response.body().getName();
                                cId = response.body().getId();
                                cNumber = response.body().getNumber();
                                cWallet = response.body().getWallet();
                                cCode = code;
                                name.setText(cName);

                            }else if(response.body().getMessage().equals("empty")){
                                name.setText("کاربر وجود ندارد");

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {

                    }
                });
    }

    private void buy(String total,String wallet,String pay,String title){
        loading.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getApi()
                .buy(cId,total,wallet,pay,title)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            loading.setVisibility(View.GONE);

                            if(response.body().getMessage().equals("success")){
                                Toast.makeText(BuyActivity.this, "با موفیقت ثبت شد!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else{
                                Toast.makeText(BuyActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            }


                        }else{
                            Toast.makeText(BuyActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        loading.setVisibility(View.GONE);

                        Toast.makeText(BuyActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }
                });




    }

}