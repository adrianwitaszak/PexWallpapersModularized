package com.adwi.pexwallpapers.data.database.settings.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.adwi.pexwallpapers.domain.model.Duration
import com.adwi.pexwallpapers.domain.model.Settings
import com.adwi.pexwallpapers.presentation.util.Constants.DEFAULT_QUERY

@Entity(tableName = "settings_table")
data class SettingsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val lastQuery: String = DEFAULT_QUERY,
    val pushNotifications: Boolean = true,
    val newWallpaperSet: Boolean = true,
    val wallpaperRecommendations: Boolean = true,
    val autoChangeWallpaper: Boolean = false,
    val durationSelected: Duration = Duration.MINUTE,
    val durationValue: Float = 5f,
    val downloadOverWiFi: Boolean = false,
    val autoHome: Boolean = true,
    val autoLock: Boolean = false
)

fun Settings.toEntity() =
    SettingsEntity(
        id = id,
        lastQuery = lastQuery,
        pushNotifications = pushNotifications,
        newWallpaperSet = newWallpaperSet,
        wallpaperRecommendations = wallpaperRecommendations,
        autoChangeWallpaper = autoChangeWallpaper,
        durationSelected = durationSelected,
        durationValue = durationValue,
        autoHome = autoHome,
        autoLock = autoLock,
        downloadOverWiFi = downloadOverWiFi
    )

fun SettingsEntity.toDomain() =
    Settings(
        id = id,
        lastQuery = lastQuery,
        pushNotifications = pushNotifications,
        newWallpaperSet = newWallpaperSet,
        wallpaperRecommendations = wallpaperRecommendations,
        autoChangeWallpaper = autoChangeWallpaper,
        durationSelected = durationSelected,
        durationValue = durationValue,
        autoHome = autoHome,
        autoLock = autoLock,
        downloadOverWiFi = downloadOverWiFi
    )

class Converters {

    @TypeConverter
    fun toDuration(value: Int) = enumValues<Duration>()[value]

    @TypeConverter
    fun fromDuration(value: Duration) = value.ordinal
}