package com.androiddeft.firebasepushnotification;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.blog.library.UpdateChecker;
import com.google.android.material.navigation.NavigationView;

import static com.androiddeft.firebasepushnotification.ExecutiveMember.isNetworkStatusAvialable;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    ScrollView scrollView;
    Toolbar toolbar = null;
    int action = 0;
    String fbapp = "fb://group/662549573867987";
    String fburl = "https://www.facebook.com/groups/662549573867987";
    private Context liContext = null;

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 22) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_home);
        hideitem();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        liContext = this.getApplicationContext();

        FrameLayout frameLayout = findViewById(R.id.layout);
        LinearLayout linearLayout = frameLayout.findViewById(R.id.update);
        LinearLayout linearLayout_fb = frameLayout.findViewById(R.id.facebook);
        LinearLayout linearLayout_online = frameLayout.findViewById(R.id.online);
        LinearLayout linearLayout_alumni = frameLayout.findViewById(R.id.alumni);
        LinearLayout linearLayout_adviser = frameLayout.findViewById(R.id.adviser);
        linearLayout_adviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleText = getString(R.string.commingsoon);
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setMessage(color)
                        /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                        .setCancelable(true)
                        .show();
            }
        });
        linearLayout_alumni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleText = getString(R.string.commingsoon);
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setMessage(color)
                        /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                        .setCancelable(true)
                        .show();
            }
        });
        linearLayout_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    Uri uri = Uri.parse(getString(R.string.dairy_link));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    String titleText = getString(R.string.online_error);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    UpdateChecker.checkForDialog(Home.this);
                } else {
                    String titleText = getString(R.string.check_new_update_error);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
            }
        });

        linearLayout_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    if (isAppInstalled(liContext, "com.facebook.orca") || isAppInstalled(liContext, "com.facebook.katana")
                            || isAppInstalled(liContext, "com.example.facebook") || isAppInstalled(liContext, "com.facebook.android")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fbapp)));
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fburl)));
                    }

                } else {
                    String titleText = getString(R.string.fb_grp);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void hideitem() {
        navigationView = findViewById(R.id.nav_view);
        Menu drawer_menu = navigationView.getMenu();
        drawer_menu.findItem(R.id.nav_home).setChecked(true).setEnabled(false);
    }

    private void doAction(Class c) {
        startActivity(new Intent(this, c));
        this.action++;
        if (this.action > 1) {

        }
    }

    public void Executive (View view) {
        doAction(ExecutiveMember.class);
    }

    public void General (View view) {
        doAction(GeneralMember.class);
    }

    public void About(View view) {
        doAction(About.class);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.nav_update:
                UpdateChecker.checkForDialog(Home.this);
                return true;*/

            case R.id.nav_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.app_name)));
                String shareMessage = (getString(R.string.msg_share));
                shareMessage = shareMessage + "https://bit.ly/dairy19app";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, (getString(R.string.share))));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_home:
                Intent h = new Intent(Home.this, Home.class);
                startActivity(h);
                break;
            case R.id.ExecutiveMember:
                Intent i = new Intent(Home.this, ExecutiveMember.class);
                startActivity(i);
                break;
            case R.id.GeneralMember:
                Intent s = new Intent(Home.this, GeneralMember.class);
                startActivity(s);
                break;

            case R.id.nav_update:
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    UpdateChecker.checkForDialog(Home.this);
                } else {
                    String titleText = getString(R.string.check_new_update_error);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
                break;

            case R.id.nav_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.app_name)));
                String shareMessage = (getString(R.string.msg_share));
                shareMessage = shareMessage + "https://bit.ly/dairy19app";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, (getString(R.string.share))));
                break;
            case R.id.nav_about:
                Intent a = new Intent(Home.this, About.class);
                startActivity(a);
                break;

            case R.id.FacebookGroupSecret:
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    if (isAppInstalled(liContext, "com.facebook.orca") || isAppInstalled(liContext, "com.facebook.katana")
                            || isAppInstalled(liContext, "com.example.facebook") || isAppInstalled(liContext, "com.facebook.android")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fbapp)));
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fburl)));
                    }

                } else {
                    String titleText = getString(R.string.fb_grp);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
                break;

            case R.id.FacebookGroupMember:
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    if (isAppInstalled(liContext, "com.facebook.orca") || isAppInstalled(liContext, "com.facebook.katana")
                            || isAppInstalled(liContext, "com.example.facebook") || isAppInstalled(liContext, "com.facebook.android")) {
                        startActivity(new Intent (Intent.ACTION_VIEW, Uri.parse(fbapp)));
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fburl)));
                    }

                } else {
                    String titleText = getString(R.string.fb_grp);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /* .setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
