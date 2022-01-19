package com.med.linkConverter.application.domain.conversion.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Condition(

    @SerialName("/")
    val path: String = "",

    @SerialName("?")
    val query: Query? = null,

    @SerialName("*")
    val default: String = "",

    val comment: String? = null

) {
    val type: ConditionType by lazy {
        if (path.isNotEmpty()) {
            ConditionType.PATH
        } else if (!query?.name.isNullOrEmpty() && !query?.value.isNullOrEmpty()) {
            ConditionType.QUERY
        } else if (default.isNotEmpty()) {
            ConditionType.DEFAULT
        } else {
            ConditionType.UNKNOWN
        }
    }
}
