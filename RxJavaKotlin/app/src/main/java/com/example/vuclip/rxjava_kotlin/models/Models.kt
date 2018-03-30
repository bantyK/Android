package com.example.vuclip.rxjava_kotlin.models

/**
 * Created by Banty on 31/03/18.
 */
object Models {
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo)
    data class SearchInfo(val totalhits: Int)
}