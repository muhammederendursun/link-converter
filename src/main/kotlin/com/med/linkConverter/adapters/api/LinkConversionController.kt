package com.med.linkConverter.adapters.api

import com.med.linkConverter.application.domain.conversion.ConversionType
import com.med.linkConverter.application.service.conversion.factory.LinkConversionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/link/convert")
class LinkConversionController @Autowired constructor(
    val linkConversionFactory: LinkConversionFactory
) {

    @GetMapping("/webURL/to/deeplink")
    fun webURLToDeeplink(@RequestParam webUrl: String): String {
        return linkConversionFactory.findStrategy(ConversionType.WEB_URL_TO_DEEPLINK).convert(webUrl)
    }

    @GetMapping("/deeplink/to/webURL")
    fun deeplinkToWebURL(@RequestParam deeplink: String): String {
        return linkConversionFactory.findStrategy(ConversionType.DEEPLINK_TO_WEB_URL).convert(deeplink)
    }
}