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
        if ((username.equals("1")) && (password.equals("1"))
                ||(username.equals("asifomar580@gmail.com")) && (password.equals("01984288806"))
                ||(username.equals("imostakim1@gmail.com")) && (password.equals("01880517297"))
                ||(username.equals("spranto89@gmail.com")) && (password.equals("01686074595"))
                ||(username.equals("sdasshuvro@gmail.com")) && (password.equals("01614556530"))
                ||(username.equals("omarfaruk13524@gmail.com")) && (password.equals("01725648111"))
                ||(username.equals("mehedihassan.ru.ais@gmail.com")) && (password.equals("01833879901"))
                ||(username.equals("zahirulislamjone747@gmail.com")) && (password.equals("01876870291"))
                ||(username.equals("shakhawatkhan742@gmail.com")) && (password.equals("01632017998"))
                ||(username.equals("ashohan74@gmail.com")) && (password.equals("01965430448"))
                ||(username.equals("shawonziko@gmail.com")) && (password.equals("01786387816"))
                ||(username.equals("anikchandro4@gmail.com")) && (password.equals("01881559688"))
                ||(username.equals("waliulmohammed16@gmail.com")) && (password.equals("01797271431"))
                ||(username.equals("kharman3183@gmail.com")) && (password.equals("01316604110"))
                ||(username.equals("salamaisru2015@gmail.com")) && (password.equals("01832303721"))
                ||(username.equals("mdanamul359@gmail.com")) && (password.equals("01624194374"))
                ||(username.equals("emonkhanjbd821@gmail.com")) && (password.equals("01537102871"))
                ||(username.equals("jahanishu100@gmail.com")) && (password.equals("01796980741"))
                ||(username.equals("mamunahmed.ru@gmail.com")) && (password.equals("01939760230"))
                ||(username.equals("opustat18106@gmail.com")) && (password.equals("01864695516"))
                ||(username.equals("1996abdulbaritamal@gmail.com")) && (password.equals("01856197028"))
                ||(username.equals("tanisullah37@gmail.com")) && (password.equals("01856013629"))
                ||(username.equals("kaocherislam97@gmail.com")) && (password.equals("01933978343"))
                ||(username.equals("mruddin0687@gmail.com")) && (password.equals("01771517312"))
                ||(username.equals("msahedzamanhridoy@gmail.com")) && (password.equals("01736209718"))
                ||(username.equals("n.nahid1998@gmail.com")) && (password.equals("01729853391"))
                ||(username.equals("ashikmgt20ru2016@gmail.com")) && (password.equals("01621615491"))
                ||(username.equals("ashikrahman947@gmail.com")) && (password.equals("01773441315"))
                ||(username.equals("meharab.nibir.360@gmail.com")) && (password.equals("01731584522"))
                ||(username.equals("yasinmollahstat123@gmail.com")) && (password.equals("01738890025"))
                ||(username.equals("sultanajakiajsb@gmail.com")) && (password.equals("01910845877"))
                ||(username.equals("tanjir127@gmail.com")) && (password.equals("01626254689"))
                ||(username.equals("syedalabibarumi7@gmail.com")) && (password.equals("01717138893"))
                ||(username.equals("mdmehedihasan5512@gmail.com")) && (password.equals("01610106278"))
                ||(username.equals("rahmanmizanur130@gmail.com")) && (password.equals("01780559636"))
                ||(username.equals("mdifranulislam@gmail.com")) && (password.equals("01534902331"))
                ||(username.equals("www.nimulislam123@gmail.com")) && (password.equals("01706166232"))
                ||(username.equals("mdisrailhossain131@gmail.com")) && (password.equals("01787020613"))
                ||(username.equals("khalilibrahim581770@gmail.com")) && (password.equals("01731698472"))
                ||(username.equals("ahmedrajutara@gmail.com")) && (password.equals("01765859382"))
                ||(username.equals("www.mdalamin1310@gmail.com")) && (password.equals("01745534715"))
                ||(username.equals("rifatkhandaker723@gmail.com")) && (password.equals("01646934680"))
                ||(username.equals("rimonsunker@gmail.com")) && (password.equals("01744889271"))
                ||(username.equals("sohel.ahmed1357@gmail.com")) && (password.equals("01734239541"))
                ||(username.equals("hasibshanto71@gmail.com")) && (password.equals("01631687110"))
                ||(username.equals("muradislam492@gmail.com")) && (password.equals("01629300667"))
                ||(username.equals("afjalur.xpn@gmail.com")) && (password.equals("01771847614"))
                ||(username.equals("badrulalam191@gmail.com")) && (password.equals("01728759482"))
                ||(username.equals("neymarimran09998@gmail.com")) && (password.equals("01766075899"))
                ||(username.equals("prantikpori248@gmail.com")) && (password.equals("01674082445"))
                ||(username.equals("suvobfdf@gmail.com")) && (password.equals("01911612515"))
                ||(username.equals("anabilckm63@gmail.com")) && (password.equals("01305607935"))
                ||(username.equals("neamulhassan.mkt.ru@gmail.com")) && (password.equals("01760126423"))
                ||(username.equals("fojailalhasan@gmail.com")) && (password.equals("01677818944"))
                ||(username.equals("zihadhosen147@gmail.com")) && (password.equals("01753154471"))
                ||(username.equals("jkofficial008@gmail.com")) && (password.equals("01521238728"))
                ||(username.equals("rimonsunker@gmail.com")) && (password.equals("01744889271"))
                ||(username.equals("sabbirhossen4226@gmail.com")) && (password.equals("01739632928"))
                ||(username.equals("adarnob@yahoo.com")) && (password.equals("01798118997"))
                ||(username.equals("sharzil66@gmail.com")) && (password.equals("01799007635"))
                ||(username.equals("rhripon063@gmail.com")) && (password.equals("0175693"))
                ||(username.equals("hossainrifat664@gmail.com")) && (password.equals("01624265722"))
                ||(username.equals("mdifranulislam@gmail.com")) && (password.equals("01534902331"))
                ||(username.equals("emonkhanjbd821@gmail.com")) && (password.equals("01537102871"))
                ||(username.equals("shakibshourav7@gmail.com")) && (password.equals("01771275540"))
                ||(username.equals("nishatanam.18@gmail.com")) && (password.equals("01979158574"))
                ||(username.equals("muradislam492@gmail.com")) && (password.equals("01629300667"))
                ||(username.equals("tasmia.tabassum017@yahoo.com")) && (password.equals("01521502013"))
                ||(username.equals("mruddin0687@gmail.com")) && (password.equals("01913837846"))
                ||(username.equals("Jahidkeriya@gmail.com")) && (password.equals("01625024066"))
                ||(username.equals("brizetshithy58@gmail.com")) && (password.equals("01763726530"))
                ||(username.equals("badrulalam191@gmail.com")) && (password.equals("01728759482"))
                ||(username.equals("mdabuhanif19110@gmail.com")) && (password.equals("01864567748"))) {
            Intent intent = new Intent(LoginActivity.this, Home.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), (getString(R.string.login_success)), Toast.LENGTH_LONG).cancel();
        } else {
            Toast.makeText(getApplicationContext(), (getString(R.string.login_unsuccess)), Toast.LENGTH_LONG).show();
        }
    }
}