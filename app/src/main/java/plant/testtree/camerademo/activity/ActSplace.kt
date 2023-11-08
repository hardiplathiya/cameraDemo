package plant.testtree.camerademo.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import plant.testtree.camerademo.activity.ActMain
import plant.testtree.camerademo.databinding.ActivitySplaceBinding

class ActSplace : AppCompatActivity() {
    private lateinit var binding: ActivitySplaceBinding
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = ActivitySplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goNext()
    }

    fun goNext() {
        Handler(Looper.getMainLooper()).postDelayed({
            this@ActSplace.startActivity(
                Intent(
                    this@ActSplace,
                    ActMain::class.java
                )
            )
        }, 3000L)
    }
}