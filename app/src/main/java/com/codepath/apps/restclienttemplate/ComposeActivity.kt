package com.codepath.apps.restclienttemplate

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import java.lang.NullPointerException

lateinit var etCompose : EditText
lateinit var btnTweet: Button
lateinit var tvDisplay: TextView
lateinit var client :TwitterClient
var tvDisplayColor: Int=0
class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose= findViewById(R.id.etTweet)
        btnTweet= findViewById(R.id.btnTweet)
        tvDisplay=findViewById(R.id.tvDisplay)
        tvDisplayColor=tvDisplay.currentTextColor
        client= TwitterApplication.getRestClient(this)
        btnTweet.setOnClickListener {
            //Grab the content of the editText
            val tweetContent= etCompose.text.toString()

            //Sanitize our input
            //1.Make sure tweet isn't empty
            if(tweetContent.isEmpty()){
                Toast.makeText(this,"Empty tweets are not allowed!",Toast.LENGTH_SHORT).show()
                    //TODO Use a SnackBar message instead
            }
            //2. Make sure the tweet doesn't violate the character limit
            if(tweetContent.length > 280){
                Toast.makeText(this,"This tweet is too long! Tweets cannot contain more than 280 characters!",Toast.LENGTH_SHORT).show()

            }
            if(tweetContent.length <=280 && !tweetContent.isEmpty()){
                //Toast.makeText(this,tweetContent,Toast.LENGTH_SHORT).show()
                client.publishTweet(object : JsonHttpResponseHandler(){
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e(TAG,"Failed to publish tweet",throwable)
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.i(TAG,"Successfully published tweet!")
                        //Send the tweet back to TimelineActivity
                        val tweet= Tweet.fromJson(json.jsonObject)
                        val intent = Intent()
                        intent.putExtra("tweet",tweet)
                        setResult(RESULT_OK,intent)
                        finish()
                        Log.i(TAG,tweet.body)
                    }

                },tweetContent)
            }

            //Make an api call to publish the tweet

            //
        }
        //End of btn onClick Listener
        //Start of et TextChangedListener
        etCompose.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Fires right as the text is being changed (even supplies the range of text)

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Fires right before text is changing
            }

            override fun afterTextChanged(s: Editable) {
                // Fires right after the text has changed
                var charCount=s.toString().length
                var charsLeft=280-charCount
                var oldColor=tvDisplayColor

                if(charsLeft<0){
                    tvDisplay.setTextColor(Color.RED)
                    if(charsLeft==-1){
                        tvDisplay.setText("You've gone over the character limit by: 1 character")
                    }else  tvDisplay.setText("You've gone over the character limit by: ${charsLeft*-1} characters")

                }else {
                    tvDisplay.setTextColor(oldColor)
                    tvDisplay.setText("Characters left: $charsLeft")
                }


            }
        })
    }


    companion object{
        val TAG="ComposeActivity"
    }
}