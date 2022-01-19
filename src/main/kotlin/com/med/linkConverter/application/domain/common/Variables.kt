package com.med.linkConverter.application.domain.common

import com.med.linkConverter.application.domain.conversion.schema.RuleSchema
import com.med.linkConverter.application.domain.common.Constants.schemaPath
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.core.io.ClassPathResource

object Variables {

    private val schemaText: String by lazy {
        ClassPathResource(schemaPath).inputStream.bufferedReader().readText()
    }

    val ruleSchema: RuleSchema by lazy {
        Json.decodeFromString(schemaText)
    }
}