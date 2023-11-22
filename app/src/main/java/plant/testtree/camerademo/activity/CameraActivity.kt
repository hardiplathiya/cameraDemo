package plant.testtree.camerademo.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.location.Location
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otaliastudios.cameraview.CameraException
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.controls.Flash
import com.otaliastudios.cameraview.controls.Grid
import com.otaliastudios.cameraview.controls.Hdr
import com.otaliastudios.cameraview.controls.Mode
import com.otaliastudios.cameraview.controls.Preview
import com.otaliastudios.cameraview.controls.WhiteBalance
import com.otaliastudios.cameraview.filter.Filters
import com.otaliastudios.cameraview.filters.BrightnessFilter
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import plant.testtree.camerademo.R
import plant.testtree.camerademo.activity.gallary.GalleryappActivity
import plant.testtree.camerademo.adapter.FilterAdapter
import plant.testtree.camerademo.databinding.ActivityCameraBinding
import plant.testtree.camerademo.model.ListModel
import plant.testtree.camerademo.util.Const
import plant.testtree.camerademo.util.GPSTracker
import plant.testtree.camerademo.util.WheelView
import plant.testtree.camerademo.util.WheelView.OnWheelItemSelectedListener
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date
import java.util.concurrent.TimeUnit

class CameraActivity : AppCompatActivity(), OnWheelItemSelectedListener {
    var FilePathStrings: Array<String?> = arrayOf()
    var audioManager: AudioManager? = null
    var count = 0
    var counter = 0
    var date: Date? = null
    var file: File? = null
    var latitude = 0.0
    var listFile: Array<File> = arrayOf()
    var longitude = 0.0
    var mp: MediaPlayer? = null
    var adLoad = 0
    var arrayPhotoVideo = ArrayList<ListModel>()
    var clickCount = 0
    var customHandler = Handler(Looper.getMainLooper())
    var fladhMode = 0
    var flashPos = 0
    var i = 0
    var isLocation = true
    var isOff = false
    var isRecordvdo = false
    var isSound = true
    val mAllFilters = Filters.values()
    var mCurrentFilter = 0
    var sce = 0
    var startTime: Long = 0
    var timeInMilliseconds: Long = 0
    var timeSwapBuff: Long = 0
    var timer = 0
    var updatedTime: Long = 0
    override fun onWheelItemChanged(wheelView: WheelView, i: Int) {}
    lateinit var binding: ActivityCameraBinding
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        requestWindowFeature(1)
        window.setFlags(1024, 1024)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        checkPermission()
        cameraLoad()
        getcurrentlocationinfo()
        setupWeelView()
        clickListner()
        setFilterAdaprter()
        Handler(Looper.getMainLooper()).postDelayed({ getcurrentlocationinfo() }, 10000L)
        date = Date()
    }

    private fun setFilterAdaprter() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.rvFilterList.layoutManager = linearLayoutManager
        val filterAdapter = FilterAdapter(this, mAllFilters)
        binding.rvFilterList.adapter = filterAdapter
        filterAdapter.setOnFilterChangeListener { i: Int ->
            if (binding.cameraViews.preview == Preview.GL_SURFACE) {
                val cameraActivity = this@CameraActivity
                cameraActivity.mCurrentFilter = i
                val filters = cameraActivity.mAllFilters[mCurrentFilter]
                binding.tvToast.text = filters.toString() + ""
                binding.tvToast.visibility = View.VISIBLE
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    binding.tvToast.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@CameraActivity,
                            R.anim.toast
                        )
                    )
                }, 300L)
                handler.postDelayed({ binding.tvToast.visibility = View.GONE }, 1300L)
                binding.cameraViews.filter = filters.newInstance()
            }
        }
    }

    private fun clickListner() {
        binding.futuremain.cameraSwitcher.setOnClickListener { toggleCamera() }
        binding.futuremain.menuFlash.setOnClickListener { toggleFlash() }
        binding.futuremain.menuHdr.setOnClickListener {
            if (binding.cameraViews.hdr == Hdr.OFF) {
                titleShow("HDR ON")
                binding.cameraViews.hdr = Hdr.ON
                binding.futuremain.menuHdr.setImageResource(R.drawable.ic_hdr_on)
            } else if (binding.cameraViews.hdr == Hdr.ON) {
                titleShow("HDR OFF")
                binding.cameraViews.hdr = Hdr.OFF
                binding.futuremain.menuHdr.setImageResource(R.drawable.hdr_off)
            }
        }
        binding.shutterButton.setOnClickListener {
            adLoad++
            if (adLoad >= 2) {
                adLoad = 0
            }
            if (binding.futuremain.settingFeature.visibility == View.VISIBLE) {
                binding.futuremain.menuSetting.performClick()
            }
            if (binding.futuremain.llTimerClick.visibility == View.VISIBLE) {
                binding.futuremain.menuTime.performClick()
            }
            binding.rvFilterList.visibility = View.GONE
            clickCount++
            if (binding.cameraViews.mode == Mode.VIDEO) {
                binding.shutterButton.isEnabled = false
                Handler(Looper.getMainLooper()).postDelayed({ binding.shutterButton.isEnabled = true }, 2000L)
                captureVideo()
                return@setOnClickListener
            }
            binding.shutterButton.isEnabled = false
            capturePhoto()
        }
        binding.imageThumb.setOnClickListener {
            if (binding.futuremain.settingFeature.visibility == View.VISIBLE) {
                binding.futuremain.menuSetting.performClick()
            }
            if (binding.futuremain.llTimerClick.visibility == View.VISIBLE) {
                binding.futuremain.menuTime.performClick()
            }
            binding.rvFilterList.visibility = View.GONE
            file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + File.separator + "iCamera"
            )
            if (!file!!.exists()) {
                file!!.mkdirs()
            }
            if (file!!.isDirectory) {
                arrayPhotoVideo.clear()
                listFile = file!!.listFiles()!!
                Arrays.sort<File>(
                    listFile,
                    java.util.Comparator<File> { file, file2 ->
                        return@Comparator java.lang.Long.valueOf(file.lastModified())
                            .compareTo(java.lang.Long.valueOf(file2.lastModified()))
                    })
                FilePathStrings = arrayOfNulls(listFile.size)
                for (str: String? in FilePathStrings) {
                    arrayPhotoVideo.add(ListModel(str))
                }
            }
            try {
                if (listFile.isEmpty()) {
                    Toast.makeText(this@CameraActivity, "No Media Found", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(Intent(this@CameraActivity, GalleryappActivity::class.java))
                }
            } catch (e4: Exception) {
                e4.printStackTrace()
            }
        }
        binding.futuremain.menuTime.setOnClickListener {
            if (binding.futuremain.settingFeature.visibility == View.VISIBLE) {
                binding.futuremain.menuSetting.performClick()
            }
            if (binding.futuremain.llTimerClick.visibility == View.VISIBLE) {
                binding.futuremain.llTimerClick.visibility = View.GONE
                binding.futuremain.menuTime.setImageResource(R.drawable.timer)
              //  binding.futuremain.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"))
                return@setOnClickListener
            }
            binding.futuremain.llTimerClick.visibility = View.VISIBLE
            binding.futuremain.menuTime.setImageResource(R.drawable.timer_on)
          //  binding.futuremain.llAllFeature.setBackgroundColor(Color.parseColor("#66000000"))
        }
        binding.menuFilter.setOnClickListener {
            TimeUnit.MILLISECONDS.toSeconds(Date().time - date!!.time)
            if (binding.rvFilterList.visibility == View.VISIBLE) {
                binding.rvFilterList.visibility = View.GONE
            } else {
                date = Date()
                binding.rvFilterList.visibility = View.VISIBLE
            }
        }
        binding.futuremain.menuSetting.setOnClickListener {
            if (binding.futuremain.settingFeature.visibility == View.VISIBLE) {
                binding.futuremain.settingFeature.visibility = View.GONE
                binding.futuremain.llWhiteBalance.visibility = View.GONE
                binding.futuremain.menuAw.setImageResource(R.drawable.aw)
                binding.futuremain.llSce.visibility = View.GONE
                binding.futuremain.menuSce.setImageResource(R.drawable.sce_white_off)
                binding.futuremain.llBrightness.visibility = View.GONE
                binding.futuremain.menuBrightness.setImageResource(R.drawable.brigh)
                binding.futuremain.llGrid.visibility = View.GONE
                binding.futuremain.menuImage.setImageResource(R.drawable.grid)
                binding.futuremain.menuSetting.setImageResource(R.drawable.ic_setting)
              //  binding.futuremain.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"))
                return@setOnClickListener
            }
            binding.futuremain.llTimerClick.visibility = View.GONE
            binding.futuremain.menuTime.setImageResource(R.drawable.timer)
            binding.futuremain.settingFeature.visibility = View.VISIBLE
            binding.futuremain.menuSetting.setImageResource(R.drawable.setting)
         //   binding.futuremain.llAllFeature.setBackgroundColor(Color.parseColor("#66000000"))
        }
        binding.futuremain.menuLocation.setOnClickListener {
            if (isLocation) {
                val cameraActivity = this@CameraActivity
                cameraActivity.isLocation = false
                binding.futuremain.menuLocation.setImageResource(R.drawable.location_off)
                return@setOnClickListener
            }
            val cameraActivity2 = this@CameraActivity
            cameraActivity2.isLocation = true
            binding.futuremain.menuLocation.setImageResource(R.drawable.location)
        }
        binding.futuremain.menuAw.setOnClickListener {
            if (binding.futuremain.llWhiteBalance.visibility == View.VISIBLE) {
                binding.futuremain.llWhiteBalance.visibility = View.GONE
                binding.futuremain.menuAw.setImageResource(R.drawable.aw)
                return@setOnClickListener
            }
            binding.futuremain.llWhiteBalance.visibility = View.VISIBLE
            binding.futuremain.menuAw.setImageResource(R.drawable.aw_on)
            binding.futuremain.llSce.visibility = View.GONE
            binding.futuremain.menuSce.setImageResource(R.drawable.sce_white_off)
            binding.futuremain.llGrid.visibility = View.GONE
            binding.futuremain.menuImage.setImageResource(R.drawable.grid)
            binding.futuremain.llBrightness.visibility = View.GONE
            binding.futuremain.menuBrightness.setImageResource(R.drawable.brigh)
        }
        binding.futuremain.menuSce.setOnClickListener {
            if (binding.futuremain.llSce.visibility == View.VISIBLE) {
                binding.futuremain.llSce.visibility = View.GONE
                binding.futuremain.menuSce.setImageResource(R.drawable.sce_white_off)
                return@setOnClickListener
            }
            binding.futuremain.llSce.visibility = View.VISIBLE
            binding.futuremain.menuSce.setImageResource(R.drawable.sce_white_on)
            binding.futuremain.llWhiteBalance.visibility = View.GONE
            binding.futuremain.menuAw.setImageResource(R.drawable.aw)
            binding.futuremain.llGrid.visibility = View.GONE
            binding.futuremain.menuImage.setImageResource(R.drawable.grid)
            binding.futuremain.llBrightness.visibility = View.GONE
            binding.futuremain.menuBrightness.setImageResource(R.drawable.brigh)
        }
        binding.futuremain.menuIncandescent.setOnClickListener {
            if (binding.cameraViews.whiteBalance != WhiteBalance.INCANDESCENT) {
                titleShow("WHITE BALANCE\nINCANDESCENT")
                binding.futuremain.menuIncandescent.setImageResource(R.drawable.light_on)
                binding.futuremain.menuFluorescent.setImageResource(R.drawable.fluore)
                binding.futuremain.menuAuto.setImageResource(R.drawable.auto)
                binding.futuremain.menuDaylight.setImageResource(R.drawable.daylight)
                binding.futuremain.menuCloudy.setImageResource(R.drawable.cloudy)
                binding.cameraViews.whiteBalance = WhiteBalance.INCANDESCENT
            }
        }
        binding.futuremain.menuFluorescent.setOnClickListener {
            if (binding.cameraViews.whiteBalance != WhiteBalance.FLUORESCENT) {
                titleShow("WHITE BALANCE\nFLUORESCENT")
                binding.futuremain.menuIncandescent.setImageResource(R.drawable.light)
                binding.futuremain.menuFluorescent.setImageResource(R.drawable.fluore_on)
                binding.futuremain.menuAuto.setImageResource(R.drawable.auto)
                binding.futuremain.menuDaylight.setImageResource(R.drawable.daylight)
                binding.futuremain.menuCloudy.setImageResource(R.drawable.cloudy)
                binding.cameraViews.whiteBalance = WhiteBalance.FLUORESCENT
            }
        }
        binding.futuremain.menuAuto.setOnClickListener {
            if (binding.cameraViews.whiteBalance != WhiteBalance.AUTO) {
                titleShow("WHITE BALANCE\nAUTO")
                binding.futuremain.menuIncandescent.setImageResource(R.drawable.light)
                binding.futuremain.menuFluorescent.setImageResource(R.drawable.fluore)
                binding.futuremain.menuAuto.setImageResource(R.drawable.auto_on)
                binding.futuremain.menuDaylight.setImageResource(R.drawable.daylight)
                binding.futuremain.menuCloudy.setImageResource(R.drawable.cloudy)
                binding.cameraViews.whiteBalance = WhiteBalance.AUTO
            }
        }
        binding.futuremain.menuDaylight.setOnClickListener {
            if (binding.cameraViews.whiteBalance != WhiteBalance.DAYLIGHT) {
                titleShow("WHITE BALANCE\nDAYLIGHT")
                binding.futuremain.menuIncandescent.setImageResource(R.drawable.light)
                binding.futuremain.menuFluorescent.setImageResource(R.drawable.fluore)
                binding.futuremain.menuAuto.setImageResource(R.drawable.auto)
                binding.futuremain.menuDaylight.setImageResource(R.drawable.daylight_on)
                binding.futuremain.menuCloudy.setImageResource(R.drawable.cloudy)
                binding.cameraViews.whiteBalance = WhiteBalance.DAYLIGHT
            }
        }
        binding.futuremain.menuCloudy.setOnClickListener {
            if (binding.cameraViews.whiteBalance != WhiteBalance.CLOUDY) {
                titleShow("WHITE BALANCE\nCLOUDY")
                binding.futuremain.menuIncandescent.setImageResource(R.drawable.light)
                binding.futuremain.menuFluorescent.setImageResource(R.drawable.fluore)
                binding.futuremain.menuAuto.setImageResource(R.drawable.auto)
                binding.futuremain.menuDaylight.setImageResource(R.drawable.daylight)
                binding.futuremain.menuCloudy.setImageResource(R.drawable.cloudy_on)
                binding.cameraViews.whiteBalance = WhiteBalance.CLOUDY
            }
        }
        binding.futuremain.menuAction.setOnClickListener {
            if (sce != 1) {
                titleShow("SCENE MODE\nACTION")
                val cameraActivity = this@CameraActivity
                cameraActivity.sce = 1
                cameraActivity.binding.cameraViews.setFilter(cameraActivity.mAllFilters[0].newInstance())
                binding.futuremain.menuScenone.setImageResource(R.drawable.sce_off)
                binding.futuremain.menuNight.setImageResource(R.drawable.night)
                binding.futuremain.menuAction.setImageResource(R.drawable.action_on)
            }
        }
        binding.futuremain.menuNight.setOnClickListener {
            if (sce != 2) {
                titleShow("SCENE MODE\nNIGHT")
                val cameraActivity = this@CameraActivity
                cameraActivity.sce = 2
                cameraActivity.binding.cameraViews.setFilter(cameraActivity.mAllFilters[21].newInstance())
                binding.futuremain.menuScenone.setImageResource(R.drawable.sce_off)
                binding.futuremain.menuNight.setImageResource(R.drawable.night_on)
                binding.futuremain.menuAction.setImageResource(R.drawable.action)
            }
        }
        binding.futuremain.menuScenone.setOnClickListener {
            if (sce != 0) {
                titleShow("SCENE MODE\nNONE")
                val cameraActivity = this@CameraActivity
                cameraActivity.sce = 0
                cameraActivity.binding.cameraViews.setFilter(cameraActivity.mAllFilters[0].newInstance())
                binding.futuremain.menuScenone.setImageResource(R.drawable.sce_on)
                binding.futuremain.menuNight.setImageResource(R.drawable.night)
                binding.futuremain.menuAction.setImageResource(R.drawable.action)
            }
        }
        binding.futuremain.menuVolume.setOnClickListener {
            if (isSound) {
                val cameraActivity = this@CameraActivity
                cameraActivity.isSound = false
                binding.futuremain.menuVolume.setImageResource(R.drawable.volume_mute)
                return@setOnClickListener
            }
            binding.futuremain.menuVolume.setImageResource(R.drawable.volume)
            isSound = true
        }
        binding.futuremain.menuTimerclose.setOnClickListener {
            if (timer != 0) {
                val cameraActivity = this@CameraActivity
                cameraActivity.timer = 0
                binding.futuremain.menuTimerclose.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_back
                    )
                )
                binding.futuremain.menuTimer3s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer5s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer9s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
            }
        }
        binding.futuremain.menuTimer3s.setOnClickListener {
            if (timer != 3) {
                val cameraActivity = this@CameraActivity
                cameraActivity.timer = 3
                binding.futuremain.menuTimerclose.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer3s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_back
                    )
                )
                binding.futuremain.menuTimer5s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer9s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
            }
        }
        binding.futuremain.menuTimer5s.setOnClickListener {
            if (timer != 5) {
                val cameraActivity = this@CameraActivity
                cameraActivity.timer = 5
                binding.futuremain.menuTimerclose.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer3s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer5s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_back
                    )
                )
                binding.futuremain.menuTimer9s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
            }
        }
        binding.futuremain.menuTimer9s.setOnClickListener {
            if (timer != 9) {
                val cameraActivity = this@CameraActivity
                cameraActivity.timer = 9
                binding.futuremain.menuTimerclose.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer3s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer5s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuTimer9s.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_back
                    )
                )
            }
        }
        binding.futuremain.menuOff.setOnClickListener {
            if (binding.cameraViews.grid != Grid.OFF) {
                binding.cameraViews.grid = Grid.OFF
                binding.futuremain.menuOff.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_back
                    )
                )
                binding.futuremain.menu3.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menu4.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuPhi.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
            }
        }
        binding.futuremain.menu3.setOnClickListener {
            if (binding.cameraViews.grid != Grid.DRAW_3X3) {
                binding.cameraViews.grid = Grid.DRAW_3X3
                binding.futuremain.menuOff.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menu3.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_back
                    )
                )
                binding.futuremain.menu4.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuPhi.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
            }
        }
        binding.futuremain.menu4.setOnClickListener {
            if (binding.cameraViews.grid != Grid.DRAW_4X4) {
                binding.cameraViews.grid = Grid.DRAW_4X4
                binding.futuremain.menuOff.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menu3.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menu4.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_back
                    )
                )
                binding.futuremain.menuPhi.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
            }
        }
        binding.futuremain.menuPhi.setOnClickListener {
            if (binding.cameraViews.grid != Grid.DRAW_PHI) {
                binding.cameraViews.grid = Grid.DRAW_PHI
                binding.futuremain.menuOff.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menu3.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menu4.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_white
                    )
                )
                binding.futuremain.menuPhi.setTextColor(
                    ContextCompat.getColor(
                        this@CameraActivity,
                        R.color.color_back
                    )
                )
            }
        }

        binding.futuremain.brightnessSeekbar.setOnSeekChangeListener(object :
            OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {}
            override fun onStartTrackingTouch(indicatorSeekBar: IndicatorSeekBar) {}
            override fun onStopTrackingTouch(indicatorSeekBar: IndicatorSeekBar) {
                binding.cameraViews.filter = mAllFilters[3].newInstance()
                if (indicatorSeekBar.progress == -2) {
                    titleShow("EXPOSURE\n-2")
                    BrightnessFilter.setBrightness(0.5f)
                } else if (indicatorSeekBar.progress == -1) {
                    BrightnessFilter.setBrightness(1.0f)
                    titleShow("EXPOSURE\n-1")
                } else if (indicatorSeekBar.progress == 0) {
                    binding.cameraViews.filter = mAllFilters[3].newInstance()
                    titleShow("EXPOSURE\n0")
                } else if (indicatorSeekBar.progress == 1) {
                    BrightnessFilter.setBrightness(1.5f)
                    titleShow("EXPOSURE\n1")
                } else if (indicatorSeekBar.progress == 2) {
                    BrightnessFilter.setBrightness(2f)
                    titleShow("EXPOSURE\n2")
                }
            }
        })
        binding.futuremain.menuBrightness.setOnClickListener {
            if (binding.futuremain.llBrightness.visibility == View.VISIBLE) {
                binding.futuremain.llBrightness.visibility = View.GONE
                binding.futuremain.menuBrightness.setImageResource(R.drawable.brigh)
                return@setOnClickListener
            }
            binding.futuremain.llBrightness.visibility = View.VISIBLE
            binding.futuremain.menuBrightness.setImageResource(R.drawable.brigh_on)
            binding.futuremain.llSce.visibility = View.GONE
            binding.futuremain.menuSce.setImageResource(R.drawable.sce_white_off)
            binding.futuremain.llWhiteBalance.visibility = View.GONE
            binding.futuremain.menuAw.setImageResource(R.drawable.aw)
            binding.futuremain.llGrid.visibility = View.GONE
            binding.futuremain.menuImage.setImageResource(R.drawable.grid)
        }
        binding.futuremain.menuImage.setOnClickListener {
            if (binding.futuremain.llGrid.visibility == View.VISIBLE) {
                binding.futuremain.llGrid.visibility = View.GONE
                binding.futuremain.menuImage.setImageResource(R.drawable.grid)
                return@setOnClickListener
            }
            binding.futuremain.llGrid.visibility = View.VISIBLE
            binding.futuremain.menuImage.setImageResource(R.drawable.grid_on)
            binding.futuremain.llSce.visibility = View.GONE
            binding.futuremain.menuSce.setImageResource(R.drawable.sce_white_off)
            binding.futuremain.llWhiteBalance.visibility = View.GONE
            binding.futuremain.menuAw.setImageResource(R.drawable.aw)
            binding.futuremain.llBrightness.visibility = View.GONE
            binding.futuremain.menuBrightness.setImageResource(R.drawable.brigh)
        }
    }

    private fun setupWeelView() {
        binding.wheelview.items =
            Arrays.asList(*resources.getStringArray(R.array.select_Item))
        binding.wheelview.setOnWheelItemSelectedListener(this)
        binding.wheelview.visibility = View.VISIBLE
        binding.wheelview.selectIndex(1)
        binding.shutterButton.setImageResource(R.drawable.btn_photo_shutter)
    }

    private val updateTimerThread: Runnable = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime
            updatedTime = timeSwapBuff + timeInMilliseconds
            val i = (updatedTime / 1000).toInt()
            val i2 = i / 60
            binding.timerText.text =
                "0" + i2 + ":" + String.format("%02d", Integer.valueOf(i % 60))
            customHandler.postDelayed(this, 0L)
        }
    }

    private fun cameraLoad() {
        try {
            binding.cameraViews.open()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (Environment.getExternalStorageState() != "mounted") {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show()
        } else {
            file =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "iCamera")
            file!!.mkdirs()
        }
        try {
            if (file!!.isDirectory) {
                listFile = file!!.listFiles()!!
                try {
                    Arrays.sort(listFile) { file: File, file2: File ->
                        java.lang.Long.valueOf(file.lastModified())
                            .compareTo(java.lang.Long.valueOf(file2.lastModified()))
                    }
                } catch (e2: Exception) {
                    e2.printStackTrace()
                }
                FilePathStrings = arrayOfNulls(listFile.size)
                if (listFile.isNotEmpty()) {
                    Glide.with((this as FragmentActivity))
                        .load(listFile[listFile.size - 1].absolutePath).into(
                        binding.imageThumb
                    )
                    i += listFile.size
                } else {
                    binding.imageThumb.setImageResource(R.drawable.img_dummy)
                }
                i = 0
                while (i < FilePathStrings.size) {
                    arrayPhotoVideo.add(ListModel(FilePathStrings[i]))
                    i++
                }
            }
        } catch (e3: Exception) {
            e3.printStackTrace()
        }
    }

    private fun initView() {
        mp = MediaPlayer.create(this, R.raw.camera)
        audioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager
        audioManager!!.setStreamVolume(3, audioManager!!.getStreamVolume(3), 0)
    }

    fun capturePhoto() {
        System.currentTimeMillis()
        binding.cameraViews.addCameraListener(Listener())
        val i = timer
        if (i != 0) {
            count = i * 1000 + 1000
            counter = i
            object : CountDownTimer(count.toLong(), 1000L) {
                override fun onTick(j: Long) {
                    binding.timerCounter.startAnimation(
                        AnimationUtils.loadAnimation(
                            applicationContext, R.anim.fadeout
                        )
                    )
                    binding.timerCounter.text = counter.toString()
                    val cameraActivity = this@CameraActivity
                    cameraActivity.counter--
                }

                override fun onFinish() {
                    binding.timerCounter.text = ""
                    if (binding.cameraViews.flash == Flash.ON) {
                        val cameraActivity = this@CameraActivity
                        cameraActivity.flashPos = 2
                        cameraActivity.binding.cameraViews.flash = Flash.TORCH
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (isSound) {
                                mp!!.start()
                            }
                            binding.cameraViews.takePictureSnapshot()
                        }, 500L)
                    } else if (binding.cameraViews.flash == Flash.AUTO) {
                        val cameraActivity2 = this@CameraActivity
                        cameraActivity2.flashPos = 1
                        cameraActivity2.binding.cameraViews.flash = Flash.TORCH
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (isSound) {
                                mp!!.start()
                            }
                            binding.cameraViews.takePictureSnapshot()
                        }, 500L)
                    } else {
                        val cameraActivity3 = this@CameraActivity
                        cameraActivity3.flashPos = 0
                        if (cameraActivity3.isSound) {
                            mp!!.start()
                        }
                        binding.cameraViews.takePictureSnapshot()
                    }
                }
            }.start()
        } else if (binding.cameraViews.flash == Flash.ON) {
            flashPos = 2
            binding.cameraViews.flash = Flash.TORCH
            Handler(Looper.getMainLooper()).postDelayed({
                if (isSound) {
                    mp!!.start()
                }
                binding.cameraViews.takePictureSnapshot()
            }, 500L)
        } else if (binding.cameraViews.flash == Flash.AUTO) {
            flashPos = 1
            binding.cameraViews.flash = Flash.TORCH
            Handler(Looper.getMainLooper()).postDelayed({
                if (isSound) {
                    mp!!.start()
                }
                binding.cameraViews.takePictureSnapshot()
            }, 500L)
        } else {
            flashPos = 0
            if (isSound) {
                mp!!.start()
            }
            binding.cameraViews.takePictureSnapshot()
        }
    }

    fun getImage(bitmap: Bitmap) {
        val date = Date(System.currentTimeMillis())
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
        val format = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        simpleDateFormat.format(date)
        val file =
            File(Const.PATH + "/IMG" + i + "" + format + "." + getMimeType(CompressFormat.JPEG))
        if (file.exists()) {
            file.delete()
        }
        try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        runOnUiThread {
            Glide.with((this@CameraActivity as FragmentActivity)).load(file.absolutePath).into(
                binding.imageThumb
            )
            binding.imageThumb.setImageURI(Uri.fromFile(file))
            binding.imageThumb.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        }
        try {
            if (isLocation) {
                getLocation(file)
            }
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
        arrayPhotoVideo.add(ListModel(file.absolutePath))
        galleryAddPic(file.absolutePath)
        val i = flashPos
        if (i == 2) {
            binding.cameraViews.flash = Flash.ON
        } else if (i == 1) {
            binding.cameraViews.flash = Flash.AUTO
        }
    }

    fun captureVideo() {
        if (isRecordvdo) {
            isRecordvdo = false
            timeSwapBuff += timeInMilliseconds
            customHandler.removeCallbacks(updateTimerThread)
            binding.timerText.text = "00:00"
            binding.shutterButton.setImageResource(R.drawable.btn_new_shutter)
            binding.cameraViews.stopVideo()
            startTime = 0L
            timeInMilliseconds = 0L
            timeSwapBuff = 0L
            updatedTime = 0L
            if (binding.cameraViews.flash == Flash.TORCH) {
                binding.cameraViews.flash = Flash.OFF
                isOff = true
                return
            }
            return
        }
        if (binding.cameraViews.flash == Flash.ON) {
            binding.cameraViews.flash = Flash.TORCH
        }
        isRecordvdo = true
        startTime = SystemClock.uptimeMillis()
        customHandler.postDelayed(updateTimerThread, 0L)
        binding.cameraViews.addCameraListener(Listener())
        binding.cameraViews.takeVideo(File(filesDir, "video.mp4"))
        binding.shutterButton.setImageResource(R.drawable.btn_new_shutter_stop_video)
    }

    operator fun set(cameraView: CameraView?, num: Int?) {
        cameraView!!.layoutParams.height = num!!
        cameraView.layoutParams = cameraView.layoutParams
    }

    override fun onWheelItemSelected(wheelView: WheelView, i: Int) {
        if (i == 0) {
            if (binding.cameraViews.facing == Facing.FRONT) {
                frontVideo()
            } else {
                backVideo()
            }
            binding.cameraViews.mode = Mode.VIDEO
            binding.llTimer.visibility = View.VISIBLE
            binding.rvFilterList.visibility = View.GONE
            binding.cameraViews.setFilter(mAllFilters[0].newInstance())
            val layoutParams = binding.cameraViews.layoutParams
            layoutParams.width = -1
            layoutParams.height = -1
            binding.cameraViews.layoutParams = layoutParams
            set(binding.cameraViews, -1)
            binding.shutterButton.setImageResource(R.drawable.btn_new_shutter)
        } else if (i == 1) {
            if (binding.cameraViews.facing == Facing.FRONT) {
                frontPhoto()
            } else {
                backPhoto()
            }
            startTime = 0L
            timeInMilliseconds = 0L
            timeSwapBuff = 0L
            updatedTime = 0L
            binding.cameraViews.mode = Mode.PICTURE
            binding.llTimer.visibility = View.GONE
            val layoutParams2 = binding.cameraViews.layoutParams
            layoutParams2.width = -1
            layoutParams2.height = -1
            set(binding.cameraViews, -1)
            binding.cameraViews.layoutParams = layoutParams2
            binding.shutterButton.setImageResource(R.drawable.btn_photo_shutter)
        } else if (i == 2) {
            if (binding.cameraViews.facing == Facing.FRONT) {
                frontPhoto()
            } else {
                backPhoto()
            }
            startTime = 0L
            timeInMilliseconds = 0L
            timeSwapBuff = 0L
            updatedTime = 0L
            binding.cameraViews.mode = Mode.PICTURE
            binding.llTimer.visibility = View.GONE
            val layoutParams3 = binding.cameraViews.layoutParams
            val measuredWidth = binding.cameraViews.measuredWidth
            layoutParams3.width = -1
            layoutParams3.height = measuredWidth
            binding.cameraViews.layoutParams = layoutParams3
            set(binding.cameraViews, Integer.valueOf(measuredWidth))
            binding.shutterButton.setImageResource(R.drawable.btn_photo_shutter)
        }
    }

    fun toggleFlash() {
        if (binding.cameraViews.flash == Flash.OFF) {
            titleShow("FLASH MODE\nFLASH ON")
            binding.cameraViews.flash = Flash.ON
            binding.futuremain.menuFlash.setImageResource(R.drawable.ic_flash_light)
        } else if (binding.cameraViews.flash == Flash.ON) {
            titleShow("FLASH MODE\nFLASH AUTO")
            binding.cameraViews.flash = Flash.AUTO
            binding.futuremain.menuFlash.setImageResource(R.drawable.ic_flash_auto)
        } else if (binding.cameraViews.flash == Flash.AUTO) {
            titleShow("FLASH MODE\nFLASH OFF")
            binding.cameraViews.flash = Flash.OFF
            binding.futuremain.menuFlash.setImageResource(R.drawable.ic_flash_off)
        }
    }

    object AnonymousClass44 {
        val BitmapCompressFormat: IntArray
        val cameraviewControlsFacing = IntArray(Facing.values().size)

        init {
            try {
                cameraviewControlsFacing[Facing.BACK.ordinal] = 1
            } catch (_: NoSuchFieldError) {
            }
            try {
                cameraviewControlsFacing[Facing.FRONT.ordinal] = 2
            } catch (_: NoSuchFieldError) {
            }
            BitmapCompressFormat = IntArray(CompressFormat.values().size)
            try {
                BitmapCompressFormat[CompressFormat.JPEG.ordinal] = 1
            } catch (_: NoSuchFieldError) {
            }
            try {
                BitmapCompressFormat[CompressFormat.PNG.ordinal] = 2
            } catch (_: NoSuchFieldError) {
            }
        }
    }

    fun toggleCamera() {
        if (binding.cameraViews.isTakingPicture || binding.cameraViews.isTakingVideo) {
            return
        }
        val i = AnonymousClass44.cameraviewControlsFacing[binding.cameraViews.toggleFacing().ordinal]
        if (i == 1) {
            if (binding.wheelview.selectedPosition == 0) {
                backVideo()
            } else {
                backPhoto()
            }
        } else if (i != 2) {
        } else {
            if (binding.wheelview.selectedPosition == 0) {
                frontVideo()
            } else {
                frontPhoto()
            }
        }
    }

    fun galleryAddPic(str: String?) {
        val intent = Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE")
        intent.data = Uri.fromFile(File(str.toString()))
        sendBroadcast(intent)
    }

    public override fun onResume() {
        super.onResume()
        binding.cameraViews.open()
        date = Date()
        binding.rvFilterList.visibility = View.GONE
        isRecordvdo = false
        try {
            if (file!!.isDirectory) {
                listFile = file!!.listFiles()!!
                Arrays.sort(listFile) { file: File, file2: File ->
                    java.lang.Long.valueOf(file.lastModified())
                        .compareTo(java.lang.Long.valueOf(file2.lastModified()))
                }
                arrayPhotoVideo.clear()
                FilePathStrings = arrayOfNulls(listFile.size)
                if (listFile.isNotEmpty()) {
                    Glide.with((this as FragmentActivity))
                        .load(listFile[listFile.size - 1].absolutePath).into(
                        binding.imageThumb
                    )
                    i += listFile.size
                } else {
                    binding.imageThumb.setImageResource(R.drawable.img_dummy)
                }
                i = 0
                while (i < FilePathStrings.size) {
                    arrayPhotoVideo.add(ListModel(FilePathStrings[i]))
                    i++
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public override fun onDestroy() {
        binding.cameraViews.close()
        super.onDestroy()
    }

    override fun onKeyDown(i: Int, keyEvent: KeyEvent): Boolean {
        if (i != 4) {
            return true
        }
        onBackPressed()
        return true
    }

    override fun dispatchKeyEvent(keyEvent: KeyEvent): Boolean {
        val action = keyEvent.action
        val keyCode = keyEvent.keyCode
        return if (keyCode == 24) {
            if (action == 0) {
                audioManager!!.adjustVolume(1, 4)
                audioManager!!.adjustStreamVolume(3, 1, 1)
            }
            true
        } else if (keyCode != 25) {
            super.dispatchKeyEvent(keyEvent)
        } else {
            if (action == 0) {
                audioManager!!.adjustVolume(-1, 4)
                audioManager!!.adjustStreamVolume(3, -1, 1)
            }
            true
        }
    }

    fun titleShow(str: String?) {
        binding.tvFunctionName.text = str
        binding.tvFunctionName.visibility = View.VISIBLE
        binding.tvFunctionName.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext, R.anim.fadeani
            )
        )
        Handler(Looper.getMainLooper()).postDelayed({ binding.tvFunctionName.visibility = View.GONE }, 1400L)
    }

    fun frontPhoto() {
        if (binding.futuremain.settingFeature.visibility == View.VISIBLE) {
            binding.futuremain.menuSetting.performClick()
        }
        if (binding.futuremain.llTimerClick.visibility == View.VISIBLE) {
            binding.futuremain.menuTime.performClick()
        }
      //  binding.futuremain.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"))
        binding.futuremain.settingFeature.visibility = View.GONE
        binding.futuremain.menuSetting.setImageResource(R.drawable.ic_setting)
        binding.futuremain.llTimerClick.visibility = View.GONE
        binding.futuremain.menuTime.setImageResource(R.drawable.timer)
        binding.futuremain.rlHdr.visibility = View.GONE
        binding.futuremain.rlFlash.visibility = View.GONE
        binding.futuremain.rlSwitcher.visibility = View.VISIBLE
        binding.futuremain.rlTime.visibility = View.VISIBLE
        binding.futuremain.rlSetting.visibility = View.VISIBLE
        binding.menuFilter.visibility = View.VISIBLE
        binding.futuremain.rlLocation.visibility = View.VISIBLE
        binding.futuremain.rlVolume.visibility = View.VISIBLE
        binding.futuremain.rlBright.visibility = View.VISIBLE
        binding.futuremain.rlAw.visibility = View.VISIBLE
        binding.futuremain.rlSce.visibility = View.VISIBLE
        binding.futuremain.rlImage.visibility = View.VISIBLE
        fladhMode = if (binding.cameraViews.flash == Flash.ON) {
            2
        } else if (binding.cameraViews.flash == Flash.AUTO) {
            1
        } else {
            0
        }
    }

    fun backPhoto() {
        if (binding.futuremain.settingFeature.visibility == View.VISIBLE) {
            binding.futuremain.menuSetting.performClick()
        }
        if (binding.futuremain.llTimerClick.visibility == View.VISIBLE) {
            binding.futuremain.menuTime.performClick()
        }
     //   binding.futuremain.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"))
        binding.futuremain.settingFeature.visibility = View.GONE
        binding.futuremain.menuSetting.setImageResource(R.drawable.ic_setting)
        binding.futuremain.llTimerClick.visibility = View.GONE
        binding.futuremain.menuTime.setImageResource(R.drawable.timer)
        binding.futuremain.rlHdr.visibility = View.VISIBLE
        binding.futuremain.rlFlash.visibility = View.VISIBLE
        binding.futuremain.rlSwitcher.visibility = View.VISIBLE
        binding.futuremain.rlTime.visibility = View.VISIBLE
        binding.futuremain.rlSetting.visibility = View.VISIBLE
        binding.menuFilter.visibility = View.VISIBLE
        binding.futuremain.rlLocation.visibility = View.VISIBLE
        binding.futuremain.rlVolume.visibility = View.VISIBLE
        binding.futuremain.rlBright.visibility = View.VISIBLE
        binding.futuremain.rlAw.visibility = View.VISIBLE
        binding.futuremain.rlSce.visibility = View.VISIBLE
        binding.futuremain.rlImage.visibility = View.VISIBLE
        val i = fladhMode
        if (i == 2) {
            binding.cameraViews.flash = Flash.ON
        } else if (i == 1) {
            binding.cameraViews.flash = Flash.AUTO
        } else {
            binding.cameraViews.flash = Flash.OFF
        }
    }

    fun frontVideo() {
        if (binding.futuremain.settingFeature.visibility == View.VISIBLE) {
            binding.futuremain.menuSetting.performClick()
        }
        if (binding.futuremain.llTimerClick.visibility == View.VISIBLE) {
            binding.futuremain.menuTime.performClick()
        }
        binding.futuremain.brightnessSeekbar.setProgress(0.0f)
      //  binding.futuremain.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"))
        binding.futuremain.settingFeature.visibility = View.GONE
        binding.futuremain.menuSetting.setImageResource(R.drawable.ic_setting)
        binding.futuremain.llTimerClick.visibility = View.GONE
        binding.futuremain.menuTime.setImageResource(R.drawable.timer)
        binding.futuremain.rlHdr.visibility = View.GONE
        binding.futuremain.rlFlash.visibility = View.GONE
        binding.futuremain.rlTime.visibility = View.GONE
        binding.futuremain.rlSetting.visibility = View.VISIBLE
        binding.futuremain.rlSwitcher.visibility = View.VISIBLE
        binding.menuFilter.visibility = View.GONE
        binding.futuremain.rlLocation.visibility = View.VISIBLE
        binding.futuremain.rlVolume.visibility = View.VISIBLE
        binding.futuremain.rlBright.visibility = View.GONE
        binding.futuremain.rlAw.visibility = View.VISIBLE
        binding.futuremain.rlSce.visibility = View.GONE
        binding.futuremain.rlImage.visibility = View.VISIBLE
    }

    fun backVideo() {
        if (binding.futuremain.settingFeature.visibility == View.VISIBLE) {
            binding.futuremain.menuSetting.performClick()
        }
        if (binding.futuremain.llTimerClick.visibility == View.VISIBLE) {
            binding.futuremain.menuTime.performClick()
        }
        binding.futuremain.brightnessSeekbar.setProgress(0.0f)
      //  binding.futuremain.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"))
        binding.futuremain.settingFeature.visibility = View.GONE
        binding.futuremain.menuSetting.setImageResource(R.drawable.ic_setting)
        binding.futuremain.llTimerClick.visibility = View.GONE
        binding.futuremain.menuTime.setImageResource(R.drawable.timer)
        binding.futuremain.rlHdr.visibility = View.GONE
        binding.futuremain.rlFlash.visibility = View.VISIBLE
        binding.futuremain.rlTime.visibility = View.GONE
        binding.futuremain.rlSetting.visibility = View.VISIBLE
        binding.futuremain.rlSwitcher.visibility = View.VISIBLE
        binding.menuFilter.visibility = View.GONE
        binding.futuremain.rlLocation.visibility = View.VISIBLE
        binding.futuremain.rlVolume.visibility = View.VISIBLE
        binding.futuremain.rlBright.visibility = View.GONE
        binding.futuremain.rlAw.visibility = View.VISIBLE
        binding.futuremain.rlSce.visibility = View.GONE
        binding.futuremain.rlImage.visibility = View.VISIBLE
    }

    fun getLocation(file: File) {
        val str: String
        val str2: String
        try {
            val exifInterface = ExifInterface(file.absolutePath)
            val d = latitude
            val split = Location.convert(Math.abs(d), Location.FORMAT_SECONDS).split(":".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val split2 =
                split[2].split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            str = if (split2.size == 0) {
                split[2]
            } else {
                split2[0]
            }
            exifInterface.setAttribute(
                ExifInterface.TAG_GPS_LATITUDE,
                split[0] + "/1," + split[1] + "/1," + str + "/1"
            )
            exifInterface.setAttribute(
                ExifInterface.TAG_GPS_LATITUDE_REF,
                if (d > 0.0) "N" else ExifInterface.LATITUDE_SOUTH
            )
            val d2 = longitude
            val split3 =
                Location.convert(Math.abs(d2), Location.FORMAT_SECONDS).split(":".toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            val split4 =
                split3[2].split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            str2 = if (split4.size == 0) {
                split3[2]
            } else {
                split4[0]
            }
            exifInterface.setAttribute(
                ExifInterface.TAG_GPS_LONGITUDE,
                split3[0] + "/1," + split3[1] + "/1," + str2 + "/1"
            )
            exifInterface.setAttribute(
                ExifInterface.TAG_GPS_LONGITUDE_REF,
                if (d2 > 0.0) ExifInterface.LONGITUDE_EAST else ExifInterface.LONGITUDE_WEST
            )
            exifInterface.saveAttributes()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getcurrentlocationinfo() {
        ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION")
        ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION")
        val gPSTracker = GPSTracker(this)
        if (gPSTracker.canGetLocation()) {
            gPSTracker.location
            latitude = gPSTracker.latitude
            longitude = gPSTracker.longitude
        }
    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.CAMERA"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.READ_MEDIA_IMAGES"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.RECORD_AUDIO"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_FINE_LOCATION"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_COARSE_LOCATION"
                ) == 0
            ) {
                binding.cameraViews.open()
                var file = File(Const.PATH)
                if (!file.exists()) {
                    file.mkdir()
                    file.mkdirs()
                }
                if (Environment.getExternalStorageState() != "mounted") {
                    Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show()
                } else {
                    file =
                        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "iCamera")
                    file.mkdirs()
                }
                getcurrentlocationinfo()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.CAMERA"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.RECORD_AUDIO"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.READ_MEDIA_IMAGES"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.READ_MEDIA_AUDIO"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.ACCESS_FINE_LOCATION"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.ACCESS_COARSE_LOCATION"
                )
            ) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Camera, Read External, and Write External Storage permissions are required to do the task.")
                builder.setTitle("Please grant those permissions")
                builder.setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                    ActivityCompat.requestPermissions(
                        this@CameraActivity,
                        arrayOf(
                            "android.permission.CAMERA",
                            "android.permission.RECORD_AUDIO",
                            "android.permission.READ_MEDIA_IMAGES",
                            "android.permission.ACCESS_FINE_LOCATION",
                            "android.permission.ACCESS_COARSE_LOCATION"
                        ),
                        123
                    )
                }
                builder.setNeutralButton("Cancel", null as DialogInterface.OnClickListener?)
                builder.create().show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        "android.permission.CAMERA",
                        "android.permission.RECORD_AUDIO",
                        "android.permission.READ_MEDIA_IMAGES",
                        "android.permission.ACCESS_FINE_LOCATION",
                        "android.permission.ACCESS_COARSE_LOCATION"
                    ),
                    123
                )
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.CAMERA"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.READ_EXTERNAL_STORAGE"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.WRITE_EXTERNAL_STORAGE"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.RECORD_AUDIO"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_FINE_LOCATION"
                ) + ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_COARSE_LOCATION"
                ) == 0
            ) {
                binding.cameraViews.open()
                var file = File(Const.PATH)
                if (!file.exists()) {
                    file.mkdir()
                    file.mkdirs()
                }
                if (Environment.getExternalStorageState() != "mounted") {
                    Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show()
                } else {
                    file =
                        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "iCamera")
                    file.mkdirs()
                }
                getcurrentlocationinfo()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.CAMERA"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.RECORD_AUDIO"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.READ_EXTERNAL_STORAGE"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.WRITE_EXTERNAL_STORAGE"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.ACCESS_FINE_LOCATION"
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.ACCESS_COARSE_LOCATION"
                )
            ) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Camera, Read External, and Write External Storage permissions are required to do the task.")
                builder.setTitle("Please grant those permissions")
                builder.setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                    ActivityCompat.requestPermissions(
                        this@CameraActivity,
                        arrayOf(
                            "android.permission.CAMERA",
                            "android.permission.RECORD_AUDIO",
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE",
                            "android.permission.ACCESS_FINE_LOCATION",
                            "android.permission.ACCESS_COARSE_LOCATION"
                        ),
                        123
                    )
                }
                builder.setNeutralButton("Cancel", null as DialogInterface.OnClickListener?)
                builder.create().show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        "android.permission.CAMERA",
                        "android.permission.RECORD_AUDIO",
                        "android.permission.READ_EXTERNAL_STORAGE",
                        "android.permission.WRITE_EXTERNAL_STORAGE",
                        "android.permission.ACCESS_FINE_LOCATION",
                        "android.permission.ACCESS_COARSE_LOCATION"
                    ),
                    123
                )
            }
        }
    }

    override fun onRequestPermissionsResult(i: Int, strArr: Array<String>, iArr: IntArray) {
        super.onRequestPermissionsResult(i, strArr, iArr)
        if (i == 123) {
            if (iArr.size <= 0 || iArr[0] + iArr[1] + iArr[2] != 0) {
                Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show()
                return
            }
            binding.cameraViews.open()
            var file = File(Const.PATH)
            if (!file.exists()) {
                file.mkdir()
                file.mkdirs()
            }
            if (Environment.getExternalStorageState() != "mounted") {
                Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show()
            } else {
                file =
                    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "iCamera")
                file.mkdirs()
            }
            getcurrentlocationinfo()
        }
    }

    inner class Listener() : CameraListener() {
        override fun onCameraOpened(cameraOptions: CameraOptions) {}
//        override fun onSwiperDetect(i: Int) {
//            super.onSwiperDetect(i)
//            Log.d("aaaa", i.toString() + "")
//        }

        override fun onCameraError(cameraException: CameraException) {
            super.onCameraError(cameraException)
            Toast.makeText(this@CameraActivity, "iCamera failed", Toast.LENGTH_SHORT).show()
        }

        override fun onPictureTaken(pictureResult: PictureResult) {
            super.onPictureTaken(pictureResult)
            if (flashPos == 1) {
                binding.cameraViews.flash = Flash.OFF
            } else if (flashPos == 2) {
                binding.cameraViews.flash = Flash.OFF
            } else {
                binding.cameraViews.flash = Flash.OFF
            }
            binding.shutterButton.isEnabled = true
            binding.cameraViews.removeCameraListener(this)
            if (binding.cameraViews.isTakingVideo) {
                val cameraActivity = this@CameraActivity
                Toast.makeText(
                    cameraActivity,
                    "Captured while taking video. Size=" + pictureResult.size,
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            System.currentTimeMillis()
            getImage(BitmapFactory.decodeByteArray(pictureResult.data, 0, pictureResult.data.size))
        }

        override fun onVideoTaken(videoResult: VideoResult) {
            super.onVideoTaken(videoResult)
            binding.cameraViews.removeCameraListener(this)
            val fromFile = Uri.fromFile(videoResult.file)
            try {
                SimpleDateFormat("yyyyMMdd_HHmmss")
                val format = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file =
                    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "iCamera")
                try {
                    if (!file.exists()) {
                        file.mkdirs()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val file2 = File(file, "VDO$i$format.mp4")
                val openInputStream = contentResolver.openInputStream(fromFile)
                val fileOutputStream = FileOutputStream(file2)
                val bArr = ByteArray(1024)
                while (true) {
                    val read = openInputStream!!.read(bArr)
                    if (read <= 0) {
                        break
                    }
                    fileOutputStream.write(bArr, 0, read)
                }
                fileOutputStream.close()
                openInputStream.close()
                galleryAddPic(file2.absolutePath)
                runOnUiThread {
                    try {
                        Glide.with((this@CameraActivity as FragmentActivity))
                            .load(file2.absolutePath).into(
                            binding.imageThumb
                        )
                        binding.imageThumb.setImageURI(Uri.fromFile(file2))
                    } catch (e2: Exception) {
                        e2.printStackTrace()
                        binding.imageThumb.setImageURI(Uri.fromFile(file2))
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.imageThumb.startAnimation(
                            AnimationUtils.loadAnimation(
                                applicationContext, R.anim.fade_out
                            )
                        )
                    }, 500L)
                }
                arrayPhotoVideo.add(ListModel(file2.absolutePath))
                try {
                    if (isLocation) {
                        getLocation(file2)
                    }
                } catch (e2: Exception) {
                    e2.printStackTrace()
                }
                if (isOff) {
                    isOff = false
                    binding.cameraViews.flash = Flash.ON
                }
            } catch (e3: FileNotFoundException) {
                Log.e("Exception", "" + e3)
            } catch (e4: IOException) {
                Log.e("Exception", "" + e4)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.cameraViews.close()
    }

    companion object {
        @JvmStatic
        fun getMimeType(compressFormat: CompressFormat): String {
            val i = AnonymousClass44.BitmapCompressFormat[compressFormat.ordinal]
            if (i != 1) {
                if (i != 2) {
                }
                return "png"
            }
            return "jpeg"
        }
    }
}