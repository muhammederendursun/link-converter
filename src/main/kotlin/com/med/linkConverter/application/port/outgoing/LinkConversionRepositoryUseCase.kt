package com.med.linkConverter.application.port.outgoing

import com.med.linkConverter.application.domain.repository.LinkConversion

interface LinkConversionRepositoryUseCase {

    fun save(linkConversion: LinkConversion)
}