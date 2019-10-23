package com.androiddeft.firebasepushnotification;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blog.library.UpdateChecker;
import com.google.android.material.navigation.NavigationView;

import static com.androiddeft.firebasepushnotification.ExecutiveMember.isNetworkStatusAvialable;

public class Notification extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String currentUrl = "http://rudf.6te.net/notice/view-notice.php";
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

    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                return netInfos.isConnected();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 22) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_notification);
        hideitem();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        liContext = this.getApplicationContext();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadWeb(currentUrl);
            }
        });
        LoadWeb("http://rudf.6te.net/notice/view-notice.php");

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
        public void LoadWeb(String url) {
            FrameLayout frameLayout = findViewById(R.id.layout);
        final WebView webView = frameLayout.findViewById(R.id.webView);
        final ProgressBar progress = frameLayout.findViewById(R.id.progress);
        //progressbar tinting color
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Drawable wrapDrawable = DrawableCompat.wrap(progress.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getApplicationContext(),
                    R.color.colorAccent));
            progress.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));

        } else {
            progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
        //progressbar tinting color
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;

            }

        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                webView.loadUrl("about:blank");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            @TargetApi(Build.VERSION_CODES.M)
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView.loadUrl("about:blank");
                super.onReceivedError(view, request, error);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String defaulturl = "http://rudf.6te.net/notice/view-notice.php";
                currentUrl = url;
                if (url != null && url.startsWith(defaulturl)) {
                    return false;
                } else if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), (getString(R.string.call_toast)),
                            Toast.LENGTH_LONG).show();

                    return true;

                } else if (url.startsWith("mailto:")) {
                    if (isNetworkStatusAvialable(getApplicationContext())) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), (getString(R.string.email_toast)),
                                Toast.LENGTH_LONG).show();
                    } else {

                        String titleText = getString(R.string.email_dialog);
                        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                        SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                        color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
                        builder.setTitle(getString(R.string.connect_net))
                                .setMessage(color)
                                /*   .setNegativeButton(getString(R.string.ok_btn), null)*/
                                .setCancelable(true)
                                .show();
                    }
                    return true;
                } else if (url.startsWith("fb:")) {
                    if (isNetworkStatusAvialable(getApplicationContext())) {
                        if (isAppInstalled(liContext, "com.facebook.orca") || isAppInstalled(liContext, "com.facebook.katana")
                                || isAppInstalled(liContext, "com.example.facebook") || isAppInstalled(liContext, "com.facebook.android")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), (getString(R.string.facebook_view)),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), (getString(R.string.not_fb)),
                                    Toast.LENGTH_LONG).show();
                        }

                    } else {
                        String titleText = getString(R.string.fb_pro);
                        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                        SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                        color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
                        builder.setTitle(getString(R.string.connect_net))
                                .setMessage(color)
                                /*  .setNegativeButton(getString(R.string.ok_btn), null)*/
                                .setCancelable(true)
                                .show();
                    }
                    return true;
                } else {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    Toast.makeText(getApplicationContext(), (getString(R.string.other_link_open)),
                            Toast.LENGTH_LONG).show();
                    return true;
                }

            }

            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

            }

        });


    }

    private void hideitem() {
        navigationView = findViewById(R.id.nav_view);
        Menu drawer_menu = navigationView.getMenu();
        drawer_menu.findItem(R.id.Notification).setChecked(true).setEnabled(false);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.notify);
        menuItem.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.notify:
                Intent n = new Intent(Notification.this, Notification.class);
                startActivity(n);
                return true;
            case R.id.nav_about_app:
                Intent h = new Intent(Notification.this, AboutApp.class);
                startActivity(h);
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
            case R.id.Notification:
                Intent n = new Intent(Notification.this, Notification.class);
                startActivity(n);
                break;
            case R.id.nav_home:
                Intent h = new Intent(Notification.this, Home.class);
                startActivity(h);
                break;
            case R.id.alumni:
                String AText = getString(R.string.commingsoon);
                AlertDialog.Builder Abuilder = new AlertDialog.Builder(Notification.this);
                Abuilder.setMessage(AText)
                        /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                        .setCancelable(true)
                        .show();
                break;
            case R.id.adviser:
                String VText = getString(R.string.commingsoon);
                AlertDialog.Builder Vbuilder = new AlertDialog.Builder(Notification.this);
                Vbuilder.setMessage(VText)
                        /*.setNegativeButton(getString(R.string.ok_btn), null)*/
                        .setCancelable(true)
                        .show();
                break;
            case R.id.ExecutiveMember:
                Intent i = new Intent(Notification.this, ExecutiveMember.class);
                startActivity(i);
                break;
            case R.id.GeneralMember:
                Intent s = new Intent(Notification.this, GeneralMember.class);
                startActivity(s);
                break;

            case R.id.nav_update:
                if (isNetworkStatusAvialable(getApplicationContext())) {
                    UpdateChecker.checkForDialog(Notification.this);
                } else {
                    String titleText = getString(R.string.check_new_update_error);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
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
            case R.id.nav_about:
                Intent a = new Intent(Notification.this, About.class);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
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
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.feedbackMail)});
                intent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.feedbacksub)));
                intent.putExtra(Intent.EXTRA_TEXT,(getString(R.string.msg_feedback)));
                startActivity(Intent.createChooser(intent, (getString(R.string.feedTitle))));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
