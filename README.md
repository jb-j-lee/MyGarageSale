<h1>My Garage Sale</h1>

<p>
  <a href="https://kotlinlang.org"><img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-2.0.21-blueviolet.svg?style=flat"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://developer.android.com/studio/releases/gradle-plugin"><img alt="AGP" src="https://img.shields.io/badge/AGP-8.5.2-blue?style=flat"/></a>
</p>

<img src="/screen/ISBN.png" width="30%" height="30%" title="ISBN" alt="ISBN"/>
<img src="/screen/RESULT.png" width="30%" height="30%" title="RESULT" alt="RESULT"/>

"중고책 판매" App은 ISBN을 통한 yes24와 aladin의 중고 판매 가격을 조회하는 Android 어플리케이션입니다.

# Android

- Supports Android Studio Koala
- minsdk 24
- targetSdk 35
- AGP 8.5.2
- Gradle 8.7


# Language

- [Kotlin](https://kotlinlang.org)


# JetPack [AAC(Android Architecture Components)](https://blog.naver.com/dev2jb/223230422126)

- Data Binding - Requires kapt plugin
- LifeCycles
- LiveData
- Navigation
- ViewModel


# UI

- SplashScreen
- MainActivity
- Light and Dark Mode


# Architectural Patterns

- ![MVVM](/screen/MVVM.png)


# Dependency Injection

- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Use Kapt plugin or Ksp plugin


# Asynchronous

- Coroutine
- Flow
- Sealed class


# 3rd Party Libraries

- [Jsoup](https://github.com/jhy/jsoup)
- [Zxing](https://github.com/zxing/zxing)
- [Glide](https://github.com/bumptech/glide) - https://bumptech.github.io/glide/doc/configuration.html


# Build Dependency

- [version catalog](https://developer.android.com/build/migrate-to-catalogs)
- ~~[kotlin-dsl](https://developer.android.com/build/migrate-to-kotlin-dsl)~~

Aladin CI
https://www.aladin.co.kr/aladdin/waladdin.aspx?pn=ci