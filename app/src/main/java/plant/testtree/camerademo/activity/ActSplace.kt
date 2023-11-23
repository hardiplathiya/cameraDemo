package plant.testtree.camerademo.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toolbar
import androidx.appcompat.widget.AppCompatButton
import com.pesonal.adsdk.ADS_SplashActivity
import com.pesonal.adsdk.AppManage
import com.pesonal.adsdk.getDataListner
import org.json.JSONObject
import plant.testtree.camerademo.R
import plant.testtree.camerademo.activity.ActMain
import plant.testtree.camerademo.databinding.ActivitySplaceBinding
import plant.testtree.camerademo.util.Const.isLoadAds
import plant.testtree.camerademo.util.PrefUtill

class ActSplace : ADS_SplashActivity() {
    private lateinit var binding: ActivitySplaceBinding
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = ActivitySplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PrefUtill.getInstance(this)
        LoadAds()
    }

    fun nextActivityLaunch() {
        startActivity(
            Intent(
                this@ActSplace,
                ActMain::class.java
            )
        )
    }

    fun getCurrentVersionCode(activity: Activity): Int {
        val manager = activity.packageManager
        var info: PackageInfo? = null
        try {
            info = manager.getPackageInfo(
                activity.packageName, 0
            )
            return info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    private fun LoadAds() {
        if (isNetworkAvailable) {
            Handler().postDelayed({
                ADSinit(this@ActSplace, getCurrentVersionCode(this@ActSplace), object :
                    getDataListner {
                    override fun onSuccess() {
                        Handler().postDelayed({
                            if(isLoadAds == "1") {
                                if (PrefUtill.getInt("splashads",0) == 0) {
                                    if (PrefUtill.getString("flag","0") == "0") {
                                        SplashAd(this@ActSplace,isLoadAds) {
                                            nextActivityLaunch()
                                        }
                                    } else {
                                        dialog(AppManage.flagURL,this@ActSplace)
                                    }
                                } else {
                                        AppManage.new_admob_id =  interstitial_admob
                                        AppManage.getInstance(this@ActSplace).loadInterstitialAd(
                                            this@ActSplace,
                                            AppManage.new_admob_id,
                                            ADS_SplashActivity.fb_Interstitial,
                                            ADS_SplashActivity.unity_Interstitial,
                                            isLoadAds
                                        )
                                        val handler = Handler()
                                        handler.postDelayed(Runnable {
                                            AppManage.Current_count_click =
                                                ADS_SplashActivity.app_mainClickCntSwAd
                                            AppManage.getInstance(this@ActSplace)
                                                .showInterstitialAd(this@ActSplace) {
                                                    nextActivityLaunch()
                                                }
                                        }, 2500)
                                }
                            }else{
                                val handler = Handler()
                                handler.postDelayed(Runnable {
                                    nextActivityLaunch()
                                }, 2500)
                            }
                        }, 3000)

                    }

                    override fun onUpdate(url: String) {
                        dialog(url,this@ActSplace)
                    }

                    override fun onRedirect(url: String) {
                        dialog(url,this@ActSplace)
                    }

                    override fun onReload() {
                        startActivity(
                            Intent(
                                this@ActSplace,
                           this@ActSplace::class.java
                            )
                        )
                        finish()
                    }

                    override fun onGetExtradata(extraData: JSONObject) {}
                })

            }, 1000)

        } else {
            Handler().postDelayed({ nextActivityLaunch() }, 5000)
        }
    }

    fun dialog(url: String?,activity: Activity) {
        try {
            val redirect = Dialog(activity)
            redirect.requestWindowFeature(Window.FEATURE_NO_TITLE)
            redirect.setContentView(R.layout.mail_update_app_dialog)
            redirect.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val window = redirect.window
            val displayMetrics = DisplayMetrics()
            activity.window.windowManager.defaultDisplay.getMetrics(displayMetrics)
            if (window != null) {
                window.setGravity(Gravity.CENTER)
                window.setLayout(
                    (0.9 * Resources.getSystem().displayMetrics.widthPixels).toInt(),
                    Toolbar.LayoutParams.WRAP_CONTENT
                )
            }
            redirect.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            redirect.show()
            val btn = redirect.findViewById<AppCompatButton>(R.id.btn_ok)
            btn.setOnClickListener { v: View? ->
                val viewIntent =
                    Intent("android.intent.action.VIEW", Uri.parse(url))
                activity.startActivity(viewIntent)
                activity.finishAffinity()
                redirect.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
