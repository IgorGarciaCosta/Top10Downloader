package com.example.top10downloader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
class FeedEntry{
    var name:String = ""
    var artist:String = ""
    var releaseData:String = ""
    var summary:String = ""
    var imageURL:String = ""

    override  fun toString(): String{
        return """
            name = $name
            artist = $artist
            releaseData = $releaseData
            imageURL = $imageURL
        """.trimIndent()
            
    }
}
class MainActivity : AppCompatActivity() {
    private val TAG = "Main Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreateCalled")
        val downloadData = DownloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
        Log.d(TAG, "onCreate: done")

    }

    companion object {
        private class DownloadData: AsyncTask<String, Void, String>(){
            private val TAG = "DownloadData"
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute: parameter is $result")
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