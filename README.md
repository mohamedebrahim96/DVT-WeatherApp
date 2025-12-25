<h1 align="center">DVT Weather App</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/mohamedebrahim96/DVT-WeatherApp/actions"><img alt="Build Status" src="https://img.shields.io/github/actions/workflow/status/mohamedebrahim96/DVT-WeatherApp/android.yml?branch=main"/></a> <br>
  <a href="https://kotlinlang.org/docs/releases.html"><img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.9.20-orange.svg"/></a>
  <a href="https://developer.android.com/jetpack/compose"><img alt="Compose" src="https://img.shields.io/badge/Jetpack%20Compose-1.5.4-blue.svg"/></a>
  <a href="https://github.com/mohamedebrahim96"><img alt="Profile" src="https://img.shields.io/badge/Github-mohamedebrahim96-black.svg"/></a>
</p>

<p align="center">  
🌤️ DVT Weather App demonstrates modern Android development with Jetpack Compose, Hilt, Coroutines, Flow, and Clean Architecture.
</p>

> [!NOTE]
> This project is a submission for the Android Mobile Assessment. It adheres to strict guidelines: **No AI generation for code**, usage of native Custom UI Widgets, and modern architectural patterns.

<p align="center">
<img src="/previews/screenshot.png" alt="App Screenshot" width="300"/>
</p>

## ⬇️ Download
Go to the [Releases](https://github.com/mohamedebrahim96/DVT-WeatherApp/releases) to download the latest APK.

## 🛠️ Tech Stack & Open-source Libraries
This project utilizes a modern Android tech stack focused on scalability, testability, and performance.

- **Minimum SDK level**: 24
- **Language**: [Kotlin](https://kotlinlang.org/) (100% Native)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern toolkit for building native UI.
- **Asynchronous**: [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/).
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/) - Standard for Android DI.
- **Architecture**: Clean Architecture + MVI/MVVM (Presentation, Domain, Data).
- **Network**: 
  - [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - For constructing REST APIs.
  - [Moshi](https://github.com/square/moshi/) - JSON serialization/deserialization.
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) - Image loading backed by Kotlin Coroutines, optimized for Compose.
- **Local Storage**: [Room](https://developer.android.com/training/data-storage/room) - SQLite abstraction for caching weather data (Offline-first approach).
- **Location**: [Play Services Location](https://developers.google.com/android/guides/setup) - For fetching current user coordinates.
- **Build Logic**: Kotlin DSL (`build.gradle.kts`) with Convention Plugins.

## 🏛️ Architecture
**DVT Weather App** is based on **Clean Architecture** and the **Repository Pattern**, ensuring separation of concerns and ease of testing.

![architecture](figure/figure0.png)

The overall architecture is composed of three main layers: **Presentation**, **Domain**, and **Data**.

### Architecture Overview

![architecture](figure/figure1.png)

- **Unidirectional Data Flow (UDF)**: The UI layer emits user intent events to the ViewModel, which processes them and exposes state via `StateFlow`.
- **Domain Layer**: Contains pure business logic (Use Cases) and is independent of the Android Framework.
- **Data Layer**: Manages data sources (Network/Database) and exposes data to the domain layer.

### UI Layer (Jetpack Compose)

![architecture](figure/figure2.png)

The UI layer uses **Jetpack Compose** for a declarative UI approach. 
- **State Management**: The UI observes `StateFlow` from the ViewModel.
- **Custom Widgets**: Backgrounds and weather animations are drawn using native Canvas and Compose modifiers, avoiding heavy third-party UI libraries.

### Data Layer & Offline Support

![architecture](figure/figure3.png)

The repository implements the **Single Source of Truth (SSOT)** principle.
1. Data is fetched from the Remote Data Source (Weather API).
2. Data is cached in the Local Data Source (Room Database).
3. The UI observes the database, ensuring the app works **offline** with the last known data.

## 📦 Modularization

![architecture](figure/figure4.png)

The project is modularized to support scalability and parallel builds:

- **:app**: The main entry point, connects features.
- **:core**: Common utilities, extensions, and UI components (Design System).
- **:feature**: Contains specific feature modules (e.g., `:feature:weather`).
- **:build-logic**: Custom Gradle convention plugins for consistent build configurations across modules.

## 🌦️ Open API

The app uses the OpenWeatherMap API to fetch current weather conditions and forecasts.

To build this project locally, you must provide your own API Key:
1. Get a key from [OpenWeatherMap](https://openweathermap.org/api).
2. Add it to your `local.properties` file:
   ```properties
   WEATHER_API_KEY="your_api_key_here"
