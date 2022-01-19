package com.med.linkConverter.adapters.persistence.mysql

import com.med.linkConverter.application.domain.repository.LinkConversion
import com.med.linkConverter.application.port.outgoing.LinkConversionRepositoryUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class LinkConversionJdbcRepository @Autowired constructor(
    val jdbcTemplate: JdbcTemplate,
    @Value("\${conversion.table}") val conversionTable: String
) : LinkConversionRepositoryUseCase {

    override fun save(linkConversion: LinkConversion) {
        jdbcTemplate.update(
            "INSERT INTO $conversionTable (request, response, conversionType) values(?,?,?)",
            linkConversion.request, linkConversion.response, linkConversion.conversionType.name
        )
    }
}