//package com.cronocode.moviecatalog.Utils
//
//import android.app.Activity
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.iterator
//
//class FullScreenHelper {
//    private lateinit var activity: Activity
//    private lateinit var views: ViewGroup
//
//    constructor(activity: Activity) {
//        this.activity = activity
//        this.views = views
//    }
//
//    private fun showSystemUi(decorView: View){
//        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//
//    }
//    private fun hideSystemUi(decorView: View){
//        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//
//    }
//    fun enterFullScreen(){
//        val view: View = activity.window.decorView
//        hideSystemUi(view)
//        for(view1 in views){
//            view1.visibility = View.GONE
//            view1.invalidate()
//        }
//
//    }
//    fun exitFullScreen(){
//        val view: View = activity.window.decorView
//        showSystemUi(view)
//        for(view1 in views){
//            view1.visibility = View.VISIBLE
//            view1.invalidate()
//        }
//
//    }
//
//}
//
//
