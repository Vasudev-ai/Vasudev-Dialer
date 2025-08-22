# Vasudev Dialer

A cyberpunk-themed Android dialer app with neon green aesthetics and terminal-style UI, built with Kotlin and Jetpack Compose.

## Features

### 🎨 UI & Theme
- **Cyberpunk Design**: Black background with neon green glowing text
- **Monospace Font**: Terminal-style typography throughout the app
- **Theme Toggle**: Switch between Neon (cyberpunk) and Classic themes
- **Glowing Animations**: Button press effects with neon glow and scale animations

### 📱 Core Functionality
- **Dialer Screen**: 3x4 grid dialpad with large number display
- **Call Logs**: View recent calls from system call log with type indicators
- **Settings**: Theme toggle, country code configuration, and app info
- **Permissions**: Runtime permission handling for phone, call log, and contacts access

### 🚀 Special Features
- **Boot Splash**: Hacker-style initialization sequence
- **Long Press 0**: Automatically inserts +91 (India country code)
- **Call Integration**: Direct calling using Android's native call intent
- **Contact Names**: Shows contact names when available in call logs

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Navigation**: Jetpack Navigation Compose
- **Permissions**: Accompanist Permissions library
- **State Management**: Compose State with remember/mutableStateOf
- **Architecture**: Clean modular structure with separate UI, data, and component layers

## Project Structure

```
app/src/main/java/com/vasudev/dialer/
├── MainActivity.kt                 # Main activity with Compose setup
├── ui/
│   ├── theme/                     # Custom neon theme and typography
│   ├── navigation/                # Bottom navigation setup
│   ├── screens/                   # Main app screens
│   │   ├── DialerScreen.kt       # Dialpad with calling functionality
│   │   ├── CallLogsScreen.kt     # Recent calls display
│   │   └── SettingsScreen.kt     # App settings and preferences
│   ├── components/               # Reusable UI components
│   │   ├── GlowingButton.kt      # Animated neon glow button
│   │   └── PermissionDialog.kt   # Permission request dialog
│   └── SplashActivity.kt         # Boot splash screen
└── data/
    └── CallLogEntry.kt           # Call log data model
```

## Permissions Required

- `CALL_PHONE`: Make phone calls
- `READ_CALL_LOG`: Access call history
- `READ_CONTACTS`: Show contact names
- `READ_PHONE_STATE`: Phone state access

## Installation

1. Open the project in Android Studio
2. Sync Gradle files
3. Build and run on an Android device (API 24+)

## App Identity

- **Package**: com.vasudev.dialer
- **Version**: 1.0
- **Developer**: Surya
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Screenshots

The app features a distinctive cyberpunk aesthetic with:
- Neon green (#00FF41) primary color
- Black background for true dark mode
- Monospace fonts for terminal feel
- Glowing button animations
- Matrix-inspired color scheme

## License

This project is created as a demonstration of modern Android development with Jetpack Compose and Material 3 theming.
