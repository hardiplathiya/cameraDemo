package com.pesonal.adsdk;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.pesonal.adsdk.ADS_SplashActivity.adPlatformSequenceBanner;
import static com.pesonal.adsdk.ADS_SplashActivity.adPlatformSequenceInterstitial;
import static com.pesonal.adsdk.ADS_SplashActivity.adPlatformSequenceNative;
import static com.pesonal.adsdk.ADS_SplashActivity.app_adShowStatus;
import static com.pesonal.adsdk.ADS_SplashActivity.app_alernateAdShowInterstitial;
import static com.pesonal.adsdk.ADS_SplashActivity.app_howShowAdBanner;
import static com.pesonal.adsdk.ADS_SplashActivity.app_howShowAdInterstitial;
import static com.pesonal.adsdk.ADS_SplashActivity.app_howShowAdNative;
import static com.pesonal.adsdk.ADS_SplashActivity.app_mainClickCntSwAd;
import static com.pesonal.adsdk.ADS_SplashActivity.banner_sequence;
import static com.pesonal.adsdk.ADS_SplashActivity.fb_Banner1;
import static com.pesonal.adsdk.ADS_SplashActivity.fb_Interstitial;
import static com.pesonal.adsdk.ADS_SplashActivity.fb_Native1;
import static com.pesonal.adsdk.ADS_SplashActivity.fb_testmode;
import static com.pesonal.adsdk.ADS_SplashActivity.interstitial_sequence;
import static com.pesonal.adsdk.ADS_SplashActivity.isPerchase;
import static com.pesonal.adsdk.ADS_SplashActivity.mysharedpreferences1;
import static com.pesonal.adsdk.ADS_SplashActivity.native_sequence;
import static com.pesonal.adsdk.ADS_SplashActivity.testmode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppManage {
    public static String ADMOB = "Admob";
    public static String ADMOB1 = "Admob1";
    public static String FACEBOOK = "Facebookaudiencenetwork";
    public static String UNITY = "Unity";
    public static String QUREKA = "Qureka";
    public static String qureka_link = "qureka_link";

    public static int count_banner = -1;
    public static int count_native = -1;
    public static int count_click_for_alt = -1;
    public static int count_click = -1;
    public static int Current_count_click = 1;

    public static String app_privacyPolicyLink = "";
    public static String app_privacyPolicyLink_ = "";
    public static String app_accountLink = "";
    public static int app_updateAppDialogStatus = 0;
    public static int app_dialogBeforeAdShow = 0;
    public static int app_redirectOtherAppStatus = 0;
    public static String gameid = "";
    public static String extrapage = "";
    public static int flag = 0;
    public static String flagURL = "";
    public static String ADMOB_APPID = "";

    public static String STARTAPP_APPID = "";
    public static String UNITY_APPID = "";
    public static String QUREKA_APPID = "";

    public static int admob_AdStatus = 0;
    public static int facebook_AdStatus = 0;

    public static int startapp_AdStatus = 0;
    public static int unity_AdStatus = 0;
    public static int qureka_AdStatus = 0;
    public static int myCustom_AdStatus = 0;

    public static int admob_loadAdIdsType = 0;
    public static int facebook_loadAdIdsType = 0;
    public static int qureka_loadAdIdsType = 0;
    public static SharedPreferences mysharedpreferences;

    public static int ad_dialog_time_in_second = 2;
    public static String emailid = "s2soft1firebasetest@gmail.com";
    static Activity activity;
    public Context context;
    static MyCallback myCallback;
    private static AppManage mInstance;

    private InterstitialAd mInterstitialAd;
    private String google_i_pre = "", facebook_i_pre = "", unity_i_pre = "", unityPlacementId = "";
    String admob_b, facebook_nb, facebook_b, unity_b, apploving_b;
    static String admob_n;
    static String facebook_n;
    static String qureka_n;
    com.facebook.ads.InterstitialAd interstitialAd;
    private static Dialog dialog;

    NativeAd preloadNativeAdNeno;
    public boolean isNativeLoadedNeno = false, isBannerLoadedNeno = false;

    public static List<CustomAdModel> myAppMarketingList = new ArrayList<>();
    public static int count_custBannerAd = 0;
    public static int count_custNBAd = 0;
    public static int count_custNativeAd = 0;
    public static int count_custIntAd = 0;
    public static int count_custAppOpenAd = 0;

    public static String new_admob_id;
    public static String new_admobnative_id;
    public static String new_banner_id;

    public AppManage(Activity activity) {
        AppManage.activity = activity;
        mysharedpreferences = activity.getSharedPreferences(activity.getPackageName(), MODE_PRIVATE);
        getResponseFromPref();
    }

    public static AppManage getInstance(Activity activity) {
        AppManage.activity = activity;
        if (mInstance == null) {
            mInstance = new AppManage(activity);
        }
        return mInstance;
    }

    public static boolean hasActiveInternetConnection(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void getResponseFromPref() {
        String response1 = mysharedpreferences.getString("response", "");
        SharedPreferences.Editor editor_count = mysharedpreferences.edit();
        editor_count.putInt("count_admob_B", 0);
        editor_count.putInt("count_admob_I", 0);
        editor_count.putInt("count_admob_N", 0);

        editor_count.putInt("count_admob_B1", 0);
        editor_count.putInt("count_admob_I1", 0);
        editor_count.putInt("count_admob_N1", 0);

        editor_count.putInt("count_apploving_B", 0);

        editor_count.putInt("count_facebook_B", 0);
        editor_count.putInt("count_facebook_NB", 0);
        editor_count.putInt("count_facebook_I", 0);
        editor_count.putInt("count_facebook_N", 0);
        editor_count.commit();

        if (!response1.isEmpty()) {
            try {

                JSONObject response = new JSONObject(response1);
                JSONObject settingsJsonObject = response.getJSONObject("APP_SETTINGS");
                app_accountLink = settingsJsonObject.getString("app_accountLink");
                app_privacyPolicyLink = settingsJsonObject.getString("app_privacyPolicyLink");
                AppManage.emailid = settingsJsonObject.getString("mailid");
                app_updateAppDialogStatus = settingsJsonObject.getInt("app_updateAppDialogStatus");
                app_redirectOtherAppStatus = settingsJsonObject.getInt("app_redirectOtherAppStatus");
                app_dialogBeforeAdShow = settingsJsonObject.getInt("app_dialogBeforeAdShow");
                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putString("app_name", settingsJsonObject.getString("app_name"));
                editor.putString("app_logo", settingsJsonObject.getString("app_logo"));
                editor.putString("app_privacyPolicyLink", app_privacyPolicyLink);
                editor.putInt("app_updateAppDialogStatus", app_updateAppDialogStatus);
                editor.putString("app_versionCode", settingsJsonObject.getString("app_versionCode"));
                editor.putInt("app_redirectOtherAppStatus", app_redirectOtherAppStatus);
                editor.putString("app_newPackageName", settingsJsonObject.getString("app_newPackageName"));
                editor.putInt("app_howShowAdInterstitial", settingsJsonObject.getInt("app_howShowAdInterstitial"));
                editor.putString("app_alernateAdShowInterstitial", settingsJsonObject.getString("app_alernateAdShowInterstitial"));

                editor.putInt("app_howShowAdNative", settingsJsonObject.getInt("app_howShowAdNative"));
                editor.putString("app_adPlatformSequenceNative", settingsJsonObject.getString("app_adPlatformSequenceNative"));
                editor.putString("app_alernateAdShowNative", settingsJsonObject.getString("app_alernateAdShowNative"));
                editor.putString("app_adPlatformSequenceBanner", settingsJsonObject.getString("app_adPlatformSequenceBanner"));
                editor.putString("app_alernateAdShowBanner", settingsJsonObject.getString("app_alernateAdShowBanner"));
                editor.commit();

                JSONObject AdmobJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Admob");
                admob_AdStatus = AdmobJsonObject.getInt("ad_showAdStatus");
                admob_loadAdIdsType = AdmobJsonObject.getInt("ad_loadAdIdsType");
                ADMOB_APPID = AdmobJsonObject.getString("AppID");

                JSONObject AdmobJsonObject1 = response.getJSONObject("PLACEMENT").getJSONObject("Admob1");
                admob_AdStatus = AdmobJsonObject1.getInt("ad_showAdStatus");
                admob_loadAdIdsType = AdmobJsonObject1.getInt("ad_loadAdIdsType");
                ADMOB_APPID = AdmobJsonObject1.getString("AppID");

                JSONObject ExtraJsonObject = response.getJSONObject("EXTRA_DATA");

                gameid = ExtraJsonObject.getString("gameid");
                extrapage = ExtraJsonObject.getString("extrapage");
                flag = ExtraJsonObject.getInt("flag");
                qureka_link = ExtraJsonObject.getString("qureka_link");


                flagURL = ExtraJsonObject.getString("furi");

                JSONObject FBJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Facebookaudiencenetwork");
                facebook_AdStatus = FBJsonObject.getInt("ad_showAdStatus");
                facebook_loadAdIdsType = FBJsonObject.getInt("ad_loadAdIdsType");

                JSONObject QUJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Qureka");
                qureka_AdStatus = QUJsonObject.getInt("ad_showAdStatus");
                qureka_loadAdIdsType = QUJsonObject.getInt("ad_loadAdIdsType");

                JSONObject SAJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("StartApp");
                startapp_AdStatus = SAJsonObject.getInt("ad_showAdStatus");
                STARTAPP_APPID = SAJsonObject.getString("AppID");

                JSONObject UNJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Unity");
                unity_AdStatus = UNJsonObject.getInt("ad_showAdStatus");
                UNITY_APPID = UNJsonObject.getString("AppID");

                JSONObject UNJsonObject1 = response.getJSONObject("PLACEMENT").getJSONObject("Qureka");
                qureka_AdStatus = UNJsonObject1.getInt("ad_showAdStatus");
                QUREKA_APPID = UNJsonObject1.getString("AppID");

                JSONObject MyAdJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("MyCustomAds");
                myCustom_AdStatus = MyAdJsonObject.getInt("ad_showAdStatus");

            } catch (Exception e) {
            }
        }
    }

    public static List<CustomAdModel> get_CustomAdData() {
        List<CustomAdModel> data = new ArrayList<>();
        SharedPreferences preferences = activity.getSharedPreferences("ad_pref", 0);
        try {

            JSONArray array = new JSONArray(preferences.getString("Advertise_List", ""));
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                CustomAdModel customAdModel = new CustomAdModel();
                customAdModel.setAd_id(object.getInt("ad_id"));
                customAdModel.setApp_name(object.getString("app_name"));
                customAdModel.setApp_packageName(object.getString("app_packageName"));
                customAdModel.setApp_logo(object.getString("app_logo"));
                customAdModel.setApp_banner(object.getString("app_banner"));
                customAdModel.setApp_shortDecription(object.getString("app_shortDecription"));
                customAdModel.setApp_rating(object.getString("app_rating"));
                customAdModel.setApp_download(object.getString("app_download"));
                customAdModel.setApp_AdFormat(object.getString("app_AdFormat"));
                customAdModel.setApp_buttonName(object.getString("app_buttonName"));
                data.add(customAdModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void loadInterstitialAd(Activity activity, String admob, String facebook, String unity, String isPerchas) {
        isPerchase = isPerchas;
        turnLoadInterstitialAd(activity, admob, facebook, unity);

    }

    public void turnLoadInterstitialAd(Activity activity, String google_i, String facebook_i, String unity_i) {

        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        facebook_AdStatus = SharedPref.getInteger(activity,"facebook_AdStatus",0);
        unity_AdStatus = SharedPref.getInteger(activity,"unity_AdStatus",0);
        if (app_adShowStatus == 0 || !isPerchase.equals("1")) {
            return;
        }
        if (admob_AdStatus == 1 && !google_i.isEmpty() && !google_i_pre.equals(google_i)) {
            loadAdmobInterstitial(activity, google_i);
        }

        if (facebook_AdStatus == 1 && !facebook_i.isEmpty() && !facebook_i_pre.equals(facebook_i)) {
            loadFacebookInterstitial(activity, facebook_i);
        }
        if (unity_AdStatus == 1 && !unity_i.isEmpty() && !unity_i_pre.equals(unity_i)) {
            loadUnityInterstitial(unity_i);
        }
    }

    private void loadUnityInterstitial(String unityI) {
        if (unityI.equals("") || unityI.contains("no id")) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
        unity_i_pre = unityI;

        UnityAds.load(unity_i_pre, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                unityPlacementId = placementId;
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                unityPlacementId = "";
              //  interstitialCallBack();
            }
        });

        boolean mode;
        if (testmode.equals("true")) {
            mode = true;
        } else {
            mode = false;
        }
        UnityAds.initialize(activity, ADS_SplashActivity.unity_game_id, mode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
            }
        });


    }

    private void loadFacebookInterstitial(final Activity activity, String facebook_i) {
        fb_Interstitial = SharedPref.getString(activity,"fb_Interstitial","");
        fb_testmode = SharedPref.getString(activity,"fb_testmode","");
        boolean mode;
        if (fb_testmode.equals("true")) {
            mode = true;
        } else {
            mode = false;
        }
        AdSettings.setTestMode(mode);

        if (fb_Interstitial.equals("") || fb_Interstitial.contains("no id")) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
        facebook_i_pre = fb_Interstitial;

        interstitialAd = new com.facebook.ads.InterstitialAd(activity, facebook_i_pre);
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                if (dialog != null) {
                    dialog.dismiss();

                }

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                fb_Interstitial = facebook_i_pre;
                loadFacebookInterstitial(activity, facebook_i_pre);
                interstitialCallBack();

            }

            @Override
            public void onError(Ad ad, AdError adError) {

                Log.e("@@@TAG", "fbFailed");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("@@@TAG", "fbloed");
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());


    }

    private void loadAdmobInterstitial(final Activity activity, final String google_i) {
        this.google_i_pre = google_i;

        if (google_i.equals("") || google_i.contains("no id")) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, google_i, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                Log.e("@@@TAG", "ADLODed");
                mInterstitialAd = interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        if (new_admob_id != null) {
                            loadAdmobInterstitial(activity, new_admob_id);
                        }
                        interstitialCallBack();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        mInterstitialAd = null;
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }
        });
    }

    public  void showInterstitialAd(Context context, MyCallback myCallback) {
        turnInterstitialAd(context, myCallback, 0, "");
    }

    public void turnInterstitialAd(Context context, MyCallback myCallback2, int how_many_clicks, String specific_platform) {
        myCallback = myCallback2;
        count_click++;
        if (app_adShowStatus == 0 || !isPerchase.equals("1")) {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
            return;
        }
        if (how_many_clicks != 0) {
            if (count_click % how_many_clicks != 0) {
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }
                return;
            }
        }

        count_click_for_alt++;

        int app_howShowAd = app_howShowAdInterstitial;
        String alernateAdShow = app_alernateAdShowInterstitial;
        if (!specific_platform.isEmpty()) {
            app_howShowAd = 0;
            adPlatformSequenceInterstitial = specific_platform;
        }

        adPlatformSequenceInterstitial = SharedPref.getString(activity,"adPlatformSequenceInterstitial","");
        interstitial_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequenceInterstitial.isEmpty()) {
            String[] adSequence = adPlatformSequenceInterstitial.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                interstitial_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String[] alernateAd = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_click_for_alt % alernateAd.length == j) {
                    index = j;
                    interstitial_sequence.add(alernateAd[index]);
                }
            }

            String[] adSequence = adPlatformSequenceInterstitial.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (interstitial_sequence.size() != 0) {
                    if (!interstitial_sequence.get(0).equals(adSequence[j])) {
                        interstitial_sequence.add(adSequence[j]);
                    }
                }
            }
        } else {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
        }

        if (interstitial_sequence.size() != 0) {
            displayInterstitialAd(interstitial_sequence.get(0), context);
        }
    }

    private void displayInterstitialAd(String platform, final Context context) {
        if (!activity.isFinishing() && dialog != null) {
            dialog.dismiss();
        }
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }

        app_mainClickCntSwAd = SharedPref.getInteger(activity,"app_mainClickCntSwAd",0);
        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        facebook_AdStatus = SharedPref.getInteger(activity,"facebook_AdStatus",0);
        unity_AdStatus = SharedPref.getInteger(activity,"unity_AdStatus",0);
        if (Current_count_click == app_mainClickCntSwAd) {
            if (platform.equals(ADMOB) || platform.equals(ADMOB1)) {
                if (admob_AdStatus == 1){
                    if (mInterstitialAd != null  ) {
                        new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                            }

                            @Override
                            public void onFinish() {
                            }
                        }.start();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Current_count_click = 1;
                                mInterstitialAd.show((Activity) context);

                            }
                        }, 2000);
                    } else {
                        if (!new_admob_id.isEmpty()) {
                            loadAdmobInterstitial((Activity) context, new_admob_id);
                        }
                        nextInterstitialPlatform(context);
                    }
                }else {
                    nextInterstitialPlatform(context);
                }

            } else if (platform.equals(FACEBOOK) && facebook_AdStatus == 1) {
               if(interstitialAd != null) {
                   if (interstitialAd.isAdLoaded()) {
                       new CountDownTimer(ad_dialog_time_in_second * 2000, 100) {
                           @Override
                           public void onTick(long millisUntilFinished) {
                               double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                           }

                           @Override
                           public void onFinish() {

                           }
                       }.start();
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               Current_count_click = 1;
                               interstitialAd.show();

                           }
                       }, 2000);

                   } else {
                       if (!facebook_i_pre.isEmpty()) {
                           loadFacebookInterstitial((Activity) context, facebook_i_pre);
                           Log.e("@@@TAG", "fbi==" + facebook_i_pre);
                       }

                       nextInterstitialPlatform(context);
                   }
               }else {
                   if (!facebook_i_pre.isEmpty()) {
                       loadFacebookInterstitial((Activity) context, facebook_i_pre);
                       Log.e("@@@TAG", "fbi==" + facebook_i_pre);
                   }

                   nextInterstitialPlatform(context);
               }
            } else if (platform.equals(UNITY) && unity_AdStatus == 1) {
                if (!unityPlacementId.equals("")) {
                    new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;

                        }

                        @Override
                        public void onFinish() {


                        }
                    }.start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UnityAds.show(activity, unity_i_pre, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                                @Override
                                public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }
                                    unityPlacementId = "";
                                  //  interstitialCallBack();

                                }

                                @Override
                                public void onUnityAdsShowStart(String placementId) {
                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onUnityAdsShowClick(String placementId) {
                                }

                                @Override
                                public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                                    Current_count_click = 1;
                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }
                                    interstitialCallBack();
                                }
                            });

                        }
                    }, 2000);
                } else {
                    if (!unity_i_pre.isEmpty()) {
                        loadUnityInterstitial(unity_i_pre);
                    }
                    nextInterstitialPlatform(context);
                }

            }else {
                nextInterstitialPlatform(context);
            }
        } else {
            interstitialCallBack();
            Current_count_click++;
            dialog.dismiss();
        }
    }

    private void nextInterstitialPlatform(Context context) {
        if (interstitial_sequence.size() != 0) {
            interstitial_sequence.remove(0);
            if (interstitial_sequence.size() != 0) {
                displayInterstitialAd(interstitial_sequence.get(0), context);
            }else  {
                if(dialog!= null){
                    dialog.dismiss();
                }
                interstitialCallBack();
            }

        } else {
            if (dialog != null) {
                dialog.dismiss();
            }
            interstitialCallBack();

        }
    }

    public static void interstitialCallBack() {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
        if (dialog != null)
            dialog.dismiss();
    }

    public void showBanner(ViewGroup banner_container, String admob_b, String facebook_b, String qureka_b, ShimmerFrameLayout shimmer_Banner_container, int checkads) {
        shimmer_Banner_container.startShimmer();
        shimmer_Banner_container.showShimmer(true);
        turnShowBanner(banner_container, admob_b, facebook_b, qureka_b, shimmer_Banner_container, checkads);
    }

    public static void showOpenAd(String admob_b, String facebook_b, String qureka_b, MyCallback myCallback) {
        turnShowOpenAdDup(admob_b, facebook_b, qureka_b, myCallback);
    }

    public void turnShowBanner(ViewGroup banner_container, String admob_b, String facebook_b, String qureka_b, ShimmerFrameLayout shimmer_Banner_container, int checkads) {
        this.facebook_b =   SharedPref.getString(activity,"fb_banner","");
        this.unity_b = qureka_b;

        if (!hasActiveInternetConnection(activity)) {
            return;
        }

        if (app_adShowStatus == 0 || !isPerchase.equals("1")) {
            banner_container.setVisibility(View.GONE);
            return;
        }

        count_banner++;

        int app_howShowAd = app_howShowAdBanner;
        String alernateAdShow = mysharedpreferences.getString("app_alernateAdShowBanner", "");

        adPlatformSequenceBanner = SharedPref.getString(activity,"adPlatformSequenceBanner","");
        banner_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequenceBanner.isEmpty()) {
            String[] adSequence = adPlatformSequenceBanner.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                banner_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String[] alernateAd = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_banner % alernateAd.length == j) {
                    index = j;
                    banner_sequence.add(alernateAd[index]);
                }
            }

            String[] adSequence = adPlatformSequenceBanner.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (banner_sequence.size() != 0) {
                    if (!banner_sequence.get(0).equals(adSequence[j])) {
                        banner_sequence.add(adSequence[j]);
                    }
                }
            }
        }

        if (banner_sequence.size() != 0) {
            displayBanner(banner_sequence.get(0), banner_container, shimmer_Banner_container, checkads);
        }
    }

    public static void turnShowOpenAdDup(String admob_b, String facebook_b, String qureka_b, MyCallback myCallback2) {

        myCallback = myCallback2;

        admob_b = admob_b;
        facebook_b = facebook_b;
        qureka_b = qureka_b;

        count_banner++;

        if (!hasActiveInternetConnection(activity)) {
            return;
        }

        if (app_adShowStatus == 0 || !isPerchase.equals("1")) {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
            return;
        }

        int app_howShowAd = app_howShowAdBanner;
        String adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequenceBanner", "");
        String alernateAdShow = mysharedpreferences.getString("app_alernateAdShowBanner", "");

        interstitial_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String[] adSequence = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                interstitial_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String[] alernateAd = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_banner % alernateAd.length == j) {
                    index = j;
                    interstitial_sequence.add(alernateAd[index]);
                }
            }

            String[] adSequence = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (interstitial_sequence.size() != 0) {
                    if (!interstitial_sequence.get(0).equals(adSequence[j])) {
                        interstitial_sequence.add(adSequence[j]);
                    }
                }
            }
        } else {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
        }

        if (interstitial_sequence.size() != 0) {
            displayOpenAD(interstitial_sequence.get(0), myCallback);
        }
    }

    public void displayBanner(String platform, ViewGroup banner_container, ShimmerFrameLayout shimmer_Banner_container, int checkads) {
        Log.e("ADSShow", "=onAdExpanded===>" + platform);
        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        facebook_AdStatus = SharedPref.getInteger(activity,"facebook_AdStatus",0);
        unity_AdStatus = SharedPref.getInteger(activity,"unity_AdStatus",0);
        if (platform.equals(ADMOB) && admob_AdStatus == 1) {
            showAdmobBanner(banner_container, shimmer_Banner_container, checkads);
        } else if (platform.equals(ADMOB1) && admob_AdStatus == 1) {
            showAdmobBanner(banner_container, shimmer_Banner_container, checkads);
        } else if (platform.equals(FACEBOOK) && facebook_AdStatus == 1) {
            showFacebookBanner(banner_container, shimmer_Banner_container, checkads);
        } else if (platform.equals(UNITY) && unity_AdStatus == 1) {
            showUnityBanner(banner_container, shimmer_Banner_container, checkads);
        } else {
            nextBannerPlatform(banner_container, shimmer_Banner_container, checkads);
        }
    }

    private void nextBannerPlatform(ViewGroup banner_container, ShimmerFrameLayout shimmer_Banner_container, int checkads) {
        Log.e("@@@TAG", "" + banner_sequence.toString());
        Log.e("@@@TAG", "" + banner_sequence.size());
        if (banner_sequence.size() != 0) {
            banner_sequence.remove(0);
            if (banner_sequence.size() != 0) {
                displayBanner(banner_sequence.get(0), banner_container, shimmer_Banner_container, checkads);
            }else {
                banner_container.setVisibility(View.GONE);
            }
        }else {
            banner_container.setVisibility(View.GONE);
        }
    }

    com.facebook.ads.AdView adView;

    private void showFacebookBanner(final ViewGroup banner_container, ShimmerFrameLayout shimmer_Banner_container, int checkads) {
        fb_Banner1 = facebook_b;
        if (facebook_b.isEmpty() || facebook_AdStatus == 0) {
            nextBannerPlatform(banner_container, shimmer_Banner_container, checkads);
            return;
        }

        if (checkads == 0) {
            adView = new com.facebook.ads.AdView(activity, facebook_b, com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
        } else {
            adView = new com.facebook.ads.AdView(activity, facebook_b, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        }
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
              //  banner_container.removeAllViews();
                nextBannerPlatform(banner_container, shimmer_Banner_container, checkads);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                banner_container.removeAllViews();
                banner_container.addView(adView);
                shimmer_Banner_container.stopShimmer();
                shimmer_Banner_container.hideShimmer();
                banner_container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

      //  banner_container.removeAllViews();
      //  banner_container.addView(adView);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());

    }

    BannerView bottomBanner;

    private void showUnityBanner(final ViewGroup banner_container, ShimmerFrameLayout shimmer_Banner_container, int checkads) {
        if (unity_b.isEmpty() || unity_AdStatus == 0) {
            nextBannerPlatform(banner_container, shimmer_Banner_container, checkads);
            return;
        }

        if (checkads == 0) {
            bottomBanner = new BannerView(activity, unity_b, new UnityBannerSize(320, 250));
        } else {
            bottomBanner = new BannerView(activity, unity_b, new UnityBannerSize(320, 50));
        }
        bottomBanner.setListener(new BannerView.IListener() {
            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
                shimmer_Banner_container.stopShimmer();
                shimmer_Banner_container.hideShimmer();
                banner_container.setVisibility(View.VISIBLE);
                Log.e("@@@TAG", "ubloded");
            }

            @Override
            public void onBannerShown(BannerView bannerAdView) {

            }

            @Override
            public void onBannerClick(BannerView bannerAdView) {

            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                shimmer_Banner_container.stopShimmer();
                shimmer_Banner_container.hideShimmer();
                banner_container.removeAllViews();

            }

            @Override
            public void onBannerLeftApplication(BannerView bannerView) {

            }
        });
        bottomBanner.load();
        banner_container.removeAllViews();
        banner_container.addView(bottomBanner);

    }

    public static boolean initialLayoutComplete = false;

    private void showAdmobBanner(final ViewGroup banner_container, ShimmerFrameLayout shimmer_Banner_container, int checkads) {
        if (new_banner_id.isEmpty() || admob_AdStatus == 0) {
            nextBannerPlatform(banner_container, shimmer_Banner_container, checkads);
            return;
        }

        AdView mAdView = new AdView(activity);

        if (checkads == 0) {
            AdSize adSize = new AdSize(300, 250);
            mAdView.setAdSize(adSize);
            mAdView.setAdUnitId(new_banner_id);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(new_banner_id);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        }

        banner_container.setVisibility(View.VISIBLE);

        AdView finalMAdView = mAdView;
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

              //  banner_container.removeAllViews();
                nextBannerPlatform(banner_container, shimmer_Banner_container, checkads);

            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                shimmer_Banner_container.stopShimmer();
                shimmer_Banner_container.hideShimmer();
                banner_container.removeAllViews();
                banner_container.addView(finalMAdView);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
    }

    private void loadAdaptiveBannerAd(AdView mAdView) {
        AdRequest adRequest =
                new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        mAdView.setAdSize(adSize);
        mAdView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public void displayNativeBanner(String platform, ViewGroup banner_container) {
        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        facebook_AdStatus = SharedPref.getInteger(activity,"facebook_AdStatus",0);
        unity_AdStatus = SharedPref.getInteger(activity,"unity_AdStatus",0);
        if (platform.equals(ADMOB) && admob_AdStatus == 1) {
            showNativeAdmobBanner(banner_container);
        } else if (platform.equals(ADMOB1) && admob_AdStatus == 1) {
            showNativeAdmobBanner(banner_container);
        } else if (platform.equals(FACEBOOK) && facebook_AdStatus == 1) {
          //  showNativeFacebookBanner(banner_container);
        } else {
            nextNativeBannerPlatform(banner_container);
        }
    }

    private void nextNativeBannerPlatform(ViewGroup banner_container) {
        if (banner_sequence.size() != 0) {
            banner_sequence.remove(0);
            if (banner_sequence.size() != 0) {
                displayNativeBanner(banner_sequence.get(0), banner_container);
            }
        }
    }

    private void showNativeAdmobBanner(final ViewGroup banner_container) {

        if (admob_b.isEmpty() || admob_AdStatus == 0) {
            nextNativeBannerPlatform(banner_container);
            return;
        }

        final AdView mAdView = new AdView(activity);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(admob_b);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                banner_container.removeAllViews();

                nextNativeBannerPlatform(banner_container);

            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                banner_container.removeAllViews();
                banner_container.addView(mAdView);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

    }


    public void showNativeSmall(ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, String admobN, String facebookN, View view, ShimmerFrameLayout shimmerViewContainer) {
        shimmerViewContainer.startShimmer();
        shimmerViewContainer.showShimmer(true);
        turnShowNativeSmall(nativeAdContainer, admobN, facebookN, nativeBannerAdContainer, view, shimmerViewContainer);
    }

    public void showNativeSmall_medium(ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, String admobN, String facebookN, View view, ShimmerFrameLayout shimmerViewContainer) {
        shimmerViewContainer.showShimmer(true);
        shimmerViewContainer.startShimmer();
        turnShowNativeSmall_medium(nativeAdContainer, admobN, facebookN, nativeBannerAdContainer, view, shimmerViewContainer);
    }

    public static String str = "activity";

    private static void displayNativeLarge(String platform, ViewGroup nativeAdContainer, View view) {
        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        facebook_AdStatus = SharedPref.getInteger(activity,"facebook_AdStatus",0);
        unity_AdStatus = SharedPref.getInteger(activity,"unity_AdStatus",0);
        if (platform.equals(ADMOB) || platform.equals(ADMOB1) && admob_AdStatus == 1) {
            showAdmobNativeLarge(nativeAdContainer, view);
        } else if (platform.equals(ADMOB1) && admob_AdStatus == 1) {
            showAdmobNativeLarge(nativeAdContainer, view);
        } else if (platform.equals(FACEBOOK) && facebook_AdStatus == 1) {
        } else {
            nextNativePlatformLarge(nativeAdContainer, view);
        }
    }

    private void displayNativeNeno(String platform, ViewGroup nativeAdContainer, View view) {
        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        facebook_AdStatus = SharedPref.getInteger(activity,"facebook_AdStatus",0);
        unity_AdStatus = SharedPref.getInteger(activity,"unity_AdStatus",0);
        if (platform.equals(ADMOB) || platform.equals(ADMOB1) && admob_AdStatus == 1) {
            if (isNativeLoadedNeno) {
                showNativePreloadNeno(nativeAdContainer, view);
            } else {
                showAdmobNativeNeno(nativeAdContainer, view);
            }

        } else if (platform.equals(ADMOB1) && admob_AdStatus == 1) {
            showAdmobNativeNeno(nativeAdContainer, view);

        } else if (platform.equals(FACEBOOK) && facebook_AdStatus == 1) {
        } else {
            nextNativePlatformNeno(nativeAdContainer, view);
        }
    }

    public void turnShowNativeSmall(ViewGroup nativeAdContainer, String admobN, String facebookN, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        Log.e(TAG, "turnShowNativeSmall: " + nativeAdContainer);
        this.admob_n = admobN;
        this.facebook_n = facebookN;
        if (app_adShowStatus == 0 || !isPerchase.equals("1")) {
            nativeAdContainer.setVisibility(View.GONE);
            return;
        }
        count_native++;
        int app_howShowAd = app_howShowAdNative;
        String alernateAdShow = mysharedpreferences.getString("app_alernateAdShowNative", "");

        adPlatformSequenceNative = SharedPref.getString(activity,"adPlatformSequenceNative","");
        native_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequenceNative.isEmpty()) {
            String[] adSequence = adPlatformSequenceNative.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                native_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String[] alernateAd = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_native % alernateAd.length == j) {
                    index = j;
                    native_sequence.add(alernateAd[index]);
                }
            }

            String[] adSequence = adPlatformSequenceNative.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (native_sequence.size() != 0) {
                    if (!native_sequence.get(0).equals(adSequence[j])) {
                        native_sequence.add(adSequence[j]);
                    }
                }
            }
        }

        if (native_sequence.size() != 0) {
            displayNativeSmall(native_sequence.get(0), nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
        }
    }

    public void turnShowNativeSmall_medium(ViewGroup nativeAdContainer, String admobN, String facebookN, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        Log.e(TAG, "turnShowNativeSmall: " + nativeAdContainer);
        this.admob_n = admobN;
        this.facebook_n = facebookN;
        if (app_adShowStatus == 0 || !isPerchase.equals("1")) {
            nativeAdContainer.setVisibility(View.GONE);
            return;
        }
        count_native++;
        int app_howShowAd = app_howShowAdNative;
        String alernateAdShow = mysharedpreferences.getString("app_alernateAdShowNative", "");

        adPlatformSequenceNative = SharedPref.getString(activity,"adPlatformSequenceNative","");
        native_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequenceNative.isEmpty()) {
            String[] adSequence = adPlatformSequenceNative.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                native_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String[] alernateAd = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_native % alernateAd.length == j) {
                    index = j;
                    native_sequence.add(alernateAd[index]);
                }
            }

            String[] adSequence = adPlatformSequenceNative.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (native_sequence.size() != 0) {
                    if (!native_sequence.get(0).equals(adSequence[j])) {
                        native_sequence.add(adSequence[j]);
                    }
                }
            }
        }

        if (native_sequence.size() != 0) {
            displayNativeSmall_medium(native_sequence.get(0), nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
        }
    }

    public static boolean splash_ad;
    static String app_open_id2;
    public static String app_open_id;
    static boolean splash_ad2;

    public static void displayOpenAD(String platform, MyCallback callback) {
        myCallback = callback;

        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        if (platform.equals(ADMOB) || platform.equals(ADMOB1) && admob_AdStatus == 1) {

            splash_ad2 = mysharedpreferences1.getBoolean("app_AppOpenAdStatus", false);
            app_open_id2 = mysharedpreferences.getString("SplashAppOpenID", "");

            if (!splash_ad2) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppManage.getInstance(activity).showInterstitialAd(activity, new AppManage.MyCallback() {
                            public void callbackCall() {
                                myCallback.callbackCall();
                            }
                        });
                    }
                }, 5000);
            } else {
                if (splash_ad && !app_open_id.isEmpty() && isNetworkAvailable()) {
                    loadAppOpenAd(activity, app_open_id, myCallback);
                } else if (splash_ad2 && !app_open_id2.isEmpty() && isNetworkAvailable()) {
                    loadAppOpenAd(activity, app_open_id2, myCallback);
                } else {
                    myCallback.callbackCall();
                }
            }
        } else {
            myCallback.callbackCall();
        }
//        } else if (platform.equals(FACEBOOK) && facebook_AdStatus == 1) {
//            //openapplovinads(activity, myCallback);
//        }
    }

    private static void nextNativePlatforopen(MyCallback myCallback1) {

        if (interstitial_sequence.size() != 0) {
            interstitial_sequence.remove(0);
            if (interstitial_sequence.size() != 0) {
                displayOpenAD(interstitial_sequence.get(0), myCallback1);

            }
        }
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private static void loadAppOpenAd(final Activity activity, String app_open_id, final AppManage.MyCallback myCallback1) {
        AppOpenAd.load(activity, app_open_id, new AdRequest.Builder().build(),
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                myCallback1.callbackCall();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                myCallback1.callbackCall();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        };
                        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                        appOpenAd.show(activity);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        nextNativePlatforopen(myCallback1);
                    }
                });
    }


    private void displayNativeSmall(String platform, ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        facebook_AdStatus = SharedPref.getInteger(activity,"facebook_AdStatus",0);

        if (platform.equals(ADMOB) || platform.equals(ADMOB1) && admob_AdStatus == 1) {
            showAdmobNativeSmall(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
        } else if (platform.equals(ADMOB1) && admob_AdStatus == 1) {
            showAdmobNativeSmall(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
        } else if (platform.equals(FACEBOOK) && facebook_AdStatus == 1) {
            showFacebookNativeSmall(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
        } else {
            nextNativePlatformSmall(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
        }
    }

    private void displayNativeSmall_medium(String platform, ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        admob_AdStatus =  SharedPref.getInteger(activity,"admob_AdStatus",0);
        facebook_AdStatus = SharedPref.getInteger(activity,"facebook_AdStatus",0);
        if (platform.equals(ADMOB) || platform.equals(ADMOB1)) {
            if( admob_AdStatus == 1){
                showAdmobNativeSmall_medium(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
            }else {
                nextNativePlatformSmall_medium(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
            }
        } else if (platform.equals(FACEBOOK) && facebook_AdStatus == 1) {
            showFacebookNativeSmall_medium(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
        } else {
            nextNativePlatformSmall_medium(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
        }
    }

    private void nextNativePlatformNeno(ViewGroup nativeAdContainer, View view) {
        if (native_sequence.size() != 0) {
            native_sequence.remove(0);
            if (native_sequence.size() != 0) {
                displayNativeNeno(native_sequence.get(0), nativeAdContainer, view);
            }else {
                nativeAdContainer.setVisibility(View.GONE);
            }
        }else {
            nativeAdContainer.setVisibility(View.GONE);
        }
    }

    private NativeBannerAd nativeBannerAd;

    private void showFacebookNativeSmall(final ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        fb_Native1 = facebook_n;
        if (fb_Native1.isEmpty() || facebook_AdStatus == 0) {
            nativeAdContainer.setVisibility(View.GONE);
            return;
        }
        createNativeAd(nativeAdContainer, nativeBannerAdContainer, shimmerViewContainer);
    }

    private void showFacebookNativeSmall_medium(final ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        facebook_n  =  SharedPref.getString(activity,"fb_small_native","");
        fb_Native1 = facebook_n;
        if (fb_Native1.isEmpty() || facebook_AdStatus == 0) {
            nativeAdContainer.setVisibility(View.GONE);
            return;
        }
        com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity, facebook_n);

        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                shimmerViewContainer.stopShimmer();
                shimmerViewContainer.hideShimmer();
                if (dialog != null) {
                    dialog.dismiss();
                }
                nativeAdContainer.setVisibility(View.GONE);
                shimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                shimmerViewContainer.stopShimmer();
                shimmerViewContainer.hideShimmer();
                if (dialog != null) {
                    dialog.dismiss();
                }
                new Inflate_ADS(activity).inflate_NATIV_FB_Small(nativeAd, nativeBannerAdContainer);
                nativeAdContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }


    void createNativeAd(final ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, ShimmerFrameLayout shimmerViewContainer) {
        fb_Native1 = facebook_n;


        nativeBannerAd = new NativeBannerAd(activity, fb_Native1);
        NativeAdListener nativeAdListener = new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                nativeAdContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                shimmerViewContainer.stopShimmer();
                shimmerViewContainer.hideShimmer();

                if (dialog != null) {
                    dialog.dismiss();
                }
                if (AppManage.str.equals("Activity")) {
                    new Inflate_ADS(activity).inflat_fbnativebaner(nativeBannerAd, nativeBannerAdContainer);
                    nativeAdContainer.setVisibility(View.VISIBLE);
                } else {
                    new Inflate_ADS(activity).inflat_fbnativebaner(nativeBannerAd, nativeBannerAdContainer);
                    nativeAdContainer.setVisibility(View.VISIBLE);
                }

                nativeAdContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        nativeBannerAd.loadAd(
                nativeBannerAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());


    }

    private void showAdmobNativeNeno(final ViewGroup nativeAdContainer, View view) {
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextNativePlatformNeno(nativeAdContainer, view);
            return;
        }


        final AdLoader adLoader = new AdLoader.Builder(activity, admob_n)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        new Inflate_ADS(activity).inflate_NATIV_ADMOBNeno(nativeAd, nativeAdContainer);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        nextNativePlatformNeno(nativeAdContainer, view);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }

    private void showAdmobNativeSmall(final ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextNativePlatformSmall(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
            return;
        }

        final AdLoader adLoader = new AdLoader.Builder(activity, new_admobnative_id)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        shimmerViewContainer.stopShimmer();
                        shimmerViewContainer.hideShimmer();
                        if (AppManage.str.equals("Activity")) {
                            new Inflate_ADS(activity).inflat_admobnativeneno(nativeAd, nativeAdContainer);
                            nativeAdContainer.setVisibility(View.VISIBLE);
                        } else {
                            new Inflate_ADS(activity).inflat_admobnativeneno(nativeAd, nativeAdContainer);
                            nativeAdContainer.setVisibility(View.VISIBLE);
                        }
                        Log.e("@@@TAG", "Ntiveadloded");
                        nativeBannerAdContainer.setVisibility(View.GONE);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        nextNativePlatformSmall(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
                        Log.e("@@@TAG", "adError" + adError.getMessage());
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }

    private void showAdmobNativeSmall_medium(final ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextNativePlatformSmall_medium(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
            return;
        }

        final AdLoader adLoader = new AdLoader.Builder(activity, new_admobnative_id)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        shimmerViewContainer.stopShimmer();
                        shimmerViewContainer.hideShimmer();
                        if (AppManage.str.equals("Activity")) {
                            new Inflate_ADS(activity).inflate_NATIV_ADMOBSmall(nativeAd, nativeAdContainer);
                            nativeAdContainer.setVisibility(View.VISIBLE);
                        } else {
                            new Inflate_ADS(activity).inflate_NATIV_ADMOBSmall(nativeAd, nativeAdContainer);
                            nativeAdContainer.setVisibility(View.VISIBLE);
                        }
                        nativeBannerAdContainer.setVisibility(View.GONE);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        nextNativePlatformSmall_medium(nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }

    private void nextNativePlatformSmall(ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        if (native_sequence.size() != 0) {
            native_sequence.remove(0);
            if (native_sequence.size() != 0) {
                displayNativeSmall(native_sequence.get(0), nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
            }else {
                nativeAdContainer.setVisibility(View.GONE);
            }
        }
    }

    private void nextNativePlatformSmall_medium(ViewGroup nativeAdContainer, NativeAdLayout nativeBannerAdContainer, View view, ShimmerFrameLayout shimmerViewContainer) {
        if (native_sequence.size() != 0) {
            native_sequence.remove(0);
            if (native_sequence.size() != 0) {
                displayNativeSmall_medium(native_sequence.get(0), nativeAdContainer, nativeBannerAdContainer, view, shimmerViewContainer);
            }else {
                nativeAdContainer.setVisibility(View.GONE);
            }
        }else {
            nativeAdContainer.setVisibility(View.GONE);
        }
    }

    public void showNativePreloadNeno(ViewGroup nativeAdContainer, View view) {
        new Inflate_ADS(activity).inflate_NATIV_ADMOBNeno(preloadNativeAdNeno, nativeAdContainer);
        showAdmobNativeNeno(null, view);
    }

    private static void nextNativePlatformLarge(ViewGroup nativeAdContainer, View view) {
        if (native_sequence.size() != 0) {
            native_sequence.remove(0);
            if (native_sequence.size() != 0) {
                displayNativeLarge(native_sequence.get(0), nativeAdContainer, view);
            }
        }
    }

    private static void showAdmobNativeLarge(final ViewGroup nativeAdContainer, View view) {
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextNativePlatformLarge(nativeAdContainer, view);
            return;
        }

        final AdLoader adLoader = new AdLoader.Builder(activity, admob_n)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        if (AppManage.str.equals("Activity")) {
                            new Inflate_ADS(activity).inflate_NATIV_ADMOBLarge(nativeAd, nativeAdContainer);
                            nativeAdContainer.setVisibility(View.VISIBLE);
                        } else {
                            new Inflate_ADS(activity).inflate_NATIV_ADMOBLarge(nativeAd, nativeAdContainer);
                            nativeAdContainer.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        nextNativePlatformLarge(nativeAdContainer, view);


                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());


    }

    public interface MyCallback {
        void callbackCall();
    }
}

