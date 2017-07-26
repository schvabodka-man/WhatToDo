package apps.scvh.com.whattodo.util.omdbImplementation

import com.google.gson.annotations.SerializedName


class Movie {

    @SerializedName("Title")
    lateinit var title: String
    @SerializedName("Rated")
    lateinit var rated: String
    @SerializedName("Genre")
    lateinit var genre: String
    @SerializedName("Director")
    lateinit var director: String
    @SerializedName("Writer")
    lateinit var writer: String
    @SerializedName("Actors")
    lateinit var actors: String
    @SerializedName("Plot")
    lateinit var plot: String
    @SerializedName("Language")
    lateinit var language: String
    @SerializedName("Country")
    lateinit var country: String
    @SerializedName("Awards")
    lateinit var awards: String
    @SerializedName("Poster")
    lateinit var poster: String
    @SerializedName("Type")
    lateinit var type: String
    @SerializedName("Production")
    lateinit var production: String
    @SerializedName("Website")
    lateinit var website: String
    @SerializedName("Runtime")
    lateinit var runtime: String
    @SerializedName("Released")
    lateinit var released: String
    @SerializedName("DVD")
    lateinit var dvd: String
    @SerializedName("BoxOffice")
    lateinit var boxoffice: String
    lateinit var imdbVotes: String
    lateinit var imdbID: String
    @SerializedName("Metascore")
    lateinit var metascore: String
    lateinit var imdbRating: String
}