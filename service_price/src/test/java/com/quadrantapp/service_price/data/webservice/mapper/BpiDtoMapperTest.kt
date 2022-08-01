package com.quadrantapp.service_price.data.webservice.mapper

import com.quadrantapp.service_price.data.webservice.dto.BpiDto
import com.quadrantapp.service_price.domain.entity.BpiEntity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BpiDtoMapperTest {

    // SUT
    private lateinit var bpiDtoMapper: BpiDtoMapper

    @Before
    fun setup() {
        bpiDtoMapper = BpiDtoMapper()
    }

    @Test
    operator fun invoke() {
        val dto = BpiDto(
            "",
            "",
            "",
            "",
            0.0f
        )
        val expectedResult = BpiEntity.DEFAULT
        val actualResult = bpiDtoMapper.invoke(dto)
        assertEquals(expectedResult, actualResult)
    }
}