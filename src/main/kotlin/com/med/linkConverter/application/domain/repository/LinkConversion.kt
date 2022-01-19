package com.med.linkConverter.application.domain.repository

import com.med.linkConverter.application.domain.conversion.ConversionType

data class LinkConversion(
    val request: String,
    val response: String,
    val conversionType: ConversionType
)
