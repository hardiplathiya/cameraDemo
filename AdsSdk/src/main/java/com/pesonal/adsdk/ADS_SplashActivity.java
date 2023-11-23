package com.pesonal.adsdk;

import static com.pesonal.adsdk.AppManage.ADMOB;
import static com.pesonal.adsdk.AppManage.admob_AdStatus;
import static com.pesonal.adsdk.AppManage.emailid;
import static com.pesonal.adsdk.AppManage.facebook_AdStatus;
import static com.pesonal.adsdk.AppManage.mysharedpreferences;
import static com.pesonal.adsdk.AppManage.new_admob_id;
import static com.pesonal.adsdk.AppManage.unity_AdStatus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ADS_SplashActivity extends AppCompatActivity {
    public static boolean need_internet = false;
    String bytemode = "";
    boolean is_retry;
    private Runnable runnable;
    private Handler refreshHandler;
    public Dialog dialog;
    public static Activity activity1;
    public static boolean splash_ad;
    public static String gameid;
    public String splash_app_open_id;
    public String recent_app_open_id;
    FirebaseRemoteConfig mFirebaseRemoteConfig;

    //interastial ads
    public static String interstitial_admob = "";

    //native
    public static String native_admob = "";

    // facebook ads
    public static String fb_Banner1 = "";
    public static String fb_Interstitial = "";
    public static String fb_small_nativebanner = "";
    public static String fb_small_native = "";
    public static String fb_banner = "";

    public static String unity_game_id = "";
    public static String testmode = "";

    public static String fb_testmode = "";
    public static String unity_Interstitial = "";
    public static String unity_banner = "";
    public static String fb_Native1 = "";
    public static String splash_appopen_id = "";
    public static String recent_appopen_id = "";
    public static String adPlatformSequenceInterstitial = "";
    public static String adPlatformSequenceNative = "";
    public static String adPlatformSequenceBanner = "";
    public static String app_alernateAdShowInterstitial = "";
    public static int app_adShowStatus = 1;
    public static String isPerchase = "1";
    ADS_SplashActivity activity;
    public static ArrayList<String> native_sequence;
    public static ArrayList<String> interstitial_sequence;
    public static ArrayList<String> banner_sequence;
    public static ArrayList<String> AdSequnce_facebookList;
    public static ArrayList<String> AdSequnce_qrekaList;
    public static int app_howShowAdInterstitial = 0;
    public static int app_howShowAdNative = 0;
    public static int app_howShowAdBanner = 0;
    public static boolean app_AppOpenAdStatus;
  //  public static int AD_count_click;
    public static int app_mainClickCntSwAd = 0;
    public static SharedPreferences mysharedpreferences1;
    public static int splashads;
    public static String flag = "0";
    public static String publisheridName = "Nell+Gold+Apps";

    public static String banner_admob = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_splash);
        activity = this;
        openadsequence = new ArrayList<String>();
        native_sequence = new ArrayList<>();
        banner_sequence = new ArrayList<>();
        interstitial_sequence = new ArrayList<>();
        AdSequnce_facebookList = new ArrayList<>();
        AdSequnce_qrekaList = new ArrayList<>();
        gameid = AppManage.gameid;
        mysharedpreferences1 = activity.getSharedPreferences(activity.getPackageName(), MODE_PRIVATE);

        AudienceNetworkAds.initialize(this);
        FirebaseApp.initializeApp(this);
        FirebaseAnalytics.getInstance(this);
        testDeviceID();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(1).build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_data);
        remote_method();
    }

    private void testDeviceID() {
        List<String> testDeviceIds = Arrays.asList("3EB81918D77559FA61E0C16C084E389A");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
        MobileAds.initialize(ADS_SplashActivity.this);
    }

    private void loadAdSequence(String str_other_data) {
        try {
            JSONArray jArray = new JSONArray(str_other_data);
            for (int j = 0; j < jArray.length(); j++) {

                JSONObject jObject = jArray.getJSONObject(j);
                adPlatformSequenceInterstitial = jObject.getString("app_adPlatformSequenceInterstitial");
                adPlatformSequenceNative = jObject.getString("app_adPlatformSequenceNative");
                adPlatformSequenceBanner = jObject.getString("app_adPlatformSequenceBanner");
                app_alernateAdShowInterstitial = jObject.getString("app_alernateAdShowInterstitial");

                app_adShowStatus = jObject.getInt("app_adShowStatus");
                app_AppOpenAdStatus = jObject.getInt("app_AppOpenAdStatus") == 1;
                 // AD_count_click = jObject.getInt("app_mainClickCntSwAd");
                admob_AdStatus = jObject.getInt("admob_AdStatus");
                facebook_AdStatus = jObject.getInt("facebook_AdStatus");
                unity_AdStatus = jObject.getInt("unity_AdStatus");
                emailid = jObject.getString("mailid");
                AppManage.app_privacyPolicyLink_ = jObject.getString("Privacy_Policy_link");

                app_mainClickCntSwAd = jObject.getInt("app_mainClickCntSwAd");

                splashads = jObject.getInt("splashads");
                flag = jObject.getString("flag");
                publisheridName = jObject.getString("publisheridName");

                SharedPreferences.Editor editor = mysharedpreferences1.edit();
                editor.putBoolean("app_AppOpenAdStatus", app_AppOpenAdStatus);
              //  editor.putInt("app_mainClickCntSwAd", AD_count_click);
                editor.putString("app_alernateAdShowInterstitial", app_alernateAdShowInterstitial);
                editor.putString("app_adPlatformSequenceBanner", adPlatformSequenceBanner);
                editor.putString("app_adPlatformSequenceInterstitial", adPlatformSequenceInterstitial);
                editor.commit();

                try {
                    SharedPref.setString(ADS_SplashActivity.this,"adPlatformSequenceInterstitial",adPlatformSequenceInterstitial);
                    SharedPref.setString(ADS_SplashActivity.this,"adPlatformSequenceNative",adPlatformSequenceNative);
                    SharedPref.setString(ADS_SplashActivity.this,"adPlatformSequenceBanner",adPlatformSequenceBanner);
                    SharedPref.setString(ADS_SplashActivity.this,"app_alernateAdShowInterstitial",app_alernateAdShowInterstitial);
                    SharedPref.setInteger(ADS_SplashActivity.this,"app_adShowStatus",app_adShowStatus);
                    SharedPref.setInteger(ADS_SplashActivity.this,"app_mainClickCntSwAd",app_mainClickCntSwAd);

                    SharedPref.setInteger(ADS_SplashActivity.this,"admob_AdStatus",admob_AdStatus);
                    SharedPref.setInteger(ADS_SplashActivity.this,"facebook_AdStatus",facebook_AdStatus);
                    SharedPref.setInteger(ADS_SplashActivity.this,"unity_AdStatus",unity_AdStatus);

                    SharedPref.setString(ADS_SplashActivity.this,"emailid",emailid);
                    SharedPref.setString(ADS_SplashActivity.this,"app_privacyPolicyLink",AppManage.app_privacyPolicyLink_);
                    SharedPref.setInteger(ADS_SplashActivity.this,"splashads",splashads);
                    SharedPref.setString(ADS_SplashActivity.this,"flag",flag);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void remote_method() {
        mysharedpreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    String Banner_data = mFirebaseRemoteConfig.getString("camera_banner_admob");
                    String Interstitial_data = mFirebaseRemoteConfig.getString("camera_interstitial_admob");
                    String Native_data = mFirebaseRemoteConfig.getString("camera_native_admob");

                    String Appopen_data = mFirebaseRemoteConfig.getString("camera_appopen_admob");
                    String adsequence_data = mFirebaseRemoteConfig.getString("camera_adsequence_data");
                    String Fb_data = mFirebaseRemoteConfig.getString("camera_facebook_data");
                    String unity_data = mFirebaseRemoteConfig.getString("camera_unity_data");

                    banner_sequence.add(Banner_data);
                    interstitial_sequence.add(Interstitial_data);
                    native_sequence.add(Native_data);

                    AdSequnce_facebookList.add(Fb_data);

                    loadAppOpen(Appopen_data);
                    loadAdSequence(adsequence_data);
                    loadBanner1(Banner_data);
                    loadInterstitial(Interstitial_data);
                    loadNative(Native_data);
                    loadFacebookData(Fb_data);
                    loadUnityData(unity_data);

                } else {
                    Log.e("@@@TAG", "Config fetch failed: " + task.getException());

                }
            }
        });
    }

    private void loadUnityData(String unityData) {
        try {
            JSONArray jArray = new JSONArray(unityData);

            for (int j = 0; j < jArray.length(); j++) {
                JSONObject jObject = jArray.getJSONObject(j);
                unity_Interstitial = jObject.getString("unity_Interstitial");
                unity_game_id = jObject.getString("unity_game_id");
                testmode = jObject.getString("testmode");
                unity_banner = jObject.getString("unity_banner");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadInterstitial(String str_interstitial_data) {
        try {
            JSONArray jArray = new JSONArray(str_interstitial_data);
            for (int j = 0; j < jArray.length(); j++) {
                JSONObject jObject = jArray.getJSONObject(j);
                interstitial_admob = jObject.getString("interstitial_admob");
                SharedPref.setString(activity, "interstitial_admob", interstitial_admob);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadBanner1(String str_banner_data) {
        try {
            JSONArray jArray = new JSONArray(str_banner_data);
            for (int j = 0; j < jArray.length(); j++) {

                JSONObject jObject = jArray.getJSONObject(j);
                banner_admob = jObject.getString("banner_admob");
                try {
                    SharedPref.setString(ADS_SplashActivity.this,"banner_admob",banner_admob);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadFacebookData(String str_applovin_data) {
        try {
            JSONArray jArray = new JSONArray(str_applovin_data);

            for (int j = 0; j < jArray.length(); j++) {
                JSONObject jObject = jArray.getJSONObject(j);
                fb_Interstitial = jObject.getString("fb_Interstitial");
                fb_testmode = jObject.getString("fb_testmode");
                fb_small_nativebanner = jObject.getString("fb_small_nativebanner");
                fb_small_native = jObject.getString("fb_small_native");
                fb_banner = jObject.getString("fb_banner");
            }

            try {
                SharedPref.setString(ADS_SplashActivity.this,"fb_Interstitial",fb_Interstitial);
                SharedPref.setString(ADS_SplashActivity.this,"fb_testmode",fb_testmode);
                SharedPref.setString(ADS_SplashActivity.this,"fb_small_nativebanner",fb_small_nativebanner);
                SharedPref.setString(ADS_SplashActivity.this,"fb_small_native",fb_small_native);
                SharedPref.setString(ADS_SplashActivity.this,"fb_banner",fb_banner);
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadNative(String str_native_data) {
        try {
            JSONArray jArray = new JSONArray(str_native_data);
            for (int j = 0; j < jArray.length(); j++) {
                JSONObject jObject = jArray.getJSONObject(j);
                native_admob = jObject.getString("native_admob");
            }
            try {
                SharedPref.setString(ADS_SplashActivity.this,"native_admob",native_admob);
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadAppOpen(String str_appopen_data) {
        try {
            JSONArray jArray = new JSONArray(str_appopen_data);
            for (int j = 0; j < jArray.length(); j++) {
                JSONObject jObject = jArray.getJSONObject(j);
                splash_appopen_id = jObject.getString("splash_appopen_id");
                recent_appopen_id = jObject.getString("recent_appopen_id");
                SharedPreferences.Editor editor1 = mysharedpreferences.edit();
                editor1.putString("SplashAppOpenID", splash_appopen_id);
                editor1.putString("RecentAppOpenID", recent_appopen_id);
                editor1.commit();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void ADSinit(final Activity activity, final int cversion, final getDataListner myCallback1) {
        this.activity1 = activity;
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.retry_layout, null);
        dialog.setContentView(view);
        final TextView retry_buttton = view.findViewById(R.id.retry_buttton);

        final SharedPreferences preferences = activity.getSharedPreferences("ad_pref", 0);
        final SharedPreferences.Editor editor_AD_PREF = preferences.edit();

        need_internet = preferences.getBoolean("need_internet", need_internet);

        if (!isNetworkAvailable() && need_internet) {
            is_retry = false;
            dialog.show();
        }

        mysharedpreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        final boolean splash_ad = mysharedpreferences1.getBoolean("app_AppOpenAdStatus", false);
        final String splash_app_open_id = mysharedpreferences.getString("SplashAppOpenID", "");
        final String recent_app_open_id = mysharedpreferences.getString("RecentAppOpenID", "");

        this.splash_ad = splash_ad;
        this.splash_app_open_id = splash_app_open_id;
        this.recent_app_open_id = recent_app_open_id;
        dialog.dismiss();
        refreshHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable()) {
                    is_retry = true;
                    retry_buttton.setText(activity.getString(R.string.retry));
                } else if (need_internet) {
                    dialog.show();
                    is_retry = false;
                    retry_buttton.setText(activity.getString(R.string.connect_internet));
                }
                refreshHandler.postDelayed(this, 1000);
            }
        };

        refreshHandler.postDelayed(runnable, 1000);

        retry_buttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_retry) {
                    if (need_internet) {
                        myCallback1.onReload();
                    } else {
                        myCallback1.onSuccess();
                    }
                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            }
        });

        Calendar calender = Calendar.getInstance();
        calender.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        String currentDate = df.format(calender.getTime());


        final int addfdsf123;
        String status = mysharedpreferences.getString("firsttime", "true");
        final SharedPreferences.Editor editor = mysharedpreferences.edit();
        if (status.equals("true")) {
            editor.putString("date", currentDate).apply();
            editor.putString("firsttime", "false").apply();
            addfdsf123 = 13421;

        } else {
            String date = mysharedpreferences.getString("date", "");
            if (!currentDate.equals(date)) {
                editor.putString("date", currentDate).apply();
                addfdsf123 = 26894;
            } else {
                addfdsf123 = 87332;
            }
        }

        String akbsvl679056 = "866CCAD9216F3C94CDAD395F30DBF4E30B7AFEF6CB7B49221EA78EB4A92DC7256905B81FB6BB7A8D10FCFCFCA361E8A6";

        try {
            bytemode = AESSUtils.decryptA(activity, akbsvl679056);
            bytemode = bytemode + "v1/get_app.php";

        } catch (Exception e) {
            e.printStackTrace();
        }
        myCallback1.onSuccess();


    }

    public static ArrayList<String> openadsequence = new ArrayList<>();

    public void SplashAd(final Activity activity, String isPerchase ,final AppManage.MyCallback myCallback1) {
        if(!isPerchase.equals("1")){
            myCallback1.callbackCall();
            return;
        }
        fb_Interstitial = SharedPref.getString(activity,"fb_Interstitial","");
        if (!isNetworkAvailable()) {
            is_retry = false;
            dialog.show();
        } else {
            new_admob_id = interstitial_admob;
            AppManage.getInstance(activity).loadInterstitialAd(activity, new_admob_id, fb_Interstitial, unity_Interstitial,isPerchase);
            AppManage.showOpenAd(ADMOB, "", "", new AppManage.MyCallback() {
                @Override
                public void callbackCall() {

                    myCallback1.callbackCall();
                }
            });
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            refreshHandler.removeCallbacks(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}