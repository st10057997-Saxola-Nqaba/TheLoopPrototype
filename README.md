The Loop - SAID Mobile Outreach App (Prototype)
A mobile outreach application prototype for The Society for Animals in Distress (SAID), built as a native Android application demonstrating complete navigation flows and role-based interfaces for three user types: Pet Owner, Animal Health Technician (AHT), and Administrator.
Phase 1 Prototype -This release validates UX journey maps and functional requirements through a fully navigable client-side Android interface. All data exists in memory only, with no persistence layer and no backend integration. It serves as a proof-of-concept before database, API, and CI/CD development begins.

What's Included
This prototype demonstrates:
Feature	Description
Role-Based Navigation	Single login screen routes users to role-specific navigation graphs (Pet Owner, AHT, Administrator) via Jetpack Navigation Component
Core Workflows	Request submission, pet/owner management, visit scheduling and logging, outreach dispatch, and outcome review
In-Memory Data	All data stored in a Kotlin singleton (DummyRequests) -session-only and resets on app restart
Form Validation	Client-side validation, including scheduling dialogs requiring Area/Group selection
Google Maps Integration	Visualization of pending/expired requests and administrator scheduling on interactive maps

Technology Stack
Prototype Implementation
Component	Technology
Language	Kotlin
UI Framework	Android Views (Fragments + XML), Material Components
Navigation	Jetpack Navigation Component
Data	In-memory Kotlin object (DummyRequests)
Maps	Google Maps SDK for Android
Architecture	Fragments operating directly on shared data singleton
applicationId	com.example.theloopprototype
minSdk	26 (Android 8.0)
targetSdk	35
compileSdk	37
Note: While Gradle includes Jetpack Compose/Material3 dependencies, the application uses only the classic Fragment/XML view system.
Planned Production Stack
Component	Technology
Backend API	ASP.NET Core Web API
Local Persistence	Room / SQLite (offline-first sync)
Cloud Database	PostgreSQL
Authentication	Firebase Authentication
App Architecture	MVVM (ViewModel + Repository)
CI/CD	GitHub Actions / Azure DevOps

Role-Based Features
Pet Owner
•	Add and view pets
•	Submit service requests
•	View request history and details
•	Access visit history
•	Profile management
Animal Health Technician (AHT)
•	Bottom navigation: Schedules, Search, Profile
•	Search pets and owners
•	Log visit entries against requests
•	View and reorder scheduled request lists
Administrator
•	Dashboard with eight management screens
•	Map-based request visualization
•	Schedule outreach visits by area
•	Manage notifications
•	Review outreach outcome statistics
•	Profile management

Workflows Demonstrated
1.	Pet Owner submits a service request.
2.	Administrator groups pending requests into scheduled outreach lists by area.
3.	AHT views schedules, logs visits, and updates request status.
4.	All operations operate on the shared DummyRequests in-memory data source.

Architecture
Current Architecture
UI / Fragments  ↔  DummyRequests (in-memory singleton)
Fragments read from and write directly to the shared data object. No ViewModel, Repository, or LiveData/StateFlow layers exist in this prototype.
Target Architecture
UI / Fragments → ViewModel → Repository → Room DAO → Room Database
                                              ↓
                                      ASP.NET Core API → PostgreSQL




Project Structure
app/src/main/java/com/example/theloopprototype/
├── adapter/                 # RecyclerView adapters
├── data/
│   └── DummyRequests.kt     # Single in-memory data source
├── models/                  # Data classes (DRequest, DScheduledRequestList, etc.)
├── ui/
│   ├── admin/               # Administrator screens
│   ├── aht/                 # AHT screens
│   ├── petowner/            # Pet Owner screens
│   ├── auth/                # Login screen
│   └── theme/               # Compose theme (unused)
└── ...

app/src/main/res/
├── layout/                  # XML screen layouts
├── navigation/nav_graph.xml # Shared navigation graph
├── menu/bottom_nav_menu.xml # AHT bottom navigation
└── drawable/, values/

Getting Started
Prerequisites
•	Android Studio (recent stable release)
•	JDK 17+
•	Android SDK Platform 35 (target) and API 26+ (minimum)
•	Android emulator or physical device (Android 8.0+)
•	Google Maps API key for map screens
Installation
git clone https://github.com/st10057997-Saxola-Nqaba/TheLoopPrototype.git
Open the project in Android Studio, allow Gradle to sync, and run the application on an emulator or physical device.

Prototype Limitations
This is a Phase 1 prototype and intentionally excludes:
•	Local database (Room/SQLite) -data is in-memory only
•	MVVM architecture (ViewModel, Repository, LiveData/StateFlow)
•	Backend API (ASP.NET Core, PostgreSQL, cloud integration)
•	Firebase Authentication -uses seeded dummy credentials only
•	Offline synchronization capability
•	CI/CD automation
What It Does
•	Complete role-based navigation flows
•	Screen-level UI for all three user roles
•	In-memory CRUD operations on dummy data
•	Client-side form validation
•	Google Maps-based area visualization

Future Roadmap
Android App (Room + ViewModel/Repository)
          ↓
    ASP.NET Core Web API
          ↓
      PostgreSQL
          ↓
    CI/CD Pipeline
The production application will eventually:
•	Read and write through a local Room database
•	Synchronize with the backend API when connectivity is available
•	Support offline-first operation

Prototype Evidence
1.Pet Owner Screens
•	Pet Owner dashboard/home
•	Pet management
•	Adding a pet
•	Service request submission
•	Request history
•	Request details
•	Visit history
•	Profile management


2. Animal Health Technician (AHT) Screens
•	AHT dashboard
•	Schedules
•	Scheduled request lists
•	Request details
•	Visit logging
•	Search
•	Pet/owner information
•	Profile
•	Other AHT-specific screens


3. Administrator Screens
•	Administrator dashboard
•	Request management
•	Map-based request visualization
•	Outreach scheduling
•	Area/group management
•	Notifications
•	Outreach outcome statistics
•	Profile management
•	Other administrator-specific screens

Repository Purpose
This repository contains the Phase 1 Android UI and navigation prototype for The Loop (SAID Mobile Outreach App). It validates UX flows and functional requirements before database, backend, and CI/CD development begins.

Contact
For questions or feedback regarding this prototype, please reach out to the development team.

Last updated: Phase 1 Prototype

