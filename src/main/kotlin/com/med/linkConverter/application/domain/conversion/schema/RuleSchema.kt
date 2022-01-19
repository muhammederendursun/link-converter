package com.med.linkConverter.application.domain.conversion.schema

import kotlinx.serialization.Serializable

@Serializable
data class RuleSchema(

    val url: String,

    val deepLink: String,

    val rules: List<Rule>
)