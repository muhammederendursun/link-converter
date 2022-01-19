package com.med.linkConverter.application.service.conversion.factory

import com.med.linkConverter.application.domain.conversion.ConversionType
import com.med.linkConverter.application.port.incoming.LinkConversionUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class LinkConversionFactory @Autowired constructor(linkConversionStrategies: Set<LinkConversionUseCase>) {

    init {
        createStrategies(linkConversionStrategies)
    }

    private fun createStrategies(strategies: Set<LinkConversionUseCase>) {
        strategies.forEach { service ->
            strategyMap[service.getConversionType()] = service
        }
    }

    fun findStrategy(conversionType: ConversionType): LinkConversionUseCase {
        return strategyMap[conversionType]
            ?: throw IllegalArgumentException("Error while finding strategy. Strategy: ${conversionType.name}")

    }

    companion object {
        val strategyMap = mutableMapOf<ConversionType, LinkConversionUseCase>()
    }
}