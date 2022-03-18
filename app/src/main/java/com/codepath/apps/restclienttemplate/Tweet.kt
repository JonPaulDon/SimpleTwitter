package com.codepath.apps.restclienttemplate

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject
@Parcelize
class Tweet(var body: String = "",
            var createdAt: String = "",
            var user: User? = null): Parcelable {

    @IgnoredOnParcel
    var time: String = ""

    companion object {
        fun fromJson(jsonObject: JSONObject): Tweet {
            Log.i("Tweet-fromJson", "$jsonObject")

            val tweet = Tweet()
            tweet.body = jsonObject.getString("text")
            tweet.createdAt = jsonObject.getString("created_at")
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"))
            tweet.time = TimeFormatter.getTimeDifference(tweet.createdAt)
            return tweet
        }

        fun fromJsonArray(jsonArray: JSONArray): List<Tweet> {
            Log.i("Tweet-fromJson", "$jsonArray")
            val tweets = ArrayList<Tweet>()
            for (i in 0 until jsonArray.length()) {
                tweets.add(fromJson(jsonArray.getJSONObject(i)))

            }
            return tweets
        }

    }

}