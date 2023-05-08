package sk.devprog.firmy.util

import junit.framework.TestCase.assertEquals
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `Check diacritics removal extension`() {
        assertEquals(
            "Žriebä učí datľa spievať.".removeDiacritics(),
            "Zrieba uci datla spievat."
        )
    }
}