# Dog Breed Lookup

Dog Breed Lookup is an Android application that allows users to identify dog breeds from images. The app presents a quiz where users can select the correct breed from multiple options.
It fetches the data from the [Dog CEO API](https://dog.ceo/dog-api/).

## Features

- Fetch random dog images and breed options.
- Quiz functionality to select the correct breed.
- Display success or error messages based on user selection.
- Animated visibility for correct and incorrect answers.

## Technologies Used

- **Kotlin**: Programming language for Android development.
- **Jetpack Compose**: Modern toolkit for building native UI.
- **ViewModel**: Manage UI-related data in a lifecycle-conscious way.
- **StateFlow**: Handle state in a reactive way.
- **Mockito**: Mocking framework for unit tests.
- **JUnit**: Testing framework for unit tests.
- **Espresso**: Testing framework for UI tests.
- **Coil**: Image loading library for Android.

## Project Structure

- `src/main/java/com/arun/dogbreedlookup/`: Main source code.
  - `data/repository/`: Contains the repository classes.
  - `ui/compose/`: Contains the Jetpack Compose UI components.
  - `ui/viewmodel/`: Contains the ViewModel and related classes.
- `src/test/java/com/arun/dogbreedlookup/`: Unit tests.
- `src/androidTest/java/com/arun/dogbreedlookup/`: Android UI tests.

## Getting Started

### Prerequisites

- Android Studio
- Kotlin 1.9.0 or later
- Gradle 8.1.1 or later

### Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/arunshankar87/DogBreedLookUp.git
    cd DogBreedLookUp
    ```

2. **Open the project in Android Studio**:
    - Open Android Studio.
    - Select `Open an existing project`.
    - Navigate to the cloned repository and select it.

3. **Sync the project with Gradle**:
    - Click on `File` > `Sync Project with Gradle Files`.

### Running the App

1. **Connect an Android device** or start an emulator.
2. **Run the app**:
    - Click on the `Run` button in Android Studio.
    - Select the target device.

## Running Tests

### Unit Tests

To run unit tests, use the following command:

```sh
./gradlew test
```

### Android Tests

To run Android UI tests, use the following command:

```sh
./gradlew connectedAndroidTest
```

## Lint Checks

To run lint checks, use the following command:

```sh
./gradlew lint
```

## Dependencies

Add the following dependencies to your `gradle/libs.versions.toml` file:

```toml
[versions]
kotlin = "1.9.0"
agp = "8.1.1"
mockito = "4.0.0"
junit = "4.13.2"
espresso = "3.4.0"

[libraries]
kotlin-stdlib = { group = "org.jetbrains.kotlin", name = "kotlin-stdlib", version.ref = "kotlin" }
mockito-core = { group = "org.mockito", name = "mockito-core", version.ref = "mockito" }
mockito-inline = { group = "org.mockito", name = "mockito-inline", version.ref = "mockito" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espresso" }
```

Update your `build.gradle.kts` file to include these dependencies:

```kotlin
dependencies {
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    androidTestImplementation(libs.espresso.core)
}
```

## License

This project is licensed under the MIT License - see the `LICENSE` file for details.
