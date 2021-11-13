package com.adwi.datasource.network

import com.adwi.core.util.Constants.COLORS_PAGE_SIZE
import com.adwi.core.util.Constants.CURATED_PAGE_SIZE
import com.adwi.core.util.Constants.DAILY_PAGE_SIZE
import com.adwi.core.util.Constants.DEFAULT_DAILY_CATEGORY
import com.adwi.core.util.Constants.SEARCH_PAGE_SIZE
import com.adwi.datasource.network.domain.WallpaperResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PexService {

    @GET("v1/search")
    suspend fun getDailyWallpapers(
        @Query("query") categoryName: String = DEFAULT_DAILY_CATEGORY,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = DAILY_PAGE_SIZE
    ): WallpaperResponse

    @GET("v1/search")
    suspend fun getColor(
        @Query("query") colorName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = COLORS_PAGE_SIZE
    ): WallpaperResponse

    @GET("v1/curated")
    suspend fun getCuratedWallpapers(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = CURATED_PAGE_SIZE
    ): WallpaperResponse

    @GET("v1/search")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = SEARCH_PAGE_SIZE
    ): WallpaperResponse
}