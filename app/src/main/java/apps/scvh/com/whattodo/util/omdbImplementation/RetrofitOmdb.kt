package apps.scvh.com.whattodo.util.omdbImplementation

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitOmdb {

    @GET("/")
    fun getMovie(@Query("i") id: String, @Query("apikey") apiKey: String): Call<Movie>

    @GET("/")
    fun getMovie(@Query("t") name: String, @Query("y") year: Int, @Query("apikey") apiKey: String): Call<Movie>

}