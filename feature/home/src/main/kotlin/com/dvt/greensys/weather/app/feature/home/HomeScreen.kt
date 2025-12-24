package com.dvt.greensys.weather.app.feature.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherButton
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherLoading
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherSnackbar
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherTopAppBar
import com.dvt.greensys.weather.app.core.designsystem.icon.WeatherIcons
import com.dvt.greensys.weather.app.core.designsystem.theme.WeatherTheme
import com.dvt.greensys.weather.app.core.model.Coord
import com.dvt.greensys.weather.app.core.ui.component.DetailContent
import com.dvt.greensys.weather.app.core.ui.component.ErrorDialog
import com.dvt.greensys.weather.app.feature.home.preview.HomePreviewParameterProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeScreen(
    navigateToSelect: () -> Unit,
    navigateToOss: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )
    val fusedLocationProviderClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val coroutineScope = rememberCoroutineScope()

    HomeScreen(
        uiState = uiState,
        locationPermissionsState = locationPermissionsState,
        onOssClick = navigateToOss,
        onLocationClick = { snackbarHostState ->
            val anyPermissionsGranted =
                locationPermissionsState.revokedPermissions.size < locationPermissionsState.permissions.size

            if (anyPermissionsGranted) {
                getLastLocation(
                    fusedLocationProviderClient = fusedLocationProviderClient,
                    onSuccess = viewModel::getForecastData,
                    onFailure = { it?.let(viewModel::getLocationError) },
                )
            } else {
                if (!locationPermissionsState.shouldShowRationale) {
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = context.getString(R.string.home_snackbar_message),
                        actionLabel = context.getString(R.string.home_snackbar_action_label),
                        duration = SnackbarDuration.Short,
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> Unit
                        SnackbarResult.ActionPerformed -> {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                val uri = Uri.fromParts("package", context.packageName, null)
                                setData(uri)
                            }
                            context.startActivity(intent)
                        }
                    }
                }
            }
        },
        onBottomSheetDismissRequest = viewModel::clearForecastData,
        onErrorDialogOkClick = viewModel::clearForecastData,
        modifier = modifier,
    )
}

private data class ForecastDayUi(
    val dayName: String,
    val temperature: String,
)

private val forecastDays = listOf(
    ForecastDayUi("Monday", "22°"),
    ForecastDayUi("Tuesday", "25°"),
    ForecastDayUi("Wednesday", "23°"),
    ForecastDayUi("Thursday", "29°"),
    ForecastDayUi("Friday", "27°"),
)

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    locationPermissionsState: MultiplePermissionsState,
    onOssClick: () -> Unit,
    onLocationClick: (snackbarHostState: SnackbarHostState) -> Unit,
    onBottomSheetDismissRequest: () -> Unit,
    onErrorDialogOkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomSheetPaddingValues = WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Top).asPaddingValues()
    val snackbarHostState = remember { SnackbarHostState() }

    LocationPermissionEffect(locationPermissionsState = locationPermissionsState)

    Box(modifier = modifier.fillMaxSize()) {
        // BACKGROUND IMAGE - Takes the whole screen including under the toolbar
        Image(
            painter = painterResource(id = R.drawable.sunny_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = {
                WeatherTopAppBar(
                    titleRes = R.string.home_title,
                    // Making the Toolbar background transparent
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = Color.White // Ensuring title is readable
                    )
                )
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { WeatherSnackbar(snackbarData = it) },
                )
            },
            containerColor = Color.Transparent, // Ensures Scaffold doesn't block the image
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherButton(
                    onClick = { onLocationClick(snackbarHostState) },
                    text = { Text(text = stringResource(id = R.string.home_to_location)) },
                    leadingIcon = { Icon(imageVector = WeatherIcons.Gps, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().alpha(0f),
                )

                forecastDays.forEach { day ->
                    ForecastDayCard(day = day)
                }
            }
        }
    }

    when (uiState) {
        is HomeUiState.Init -> Unit
        is HomeUiState.Loading -> WeatherLoading(
            hasScrim = true,
            modifier = Modifier.fillMaxSize(),
        )
        is HomeUiState.Success -> ModalBottomSheet(
            onDismissRequest = onBottomSheetDismissRequest,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(paddingValues = bottomSheetPaddingValues),
        ) {
            DetailContent(
                forecastData = uiState.forecastData,
                modifier = Modifier.fillMaxSize(),
            )
        }
        is HomeUiState.NetworkError -> ErrorDialog(
            onDismissRequest = onErrorDialogOkClick,
            title = stringResource(id = R.string.home_error_loading_title),
            text = stringResource(id = R.string.home_error_loading_message),
        )
        is HomeUiState.LocationError -> ErrorDialog(
            onDismissRequest = onErrorDialogOkClick,
            title = stringResource(id = R.string.home_location_error_title),
            text = stringResource(id = R.string.home_location_error_message),
        )
    }
}

@Composable
private fun ForecastDayCard(day: ForecastDayUi) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = day.dayName,
                    color = Color(0xFF1B1C1E),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp)) // Reduced spacer for better balance

                // Increased size from 32.dp to 48.dp
                Box(
                    modifier = Modifier.size(48.dp)
                ) {
                    val customPainter = painterResource(id = R.drawable.ic_sun_yellow)

                    Icon(
                        painter = customPainter,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Text(
                text = day.temperature,
                color = Color(0xFF1B1C1E),
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.End
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun LocationPermissionEffect(locationPermissionsState: MultiplePermissionsState) {
    if (LocalInspectionMode.current) return
    val allPermissionsRevoked = locationPermissionsState.permissions.size == locationPermissionsState.revokedPermissions.size
    LaunchedEffect(locationPermissionsState) {
        if (allPermissionsRevoked) {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }
}

private fun getLastLocation(
    fusedLocationProviderClient: FusedLocationProviderClient,
    onSuccess: (Coord) -> Unit,
    onFailure: (Exception?) -> Unit,
) {
    fusedLocationProviderClient.lastLocation
        .addOnSuccessListener { location ->
            try {
                onSuccess(Coord(lat = location.latitude, lon = location.longitude))
            } catch (e: Exception) {
                onFailure(e)
            }
        }
        .addOnFailureListener { exception -> onFailure(exception) }
        .addOnCanceledListener { onFailure(null) }
}

@OptIn(ExperimentalPermissionsApi::class)
@PreviewLightDark
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomePreviewParameterProvider::class)
    uiState: HomeUiState,
) {
    WeatherTheme {
        HomeScreen(
            uiState = uiState,
            locationPermissionsState = DummyLocationPermissionsState,
            onOssClick = {},
            onLocationClick = {},
            onBottomSheetDismissRequest = {},
            onErrorDialogOkClick = {},
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private val DummyLocationPermissionsState = object : MultiplePermissionsState {
    override val allPermissionsGranted: Boolean = true
    override val permissions: List<PermissionState> = emptyList()
    override val revokedPermissions: List<PermissionState> = emptyList()
    override val shouldShowRationale: Boolean = false
    override fun launchMultiplePermissionRequest() = Unit
}