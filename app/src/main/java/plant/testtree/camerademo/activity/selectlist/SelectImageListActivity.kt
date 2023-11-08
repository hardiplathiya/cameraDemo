package plant.testtree.camerademo.activity.selectlist

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.ButterKnife
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import plant.testtree.camerademo.databinding.ActivitySelectImageListBinding
import plant.testtree.camerademo.model.Image
import plant.testtree.camerademo.util.Const.imgList
import java.io.File
import java.util.TreeSet

class SelectImageListActivity : AppCompatActivity() {
    var adapter: SelectImageAdapter? = null
  //  var imgList = ArrayList<Image>()
    lateinit var binding: ActivitySelectImageListBinding
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = ActivitySelectImageListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ButterKnife.bind(this)
        binding.ivBack.setOnClickListener { onBackPressed() }
        adapter = SelectImageAdapter(imgList, this)
        binding.rvListimage.layoutManager = GridLayoutManager(this, 3)
        binding.rvListimage.itemAnimator = DefaultItemAnimator()
        binding.rvListimage.adapter = adapter
        addList()
    }

    fun addList() {
        CoroutineScope(Dispatchers.IO).launch {
            ArrayList<Any?>(filePaths)
            runOnUiThread{
                Log.e("Datalist",imgList.size.toString())
                adapter?.addData(imgList)
            }
        }
    }

    private val filePaths: ArrayList<String?>
        get() {
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val strArr = arrayOf("_data")
            val treeSet = TreeSet<Any?>()
            val arrayList = ArrayList<String?>()
            var strArr2: Array<String?>? = null
            val managedQuery =
                if (uri != null) managedQuery(uri, strArr, null, null, null) else null
            if (managedQuery != null && managedQuery.moveToFirst()) {
                do {
                    val string = managedQuery.getString(0)
                    try {
                        treeSet.add(string.substring(0, string.lastIndexOf("/")))
                    } catch (unused: Exception) {
                    }
                } while (managedQuery.moveToNext())
                strArr2 = arrayOfNulls(treeSet.size)
                treeSet.toArray(strArr2)
            }
            for (i in treeSet.indices) {
                val listFiles = File(strArr2!![i].toString()).listFiles()
                if (listFiles != null) {
                    val length = listFiles.size
                    var i2 = 0
                    while (i2 < length) {
                        val file = listFiles[i2]
                        try {
                            if (file.isDirectory) {
                                file.listFiles()
                            }
                            if (file.name.contains(".jpg") || file.name.contains(".JPG") || file.name.contains(
                                    ".jpeg"
                                ) || file.name.contains(".JPEG") || file.name.contains(".png") || file.name.contains(
                                    ".PNG"
                                ) || file.name.contains(".bmp") || file.name.contains(".BMP")
                            ) {
                                arrayList.add(file.absolutePath)
                                imgList.add(Image(file.absolutePath, false))
                                runOnUiThread{
                                    if(imgList.size == 100) {
                                        Log.e("Datalist", imgList.size.toString())
                                        adapter?.addData(imgList)
                                    }
                                }
                            }
                            i2++
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            return arrayList
        }

    public override fun onResume() {
        super.onResume()
    }
}