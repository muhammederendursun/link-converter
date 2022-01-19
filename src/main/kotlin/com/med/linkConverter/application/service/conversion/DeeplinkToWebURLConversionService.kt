package com.med.linkConverter.application.service.conversion

import com.med.linkConverter.application.domain.conversion.ConversionType
import com.med.linkConverter.application.port.outgoing.LinkConversionRepositoryUseCase
import com.med.linkConverter.application.service.conversion.base.AbstractLinkConversionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeeplinkToWebURLConversionService @Autowired constructor(
    val linkConversionRepositoryUseCase_: LinkConversionRepositoryUseCase
) : AbstractLinkConversionService(
    linkConversionRepositoryUseCase = linkConversionRepositoryUseCase_
) {

    override fun convert(str: String): String {
        return convert(str, getConversionType())
    }

    override fun getConversionType() = ConversionType.DEEPLINK_TO_WEB_URL
}