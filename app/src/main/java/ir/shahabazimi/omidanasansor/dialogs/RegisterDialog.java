package ir.shahabazimi.omidanasansor.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.classes.Utils;
import ir.shahabazimi.omidanasansor.data.RetrofitClient;
import ir.shahabazimi.omidanasansor.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterDialog extends Dialog implements DatePickerDialog.OnDateSetListener {

    private Context context;
    private TextInputEditText name, code, phone,bDate;
    private View bDateView;
    private MaterialButton register,cancel;
    private Spinner reason;


    public RegisterDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_register);
        init();
    }

    private void init() {
        reason = findViewById(R.id.reg_reason);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.reason, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reason.setAdapter(adapter);
        name = findViewById(R.id.reg_name);
        code = findViewById(R.id.reg_code);
        bDateView = findViewById(R.id.reg_bd_view);
        bDate = findViewById(R.id.reg_bd);
        phone = findViewById(R.id.reg_number);

        cancel = findViewById(R.id.reg_cancel);
        register = findViewById(R.id.reg_reg);


        onClicks();
    }

    private void onClicks() {
        cancel.setOnClickListener(x -> dismiss());

        bDateView.setOnClickListener(w->{
            PersianCalendar persianCalendar = new PersianCalendar();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    this::onDateSet,
                    persianCalendar.getPersianYear(),
                    persianCalendar.getPersianMonth(),
                    persianCalendar.getPersianDay()
            );
            PersianCalendar maxDate = new PersianCalendar();
            maxDate.setPersianDate(1398,12,29);

            PersianCalendar minDate = new PersianCalendar();
            minDate.setPersianDate(1300,1,1);
            datePickerDialog.setMaxDate(maxDate);
            datePickerDialog.setMinDate(minDate);
            datePickerDialog.setFirstDayOfWeek(Calendar.SATURDAY);
            //   datePickerDialog.setThemeDark(true);

            datePickerDialog.show(((Activity)context).getFragmentManager(), "Datepickerdialog");
        });

        register.setOnClickListener(x -> {
            Utils.hideKeyboard(getOwnerActivity());
            String n = name.getText().toString().trim();
            String c = code.getText().toString().trim();
            String b = bDate.getText().toString().trim();
            String p = phone.getText().toString().replace(" ","");
            String r = reason.getSelectedItemPosition()+1+"";

            if (n.isEmpty() || p.isEmpty() || p.length() < 11) {
                Toast.makeText(context, "لطفا نام و نام خانوادگی/ شماره تلفن را بررسی کنید", Toast.LENGTH_SHORT).show();
            } else {
                if (Utils.checkInternet(context))
                    reg(n, c, b, p,r);
                else
                    Toast.makeText(context, "لطفا دسترسی به اینترنت را بررسی کنید!", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"/"+(monthOfYear+1)+"/"+dayOfMonth;
        bDate.setText(date);

    }

    private void reg(String name, String invite, String birthday, String phone, String reason) {
        cancel.setEnabled(false);
        register.setEnabled(false);
        register.setText("...");
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        RetrofitClient.getInstance().getApi()
                .register(name,phone,birthday,invite,reason)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        setCanceledOnTouchOutside(true);
                        setCancelable(true);
                        cancel.setEnabled(true);
                        register.setEnabled(true);
                        register.setText("ثبت نام");
                        if(response.isSuccessful() && response.body()!=null  ){
                            if(response.body().getMessage().equals("success")) {
                                Toast.makeText(context, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }else if(response.body().getMessage().equals("conflict")){
                                Toast.makeText(context, "این شماره قبلا ثبت شده است", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        setCanceledOnTouchOutside(true);
                        setCancelable(true);
                        cancel.setEnabled(true);
                        register.setEnabled(true);
                        register.setText("ثبت نام");
                        Toast.makeText(context, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
