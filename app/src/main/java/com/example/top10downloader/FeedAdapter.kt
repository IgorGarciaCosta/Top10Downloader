package com.example.top10downloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ViewHolder(v:View){
    val tvName: TextView = v.findViewById(R.id.tvName)
    val tvArtist: TextView = v.findViewById(R.id.tvArtist)
    val tvSummary: TextView = v.findViewById(R.id.tvSummary)
}


class FeedAdapter(context: Context, private val resourse:Int, private val applications: List<FeedEntry>) : ArrayAdapter<FeedEntry>(context, resourse){


    private val TAG = "FadeAdapter"
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        Log.d(TAG, "getCount() called")
        return applications.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getView() called")
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

        return view
    }
}