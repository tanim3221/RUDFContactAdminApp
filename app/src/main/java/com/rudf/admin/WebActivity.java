package com.rudf.admin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blog.library.UpdateChecker;

public class WebActivity extends AppCompatActivity {


    Toolbar toolbar = null;
    private WebView webView = null;
    private SwipeRefreshLayout swipeRefreshLayout;
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
        setContentView(R.layout.activity_webview);

        String url = getIntent().getExtras().getString("url");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        liContext = this.getApplicationContext();
        webView = findViewById(R.id.webView);
        /*  final ProgressBar progress = frameLayout.findViewById(R.id.progress);*/
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });


       /* //progressbar tinting color
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Drawable wrapDrawable = DrawableCompat.wrap(progress.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getApplicationContext(),
                    R.color.colorPrimary));
            progress.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));

        } else {
            progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }
        //progressbar tinting color*/
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024);
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setScrollbarFadingEnabled(true);
        if (isNetworkStatusAvialable(getApplicationContext())) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }

        });

        webView.setWebChromeClient(new WebChromeClient());

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

                if (url.startsWith("tel:")) {
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
                        builder.setTitle(getString(R.string.connect_net))
                                .setMessage(color)
                                /*  .setNegativeButton(getString(R.string.ok_btn), null)*/
                                .setCancelable(true)
                                .show();
                    }
                    return true;
                } else {
                    view.loadUrl(url);
                    return true;
                }

            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                setTitle("Loading...");
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);


            }

            public void onPageFinished(WebView view, String url) {
                setTitle(view.getTitle());
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
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
                    UpdateChecker.checkForDialog(WebActivity.this);
                } else {
                    String titleText = getString(R.string.check_new_update_error);
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(140, 140, 140));
                    SpannableStringBuilder color = new SpannableStringBuilder(titleText);
                    color.setSpan(foregroundColorSpan, 0, titleText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
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
                Intent h = new Intent(WebActivity.this, HomeActivity.class);
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
