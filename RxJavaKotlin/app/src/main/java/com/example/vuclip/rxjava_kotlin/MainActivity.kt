package com.example.vuclip.rxjava_kotlin

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.vuclip.rxjava_kotlin.dagger.DaggerAppComponent
import com.example.vuclip.rxjava_kotlin.dagger.NetworkModule
import com.example.vuclip.rxjava_kotlin.retrofit.WikiAppService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    @Inject
    lateinit var wikiAppService: WikiAppService
    private lateinit var searchStringEditText: EditText
    private lateinit var totalHits: TextView
    private lateinit var fetchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUIElements()

        DaggerAppComponent.builder()
                .networkModule(NetworkModule())
                .build()
                .inject(this)

    }

    private fun initUIElements() {
        searchStringEditText = findViewById(R.id.search_string)
        totalHits = findViewById(R.id.total_hits)
        fetchButton = findViewById(R.id.fetch_button)
        fetchButton.setOnClickListener { fetchWikiData(searchStringEditText.text.toString()) }
    }

    private fun fetchWikiData(searchString: String) {
        val hitCountCheck = wikiAppService.hitCountCheck("query", "json", "search", searchString)
        hitCountCheck
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            result.query.search.stream()
                                    .forEach { search -> Log.d(TAG, search.title) }
                            totalHits.text = "${result.query.searchinfo.totalhits}"
                        },
                        { error -> error.printStackTrace() }
                )


    }
}
