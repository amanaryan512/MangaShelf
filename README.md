# Manga Explorer App

## Overview
Manga Explorer is an Android application that allows users to browse, sort, and favorite manga titles. The app retrieves manga data from a remote API and stores it locally using Room for offline access. Users can filter manga by year, sort by score/popularity, and mark mangas as favorites or read.

## Features
### **Manga List Page (Home Screen)**
- Displays a list of manga with title, cover image, score, popularity, and publication year.
- Stores fetched data in a local database for offline access.
- Implements sorting options:
  - Sort by Score (Descending)
  - Sort by Popularity (Descending)
- Implements filtering:
  - Manga grouped into tabs based on publication year.
  - Tabs are horizontally scrollable if they exceed the screen width.
  - Clicking a year scrolls to the first manga published that year.
  - Auto-switching of year tabs based on scroll position.
- Users can mark/unmark a manga as favorite.
- Favorite status persists across sessions.
- Error handling for network failures, empty data, and invalid inputs.

### **Manga Detail Page**
- Displays manga details:
  - Title
  - Cover Image
  - Score
  - Popularity
  - Published Chapter Date (formatted to a readable date)
  - Category
- Allows users to mark/unmark a manga as favorite.
- Allows users to mark a manga as read.
- Favorite and read status persist across sessions.
- Updates to favorite status reflect on the manga list upon returning.

## **Tech Stack**
- **Architecture:** MVVM (Model-View-ViewModel)
- **Local Database:** Room
- **Networking:** Retrofit
- **Image Loading:** Coil
- **Dependency Injection:** Hilt
- **Concurrency:** Kotlin Coroutines & Flows
- **Navigation:** Jetpack Navigation Component
- **UI:** Jetpack Compose

## **Installation & Setup**
1. Clone the repository:
   ```bash
   git clone https://github.com/amanaryan512/MangaShelf.git
   ```
2. Open the project in Android Studio.
3. Build and run the project on an emulator or a physical device.

## **Error Handling**
- Displays appropriate messages for network failures and empty states.

## **Facing issue while running the project?**
- Clean build the project and rebuild it.
- Still not able to run, then go the project folder -> app -> then delete build folder.
- Re-run the project.

