package com.dvt.greensys.weather.app.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import com.dvt.greensys.weather.app.core.designsystem.icon.WeatherIcons
import com.dvt.greensys.weather.app.core.designsystem.theme.Orange
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme

data class ActionIconSet(
    val icon: ImageVector,
    val contentDescription: String?,
    val menu: List<MenuSet>? = null,
    val onClick: () -> Unit = {},
)

data class MenuSet(
    @StringRes val textRes: Int,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(
    @StringRes titleRes: Int?,
    modifier: Modifier = Modifier,
    navigationIcon: ActionIconSet? = null,
    actionIcon: ActionIconSet? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Orange,
        navigationIconContentColor = Color.White,
        titleContentColor = Color.White,
        actionIconContentColor = Color.White,
    ),
) {
    val expandedDropdownMenu = remember { mutableStateOf(value = false) }

    TopAppBar(
        title = { titleRes?.let { id -> Text(text = stringResource(id = id)) } },
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = navigationIcon.onClick) {
                    Icon(
                        imageVector = navigationIcon.icon,
                        contentDescription = navigationIcon.contentDescription,
                    )
                }
            }
        },
        actions = {
            actionIcon?.let {
                TooltipBox(
                    positionProvider = MicrowaveTooltipDefaults.rememberPlainTooltipBottomPositionProvider(),
                    tooltip = {
                        actionIcon.contentDescription?.let {
                            PlainTooltip { Text(text = it) }
                        }
                    },
                    state = rememberTooltipState(),
                    enableUserInput = actionIcon.contentDescription != null,
                ) {
                    IconButton(onClick = { expandedDropdownMenu.value = true }) {
                        Icon(
                            imageVector = actionIcon.icon,
                            contentDescription = actionIcon.contentDescription,
                        )
                    }
                    actionIcon.menu?.let {
                        DropdownMenu(
                            expanded = expandedDropdownMenu.value,
                            onDismissRequest = { expandedDropdownMenu.value = false },
                        ) {
                            actionIcon.menu.forEach {
                                DropdownMenuItem(
                                    text = { Text(stringResource(id = it.textRes)) },
                                    onClick = {
                                        it.onClick()
                                        expandedDropdownMenu.value = false
                                    },
                                )
                            }
                        }
                    }
                }
            }
        },
        colors = colors,
        modifier = modifier,
    )
}

internal object MicrowaveTooltipDefaults {
    /**　[TooltipDefaults.rememberPlainTooltipPositionProvider] を参考にポジションを下に変更 */
    @Composable
    fun rememberPlainTooltipBottomPositionProvider(
        spacingBetweenTooltipAndAnchor: Dp = SpacingBetweenTooltipAndAnchor,
    ): PopupPositionProvider {
        val tooltipAnchorSpacing = with(LocalDensity.current) {
            spacingBetweenTooltipAndAnchor.roundToPx()
        }
        return remember(tooltipAnchorSpacing) {
            object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize,
                ): IntOffset {
                    val x = anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2
                    val y = anchorBounds.bottom + tooltipAnchorSpacing
                    return IntOffset(x, y)
                }
            }
        }
    }
}

internal val SpacingBetweenTooltipAndAnchor = 4.dp

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun WeatherTopAppBarPreview() {
    WeatherTheme {
        WeatherTopAppBar(
            titleRes = android.R.string.untitled,
            navigationIcon = ActionIconSet(
                icon = WeatherIcons.ArrowBack,
                contentDescription = "Navigation icon",
            ),
            actionIcon = ActionIconSet(
                icon = WeatherIcons.More,
                contentDescription = "Action icon",
            ),
        )
    }
}
