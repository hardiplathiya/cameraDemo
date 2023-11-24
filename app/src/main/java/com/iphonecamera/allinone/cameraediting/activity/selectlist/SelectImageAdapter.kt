package com.iphonecamera.allinone.cameraediting.activity.selectlist

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pesonal.adsdk.AppManage
import com.iphonecamera.allinone.cameraediting.R
import com.iphonecamera.allinone.cameraediting.activity.gallary.GalleryappActivity
import com.iphonecamera.allinone.cameraediting.activity.selectlist.SelectImageAdapter.MyViewHolder
import com.iphonecamera.allinone.cameraediting.model.Image
import java.io.File

class SelectImageAdapter(var moviesList: ArrayList<Image>, var context: Activity) :
    RecyclerView.Adapter<MyViewHolder>() {
    fun addData(imgList: ArrayList<Image>) {
        moviesList = ArrayList()
        moviesList.addAll(imgList)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cv_item: RelativeLayout
        var iv_img: ImageView
        var rl_check: RelativeLayout

        init {
            iv_img = view.findViewById<View>(R.id.iv_img) as ImageView
            rl_check = view.findViewById<View>(R.id.rl_check) as RelativeLayout
            cv_item = view.findViewById<View>(R.id.cv_item) as RelativeLayout
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.select_list_item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        Glide.with(context).load(moviesList[i].path).into(myViewHolder.iv_img)
        if (moviesList[i].isCheck) {
            myViewHolder.cv_item.setPadding(10, 10, 10, 10)
            myViewHolder.rl_check.visibility = View.VISIBLE
            moviesList[i].isCheck = true
        } else {
            myViewHolder.rl_check.visibility = View.GONE
            myViewHolder.cv_item.setPadding(0, 0, 0, 0)
            moviesList[i].isCheck = false
        }
        myViewHolder.itemView.setOnClickListener {
            AppManage.getInstance(context)
                .showInterstitialAd(context) {
                    val file = File(moviesList[i].path)
                    val intent = Intent(context, GalleryappActivity::class.java)
                    intent.putExtra("path", file.parentFile!!.name)
                    intent.putExtra("dir", file.parentFile?.toString())
                    intent.putExtra("name", file.name)
                    context.startActivity(intent)
                }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}