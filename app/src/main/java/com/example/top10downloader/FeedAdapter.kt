package com.example.top10downloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ViewHolder(v:View){
    val tvName: TextView = v.findViewById(R.id.tvName)
    val tvArtist: TextView = v.findViewById(R.id.tvArtist)
    val tvSummary: TextView = v.findViewById(R.id.tvSummary)
    val tvImage: ImageView = v.findViewById(R.id.tvImage)
}


class FeedAdapter(context: Context, private val resourse:Int, private val applications: List<FeedEntry>) : ArrayAdapter<FeedEntry>(context, resourse){


    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return applications.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val viewHolder:ViewHolder
        if(convertView==null){
            view= inflater.inflate(resourse, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else{//se convertView não for null, reusa ele
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        //val tvName :TextView  =view.findViewById(R.id.tvName)
        //val tvArtist :TextView  =view.findViewById(R.id.tvArtist)
        //val tvSummary :TextView  =view.findViewById(R.id.tvSummary)

        val currentApp = applications[position]

        viewHolder.tvName.text = currentApp.name
        viewHolder.tvArtist.text = currentApp.artist
        viewHolder.tvSummary.text = currentApp.summary
        //viewHolder.tvImage.text= currentApp.imageURL
        Picasso.get().load(currentApp.imageURL)
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_background)
            .into(viewHolder.tvImage)

        return view
    }
}