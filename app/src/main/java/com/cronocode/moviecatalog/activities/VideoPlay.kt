//package com.cronocode.moviecatalog.activities
//
//import android.app.Activity
//import android.content.Intent
//import android.content.pm.ActivityInfo
//import android.graphics.Color
//import android.graphics.PorterDuff
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.view.animation.AnimationUtils
//import android.view.animation.LayoutAnimationController
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.lifecycle.Lifecycle
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.codewaves.youtubethumbnailview.ThumbnailLoader
//import com.codewaves.youtubethumbnailview.ThumbnailView
//import com.cronocode.moviecatalog.BuildConfig
//import com.cronocode.moviecatalog.R
//import com.cronocode.moviecatalog.Utils.FullScreenHelper
//import com.cronocode.moviecatalog.models.MovieVideosResults
//import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer
//
//import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
//import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
//import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener
//import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener
//import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerListener
//import java.lang.reflect.Array
//
//class VideoPlay() : AppCompatActivity() {
//    private lateinit var thumbnailView: ThumbnailView
//    private lateinit var playerView: YouTubePlayerView
//    private lateinit var progressBar: ProgressBar
//    private lateinit var otherVideosRV: RecyclerView
//    private lateinit var fullScreenHelper: FullScreenHelper
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_video_play)
//
//        val intent: Intent = intent
//
//        ThumbnailLoader.initialize("AIzaSyB2brH5x0ORtCowFDcgjGcXeYBsSyBXTx0")
//
//        fullScreenHelper = FullScreenHelper(this)
//        thumbnailView = findViewById(R.id.video_thumbnail_view)
//        playerView = findViewById(R.id.video_player_view)
//
//        val videoTitle = findViewById<TextView>(R.id.play_vid_title)
//        val noResultFound = findViewById<TextView>(R.id.no_result_found)
//
//        otherVideosRV = findViewById(R.id.other_videos_RV)
//        otherVideosRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//
//        progressBar = findViewById(R.id.progress_bar)
//        progressBar.indeterminateDrawable.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
//
//
//        if (intent != null && intent.extras != null) {
//            val movieVideosResultArrayList: ArrayList<MovieVideosResults> =
//                intent.extras!!.getParcelableArrayList("video")!!
//            val position: Int = Integer.parseInt(intent.extras!!.getString("position"))
//
//            if (movieVideosResultArrayList != null && movieVideosResultArrayList.size > 0) {
//                val videoId: String = movieVideosResultArrayList[position].key!!
//                val title: String = movieVideosResultArrayList[position].name!!
//                if (title != null) {
//                    videoTitle.text = title
//                }
//                if (videoId != null) {
//                    val baseUrl: String = "https://www.youtube.com/watch?v="
//                    thumbnailView.loadThumbnail(baseUrl + videoId)
//                    playerView.initialize(YouTubePlayerInitListener { youTubePlayer ->
//                        youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
//                            override fun onReady() {
//                                thumbnailView.visibility = View.GONE
//                                progressBar.visibility = View.GONE
//
//                                playerView.visibility = View.VISIBLE
//                                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
//                                    youTubePlayer.loadVideo(videoId, 0F)
//                                } else {
//                                    youTubePlayer.cueVideo(videoId, 0F)
//
//                                }
//                            }
//                        })
//                        playerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
//                            override fun onYouTubePlayerEnterFullScreen() {
//                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                                fullScreenHelper.enterFullScreen()
//                            }
//
//                            override fun onYouTubePlayerExitFullScreen() {
//                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                                fullScreenHelper.exitFullScreen()
//                            }
//
//                        })
//                    }, true)
//
//                    val movieVideoResultsList: ArrayList<MovieVideosResults> = ArrayList(movieVideosResultArrayList)
//
//                    movieVideoResultsList.removeAt(position)
//                    if(movieVideoResultsList.size>0){
//                        noResultFound.visibility = View.GONE
//                        val adapter: ExtraVideoRecyclerAdapter = ExtraVideoRecyclerAdapter(this, movieVideoResultsList)
//                        otherVideosRV.adapter = adapter
//                        otherVideosRV.visibility = View.VISIBLE
////
////                        val controller: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, )
//                    }
//                    else{
//                        otherVideosRV.visibility = View.GONE
//                        noResultFound.visibility = View.VISIBLE
//                    }
//
//                }
//            }
//
//        }
//    }
//
//    // exit the fullscreen while back pressed
//
//    override fun onBackPressed() {
//
//        if(playerView.isFullScreen){
//            playerView.exitFullScreen()
//        }
//        else{
//            otherVideosRV.visibility = View.GONE
//            playerView.visibility = View.GONE
//            thumbnailView.visibility = View.VISIBLE
//            super.onBackPressed()
//        }
//    }
//}