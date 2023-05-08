package sk.devprog.firmy.util

import java.text.Normalizer

fun String.removeDiacritics() =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
