package com.med.linkConverter.application.domain.conversion.schema

import kotlinx.serialization.Serializable

@Serializable
data class Query(

    val name: String,

    val value: String
)
