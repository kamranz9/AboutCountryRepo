package com.assignment.aboutcountryproject.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Class to handle the row class inside main model class to handle the  user response
 */
data class RowInformation  (

    @SerializedName("title")val title: String? = null,
    @SerializedName("description")val description: String? = null,
    @SerializedName("imageHref")val imageHref: String? = null

)