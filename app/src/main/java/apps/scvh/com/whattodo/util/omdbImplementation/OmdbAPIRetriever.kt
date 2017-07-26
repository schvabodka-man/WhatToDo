package apps.scvh.com.whattodo.util.omdbImplementation

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class OmdbAPIRetriever {

    fun retrieveMovie(id: String, token: String): Movie? {
        return getRetrofitApi().getMovie(id, token).execute().body()
    }

    fun retrieveMovie(name: String, year: Int, token: String): Movie? {
        return getRetrofitApi().getMovie(name, year, token).execute().body()
    }

    private fun getRetrofitApi(): RetrofitOmdb {
        val okHttp = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()
        return Retrofit.Builder().baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create()).client(okHttp).build().create(RetrofitOmdb::class.java)
    }

}