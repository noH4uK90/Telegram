package com.example.telegram.domain.util

import com.example.telegram.domain.models.PhoneCode
import com.example.telegram.domain.models.PhoneCodes
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.*

fun phoneCodes(): PhoneCodes {
    val phoneNumberUtils = PhoneNumberUtil.getInstance()
    val regionList = phoneNumberUtils.supportedRegions.toList()

    return PhoneCodes(
        items = regionList.map {
            PhoneCode(
                region = it,
                countryName = Locale("", it).displayName,
                code = phoneNumberUtils.getCountryCodeForRegion(it)
            )
        }
    )
}

fun String.toEmojiFlag(): String {
    return this
        .uppercase(Locale.US)
        .map { char ->
            Character.codePointAt("$char", 0) - 0x41 + 0x1F1E6
        }
        .map { codePoint ->
            Character.toChars(codePoint)
        }
        .joinToString(separator = "") { charArray ->
            String(charArray)
        }
}