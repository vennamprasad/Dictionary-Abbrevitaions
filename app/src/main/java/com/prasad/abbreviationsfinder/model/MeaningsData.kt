package com.prasad.abbreviationsfinder.model
import com.google.gson.annotations.SerializedName

class MeaningsData : ArrayList<MeaningsData.MeaningsDataItem>(){
    data class MeaningsDataItem(
        @SerializedName("lfs")
        val lfs: List<Lf> = listOf(),
        @SerializedName("sf")
        val sf: String = ""
    ) {
        data class Lf(
            @SerializedName("freq")
            val freq: Int = 0,
            @SerializedName("lf")
            val lf: String = "",
            @SerializedName("since")
            val since: Int = 0,
            @SerializedName("vars")
            val vars: List<Var> = listOf()
        ) {
            data class Var(
                @SerializedName("freq")
                val freq: Int = 0,
                @SerializedName("lf")
                val lf: String = "",
                @SerializedName("since")
                val since: Int = 0
            )
        }
    }
}