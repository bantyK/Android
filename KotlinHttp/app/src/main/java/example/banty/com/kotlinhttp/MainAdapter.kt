package example.banty.com.kotlinhttp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainAdapter(val photos: List<Photo>, val context: Context) : RecyclerView.Adapter<MainAdapter.PhotoViewHolder>() {
    override fun onBindViewHolder(holder: PhotoViewHolder?, position: Int) {
        val photo: Photo = photos[position]
        Glide.with(context)
                .load(photo.pageURL)
                .into(holder?.imageView)
    }

    override fun getItemCount(): Int = photos.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoViewHolder {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.list_item, parent, false)
        return PhotoViewHolder(view)
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.image) as ImageView
        }
    }
}