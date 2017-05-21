package example.banty.com.kotlinhttp

import org.json.JSONArray
import org.json.JSONObject

class JsonParser (val result : String?){

    fun getPhotoList() : List<Photo> {
        var photoList = mutableListOf<Photo>()
        val rootObject : JSONObject = JSONObject(result)
        val hits : JSONArray = rootObject.getJSONArray("hits")
        for (i in 0..hits.length()-1) {
            val hit : JSONObject = hits.getJSONObject(i)
            val photoURL : String = hit.getString("previewURL")
            val photo : Photo = Photo(photoURL)
            photoList.add(photo)
        }

        return photoList
    }
}