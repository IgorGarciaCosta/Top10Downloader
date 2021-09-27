package com.example.top10downloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

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
        if(convertView==null){
            view= inflater.inflate(resourse, parent, false)
        }
        else{//se convertView não for null, reusa ele
            view = convertView
        }

        val tvName :TextView  =view.findViewById(R.id.tvName)
        val tvArtist :TextView  =view.findViewById(R.id.tvArtist)
        val tvSummary :TextView  =view.findViewById(R.id.tvSummary)

        val currentApp = applications[position]

        tvName.text = currentApp.name
        tvArtist.text = currentApp.artist
         tvSummary.text = currentApp.summary

        return view
    }
}