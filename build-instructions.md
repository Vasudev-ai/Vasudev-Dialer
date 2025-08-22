# Building Vasudev Dialer APK

## Prerequisites
1. Download Android SDK Command Line Tools
2. Set ANDROID_HOME environment variable
3. Install required SDK platforms and build tools

## Build Commands
```bash
# Set SDK path (Windows)
set ANDROID_HOME=C:\Android\Sdk
set PATH=%PATH%;%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools

# Build debug APK
./gradlew assembleDebug

# APK will be generated at:
# app/build/outputs/apk/debug/app-debug.apk
```

## Install on Phone
```bash
# Enable USB debugging on phone first
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Alternative: Use Android Studio
1. Download from https://developer.android.com/studio
2. Import project folder
3. Connect phone via USB
4. Click "Run" button
