package com.adwi.feature_settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ReadMore
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.CategoryTitle
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.feature_settings.R
import com.adwi.feature_settings.domain.privacy.PrivacyItem
import com.adwi.feature_settings.domain.privacy.privacyCategoryList

@ExperimentalMaterialApi
@Composable
fun PrivacyCategoryPanel(
    modifier: Modifier = Modifier,
    name: String,
    privacyItems: List<PrivacyItem>,
    shape: Shape = MaterialTheme.shapes.large
) {
    Column {
        CategoryTitle(name = name)
        Card(
            shape = shape,
            modifier = modifier
                .fillMaxSize()
                .neumorphicShadow()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                items(items = privacyItems, itemContent = { item ->
                    PrivacyCategoryItem(
                        title = item.title,
                        description = item.description
                    )
                })
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun PrivacyCategoryItem(
    title: String,
    description: String
) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = paddingValues / 2),
            ) {
                Text(
                    text = title,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Outlined.ReadMore,
                    contentDescription = stringResource(id = R.string.show_more)
                )
            }
            if (expanded) {
                Column {
                    Text(text = description)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "PrivacyScreen Light")
@Composable
private fun PrivacyScreenPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            val category = privacyCategoryList[0]
            PrivacyCategoryPanel(name = category.name, privacyItems = category.items)
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, name = "PrivacyScreen Dark")
@Composable
private fun PrivacyScreenPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            val category = privacyCategoryList[0]
            PrivacyCategoryPanel(name = category.name, privacyItems = category.items)
        }
    }
}