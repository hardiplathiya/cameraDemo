package plant.testtree.camerademo.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import android.opengl.GLException
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.PixelCopy
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drew.metadata.exif.makernotes.FujifilmMakernoteDirectory
import com.yalantis.ucrop.UCrop
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
import plant.testtree.camerademo.R
import plant.testtree.camerademo.activity.CameraActivity.Companion.getMimeType
import plant.testtree.camerademo.adapter.FilterAdapterGalalry
import plant.testtree.camerademo.adapter.FilterType
import plant.testtree.camerademo.databinding.ActivityEditImageBinding
import plant.testtree.camerademo.helper.MyApp
import plant.testtree.camerademo.util.BitmapUtils
import plant.testtree.camerademo.util.Const
import plant.testtree.camerademo.util.FileUtils
import plant.testtree.camerademo.util.GLRootView
import plant.testtree.camerademo.util.GLWrapper
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.IntBuffer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.microedition.khronos.opengles.GL10

class EditImageActivity : AppCompatActivity() {
    var glRootView: GLRootView? = null
    lateinit var glWrapper: GLWrapper
    var image: String? = null
    var imageBitmap: Bitmap? = null
    var imageUri: Uri? = null
    var isColorEffect = false
    var isCrop = false
    var isFilter = false
    lateinit var binding: ActivityEditImageBinding
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        requestWindowFeature(1)
        window.setFlags(1024, 1024)
        binding = ActivityEditImageBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        FileUtils.upZipFile(this, "filter/thumbs/thumbs.zip", filesDir.absolutePath)
        binding.ivBack.setOnClickListener { onBackPressed() }
        try {
            image = intent.getStringExtra("FilePath")
            imageUri = Uri.fromFile(File(image.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            binding.ivImage.setImageURI(imageUri)
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.filterList.layoutManager = linearLayoutManager
        binding.tvDone.isEnabled = false
        val arrayList = ArrayList<FilterType>()
        for (i in FilterType.values().indices) {
            arrayList.add(FilterType.values()[i])
            if (i == 0) {
                arrayList.add(FilterType.NONE)
            }
        }
        val filterAdapter = FilterAdapterGalalry(this, arrayList, image)
        binding.filterList.adapter = filterAdapter
        filterAdapter.setOnFilterChangeListener { filterType: FilterType? ->
            glWrapper.switchLastFilterOfCustomizedFilters(
                filterType
            )
        }
        glRootView = findViewById<View>(R.id.camera_view) as GLRootView
        glWrapper = GLWrapper.newInstance().setGlImageView(glRootView).setContext(this).init()
        val loadBitmapFromFile = BitmapUtils.loadBitmapFromFile(image)
        glRootView!!.setAspectRatio(loadBitmapFromFile.width, loadBitmapFromFile.height)
        loadBitmapFromFile.recycle()
        glWrapper.setFilePath(image)
        binding.llCrop.setOnClickListener {
            if (binding.rlColorFilter.visibility == View.VISIBLE) {
                binding.llColorEdit.performClick()
                binding.rlColorFilter.visibility = View.GONE
                isCrop = true
                binding.rlCropLayout.visibility = View.VISIBLE
                binding.ivCrop.setImageResource(R.drawable.crop_press)
                binding.ivFilter.setImageResource(R.drawable.color1)
                binding.ivColorEdit.setImageResource(R.drawable.effect)
                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        UCrop.of((imageUri)!!, Uri.fromFile(File(cacheDir, "destination.jpg")))
                            .start(this@EditImageActivity)
                        Log.d("imagepath", (imageUri!!.path)!!)
                    } catch (e3: Exception) {
                        e3.printStackTrace()
                    }
                }, 500L)
            } else if (binding.rlFilterView.visibility == View.VISIBLE) {
                binding.llFilter.performClick()
                binding.rlFilterView.visibility = View.GONE
                isCrop = true
                binding.rlCropLayout.visibility = View.VISIBLE
                binding.ivCrop.setImageResource(R.drawable.crop_press)
                binding.ivFilter.setImageResource(R.drawable.color1)
                binding.ivColorEdit.setImageResource(R.drawable.effect)
                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        UCrop.of((imageUri)!!, Uri.fromFile(File(cacheDir, "destination.jpg")))
                            .start(this@EditImageActivity)
                        Log.d("imagepath", (imageUri!!.path)!!)
                    } catch (e3: Exception) {
                        e3.printStackTrace()
                    }
                }, 500L)
            } else if (isCrop) {
                isCrop = false
                binding.rlCropLayout.visibility = View.GONE
                binding.ivCrop.setImageResource(R.drawable.crop)
                binding.ivFilter.setImageResource(R.drawable.color1)
                binding.ivColorEdit.setImageResource(R.drawable.effect)
            } else {
                val editImageActivity4 = this@EditImageActivity
                editImageActivity4.isCrop = true
                binding.rlCropLayout.visibility = View.VISIBLE
                binding.ivCrop.setImageResource(R.drawable.crop_press)
                binding.ivFilter.setImageResource(R.drawable.color1)
                binding.ivColorEdit.setImageResource(R.drawable.effect)
                try {
                    UCrop.of((imageUri)!!, Uri.fromFile(File(cacheDir, "destination.jpg")))
                        .start(this@EditImageActivity)
                    Log.d("imagepath", (imageUri!!.path)!!)
                } catch (e3: Exception) {
                    e3.printStackTrace()
                }
            }
        }
        binding.llFilter.setOnClickListener {
            binding.tvDone.isEnabled = true
            binding.tvDone.setTextColor(
                ContextCompat.getColor(
                    this@EditImageActivity,
                    R.color.yellow
                )
            )
            if (binding.rlColorFilter.visibility == View.VISIBLE) {
                binding.llColorEdit.performClick()
                binding.rlColorFilter.visibility = View.GONE
                Handler(Looper.getMainLooper()).postDelayed({
                    var bitmap: Bitmap?
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    } catch (e3: IOException) {
                        e3.printStackTrace()
                        bitmap = null
                    }
                    glRootView!!.setAspectRatio(bitmap!!.width, bitmap!!.height)
                    bitmap!!.recycle()
                    Log.d("imagepath", imageUri!!.path + "")
                    glWrapper.setFilePath(imageUri!!.path)
                    isFilter = true
                    binding.rlFilterView.visibility = View.VISIBLE
                    binding.ivCrop.setImageResource(R.drawable.crop)
                    binding.ivFilter.setImageResource(R.drawable.color_press)
                }, 600L)
                binding.ivColorEdit.setImageResource(R.drawable.effect)
            } else if (isFilter) {
                isFilter = false
                binding.rlFilterView.visibility = View.GONE
                binding.ivCrop.setImageResource(R.drawable.crop)
                binding.ivFilter.setImageResource(R.drawable.color1)
                binding.ivColorEdit.setImageResource(R.drawable.effect)
                takePhoto()
            } else {
                var bitmap: Bitmap? = null
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                } catch (e3: IOException) {
                    e3.printStackTrace()
                }
                glRootView!!.setAspectRatio(bitmap!!.width, bitmap!!.height)
                bitmap!!.recycle()
                Log.d("imagepath", imageUri!!.path + "")
                glWrapper.setFilePath(imageUri!!.path)
                isFilter = true
                binding.rlFilterView.visibility = View.VISIBLE
                binding.ivCrop.setImageResource(R.drawable.crop)
                binding.ivFilter.setImageResource(R.drawable.color_press)
                binding.ivColorEdit.setImageResource(R.drawable.effect)
            }
        }
        binding.llColorEdit.setOnClickListener {
            binding.tvDone.isEnabled = true
            binding.tvDone.setTextColor(
                ContextCompat.getColor(
                    this@EditImageActivity,
                    R.color.yellow
                )
            )
            if (binding.rlFilterView.visibility == View.VISIBLE) {
                binding.rlFilterView.visibility = View.GONE
                binding.llFilter.performClick()
                val editImageActivity = this@EditImageActivity
                editImageActivity.isColorEffect = true
                binding.ivCrop.setImageResource(R.drawable.crop)
                binding.ivFilter.setImageResource(R.drawable.color1)
                binding.ivColorEdit.setImageResource(R.drawable.effect_press)
                binding.rlColorFilter.visibility = View.VISIBLE
                binding.llAnimationFilter.visibility = View.GONE
                binding.llAnimationFilter.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        binding.ivEditImage.setImageBitmap(
                            MediaStore.Images.Media.getBitmap(
                                contentResolver, imageUri
                            )
                        )
                    } catch (e3: IOException) {
                        e3.printStackTrace()
                    }
                }, 500L)
            } else if (isColorEffect) {
                binding.ivCrop.setImageResource(R.drawable.crop)
                binding.ivFilter.setImageResource(R.drawable.color1)
                binding.ivColorEdit.setImageResource(R.drawable.effect)
                binding.tvDone.isEnabled = true
                binding.tvDone.setTextColor(
                    ContextCompat.getColor(
                        this@EditImageActivity,
                        R.color.yellow
                    )
                )
                try {
                    val file = File(filesDir, "Image.jpeg")
                    if (file.exists()) {
                        file.delete()
                    }
                    binding.ivEditImage.isDrawingCacheEnabled = true
                    val createBitmap = Bitmap.createBitmap(
                        binding.ivEditImage.drawingCache
                    )
                    createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ByteArrayOutputStream())
                    val fileOutputStream = FileOutputStream(file)
                    createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    imageBitmap = createBitmap
                    binding.ivImage.setImageBitmap(createBitmap)
                    imageUri = Uri.fromFile(file)
                    Handler(Looper.getMainLooper()).postDelayed(
                        { binding.ivEditImage.isDrawingCacheEnabled = false },
                        300L
                    )
                } catch (e3: Exception) {
                    Log.e("Your Error Message", (e3.message)!!)
                }
                binding.rlColorFilter.visibility = View.GONE
                isColorEffect = false
            } else {
                val editImageActivity2 = this@EditImageActivity
                editImageActivity2.isColorEffect = true
                binding.ivCrop.setImageResource(R.drawable.crop)
                binding.ivFilter.setImageResource(R.drawable.color1)
                binding.ivColorEdit.setImageResource(R.drawable.effect_press)
                binding.rlColorFilter.visibility = View.VISIBLE
                binding.llAnimationFilter.visibility = View.GONE
                binding.llAnimationFilter.visibility = View.VISIBLE
                Handler(Looper.getMainLooper())
                try {
                    binding.ivEditImage.setImageBitmap(
                        MediaStore.Images.Media.getBitmap(
                            contentResolver, imageUri
                        )
                    )
                } catch (e4: IOException) {
                    e4.printStackTrace()
                }
            }
        }
        binding.tvDone.setOnClickListener {
            if (binding.rlFilterView.visibility == View.VISIBLE) {
                binding.llFilter.performClick()
                binding.rlFilterView.visibility = View.GONE
            } else if (binding.rlColorFilter.visibility == View.VISIBLE) {
                binding.llColorEdit.performClick()
                binding.rlColorFilter.visibility = View.GONE
            } else {
                MyApp.getInstance().isFromEdit = true
                binding.rvProgress.visibility = View.VISIBLE
                val date = Date(System.currentTimeMillis())
                val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
                val format = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                simpleDateFormat.format(date)
                val file =
                    File(Const.PATH + "/" + ("IMG0" + format + "." + getMimeType(Bitmap.CompressFormat.JPEG)))
                if (file.exists()) {
                    file.delete()
                }
                try {
                    copyFile(File(imageUri!!.path.toString()), file)
                } catch (e3: IOException) {
                    e3.printStackTrace()
                    binding.ivImage.buildDrawingCache()
                    val bmap = binding.ivImage.drawingCache
                    val saveBitmapAsFile: File =
                        saveBitmapAsFile(bmap, this@EditImageActivity)!!
                    MediaScannerConnection.scanFile(
                        applicationContext,
                        arrayOf<String>(saveBitmapAsFile.absolutePath),
                        null,
                        object : MediaScannerConnectionClient {
                            override fun onMediaScannerConnected() {
                            }
                            override fun onScanCompleted(path: String?, uri: Uri?) {}
                        })
                    Toast.makeText(this@EditImageActivity,"Edit Sucessfully",Toast.LENGTH_SHORT).show()
                    binding.rvProgress.visibility = View.GONE
                }
                Log.d("aaaaaaa", imageUri!!.path + "")
            }
        }
        binding.tvCancel.setOnClickListener { onBackPressed() }
        binding.ivFilterClose.setOnClickListener { binding.llFilter.performClick() }
        binding.ivFilterDone.setOnClickListener {
            binding.llFilter.performClick()
            takePhoto()
        }
        binding.ivColorClose.setOnClickListener { binding.llColorEdit.performClick() }
        binding.ivColorDone.setOnClickListener {
            binding.llColorEdit.performClick()
            binding.tvDone.isEnabled = true
            binding.tvDone.setTextColor(
                ContextCompat.getColor(
                    this@EditImageActivity,
                    R.color.yellow
                )
            )
            try {
                val file = File(filesDir, "Image.jpeg")
                if (file.exists()) {
                    file.delete()
                }
                binding.ivEditImage.isDrawingCacheEnabled = true
                val createBitmap = Bitmap.createBitmap(
                    binding.ivEditImage.drawingCache
                )
                createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ByteArrayOutputStream())
                val fileOutputStream = FileOutputStream(file)
                createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
                imageBitmap = createBitmap
                binding.ivImage.setImageBitmap(createBitmap)
                imageUri = Uri.fromFile(file)
            } catch (e3: Exception) {
                Log.e("Your Error Message", (e3.message)!!)
            }
        }
        binding.seekbarBrightness.progress = binding.ivEditImage.brightness + 280
        binding.seekbarSaturation.progress = 115
        binding.seekbarContrast.progress = (binding.ivEditImage.contrast * 130.0f).toInt()
        binding.seekbarBrightness.setOnProgressChangeListener(object : OnProgressChangeListener {
            override fun onStartTrackingTouch(discreteSeekBar: DiscreteSeekBar) {}
            override fun onStopTrackingTouch(discreteSeekBar: DiscreteSeekBar) {}
            override fun onProgressChanged(discreteSeekBar: DiscreteSeekBar, i2: Int, z: Boolean) {
                binding.ivEditImage.setBrightness(i2 - 255).updateStyle()
            }
        })
        binding.seekbarSaturation.setOnProgressChangeListener(object : OnProgressChangeListener {
            override fun onStartTrackingTouch(discreteSeekBar: DiscreteSeekBar) {}
            override fun onStopTrackingTouch(discreteSeekBar: DiscreteSeekBar) {}
            override fun onProgressChanged(discreteSeekBar: DiscreteSeekBar, i2: Int, z: Boolean) {
                binding.ivEditImage.setMode(0).updateStyle()
                binding.ivEditImage.setSaturation(i2 / 100.0f).updateStyle()
            }
        })
        binding.seekbarContrast.setOnProgressChangeListener(object : OnProgressChangeListener {
            override fun onStartTrackingTouch(discreteSeekBar: DiscreteSeekBar) {}
            override fun onStopTrackingTouch(discreteSeekBar: DiscreteSeekBar) {}
            override fun onProgressChanged(discreteSeekBar: DiscreteSeekBar, i2: Int, z: Boolean) {
                binding.ivEditImage.setContrast(i2 / 100.0f).updateStyle()
            }
        })
        binding.ivBrightness.setOnClickListener {
            binding.llAnimationFilter.visibility = View.GONE
            binding.seekbarBrightness.visibility = View.VISIBLE
            binding.seekbarContrast.visibility = View.GONE
            binding.seekbarSaturation.visibility = View.GONE
            binding.tvFilterName.text = "Brightness"
        }
        binding.ivContrast.setOnClickListener {
            binding.llAnimationFilter.visibility = View.GONE
            binding.seekbarBrightness.visibility = View.GONE
            binding.seekbarContrast.visibility = View.VISIBLE
            binding.seekbarSaturation.visibility = View.GONE
            binding.tvFilterName.text = ExifInterface.TAG_CONTRAST
        }
        binding.ivsaturation.setOnClickListener {
            binding.llAnimationFilter.visibility = View.GONE
            binding.seekbarBrightness.visibility = View.GONE
            binding.seekbarContrast.visibility = View.GONE
            binding.seekbarSaturation.visibility = View.VISIBLE
            binding.tvFilterName.text = ExifInterface.TAG_SATURATION
        }
        binding.ivMenu.setOnClickListener {
            binding.llAnimationFilter.visibility = View.VISIBLE
        }
    }

    fun saveBitmapAsFile(bitmap: Bitmap, activity: Activity): File? {
        var fileOutputStream: FileOutputStream?
        val file2 = File(Const.PATH)
        if (!file2.exists()) {
            file2.mkdirs()
        }
        return try {
            val file3 = File(
                Const.PATH + "/" + "EditImg" + SimpleDateFormat(
                    "yyyyMMdd_HHmmss",
                    Locale.ENGLISH
                ).format(
                    Date()
                ) + ".jpg"
            )
            file3.createNewFile()
            fileOutputStream = FileOutputStream(file3)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            file3
        } catch (e2: java.lang.Exception) {
            fileOutputStream = null
            e2.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    fun copyFile(file: File, file2: File?) {
        if (file.exists()) {
            val channel = FileInputStream(file).channel
            val channel2 = FileOutputStream(file2).channel
            if (channel2 != null && channel != null) {
                channel2.transferFrom(channel, 0L, channel.size())
            }
            channel?.close()
            channel2?.close()
            binding.rvProgress.visibility = View.GONE
            finish()
        }
    }

    fun takePhoto() {
        try {
            val createBitmap = Bitmap.createBitmap(
                glRootView!!.width, glRootView!!.height, Bitmap.Config.ARGB_8888
            )
            val handlerThread = HandlerThread("PixelCopier")
            handlerThread.start()
            binding.tvDone.isEnabled = true
            binding.tvDone.setTextColor(ContextCompat.getColor(this, R.color.yellow))
            PixelCopy.request(glRootView!!, createBitmap!!, { i: Int ->
                if (i == 0) {
                    imageBitmap = createBitmap
                    binding.ivImage.setImageBitmap(createBitmap)
                    val file = File(filesDir, "Image.jpeg")
                    if (file.exists()) {
                        file.delete()
                    }
                    createBitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        100,
                        ByteArrayOutputStream()
                    )
                    try {
                        val fileOutputStream = FileOutputStream(file)
                        createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                        fileOutputStream.flush()
                        fileOutputStream.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e2: IOException) {
                        e2.printStackTrace()
                    }
                    imageUri = Uri.fromFile(file)
                    Log.d("imagepath", file.absolutePath)
                } else {
                    val editImageActivity2 = this@EditImageActivity
                    Toast.makeText(
                        editImageActivity2,
                        "Failed to copyPixels: $i",
                        Toast.LENGTH_LONG
                    ).show()
                }
                handlerThread.quitSafely()
            }, Handler(handlerThread.looper))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createBitmapFromGLSurface(i: Int, i2: Int, i3: Int, i4: Int, gl10: GL10): Bitmap? {
        val i5 = i3 * i4
        val iArr = IntArray(i5)
        val iArr2 = IntArray(i5)
        val wrap = IntBuffer.wrap(iArr)
        wrap.position(0)
        return try {
            gl10.glReadPixels(i, i2, i3, i4, 6408, FujifilmMakernoteDirectory.TAG_FILM_MODE, wrap)
            for (i6 in 0 until i4) {
                val i7 = i6 * i3
                val i8 = (i4 - i6 - 1) * i3
                for (i9 in 0 until i3) {
                    val i10 = iArr[i7 + i9]
                    iArr2[i8 + i9] =
                        i10 shr 16 and 255 or (-16711936 and i10) or (i10 shl 16 and 16711680)
                }
            }
            Bitmap.createBitmap(iArr2, i3, i4, Bitmap.Config.ARGB_8888)
        } catch (e: GLException) {
            Log.e("#DEBUG", "createBitmapFromGLSurface: " + e.message, e)
            null
        }
    }

    public override fun onActivityResult(i: Int, i2: Int, intent: Intent?) {
        super.onActivityResult(i, i2, intent)
        if (i2 == -1 && i == 69) {
            val output = UCrop.getOutput(intent!!)
            imageUri = output
            try {
                binding.ivImage.setImageBitmap(
                    MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        output
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            binding.llCrop.performClick()
            binding.tvDone.isEnabled = true
            binding.tvDone.setTextColor(ContextCompat.getColor(this, R.color.yellow))
            return
        }
        var bitmap: Bitmap? = null
        try {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        } catch (e2: IOException) {
            e2.printStackTrace()
        }
        binding.ivImage.setImageBitmap(bitmap)
        binding.llCrop.performClick()
    }

    override fun onBackPressed() {
        if (binding.rlFilterView.visibility == View.VISIBLE) {
            binding.rlFilterView.visibility = View.GONE
            binding.ivFilter.setImageResource(R.drawable.color1)
            isFilter = false
        } else if (binding.rlColorFilter.visibility == View.VISIBLE) {
            binding.rlColorFilter.visibility = View.GONE
            binding.ivColorEdit.setImageResource(R.drawable.effect)
            isColorEffect = false
        } else {
            super.onBackPressed()
        }
    }

    public override fun onResume() {
        super.onResume()
    }

    companion object {
        @JvmField
        var filterPosition = 0
    }
}