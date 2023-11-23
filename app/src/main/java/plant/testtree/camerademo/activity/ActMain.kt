package plant.testtree.camerademo.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import plant.testtree.camerademo.R
import plant.testtree.camerademo.activity.gallary.GalleryappActivity
import plant.testtree.camerademo.activity.selectlist.SelectImageListActivity
import plant.testtree.camerademo.databinding.ActivityMainhomeBinding
import java.io.File
import kotlin.system.exitProcess

class ActMain : AppCompatActivity() {
    lateinit var binding: ActivityMainhomeBinding
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = ActivityMainhomeBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        clickListner()

    }

    private fun clickListner() {
        binding.imgBack.setOnClickListener {
            showExitDialog()
        }
        binding.btncamera.setOnClickListener {
            startActivity(
                Intent(
                    this@ActMain,
                    CameraActivity::class.java
                )
            )
        }
        binding.gallary.setOnClickListener { checkPermission() }
        binding.cardRate.setOnClickListener {
            val intent = Intent("android.intent.action.VIEW")
            intent.data =
                Uri.parse("https://play.google.com/store/apps/details?id=" + this@ActMain.packageName)
            this@ActMain.startActivity(intent)
        }
        binding.cardShare.setOnClickListener {
            val intent = Intent("android.intent.action.SEND")
            intent.type = "text/plain"
            intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name))
            intent.putExtra(
                "android.intent.extra.TEXT", """
     Watch and Download Latest Status sorry and many more using this app.
     
     Try now - https://play.google.com/store/apps/details?id=$packageName
     """.trimIndent()
            )
            startActivity(Intent.createChooser(intent, "Share via"))
        }
    }

    public override fun onActivityResult(i: Int, i2: Int, intent: Intent?) {
        super.onActivityResult(i, i2, intent)
        if (i2 == -1) {
            val file = File(getPath(this, intent!!.data))
            val intent2 = Intent(this, GalleryappActivity::class.java)
            intent2.putExtra("path", file.parentFile!!.name)
            intent2.putExtra("dir", file.parentFile?.toString())
            intent2.putExtra("expired_event_name", file.name)
            startActivity(intent2)
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
                startActivity(Intent(this, SelectImageListActivity::class.java))
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
                        this@ActMain,
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
                builder.setNeutralButton("Cancel", null)
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
                )+ ContextCompat.checkSelfPermission(
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
                startActivity(Intent(this, SelectImageListActivity::class.java))
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
                        this@ActMain,
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
                builder.setNeutralButton("Cancel", null)
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

    fun showExitDialog() {
        val builder = AlertDialog.Builder(this)
        val inflate = LayoutInflater.from(this)
            .inflate(R.layout.exit_dialog, null as ViewGroup?)
        builder.setView(inflate)
        val create = builder.create()
        (inflate.findViewById<View>(R.id.btnExit) as TextView).setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
        (inflate.findViewById<View>(R.id.btnContinue) as TextView).setOnClickListener { view: View? ->
            create.dismiss()
        }
        create.window!!.setBackgroundDrawable(ColorDrawable(0))
        create.show()
    }

    override fun onRequestPermissionsResult(i: Int, strArr: Array<String>, iArr: IntArray) {
        super.onRequestPermissionsResult(i, strArr, iArr)
        if (i == 123) {
            if (iArr.isEmpty() || iArr[0] + iArr[1] + iArr[2] != 0) {
                Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this@ActMain, SelectImageListActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        showExitDialog()
    }

    companion object {
        fun getPath(context: Context, uri: Uri?): String {
            val strArr = arrayOf("_data")
            val query = context.contentResolver.query(uri!!, strArr, null, null, null)
            var r9: String? = null
            if (query != null) {
                r9 =
                    if (query.moveToFirst()) query.getString(query.getColumnIndexOrThrow(strArr[0])) else null
                query.close()
            }
            return r9 ?: "Not found"
        }
    }
}