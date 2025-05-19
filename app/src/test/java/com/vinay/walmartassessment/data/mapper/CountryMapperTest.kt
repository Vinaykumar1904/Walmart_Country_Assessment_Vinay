package com.vinay.walmartassessment.data.mapper

import com.vinay.walmartassessment.data.dto.CountryResponse
import com.vinay.walmartassessment.data.dto.Currency
import com.vinay.walmartassessment.data.dto.Language
import com.vinay.walmartassessment.domain.data.Country
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CountryMapperTest {

    @Test
    fun `mapEntityToDomain is invoked with CountryResponse then CountryDetails is returned`() {
        val res = CountryMapper().mapEntityToDomain(
            CountryResponse(
                "New Delhi",
                "IN",
                mockk<Currency>(),
                "",
                "",
                mockk<Language>(),
                "India",
                "AS"
            )
        )
        assertEquals("IN", res.countryCode)
    }

    @Test
    fun `mapEntityToDomainList is invoked with list of CountryResponse then list of CountryDetails is returned`() {
        val res = listOf(
            CountryResponse(
                "New Delhi",
                "IN",
                mockk<Currency>(),
                "",
                "",
                mockk<Language>(),
                "India",
                "AS"
            )
        )
            .map { CountryMapper().mapEntityToDomain(it) }

        assertTrue(res.isNotEmpty())
        assertTrue(res.all { it is Country })
    }

    @Test
    fun `mapEntityToDomainList is invoked with empty list then empty list is returned`() {
        val res = emptyList<CountryResponse>()
            .map { CountryMapper().mapEntityToDomain(it) }

        assertTrue(res.isEmpty())
    }
}