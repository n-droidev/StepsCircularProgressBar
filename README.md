StepsCircularProgressBar
=================


[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![](https://jitpack.io/v/aliab/circular-music-progressbar.svg)](https://jitpack.io/#n-droidev/StepsCircularProgressBar)
![Apache 2.0](https://img.shields.io/badge/License-Apache%202-brightgreen)

This is an Android project allowing CircularProgressBar to be segmented as steps. It consists of parts as separated arcs. </br>

![Two colored progressbar](/art/two_color_round_normal.png)
![Colorful progressbar 1](/art/colorful_butt_thick.png)
![Colorful progressbar 2](/art/colorful_round_thin.png)
</br>

Usage
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

Kotlin
-----

```kotlin
        val colors = intArrayOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.LTGRAY,
            Color.BLACK,
        )

        with(binding.stepsProgress) {
            setData(5, dynamicStepColors = colors)
            setTotalStepsCount(7)
            setDoneStepsCount(4)
            setGapBetweenEachStep(30f.dp)
            setStepDoneColor(Color.BLUE)
            setStepUndoneColor(Color.BLACK)
            setStepThickness(20f.dp)
            setStartAngleExtra(20f)
            setStepStrokeType(Paint.Cap.ROUND)
        }
```

Licence
-----

StepsCircularProgressBar by [Samir Alakbarov] is licensed under a [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
