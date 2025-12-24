package com.dvt.greensys.weather.app.feature.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherLoading
import com.dvt.greensys.weather.app.core.designsystem.component.WeatherTopAppBar
import com.dvt.greensys.weather.app.core.model.Coord
import com.dvt.greensys.weather.app.core.model.ForecastItem
import com.dvt.greensys.weather.app.core.ui.component.ErrorDialog
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

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

    // Permission State
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )

    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Logic to handle location fetching
    val fetchLocationAndWeather = {
        getLastLocation(
            fusedLocationProviderClient = fusedLocationProviderClient,
            onSuccess = viewModel::getForecastData,
            onFailure = { it?.let(viewModel::getLocationError) },
        )
    }

    // Effect: Automatically fetch data if permissions are already granted
    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
        if (locationPermissionsState.allPermissionsGranted) {
            fetchLocationAndWeather()
        }
    }

    HomeScreenContent(
        uiState = uiState,
        locationPermissionsState = locationPermissionsState,
        snackbarHostState = snackbarHostState,
        onLocationRequest = {
            if (locationPermissionsState.allPermissionsGranted) {
                fetchLocationAndWeather()
            } else {
                locationPermissionsState.launchMultiplePermissionRequest()
            }
        },
        onErrorDialogDismiss = viewModel::clearForecastData,
        modifier = modifier,
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    locationPermissionsState: MultiplePermissionsState,
    snackbarHostState: SnackbarHostState,
    onLocationRequest: () -> Unit,
    onErrorDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Determine Background Resource
    val backgroundRes = when (uiState) {
        is HomeUiState.Success -> {
            when (uiState.currentWeatherCondition) {
                WeatherCondition.CLOUDY -> R.drawable.cloudy_bg
                WeatherCondition.RAINY -> R.drawable.rainy_bg
                else -> R.drawable.sunny_bg
            }
        }

        else -> R.drawable.sunny_bg // Default
    }

    Box(modifier = modifier.fillMaxSize()) {
        // 1. Background Layer
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Content Layer
        Scaffold(
            topBar = {
                WeatherTopAppBar(
                    titleRes = R.string.home_title,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    )
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (uiState) {
                    is HomeUiState.Init -> {
                        // Show a placeholder or button to trigger load if not auto-started
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = onLocationRequest) {
                                Text("Get Weather for Current Location")
                            }
                        }
                    }

                    is HomeUiState.Loading -> {
                        WeatherLoading(hasScrim = false)
                    }

                    is HomeUiState.Success -> {
                        // Display the 5-day Forecast List
                        ForecastList(
                            // Assuming your ForecastData has a 'list' field.
                            // You might need to filter this list to get 1 item per day if the API returns 3-hour steps.
                            forecastItems = uiState.forecastData.list
                        )
                    }

                    is HomeUiState.NetworkError -> {
                        ErrorDialog(
                            onDismissRequest = onErrorDialogDismiss,
                            title = stringResource(id = R.string.home_error_loading_title),
                            text = stringResource(id = R.string.home_error_loading_message),
                        )
                    }

                    is HomeUiState.LocationError -> {
                        ErrorDialog(
                            onDismissRequest = onErrorDialogDismiss,
                            title = stringResource(id = R.string.home_location_error_title),
                            text = stringResource(id = R.string.home_location_error_message),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastList(forecastItems: List<ForecastItem>) {
    // ^ Replace ForecastItem with your actual inner data class

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecastItems) { item ->
            ForecastDayCard(item = item)
        }
    }
}

@Composable
private fun ForecastDayCard(item: ForecastItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp), // Adjusted height
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
                verticalArrangement = Arrangement.Center
            ) {
                // Use a proper date formatter here
                Text(
                    text = item.dt_txt ?: "Unknown Day",
                    color = Color(0xFF1B1C1E),
                    style = MaterialTheme.typography.titleMedium
                )

                // Weather Icon
                // You should map item.weather[0].icon to a resource or URL
                Icon(
                    painter = painterResource(id = R.drawable.ic_sun_yellow),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(
                text = "${item.main.temp}°",
                color = Color(0xFF1B1C1E),
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.End
            )
        }
    }
}

@SuppressLint("MissingPermission")
private fun getLastLocation(
    fusedLocationProviderClient: FusedLocationProviderClient,
    onSuccess: (Coord) -> Unit,
    onFailure: (Exception?) -> Unit,
) {
    fusedLocationProviderClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                onSuccess(Coord(lat = location.latitude, lon = location.longitude))
            } else {
                onFailure(Exception("Location is null"))
            }
        }
        .addOnFailureListener { onFailure(it) }
}