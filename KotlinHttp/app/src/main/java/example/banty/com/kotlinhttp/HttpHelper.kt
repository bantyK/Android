package example.banty.com.kotlinhttp

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpHelper (val url : URL){

    fun getResponse() : String{
        val conn : HttpURLConnection = this.url.openConnection() as HttpURLConnection
        val inputStream : InputStream = BufferedInputStream(conn.inputStream)
        val reader : BufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line : String? = null
        var result : StringBuilder = StringBuilder()
        line = reader.readLine()
        while (line != null) {
            result.append(line)
            line = reader.readLine()
        }

        return result.toString()
    }
}