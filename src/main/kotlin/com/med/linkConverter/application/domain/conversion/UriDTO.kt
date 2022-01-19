package com.med.linkConverter.application.domain.conversion

import com.med.linkConverter.application.domain.conversion.schema.Query

data class UriDTO(

    val host: String?,

    val scheme: String?,

    val paths: List<String> = emptyList(),

    val queries: List<Query> = emptyList()
)