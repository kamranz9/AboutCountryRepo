package com.assignment.aboutcountryproject.model.remote

import com.google.gson.annotations.SerializedName

/**
 * Class which will handle main response of API
 */
class CountryFactsModels(
    @SerializedName("title")var title: String? = null,
    @SerializedName("rows")val rowInformations: List<RowInformation>? = null
)