package com.codepath.apps.restclienttemplate.models

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class Tweet {
    var body: String = ""
    var createdAt: String = ""
    var user: User? = null

    companion object {
        fun fromJson(jsonObject: JSONObject) : Tweet{
            Log.i("Tweet-fromJson","$jsonObject")

            val tweet= Tweet()
            Log.i("Tweet-fromJson","A")
            tweet.body =jsonObject.getString("text")
            Log.i("Tweet-fromJson","B")
            tweet.createdAt=jsonObject.getString("created_at")
            Log.i("Tweet-fromJson","C")
            tweet.user=User.fromJson(jsonObject.getJSONObject("user"))
            Log.i("Tweet-fromJson","D")

            return tweet
        }

        fun fromJsonArray(jsonArray:JSONArray): List<Tweet> {
            Log.i("Tweet-fromJson","$jsonArray")
            val tweets = ArrayList<Tweet>()
            for(i in 0 until jsonArray.length()){
                tweets.add(fromJson(jsonArray.getJSONObject(i)))

            }
            return tweets
        }

    }
}