package com.med.linkConverter

import com.med.linkConverter.application.domain.conversion.ConversionType
import com.med.linkConverter.application.service.conversion.factory.LinkConversionFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.AssertionErrors

@SpringBootTest
class LinkConverterApplicationTests @Autowired constructor(
    val linkConversionFactory: LinkConversionFactory
) {
    @ParameterizedTest
    @CsvSource(
        value = [
            "https://www.med.com/brand/name-p-1925865?boutiqueId=439892&merchantId=105064,ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064",
            "https://www.med.com/casio/erkek-kol-saati-p-1925865,ty://?Page=Product&ContentId=1925865",
            "https://www.med.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892,ty://?Page=Product&ContentId=1925865&CampaignId=439892",
            "https://www.med.com/casio/erkek-kol-saati-p-1925865?merchantId=105064,ty://?Page=Product&ContentId=1925865&MerchantId=105064",
            "https://www.med.com/sr?q=elbise,ty://?Page=Search&Query=elbise",
            "https://www.med.com/sr?q=%C3%BCt%C3%BC,ty://?Page=Search&Query=%C3%BCt%C3%BC",
            "https://www.med.com/sr?q=süpürge,ty://?Page=Search&Query=s%C3%BCp%C3%BCrge",
            "https://www.med.com/Hesabim/Favoriler,ty://?Page=Home",
            "https://www.med.com/Hesabim/#/Siparislerim,ty://?Page=Home",
            "https://www.med.com/Mesajlarim,ty://?Page=Home",
            "https://www.med.com/apple/macbook-b-1925123,ty://?Page=Home",
            "https://www.med.com/kadin-gomlek-p-1925869,ty://?Page=Home"

        ], delimiter = ','
    )
    fun conversionWebURLToDeeplinkTest(input: String, excepted: String) {
        val actual = linkConversionFactory.findStrategy(ConversionType.WEB_URL_TO_DEEPLINK).convert(input)
        println("$input -> $actual")
        AssertionErrors.assertEquals("Error while conversion web url to deeplink.", excepted, actual)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064,https://www.med.com/brand/name-p-1925865?boutiqueId=439892&merchantId=105064",
            "ty://?Page=Product&ContentId=1925865,https://www.med.com/brand/name-p-1925865",
            "ty://?Page=Product&ContentId=1925865&CampaignId=439892,https://www.med.com/brand/name-p-1925865?boutiqueId=439892",
            "ty://?Page=Product&ContentId=1925865&MerchantId=105064,https://www.med.com/brand/name-p-1925865?merchantId=105064",
            "ty://?Page=Search&Query=elbise,https://www.med.com/sr?q=elbise",
            "ty://?Page=Search&Query=%C3%BCt%C3%BC,https://www.med.com/sr?q=%C3%BCt%C3%BC",
            "ty://?Page=Favorites,https://www.med.com",
            "ty://?Page=Orders,https://www.med.com",
            "ty://?Page=Search&Query=süpürge,https://www.med.com/sr?q=s%C3%BCp%C3%BCrge",
            "ty://?Page=Product&ContentId=1925865&OtherId=365064,https://www.med.com",
        ], delimiter = ','
    )
    fun conversionDeeplinkToWebURLTest(input: String, excepted: String) {
        val actual = linkConversionFactory.findStrategy(ConversionType.DEEPLINK_TO_WEB_URL).convert(input)
        println("$input -> $actual")
        AssertionErrors.assertEquals("Error while conversion deeplink to web url.", excepted, actual)
    }
}
