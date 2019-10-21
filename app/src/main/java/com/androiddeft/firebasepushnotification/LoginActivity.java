package com.androiddeft.firebasepushnotification;

import android.content.Intent;
import android.net.Uri;
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

    int action = 0;

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

/*        Button button = findViewById(R.id.routine_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertadd = new AlertDialog.Builder(LoginActivity.this);
                LayoutInflater factory = LayoutInflater.from(LoginActivity.this);
                final View view1 = factory.inflate(R.layout.routine, null);
                alertadd.setView(view1);
                alertadd.setNegativeButton(R.string.routine_close, null);
                alertadd.setCancelable(false);
                alertadd.show();
            }
        });*/
    }


    private void validate(String username, String password) {
        if ((username.equals("1510633101")) && (password.equals("01571728906"))
                || (username.equals("1510533102")) && (password.equals("01762824252"))
                || (username.equals("1510633103")) && (password.equals("01765392034"))
                || (username.equals("1510533104")) && (password.equals("01671125913"))
                || (username.equals("1510433105")) && (password.equals("01717211546"))
                || (username.equals("1510333106")) && (password.equals("01734891460"))
                || (username.equals("1512433107")) && (password.equals("01789503867"))
                || (username.equals("1512133109")) && (password.equals("01771790995"))
                || (username.equals("1512233110")) && (password.equals("01316839443"))
                || (username.equals("1510733111")) && (password.equals("01713603880"))
                || (username.equals("1510533112")) && (password.equals("01701006636"))
                || (username.equals("1510633113")) && (password.equals("01752231304"))
                || (username.equals("1510633114")) && (password.equals("01837096132"))
                || (username.equals("1510433115")) && (password.equals("01757279002"))
                || (username.equals("1511133116")) && (password.equals("01752705687"))
                || (username.equals("1512133117")) && (password.equals("01771442224"))
                || (username.equals("1510633118")) && (password.equals("01763015476"))
                || (username.equals("1510633119")) && (password.equals("01781540160"))
                || (username.equals("1512233120")) && (password.equals("01981212787"))
                || (username.equals("1510533121")) && (password.equals("01763826779"))
                || (username.equals("1510533122")) && (password.equals("01753587169"))
                || (username.equals("1510633123")) && (password.equals("01762117160"))
                || (username.equals("1510533124")) && (password.equals("01796482403"))
                || (username.equals("1510833125")) && (password.equals("01716289321"))
                || (username.equals("1510433126")) && (password.equals("01683846046"))
                || (username.equals("1510433127")) && (password.equals("01673436757"))
                || (username.equals("1510833128")) && (password.equals("01714309555"))
                || (username.equals("1512133129")) && (password.equals("01629620772"))
                || (username.equals("1510733130")) && (password.equals("01764567173"))
                || (username.equals("1510533131")) && (password.equals("01930546480"))
                || (username.equals("1510533132")) && (password.equals("01871448367"))
                || (username.equals("1510533133")) && (password.equals("01738711258"))
                || (username.equals("1510633134")) && (password.equals("01783213909"))
                || (username.equals("1512333135")) && (password.equals("01744934709"))
                || (username.equals("1510633136")) && (password.equals("01762711993"))
                || (username.equals("1510633137")) && (password.equals("01829464315"))
                || (username.equals("1510833138")) && (password.equals("01783032892"))
                || (username.equals("1510433139")) && (password.equals("01779469978"))
                || (username.equals("1510433140")) && (password.equals("01790114430"))
                || (username.equals("1510533141")) && (password.equals("01745247972"))
                || (username.equals("1510933142")) && (password.equals("01988034406"))
                || (username.equals("1510633143")) && (password.equals("01773365826"))
                || (username.equals("1510433144")) && (password.equals("01775944932"))
                || (username.equals("1510833145")) && (password.equals("01786387816"))
                || (username.equals("1510633146")) && (password.equals("01738031287"))
                || (username.equals("1510933147")) && (password.equals("01949254412"))
                || (username.equals("1510433148")) && (password.equals("01706687474"))
                || (username.equals("1512133149")) && (password.equals("01747849669"))
                || (username.equals("1510433150")) && (password.equals("01739585069"))
                || (username.equals("1511033151")) && (password.equals("01753730050"))
                || (username.equals("1510533152")) && (password.equals("01793440744"))
                || (username.equals("1510533153")) && (password.equals("01764794002"))
                || (username.equals("1510533155")) && (password.equals("01687373759"))
                || (username.equals("1510433156")) && (password.equals("01834500857"))
                || (username.equals("1510733158")) && (password.equals("01815270354"))
                || (username.equals("1510633160")) && (password.equals("01749366307"))
                || (username.equals("1510533161")) && (password.equals("01760992965"))
                || (username.equals("1510633163")) && (password.equals("01710728948"))
                || (username.equals("1510633164")) && (password.equals("01943186650"))
                || (username.equals("1510633165")) && (password.equals("01828836275"))
                || (username.equals("1512133166")) && (password.equals("01786882505"))
                || (username.equals("1510633167")) && (password.equals("01758530952"))
                || (username.equals("1510633168")) && (password.equals("01747263335"))
                || (username.equals("1512133169")) && (password.equals("01783788352"))
                || (username.equals("1510433171")) && (password.equals("01765230835"))
                || (username.equals("1510433172")) && (password.equals("01755192273"))
                || (username.equals("1510633173")) && (password.equals("01750971752"))
                || (username.equals("1510633174")) && (password.equals("01936835018"))
                || (username.equals("1510633175")) && (password.equals("01679679207"))
                || (username.equals("1510533176")) && (password.equals("01735946252"))
                || (username.equals("1512133177")) && (password.equals("01727732733"))
                || (username.equals("1510833178")) && (password.equals("01771131952"))
                || (username.equals("1510433179")) && (password.equals("01521494633"))
                || (username.equals("1510533180")) && (password.equals("01913349658"))
                || (username.equals("1510733181")) && (password.equals("01623644688"))
                || (username.equals("1510533183")) && (password.equals("01749280708"))
                || (username.equals("1511133184")) && (password.equals("01750313966"))
                || (username.equals("1510533185")) && (password.equals("01776218545"))
                || (username.equals("1510633186")) && (password.equals("01862258141"))
                || (username.equals("1510833187")) && (password.equals("01770449488"))
                || (username.equals("1510633188")) && (password.equals("01703275475"))
                || (username.equals("1510633189")) && (password.equals("01731301352"))
                || (username.equals("1510533190")) && (password.equals("01837036433"))
                || (username.equals("1510433191")) && (password.equals("01928768391"))
                || (username.equals("1510433194")) && (password.equals("01714863691"))
                || (username.equals("1510433195")) && (password.equals("01728566652"))
                || (username.equals("1510333196")) && (password.equals("01763929148"))
                || (username.equals("1510533197")) && (password.equals("01964381214"))
                || (username.equals("1510633198")) && (password.equals("01765819613"))
                || (username.equals("1511033199")) && (password.equals("01752855967"))
                || (username.equals("1512233200")) && (password.equals("01762974133"))
                || (username.equals("1510833201")) && (password.equals("01744758751"))
                || (username.equals("1510633202")) && (password.equals("01744167900"))
                || (username.equals("1510633203")) && (password.equals("01718826938"))
                || (username.equals("1510733204")) && (password.equals("01838579793"))
                || (username.equals("1510833207")) && (password.equals("01763952123"))
                || (username.equals("1512333208")) && (password.equals("01762347934"))
                || (username.equals("1510633209")) && (password.equals("01742648208"))
                || (username.equals("1510633210")) && (password.equals("01756687754"))
                || (username.equals("1510433211")) && (password.equals("01740108911"))
                || (username.equals("1510833212")) && (password.equals("01790162837"))
                || (username.equals("1512333213")) && (password.equals("01759233409"))
                || (username.equals("1512033214")) && (password.equals("01783701659"))
                || (username.equals("1510433215")) && (password.equals("01773927220"))
                || (username.equals("1510933216")) && (password.equals("01773819630"))
                || (username.equals("1510133217")) && (password.equals("01757009896"))
                || (username.equals("1510233218")) && (password.equals("01783067889"))
                || (username.equals("1510933220")) && (password.equals("01717838375"))
                || (username.equals("1510233221")) && (password.equals("01727268458"))
                || (username.equals("1512033222")) && (password.equals("01521405473"))
                || (username.equals("1510233223")) && (password.equals("01551807751"))
                || (username.equals("1510233225")) && (password.equals("01679073855"))
                || (username.equals("1512133226")) && (password.equals("01760049539"))
                || (username.equals("14027008")) && (password.equals("01750853884"))
                || (username.equals("14097081")) && (password.equals("01762845428"))
                || (username.equals("14107098")) && (password.equals("01946887570"))
                || (username.equals("14077127")) && (password.equals("01722347879"))
                || (username.equals("13027191")) && (password.equals("01558922366"))) {
            Intent intent = new Intent(LoginActivity.this, Home.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), (getString(R.string.login_success)), Toast.LENGTH_LONG).cancel();
        } else {

            //User Logged in Failed
            Toast.makeText(getApplicationContext(), (getString(R.string.login_unsuccess)), Toast.LENGTH_LONG).show();

        }
    }

}