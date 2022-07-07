package com.quadrantapp.core.util

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager

object StringUtil {

    fun isPromoCodeValid(text: String): Boolean {
        return !text.matches(Regex("[a-zA-Z0-9]*"))
    }

    fun isPersonNameValid(text: String): Boolean {
        return text.isNotEmpty() && text.length <= 22 && text.matches("[a-zA-Z0-9.?' ]*".toRegex())
    }

    fun isEmailAddressValid(text: String): Boolean {
        return text.isNotEmpty() && text.matches("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})\$".toRegex())
    }

    fun isPhoneNumberValid(text: String): Boolean {
        return text.matches("(\\()?(\\+62|62|0)(\\d{2,3})?\\)?[ .-]?\\d{2,4}[ .-]?\\d{2,4}[ .-]?\\d{2,4}".toRegex())
    }

    fun numberValid(text: String): String {
        return text.replace(Regex("[^0-9]"), "")
    }

    fun isPukNumberValid(text: String): Boolean {
        return text.matches("^(\\d{15,19}-?\\d)\$".toRegex())
    }

    /**
     * Performs the Luhn check on the given card number.
     *
     * @param cardNumber a String consisting of numeric digits (only).
     * @return `true` if the sequence passes the checksum
     * @throws IllegalArgumentException if `cardNumber` contained a non-digit (where [ ][Character.isDefined] is `false`).
     * @see [Luhn Algorithm
    ](http://en.wikipedia.org/wiki/Luhn_algorithm) */
    fun isLuhnValid(cardNumber: String): Boolean {
        val reversed = StringBuffer(cardNumber).reverse().toString()
        val len = reversed.length
        var oddSum = 0
        var evenSum = 0
        for (i in 0 until len) {
            val c = reversed[i]
            require(Character.isDigit(c)) {
                String.format(
                    "Not a digit: '%s'",
                    c
                )
            }
            val digit = Character.digit(c, 10)
            if (i % 2 == 0) {
                oddSum += digit
            } else {
                evenSum += digit / 5 + 2 * digit % 10
            }
        }
        return (oddSum + evenSum) % 10 == 0
    }

    fun getStringWithGap(source: String, individualWordLength: Int): String {
        val space = ' '
        val charList = source.toMutableList()

        // count number of space based on multiplication of 4
        val amountOfSpace = source.length / individualWordLength

        for (i in 1..amountOfSpace) {
            val spaceIndex = ((i * individualWordLength) + (i-1)) // put space every index with multiplication of 4
            charList.add(spaceIndex, space)
        }

        // remove last whitespace if whitespace is the last char
        if (charList.size > 0 && charList[charList.size-1] == space)
            charList.removeLast()

        val strBuilder = StringBuilder("")
        charList.forEach {
            strBuilder.append(it)
        }
        return strBuilder.toString()
    }

    fun extractOTP(message: String): String {
        val regex = "[A-Z0-9]{6}".toRegex()
        return regex.find(message)?.value?: ""
    }

    fun maskPhoneNumber(
        phoneNumber: String,
        maskChar: String = "x"
    ): String {
        var msisdnPhone = phoneNumber
        for (index in 6 until msisdnPhone.length) {
            msisdnPhone = msisdnPhone.replaceRange(index, index + 1, maskChar)
        }

        return msisdnPhone
    }

    fun subscriptionType(subsType: String, isCorporate: Boolean): String {
        val subsType = if (isCorporate)
                when (subsType) {
                    "HOMEIZI" -> "E-XLHOMEIZI"
                    "XLHOME_IZI" -> "E-XLHOMEIZI"
                    "HOME_FIBER" -> "E-HOMEFIBER"
                    "POSTPAID" -> "E-PASCABAYAR"
                    "HOME_POSTPAID" -> "E-HOMEPOSTPAID"
                    "PRIO_GO" -> "E-PRIORITAS"
                    "PRIOGO" -> "E-PRIORITAS"
                    "EGO" -> "E-GO"
                    else -> "E-${subsType}"
                }
            else
                when (subsType) {
                    "HOMEIZI" -> "XLHOMEIZI"
                    "XLHOME_IZI" -> "XLHOMEIZI"
                    "HOME_FIBER" -> "HOMEFIBER"
                    "POSTPAID" -> "PASCABAYAR"
                    "HOME_POSTPAID" -> "HOMEPOSTPAID"
                    "PRIO_GO" -> "PRIORITAS"
                    "PRIOGO" -> "PRIORITAS"
                    "EGO" -> "E-GO"
                    else -> "${subsType}"
                }

        return subsType
    }

    fun getPaymentMethod(payment: String): String {

        return when (payment) {
            "BALANCE" -> "Balance"
            "Pulsa" -> "Balance"
            "GOPAY" -> "Gopay"
            "CCDC" -> "Kartu Kredit"
            "VABRI" -> "BRI Virtual Account"
            "VABNI" -> "BNI Virtual Account"
            "VAPERMATA" -> "Permata Virtual Account"
            "Postpaid Balance" -> "Saldo Postpaid"
            "PRIO Flex Balance" -> "Saldo PRIO Flex"
            "Saldo Flex" -> "Saldo PRIO Flex"
            else -> "$payment"
        }
    }

    fun getInitialName(name: String): String {
        var lastLetter = ""
        var firstLetter = ""
        var initial = ""
        if (name.isNotEmpty()) {
            firstLetter = name.substring(0, 1).toUpperCase()
        }

        if (name.isNotEmpty() && name.split(" ").size > 1) {
            lastLetter = try {
                name.substring(name.lastIndexOf(" ") + 1, name.lastIndexOf(" ") + 2).toUpperCase()
            } catch (err: StringIndexOutOfBoundsException) {
                ""
            }
        }
        initial = firstLetter + lastLetter
        return initial
    }

    fun getOperatorName(context: Context): String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    "${manager.networkOperatorName}"

                } else {
                    ""
                }

    }

    fun String.toCleanJson(): String {
        return this.replace("\"{", "{")
            .replace("}\"", "}")
            .replace("\\", "")
    }

    fun getLast8digit(number: String): String {
        return if (number.length > 8) {
            val last8digit = number.substring(number.length-8, number.length-4)
            val last4digit = number.substring(number.length-4, number.length)
            "$last8digit $last4digit"
        } else {
            number
        }
    }

    fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
        return this?.let {
            val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
            regex.findAll(this).map { it.range.first }.toList()
        } ?: emptyList()
    }

}