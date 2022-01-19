package com.med.linkConverter.application.port.incoming

import com.med.linkConverter.application.domain.conversion.ConversionType

interface LinkConversionUseCase {

    fun convert(str: String): String

    fun getConversionType(): ConversionType
}