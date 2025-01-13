<p align="center"><img src="/preview/header.png"></p>

StepsCircularProgressBar
=================

<img src="/preview/preview.gif" alt="sample" title="sample" width="300" height="480" align="right" vspace="24" />

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![](https://jitpack.io/v/aliab/circular-music-progressbar.svg)](https://jitpack.io/#n-droidev/StepsCircularProgressBar)
![Apache 2.0](https://img.shields.io/badge/License-Apache%202-brightgreen)

This is an Android project allowing CircularProgressBar to be segmented as steps. It consists of parts as separated arcs. 

USAGE
-----

To use StepsCircularProgressBar you must add it as a dependency in your Gradle build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle (or settings.gradle) at the end of repositories:

```groovy
// If you use Groovy for Gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
```groovy
// If you use Kotlin DSL for Gradle
allprojects {
	repositories {
		...
		maven { setUrl("https://jitpack.io") }
	}
}
```

Step 2. Add the dependency
```groovy
// Groovy
implementation 'com.github.n-droidev:StepsCircularProgressBar:1.1'
```
```kotlin
// Kotlin DSL
implementation("com.github.n-droidev:StepsCircularProgressBar:1.1")
```


XML
-----

```xml
<n_droidev.progressbar.StepsCircularProgressBar
      android:id="@+id/stepsProgress"
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      android:layout_margin="30dp"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintEnd_toEndOf="parent"
      app:layout_constraintStart_toStartOf="parent"
      app:layout_constraintTop_toTopOf="parent"
      app:scp_doneStepColor="#AA354F"
      app:scp_gapBetweenSteps="10dp"
      app:scp_stepCornerType="Round"
      app:scp_stepExtraRotationAngle="-2.5"
      app:scp_stepThickness="5dp"
      app:scp_stepsCount="5"
      app:scp_undoneStepColor="#88AA35FF" />
```

KOTLIN
-----

```kotlin
      val colors = intArrayOf(
          Color.GREEN,
          Color.MAGENTA,
          Color.YELLOW,
          Color.BLUE
      )

      binding.stepsProgress.setData(9, dynamicStepColors = colors)
```

LICENCE
-----

StepsCircularProgressBar by [Samir Alakbarov] is licensed under a [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
