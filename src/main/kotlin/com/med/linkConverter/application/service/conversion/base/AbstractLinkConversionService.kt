package com.med.linkConverter.application.service.conversion.base

import com.med.linkConverter.application.domain.conversion.schema.ConditionType
import com.med.linkConverter.application.domain.conversion.ConversionType
import com.med.linkConverter.application.domain.conversion.schema.Query
import com.med.linkConverter.application.domain.common.Constants
import com.med.linkConverter.application.domain.common.Variables.ruleSchema
import com.med.linkConverter.application.domain.conversion.UriDTO
import com.med.linkConverter.application.domain.repository.LinkConversion
import com.med.linkConverter.application.port.incoming.LinkConversionUseCase
import com.med.linkConverter.application.port.outgoing.LinkConversionRepositoryUseCase
import java.net.URI
import java.net.URLEncoder

abstract class AbstractLinkConversionService(
    val linkConversionRepositoryUseCase: LinkConversionRepositoryUseCase
) : LinkConversionUseCase {

    protected fun convert(input: String, conversionType: ConversionType): String {

        val uri = input.uriParser()
        var isMatched = false
        var link = ""

        for (rule in ruleSchema.rules) {

            val pathConditions = rule.conditions.filter { it.type == ConditionType.PATH }
            val queryConditions = rule.conditions.filter { it.type == ConditionType.QUERY }

            if (uri.paths.size != pathConditions.size || uri.queries.size != queryConditions.size) {
                link = getDefaultLink(rule.link, conversionType)
                isMatched = false
                continue
            }

            val pathParams = mutableMapOf<String, String>()

            for (condition in rule.conditions) {

                when (condition.type) {

                    ConditionType.PATH -> {
                        isMatched = isPathMatched(uri.paths, condition.path, pathParams)
                    }

                    ConditionType.QUERY -> {
                        isMatched = condition.query?.let {
                            isQueryMatched(uri.queries, it)
                        } ?: false
                    }

                    ConditionType.DEFAULT -> {
                        link = getDefaultLink(rule.link, conversionType)
                    }

                    else -> {
                        isMatched = false
                    }
                }

                if (!isMatched) {
                    break
                }
            }

            if (isMatched) {

                link = rule.link

                link = replaceQueries(link, uri.queries)

                link = replacePathParams(link, pathParams)

                break
            }
        }

        val linkConversion = LinkConversion(input, "${getHost(conversionType)}$link", conversionType)

        saveLinkConversion(linkConversion)

        return linkConversion.response
    }

    private fun saveLinkConversion(linkConversion: LinkConversion) {
        linkConversionRepositoryUseCase.save(linkConversion)
    }

    private fun String.uriParser(): UriDTO {

        val uri = URI(this)
        val queryList = uri.query?.split('&')
        val queries = mutableListOf<Query>()

        queryList?.forEach { query ->

            query.split('=').takeIf { it.size == 2 }?.let {
                val name = it[0]
                val value = URLEncoder.encode(it[1], "utf-8")
                queries.add(Query(name, value))
            }
        }

        val paths = uri.path.split('/').filter { it.isNotEmpty() }.map { "/$it" }

        return UriDTO(uri.host, uri.scheme, paths, queries)
    }

    private fun isPathMatched(uriPathList: List<String>, conditionPath: String, pathParamList: MutableMap<String, String>): Boolean {

        return !uriPathList.find { path ->

            val values = conditionPath.toRegex().find(path)?.groupValues

            fillPathParams(values, pathParamList, conditionPath)

            !values.isNullOrEmpty()

        }.isNullOrEmpty()
    }

    private fun isQueryMatched(uriQueryList: List<Query>, conditionQuery: Query): Boolean {

        return uriQueryList.find { query ->
            conditionQuery.name == query.name && query.value.contains(conditionQuery.value.toRegex())
        } != null
    }

    private fun getDefaultLink(link: String, conversionType: ConversionType): String {

        val defaultList = link.split(',')

        return when (conversionType) {
            ConversionType.WEB_URL_TO_DEEPLINK -> {
                defaultList.getOrNull(0)
            }
            ConversionType.DEEPLINK_TO_WEB_URL -> {
                val url = defaultList.getOrNull(1)
                if (url == "{url}") "" else url
            }
        } ?: ""
    }

    private fun getHost(conversionType: ConversionType): String {

        return when (conversionType) {
            ConversionType.WEB_URL_TO_DEEPLINK -> {
                ruleSchema.deepLink
            }
            ConversionType.DEEPLINK_TO_WEB_URL -> {
                ruleSchema.url
            }
        }
    }

    private fun replaceQueries(link: String, uriQueryList: List<Query>): String {

        var tempLink = link

        uriQueryList.forEach { query ->
            tempLink = tempLink.replace("{${query.name}}", query.value)
        }

        return tempLink
    }

    private fun replacePathParams(link: String, pathParamList: Map<String, String>): String {

        var tempLink = link

        pathParamList.forEach { param ->
            tempLink = tempLink.replace("{${param.key}}", param.value)
        }

        return tempLink
    }

    private fun fillPathParams(values: List<String>?, pathParamList: MutableMap<String, String>, conditionPath: String) {

        if (values.isNullOrEmpty()) {
            return
        }

        Constants.PATH_PARAM_REGEX.toRegex().find(conditionPath)?.groupValues?.let { names ->
            names.zip(values).forEach { parameter ->
                pathParamList[parameter.first] = parameter.second
            }
        }
    }
}