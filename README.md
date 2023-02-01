
# WhaleUtils

WhaleUtils provide a bundle of useful utility codes.

## Get Started

You are required to follow the instruction if you want to use this library in your project.

### Add JitPack Repository

Add the JitPack repository to your root `build.gradle` or `build.gradle (Project: xxx)` inside Gradle Scripts.

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

However, if you’re using Android Studio Bumblebee, the method describes above is not working in this version. Instead, you need to add JitPack repository in your `settings.gradle`.

```gradle
pluginManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Add Dependency

Add the dependency in your app `build.gradle` or `build.gradle (Module: xxx.app)` inside Gradle Scripts.

The `$version` can be found in Releases.-

```gradle
dependencies {
    implementation 'com.github.BlueWhaleYT:WhaleUtils:$version'
}
```