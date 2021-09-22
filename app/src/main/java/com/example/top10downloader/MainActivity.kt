package com.example.top10downloader

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry{
    var name:String = ""
    var artist:String = ""
    var releaseDate:String = ""
    var summary:String = ""
    var imageURL:String = ""

    override  fun toString(): String{
        return """
            name = $name
            artist = $artist
            releaseData = $releaseDate
            imageURL = $imageURL
        """.trimIndent()
            
    }
}
class MainActivity : AppCompatActivity() {
    private val TAG = "Main Activity"

    private val downloadData by lazy { DownloadData(this, findViewById(R.id.xmlListView)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreateCalled")
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=25/xml")
        Log.d(TAG, "onCreate: done")

    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData.cancel(true)
    }

    companion object {
        private class DownloadData(context: Context, listView:ListView): AsyncTask<String, Void, String>(){
            private val TAG = "DownloadData"

            var propContext:Context by Delegates.notNull()
            var propListView:ListView by Delegates.notNull()

            init{
                propContext = context
                propListView = listView
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val parseApplications =ParseApplications()
                parseApplications.parse(result)

                val arrayAdapter = ArrayAdapter<FeedEntry>(propContext, R.layout.list_item, parseApplications.applications)
                propListView.adapter = arrayAdapter
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG, "doInBackground: starts with ${url[0]}")
                val rssFeed = downloadXML(url[0])
                if(rssFeed.isEmpty()){
                    Log.e(TAG, "doInBackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath:String?):String{
                val xmlResult = StringBuilder()

                try{
                    val url = URL(urlPath)
                    val connection:HttpURLConnection = url.openConnection() as HttpURLConnection
                    val response = connection.responseCode
                    Log.d(TAG, "downloadXML: The response code was $response")


                    connection.inputStream.buffered().reader().use{xmlResult.append(it.readText()) }

                    Log.d(TAG, "Received ${xmlResult.length} bytes")
                    return xmlResult.toString()
                }

                catch(e:Exception){
                    val errMsg = when(e){
                        is MalformedURLException -> "downloadXML: Invalid URL ${e.message}"
                        is IOException-> "downloadXML: IoException reading data: ${e.message}"
                        is SecurityException-> "downloadXML: Needs permission ${e.message}"
                        else-> "Unknown error> ${e.message}"

                    }

                }
                return ""//if gets here, theres a problem
            }

        }
    }


}