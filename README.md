# Git Trending Repos (XML Views Implementation)

A demo app displaying the trending git repositories, sorted by the number of stars descending.

**Made for the comparison study versus the** [Jetpack Compose implementation](https://github.com/oblakr24/GitTrendingCompose).

The app will load pages automatically once the user scrolls far enough (close enough to the last item) and supports offline mode and a togglable dark mode setting.

## Screenshots

<p align="center">
  <img src="https://github-production-user-asset-6210df.s3.amazonaws.com/32245831/246509676-98a682c2-5598-45ac-a712-19819afa7c57.png" width="270" height="570">
  <img src="https://github-production-user-asset-6210df.s3.amazonaws.com/32245831/246509697-038f5ad4-3d8a-444d-a702-8d6bd2f6283b.png" width="270" height="570">
</p>

## Features

1. **Listing trending repositories:** Trending repositories are listed in an infinitely scrollable list, with pages loaded dynamically. Scroll to-refresh to refresh the data.

2. **Offline mode:** The repositories are stored in a database for offline use and to make fewer requests when the data is not stale.

3. **Dark mode:** Toggle to override system settings.

## Technologies

**This app is made using:**

- A mix of MVVM/MVI for reactive state construction with Coroutines/Flows
- Room for data and preferences persistence and offline support

**Stack:**
- MVVM architecture (mix of MVVM and MVI)
- Coroutines/Flows on the app module for interacting with RxJava3 observables from the BLE module
- [Hilt](https://dagger.dev/hilt/) for dependency injection
- [Coil](https://coil-kt.github.io/coil/) for image loading
- [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization) for serialization and deserialization of models into and from files
- [Retrofit](https://github.com/square/retrofit) for network requests
- [Room](https://developer.android.com/training/data-storage/room) for data persistence
- [Shimmer](https://facebook.github.io/shimmer-android/) for loading item shimmer support
- [Lottie](https://github.com/airbnb/lottie-android/) for Composable JSON animations
