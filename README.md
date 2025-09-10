# minipomo 🍅

A minimalist Pomodoro timer app for Android with a clean monochromatic design, built using Kotlin and Jetpack Compose.

## Features

- **Simple Pomodoro Timer**: Focus sessions with automatic break transitions
- **Customizable Timer Durations**: Adjust focus time (1-60 minutes) and break time (1-30 minutes)
- **Clean Monochromatic Design**: Distraction-free interface that adapts to system theme (light/dark)
- **Task Management**: Add and track tasks during your focus sessions
- **Audio & Visual Notifications**: Sound alerts and system notifications when timers complete
- **Intuitive Controls**: Play, pause, and reset functionality

## Screenshots

*Coming soon...*

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 21+ (Android 5.0 Lollipop)
- Kotlin 1.5+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/minipomo.git
```

2. Open the project in Android Studio

3. Build and run the project on your device or emulator

### Permissions

The app requests the following permission:
- `POST_NOTIFICATIONS` (Android 13+): To display timer completion notifications

## How It Works

### Timer Flow
1. **Welcome Screen**: Brief greeting animation on app launch
2. **Main Timer**: Start your focus session with customizable duration
3. **Automatic Break**: When focus time ends, break timer starts automatically
4. **Notifications**: Audio and visual alerts notify you when each session completes

### Customization
- Navigate to Settings to adjust timer durations
- Focus timer: 1-60 minutes (default: 25 minutes)
- Break timer: 1-30 minutes (default: 5 minutes)

### Task Management
- Tap "Add a Task" to set your focus goal
- Tasks help maintain concentration during sessions
- Simple one-task-at-a-time approach

## Technical Architecture

### Built With
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern Android UI toolkit
- **Material 3** - Design system with dynamic theming
- **Navigation Component** - Screen navigation management
- **CountDownTimer** - Core timer functionality
- **WorkManager** - Background timer operations (TimerWorker)
- **MediaPlayer** - Audio notification playback

### Project Structure
```
app/src/main/java/com/example/simplepomodoro/
├── MainActivity.kt              # Entry point with permission handling
├── TimeWorker.kt               # Background timer worker
├── navigation/
│   └── AppNavigator.kt         # Navigation configuration
├── screens/
│   ├── GreetingScreen.kt       # Welcome animation screen
│   ├── MainContentScreen.kt    # Main timer interface
│   └── SettingsScreen.kt       # Timer customization
└── ui/theme/
    ├── Color.kt               # Color definitions
    ├── Theme.kt               # Material theme setup
    └── Type.kt                # Typography with custom font
```

### Key Features Implementation
- **Automatic Transitions**: Focus timer automatically starts break timer on completion
- **Background Operations**: Timer continues running using WorkManager
- **Adaptive UI**: Supports both light and dark themes
- **Custom Typography**: Uses Righteous font family for consistent branding

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Future Enhancements

- [ ] Statistics and session tracking
- [ ] Multiple task management
- [ ] Custom sound selection
- [ ] Widget support
- [ ] Session history
- [ ] Focus session streaks

## Acknowledgments

- Inspired by the Pomodoro Technique developed by Francesco Cirillo
- Built with modern Android development practices
- Uses Material 3 design principles for accessibility and usability

---

*Stay focused, stay productive* 🍅
