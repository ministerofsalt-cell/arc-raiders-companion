# ARC Raiders Android Companion App

An Android companion application for **ARC Raiders** featuring live game data from the MetaForge API and community resources.

## Features

✨ **Live Data Integration**
- Real-time items, quests, and event data via MetaForge API
- Event timers with countdown functionality  
- Interactive maps with point-of-interest markers
- Trader inventory information

🎯 **Tracking & Planning**
- Quest progression tracker
- Item database browser with search/filters
- Needed items calculator
- Skill tree planner
- Hideout upgrade tracker

📱 **Modern Android Development**
- Built with Kotlin and Jetpack Compose
- MVVM architecture with clean separation
- Retrofit for API communication
- Room database for offline caching
- Hilt dependency injection
- Coroutines for async operations

💾 **Offline Support**
- Local caching with Room
- Offline browsing of previously loaded data

## Data Sources

- **MetaForge API**: https://metaforge.app/api/arc-raiders
- **Community Data**: https://github.com/RaidTheory/arcraiders-data
- **Data Visualization**: https://arctracker.io

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Networking**: Retrofit 2.9.0 + OkHttp
- **Database**: Android Room
- **DI**: Dagger Hilt
- **Async**: Coroutines + Flow
- **Navigation**: Navigation Compose
- **Image Loading**: Coil

## Project Structure

```
app/src/main/java/com/arcraiders/companion/
├── data/
│   ├── remote/
│   │   ├── MetaForgeApi.kt
│   │   ├── MetaForgeService.kt
│   │   └── dto/
│   ├── repository/
│   │   ├── MetaForgeRepository.kt
│   │   └── ArcDataRepository.kt
│   └── local/
│       └── ArcDatabase.kt
├── domain/
│   └── model/
│       ├── Item.kt
│       ├── Quest.kt
│       ├── Event.kt
│       └── MapData.kt
├── ui/
│   ├── MainActivity.kt
│   ├── screens/
│   ├── viewmodel/
│   └── theme/
└── ArcRaidersApplication.kt
```

## Getting Started

### Prerequisites
- Android Studio 2023.1+
- JDK 11+
- Kotlin 1.9.0+

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/ministerofsalt-cell/arc-raiders-companion.git
   cd arc-raiders-companion
   ```

2. **Open in Android Studio**
   - Select "Open an Existing Project"
   - Navigate to the cloned repository
   - Wait for Gradle sync to complete

3. **Build the app**
   ```bash
   ./gradlew build
   ```

4. **Run on emulator or device**
   ```bash
   ./gradlew installDebug
   ```

## API Integration

The app uses the MetaForge ARC Raiders API with these endpoints:

- `GET /api/arc-raiders/items` - Items with pagination
- `GET /api/arc-raiders/quests` - Quest data
- `GET /api/arc-raiders/event-timers` - Live event information
- `GET /api/game-map-data` - Map markers and POIs
- `GET /api/arc-raiders/traders` - Trader inventories

### Rate Limiting

Please respect API usage guidelines:
- Cache data client-side
- Avoid repeated direct API calls
- Implement appropriate backoff strategies

## Attribution

This project uses data from:
- **MetaForge** (https://metaforge.app) - Live game data
- **RaidTheory/arcraiders-data** (https://github.com/RaidTheory/arcraiders-data) - Community dataset (MIT License)
- **ARCTracker** (https://arctracker.io) - Community resource

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit pull requests.

## Legal Notice

ARC Raiders is copyright Embark Studios. This is a fan-made companion app and is not affiliated with Embark Studios.
