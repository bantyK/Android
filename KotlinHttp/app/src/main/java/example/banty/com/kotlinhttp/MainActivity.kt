package example.banty.com.kotlinhttp

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import java.net.URL

class MainActivity : AppCompatActivity() {

    var inputArea: LinearLayout? = null
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerview) as RecyclerView
        inputArea = findViewById(R.id.input_area) as LinearLayout
        val button: Button = findViewById(R.id.btn_get_pics) as Button
        val editText: EditText = findViewById(R.id.et_key) as EditText
        button.setOnClickListener { buttonClicked(editText.text.toString()) }
    }

    private fun buttonClicked(keyword: String) {
        if(keyword.isNotEmpty()) {
            val httpTask: HttpTask = HttpTask()
            httpTask.execute(keyword)
        } else {
            Toast.makeText(this,"Enter a keyword",Toast.LENGTH_SHORT).show()
        }
    }

    inner class HttpTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val url: URL = URL("https://pixabay.com/api/?key=5422819-61bf0c40c73aff759b44b3486&q=${params[0]}&photo_type=photo")
            val httpHelper: HttpHelper = HttpHelper(url)
            val response: String = httpHelper.getResponse()
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val parser: JsonParser = JsonParser(result)
            val photoList: List<Photo> = parser.getPhotoList()
            inputArea?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            recyclerView?.layoutManager = LinearLayoutManager(this@MainActivity)
            val mainAdapter: MainAdapter = MainAdapter(photoList, this@MainActivity)
            recyclerView?.adapter = mainAdapter
        }


    }
}
