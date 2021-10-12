package com.example.finalproject

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var fetche:  tmdbFetchr
    private lateinit var tf:  TrendingFragment

    @Before
    fun setUp() {
        fetche = mock(tmdbFetchr::class.java)
        tf = mock(TrendingFragment::class.java)
    }

    @Test
    fun testFragmentGet() {
        assertEquals(4, 2 + 2)
        assertThat(fetche.getTrending(), `is`(tf) )

    }
}