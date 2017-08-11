package apps.scvh.com.whattodo.util.omdbImplementation

import com.google.gson.annotations.SerializedName


class Movie {

    @SerializedName("Title")
    var title: String = "N/A"
    @SerializedName("Rated")
    var rated: String? = "N/A"
    @SerializedName("Genre")
    var genre: String? = "N/A"
    @SerializedName("Director")
    var director: String? = "N/A"
    @SerializedName("Writer")
    var writer: String? = "N/A"
    @SerializedName("Actors")
    var actors: String? = "N/A"
    @SerializedName("Plot")
    var plot: String? = "N/A"
    @SerializedName("Language")
    var language: String? = "N/A"
    @SerializedName("Country")
    var country: String? = "N/A"
    @SerializedName("Awards")
    var awards: String? = "N/A"
    @SerializedName("Poster")
    var poster: String? = "N/A"
    @SerializedName("Type")
    var type: String? = "N/A"
    @SerializedName("Production")
    var production: String? = "N/A"
    @SerializedName("Website")
    var website: String? = "N/A"
    @SerializedName("Runtime")
    var runtime: String? = "N/A"
    @SerializedName("Released")
    var released: String? = "N/A"
    @SerializedName("DVD")
    var dvd: String? = "N/A"
    @SerializedName("BoxOffice")
    var boxoffice: String? = "N/A"
    var imdbVotes: String? = "N/A"
    var imdbID: String? = null
    @SerializedName("Metascore")
    var metascore: String? = "N/A"
    var imdbRating: String? = "N/A"
}