//package com.cronocode.moviecatalog.activities
//
//import android.app.Activity
//import android.app.ActivityOptions
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.app.ActivityOptionsCompat
//import androidx.core.view.ViewCompat
//import androidx.recyclerview.widget.RecyclerView
//import com.codewaves.youtubethumbnailview.ThumbnailLoadingListener
//import com.codewaves.youtubethumbnailview.ThumbnailView
//import com.cronocode.moviecatalog.R
//import com.cronocode.moviecatalog.models.MovieVideos
//import com.cronocode.moviecatalog.models.MovieVideosResults
//import kotlinx.android.synthetic.main.video_item.view.*
//import org.jetbrains.annotations.NotNull
//import retrofit2.Callback
//
//class MovieVideosAdapter(activity: Activity, movieVideosResultList: List<MovieVideosResults>): RecyclerView.Adapter<MovieVideosAdapter.MovieVideosViewHolder>() {
//    private lateinit var thumbnailView: ThumbnailView
//    private var activity = activity
//    private var movieVideosResultList = movieVideosResultList
//    class MovieVideosViewHolder(view : View) : RecyclerView.ViewHolder(view) {
//        val thumbnailView: ThumbnailView = itemView.findViewById(R.id.video_view)
//        val videoTitle = itemView.findViewById<TextView>(R.id.video_title)
//
//        fun setThumbnailView(activity: Activity, videoUrl: String){
//            thumbnailView.loadThumbnail(videoUrl, object : ThumbnailLoadingListener{
//                override fun onLoadingStarted(url: String, view: View) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onLoadingComplete(url: String, view: View) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onLoadingCanceled(url: String, view: View) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onLoadingFailed(url: String, view: View, error: Throwable?) {
//                    Toast.makeText(activity, error!!.localizedMessage, Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVideosViewHolder {
//        return MovieVideosAdapter.MovieVideosViewHolder(
//            LayoutInflater.from(activity).inflate(R.layout.video_item, parent, false)
//        )
//
//    }
//
//    override fun onBindViewHolder(holder: MovieVideosViewHolder, position: Int) {
//        val movieVideosResults: MovieVideosResults = movieVideosResultList[position]
//        val baseUrl = "https://www.youtube.com/watch?v="
//        val videoUrl = baseUrl + movieVideosResults.key
//
//        holder.setThumbnailView(activity, videoUrl)
//        holder.itemView.video_title.text = movieVideosResults.name
//        holder.itemView.setOnClickListener(View.OnClickListener {
//            val movieVideosResultsArrayList : ArrayList<MovieVideosResults> = ArrayList(movieVideosResultList)
//
//            val intent = Intent(activity, VideoPlay::class.java)
//
//            val compat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, holder.thumbnailView,
//                ViewCompat.getTransitionName(holder.thumbnailView).toString()
//            )
//            intent.putExtra("position", holder.adapterPosition)
//            intent.putExtra("video", movieVideosResultsArrayList)
//
//            activity.startActivity(intent, compat.toBundle())
//        })
//    }
//
//    override fun getItemCount(): Int {
//        return movieVideosResultList.size!!
//    }
//
//
//}