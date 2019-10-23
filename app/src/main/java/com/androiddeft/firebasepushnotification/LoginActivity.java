package com.androiddeft.firebasepushnotification;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.blog.library.UpdateChecker;
import static com.androiddeft.firebasepushnotification.ExecutiveMember.isNetworkStatusAvialable;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 23) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_login);

        final EditText Name = findViewById(R.id.username);
        final EditText Password = findViewById(R.id.password);
        final Button Login = findViewById(R.id.login);
        final Button checknew = findViewById(R.id.checkNew);

        checknew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateChecker.checkForDialog(LoginActivity.this);
            }
        });
        if (isNetworkStatusAvialable(getApplicationContext())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checknew.performClick();
                }
            }, 1);
        } else {

            Toast.makeText(getApplicationContext(), (getString(R.string.connect_net)), Toast.LENGTH_LONG).cancel();
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }
    private void validate(String username, String password) {
        if ((username.equals("11111")) && (password.equals("11111"))) {
            Intent intent = new Intent(LoginActivity.this, Home.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), (getString(R.string.login_success)), Toast.LENGTH_LONG).cancel();
        } else {
            Toast.makeText(getApplicationContext(), (getString(R.string.login_unsuccess)), Toast.LENGTH_LONG).show();
        }
    }
}