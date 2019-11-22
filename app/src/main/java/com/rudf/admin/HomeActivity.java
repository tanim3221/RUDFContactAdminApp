package com.rudf.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.blog.library.UpdateChecker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.rudf.admin.WebActivity.isNetworkStatusAvialable;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    ScrollView scrollView;
    MenuItem popup;
    FloatingActionButton add_member;
    Toolbar toolbar = null;
    String master_admin = "http://rudf.6te.net/webapp/database/admin";
    String editor_admin = "http://rudf.6te.net/webapp/database/editor";
    String add_member_rudf = "http://rudf.6te.net/webapp/database/users";


    String admin_pass = "http://rudf.6te.net/webapp/database/admin/change-password.php";
    String editor_pass = "http://rudf.6te.net/webapp/database/editor/change-password.php";

    String fbapp = "fb://group/49880688703";
    String fburl = "https://www.facebook.com/groups/bfdf.ru/";
    String pageApp = "fb://page/169680089735915";
    String pageurl = "https://www.facebook.com/rubfdf/";

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
        final Button checknew = frameLayout.findViewById(R.id.checkNew);
        final TextView admin = (TextView) findViewById(R.id.admin);
        final TextView editor = (TextView) findViewById(R.id.editor);

        final TextView add_member = (TextView) findViewById(R.id.add_member);

        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(getApplicationContext(), WebActivity.class);
                aa.putExtra("url", (add_member_rudf));
                startActivity(aa);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(getApplicationContext(), WebActivity.class);
                aa.putExtra("url", (master_admin));
                startActivity(aa);
            }
        });

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ie = new Intent(getApplicationContext(), WebActivity.class);
                ie.putExtra("url", (editor_admin));
                startActivity(ie);
            }
        });

        checknew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateChecker.checkForDialog(HomeActivity.this);
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

        final TextView popup = (TextView) findViewById(R.id.rudfapp);

        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nintent = getPackageManager().getLaunchIntentForPackage(getString(R.string.namaz_app_package));
                if (nintent != null) {
                    nintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(nintent);
                } else {
                    String titleText = getString(R.string.namaz_app_download_details);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle(getString(R.string.namaz_app_download_title))
                            .setMessage(color)
                            .setNegativeButton(getString(R.string.later), null)
                            .setIcon(getResources().getDrawable(R.drawable.ic_file_download))
                            .setPositiveButton(getString(R.string.download_now), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (isNetworkStatusAvialable(getApplicationContext())) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.namaz_app_download_url)));
                                        startActivity(browserIntent);
                                        Toast.makeText(getApplicationContext(), R.string.namaz_app_download_toast, Toast.LENGTH_LONG).show();
                                    } else {
                                        String titleText = getString(R.string.namaz_app_download_error);
                                        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                                        SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                                        color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                        builder.setTitle(getString(R.string.connect_net))
                                                .setMessage(color)
                                                /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                                                .setCancelable(true)
                                                .show();
                                    }
                                }
                            })
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
        if (menu instanceof MenuBuilder) {  //To display icon on overflow menu

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);

        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem1 = menu.findItem(R.id.nav_home);
        menuItem1.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_about_app:
                showAboutDialog();
                return true;

            case R.id.nav_rudfapp:
                Intent nintent = getPackageManager().getLaunchIntentForPackage(getString(R.string.namaz_app_package));
                if (nintent != null) {
                    nintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(nintent);
                } else {
                    String titleText = getString(R.string.namaz_app_download_details);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle(getString(R.string.namaz_app_download_title))
                            .setMessage(color)
                            .setNegativeButton(getString(R.string.later), null)
                            .setIcon(getResources().getDrawable(R.drawable.ic_file_download))
                            .setPositiveButton(getString(R.string.download_now), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (isNetworkStatusAvialable(getApplicationContext())) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.namaz_app_download_url)));
                                        startActivity(browserIntent);
                                        Toast.makeText(getApplicationContext(), R.string.namaz_app_download_toast, Toast.LENGTH_LONG).show();
                                    } else {
                                        String titleText = getString(R.string.namaz_app_download_error);
                                        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                                        SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                                        color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                        builder.setTitle(getString(R.string.connect_net))
                                                .setMessage(color)
                                                /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                                                .setCancelable(true)
                                                .show();
                                    }
                                }
                            })
                            .setCancelable(true)
                            .show();


                }
                return true;

            case R.id.nav_update:
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    UpdateChecker.checkForDialog(HomeActivity.this);
                } else {
                    String titleText = getString(R.string.check_new_update_error);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
                return true;

            case R.id.nav_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.app_name)));
                String shareMessage = (getString(R.string.msg_share));
                shareMessage = shareMessage + getString(R.string.app_download_link);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, (getString(R.string.share))));
                return true;

            case R.id.nav_home:
                Intent h = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(h);
                return true;

            case R.id.nav_feedback:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.feedbackMail)});
                intent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.feedbacksub)));
                intent.putExtra(Intent.EXTRA_TEXT, (getString(R.string.msg_feedback)));
                startActivity(Intent.createChooser(intent, (getString(R.string.feedTitle))));
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

            case R.id.add_member:
                Intent aa = new Intent(getApplicationContext(), WebActivity.class);
                aa.putExtra("url", (add_member_rudf));
                startActivity(aa);
                break;

            case R.id.rudfapp:
                Intent nintent = getPackageManager().getLaunchIntentForPackage(getString(R.string.namaz_app_package));
                if (nintent != null) {
                    nintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(nintent);
                } else {
                    String titleText = getString(R.string.namaz_app_download_details);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle(getString(R.string.namaz_app_download_title))
                            .setMessage(color)
                            .setNegativeButton(getString(R.string.later), null)
                            .setIcon(getResources().getDrawable(R.drawable.ic_file_download))
                            .setPositiveButton(getString(R.string.download_now), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (isNetworkStatusAvialable(getApplicationContext())) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.namaz_app_download_url)));
                                        startActivity(browserIntent);
                                        Toast.makeText(getApplicationContext(), R.string.namaz_app_download_toast, Toast.LENGTH_LONG).show();
                                    } else {
                                        String titleText = getString(R.string.namaz_app_download_error);
                                        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                                        SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                                        color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                        builder.setTitle(getString(R.string.connect_net))
                                                .setMessage(color)
                                                /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                                                .setCancelable(true)
                                                .show();
                                    }
                                }
                            })
                            .setCancelable(true)
                            .show();


                }
                break;

            case R.id.nav_home:
                Intent h = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(h);
                break;

            case R.id.nav_about:
                Intent rrh = new Intent(HomeActivity.this, AboutApp.class);
                startActivity(rrh);
                break;
            case R.id.admin:
                Intent raa = new Intent(getApplicationContext(), WebActivity.class);
                raa.putExtra("url", (master_admin));
                startActivity(raa);
                break;
            case R.id.editor:
                Intent ie = new Intent(getApplicationContext(), WebActivity.class);
                ie.putExtra("url", (editor_admin));
                startActivity(ie);
                break;
            case R.id.nav_update:
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    UpdateChecker.checkForDialog(HomeActivity.this);
                } else {
                    String titleText = getString(R.string.check_new_update_error);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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
                shareMessage = shareMessage + getString(R.string.app_download_link);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, (getString(R.string.share))));
                break;

            case R.id.FacebookGroup:
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
                break;

            case R.id.FacebookPage:
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    if (isAppInstalled(liContext, "com.facebook.orca") || isAppInstalled(liContext, "com.facebook.katana")
                            || isAppInstalled(liContext, "com.example.facebook") || isAppInstalled(liContext, "com.facebook.android")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageApp)));
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageurl)));
                    }

                } else {
                    String titleText = getString(R.string.fb_grp);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle(getString(R.string.connect_net))
                            .setMessage(color)
                            /* .setNegativeButton(getString(R.string.ok_btn), null)*/
                            .setCancelable(true)
                            .show();
                }
                break;
            case R.id.nav_feedback:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.feedbackMail)});
                intent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.feedbacksub)));
                intent.putExtra(Intent.EXTRA_TEXT, (getString(R.string.msg_feedback)));
                startActivity(Intent.createChooser(intent, (getString(R.string.feedTitle))));
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showAboutDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_about_app, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }
}
