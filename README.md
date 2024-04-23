# ElectionsApp

Welcome to ElectionsApp, presents list of candidates with their poll votes sorted by winner, built with MVVM architecture and Jetpack Compose.

## Major Highlights

- **Jetpack Compose** for modern UI
- **MVVM architecture** for a clean and scalable codebase
- **Kotlin** and **Kotlin DSL**
- **Dagger Hilt** for efficient dependency injection.
- **Retrofit** for seamless networking
- **Coroutines** and **Flow** for asynchronous programming
- **StateFlow** for streamlined state management
- **Unit tests** and **UI tests** for robust code coverage
- **Navigation** for smooth transitions between screens
- **Coil** for efficient image loading
- **Pull to refresh** for refreshing the contents

<p align="center">
<img alt="screenshots"  src="https://github.com/genxsolutions/Elections/blob/main/assets/Elections_app_architecture.jpeg">
</p>

## Features Implemented

- Show list of poll results
- show popup to show candidate name - just for the sake of 2nd screen flow
- pull to refresh
- floating button to refresh
- show winner when counting completes 
- hide refresh button when counting completes
- Room database for candidates to avoid loading again
- no network and error screen with retry functionality
- accessibility support
- dark and light mode support
- config change handling
- central dependency management
- modularised concept

## Features planned as backlog
- **Pagination** to efficiently load and display polling results 
- **Offline caching** with a **single source of truth**
- **work manager** for background caching
- **version catalogs** centralised dependency system 
- detailed testing and handling the low memory and restart scenarios 

## Dependency Use

- Jetpack Compose for UI: Modern UI toolkit for building native Android UIs
- Coil for Image Loading: Efficiently loads and caches images
- Retrofit for Networking: A type-safe HTTP client for smooth network requests
- Dagger Hilt for Dependency Injection: Simplifies dependency injection
- Mockito, JUnit, Turbine for Testing: Ensures the reliability of the application

## How to Run the Project

- Clone the Repository:
```
git clone https://github.com/genxsolutions/Elections.git
cd Elections
```
- Build and run the Elections App.


## The Complete Project Folder Structure

```
app:
|── ElectionsApplication.kt
├── common
│   ├── Const.kt
│   ├── NoInternetException.kt
│   ├── dispatcher
│   │   ├── DefaultDispatcherProvider.kt
│   │   └── DispatcherProvider.kt
│   ├── logger
│   │   ├── AppLogger.kt
│   │   └── Logger.kt
│   └── util
│       ├── Util.kt
├── data
│   ├── database
│   │   ├── DatabaseService.kt
│   └── repository
│       └── ElectionsRepository.kt
├── di
│   ├── module
│   │   └── ApplicationModule.kt
│   └── qualifiers.kt
├── ui
│   ├── ElectionsActivity.kt
│   ├── base
│   │   ├── CommonUI.kt
│   │   ├── ScreenDestination.kt
│   │   ├── ScreenNavigation.kt
│   │   └── UIState.kt
│   ├── components
│   │   ├── ResultItem.kt
│   │   └── ResultListLayout.kt
│   ├── screens
│   │   ├── ResultScreen.kt
│   │   ├── PopupScreen.kt
│   ├── theme
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodels
│       ├── ElectionsViewModel.kt

utilities:
│   ├── networkhelper
│   │   ├── NetworkHelper.kt
│   │   └── NetworkHelperImpl.kt

buildSrc:
│   ├── Dependencies.kt
│   ├── Versions.kt 

```
<div style="display: flex; justify-content: space-between;">
</div>
