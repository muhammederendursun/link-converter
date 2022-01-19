package com.med.linkConverter.application.domain.conversion.schema

import kotlinx.serialization.Serializable

@Serializable
data class Rule(

    val conditions: List<Condition>,

    val link: String
)
