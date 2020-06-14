package ir.shahabazimi.omidanasansor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.dialogs.RegisterDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_setting).setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
        });
        findViewById(R.id.main_reg).setOnClickListener(v->{
            RegisterDialog dialog = new RegisterDialog(MainActivity.this);
            dialog.setCanceledOnTouchOutside(true);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        });
    }
}