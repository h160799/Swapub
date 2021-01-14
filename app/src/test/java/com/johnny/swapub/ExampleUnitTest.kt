package com.johnny.swapub

import com.johnny.swapub.util.TimeUtil
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example com.johnny.swapub.data.local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun bindDisplayFormatTime() {
        val first = 1608693426918
        val second = 2
        val simpleDateFormat = "2020-12-23 11:17"

        //assert
        assertEquals(simpleDateFormat,TimeUtil.StampToDate(first))
        assertEquals(simpleDateFormat, second)
    }
}


