package com.adwi.repository.wallpaper

import androidx.paging.ExperimentalPagingApi
import com.adwi.datasource.CoroutineAndroidTestRule
import com.adwi.datasource.local.WallpaperDatabase
import com.adwi.datasource.local.domain.toEntity
import com.adwi.datasource.network.PexService
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class WallpaperRepositoryImplTest {

    @get:Rule(order = 1)
    val coroutineScope = CoroutineAndroidTestRule()

    private val database: WallpaperDatabase = mockk()
    private val service: PexService = mockk()

    private lateinit var wallpaperRepository: WallpaperRepository

    private val firstWallpaper = WallpapersMock.first
    private val secondWallpaper = WallpapersMock.second
    private val thirdWallpaper = WallpapersMock.third

    private val firstCurated = CuratedMock.first

    @Before
    fun setup() {
        wallpaperRepository = WallpaperRepositoryImpl(database, service)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun getCuratedTest_returnsCurated() {
        coroutineScope.dispatcher.runBlockingTest {

            database.wallpaperDao()
                .insertWallpapers(
                    listOf(
                        firstWallpaper.toEntity(),
                        secondWallpaper.toEntity(),
                        thirdWallpaper.toEntity()
                    )
                )

            database.wallpaperDao().insertCuratedWallpapers(listOf(firstCurated))

            val v = wallpaperRepository.getCurated(true, {}, {}).first()

            val data = v.data

            assertThat(data).hasSize(1)
        }
    }
}