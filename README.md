# The Loop - SAID Mobile Outreach App (Prototype)

A mobile outreach application prototype for **The Society for Animals in Distress (SAID)**, built as a native Android application demonstrating complete navigation flows and role-based interfaces for three user types: **Pet Owner, Animal Health Technician (AHT), and Administrator**.

## Phase 1 Prototype

This release validates UX journey maps and functional requirements through a fully navigable client-side Android interface. All data exists in memory only, with no persistence layer and no backend integration. It serves as a proof-of-concept before database, API, and CI/CD development begins.

---

## What's Included

This prototype demonstrates:

| Feature                     | Description                                                                                                                          |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| **Role-Based Navigation**   | Single login screen routes users to role-specific navigation graphs (Pet Owner, AHT, Administrator) via Jetpack Navigation Component |
| **Core Workflows**          | Request submission, pet/owner management, visit scheduling and logging, outreach dispatch, and outcome review                        |
| **In-Memory Data**          | All data stored in a Kotlin singleton (`DummyRequests`) - session-only and resets on app restart                                     |
| **Form Validation**         | Client-side validation, including scheduling dialogs requiring Area/Group selection                                                  |
| **Google Maps Integration** | Visualization of pending/expired requests and administrator scheduling on interactive maps                                           |

---

## Technology Stack

### Prototype Implementation

| Component         | Technology                                            |
| ----------------- | ----------------------------------------------------- |
| **Language**      | Kotlin                                                |
| **UI Framework**  | Android Views (Fragments + XML), Material Components  |
| **Navigation**    | Jetpack Navigation Component                          |
| **Data**          | In-memory Kotlin object (`DummyRequests`)             |
| **Maps**          | Google Maps SDK for Android                           |
| **Architecture**  | Fragments operating directly on shared data singleton |
| **applicationId** | `com.example.theloopprototype`                        |
| **minSdk**        | 26 (Android 8.0)                                      |
| **targetSdk**     | 35                                                    |
| **compileSdk**    | 37                                                    |

> **Note:** While Gradle includes Jetpack Compose/Material3 dependencies, the application uses only the classic Fragment/XML view system.

### Planned Production Stack

| Component             | Technology                         |
| --------------------- | ---------------------------------- |
| **Backend API**       | ASP.NET Core Web API               |
| **Local Persistence** | Room / SQLite (offline-first sync) |
| **Cloud Database**    | PostgreSQL                         |
| **Authentication**    | Firebase Authentication            |
| **App Architecture**  | MVVM (ViewModel + Repository)      |
| **CI/CD**             | GitHub Actions / Azure DevOps      |

---

## Role-Based Features

### Pet Owner

* Add and view pets
* Submit service requests
* View request history and details
* Access visit history
* Profile management

### Animal Health Technician (AHT)

* Bottom navigation: Schedules, Search, Profile
* Search pets and owners
* Log visit entries against requests
* View and reorder scheduled request lists

### Administrator

* Dashboard with eight management screens
* Map-based request visualization
* Schedule outreach visits by area
* Manage notifications
* Review outreach outcome statistics
* Profile management

---

## Workflows Demonstrated

1. **Pet Owner** submits a service request.
2. **Administrator** groups pending requests into scheduled outreach lists by area.
3. **AHT** views schedules, logs visits, and updates request status.
4. All operations operate on the shared `DummyRequests` in-memory data source.

---

## Architecture

### Current Architecture

```text
UI / Fragments
       ↕
DummyRequests (in-memory singleton)
```

Fragments read from and write directly to the shared data object. No ViewModel, Repository, or LiveData/StateFlow layers exist in this prototype.

### Target Architecture

```text
UI / Fragments
       ↓
   ViewModel
       ↓
   Repository
       ↓
    Room DAO
       ↓
  Room Database
       ↓
ASP.NET Core API
       ↓
  PostgreSQL
```

---

## Project Structure

```text
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
```

---

## Getting Started

### Prerequisites

* Android Studio (recent stable release)
* JDK 17+
* Android SDK Platform 35 (target) and API 26+ (minimum)
* Android emulator or physical device (Android 8.0+)
* Google Maps API key for map screens

### Installation

```bash
git clone https://github.com/st10057997-Saxola-Nqaba/TheLoopPrototype.git
```

Open the project in Android Studio, allow Gradle to sync, and run the application on an emulator or physical device.

---

## Prototype Limitations

This is a **Phase 1 prototype** and intentionally excludes:

* Local database (Room/SQLite) - data is in-memory only
* MVVM architecture (ViewModel, Repository, LiveData/StateFlow)
* Backend API (ASP.NET Core, PostgreSQL, cloud integration)
* Firebase Authentication - uses seeded dummy credentials only
* Offline synchronization capability
* CI/CD automation

---

## What It Does

* Complete role-based navigation flows
* Screen-level UI for all three user roles
* In-memory CRUD operations on dummy data
* Client-side form validation
* Google Maps-based area visualization

---

## Future Roadmap

```text
Android App (Room + ViewModel/Repository)
                  ↓
        ASP.NET Core Web API
                  ↓
              PostgreSQL
                  ↓
           CI/CD Pipeline
```

The production application will eventually:

* Read and write through a local Room database
* Synchronize with the backend API when connectivity is available
* Support offline-first operation

---

# Prototype Evidence

## 1. Pet Owner Screens

* Login screen
* <img width="333" height="703" alt="login screen" src="https://github.com/user-attachments/assets/f4934a3f-9119-4e71-8c29-565be82dee23" />

* Pet owner home
* <img width="333" height="746" alt="pet owner home " src="https://github.com/user-attachments/assets/97bef6b9-dd2d-4958-8de5-8c09d2e18b88" />

  
* Add pet form
* <img width="333" height="746" alt="add pet form" src="https://github.com/user-attachments/assets/315b3cd9-bcbe-4ad9-8e48-910a4c7229d4" />

* view pet details
* <img width="333" height="746" alt="view pet details" src="https://github.com/user-attachments/assets/e1f686b5-69cd-4fc6-b977-1d8a99ed3450" />

* Request a visit
* <img width="333" height="746" alt="request a visit" src="https://github.com/user-attachments/assets/a02952ea-4cb5-4d19-b69d-4785d14a89b7" />

* my requests
* <img width="333" height="746" alt="my requests" src="https://github.com/user-attachments/assets/1299b9b6-953b-4052-80b2-b92a3e929bcf" />

* Request details
* <img width="333" height="746" alt="request details" src="https://github.com/user-attachments/assets/a1d47f36-74bf-417b-9680-4dccba577b46" />

* pet owner profile
* <img width="333" height="746" alt="pet owner profile" src="https://github.com/user-attachments/assets/5e5e5b1c-276b-4c2f-a242-ae77d6452cac" />

## 2. Animal Health Technician (AHT) Screens

* AHT dashboard
* <img width="333" height="746" alt="AHT home" src="https://github.com/user-attachments/assets/6c0b6c00-73ff-4a1a-a6be-f17f8594e06d" />

* AHT map view
* <img width="333" height="746" alt="AHT map view" src="https://github.com/user-attachments/assets/389feb8f-aa55-4654-86a8-38a4cf33e6a8" />

* Owner and pet search
* <img width="333" height="746" alt="Screenshot 2026-08-17 at 23 32 43" src="https://github.com/user-attachments/assets/509b9fb3-d287-4fdd-bcc9-29d4cf8a9da2" />


* Owner details
* <img width="333" height="746" alt="owner details" src="https://github.com/user-attachments/assets/4831ebfb-a7b7-46e5-9168-6e9be4b56a59" />

* update owner and pet
* <img width="333" height="746" alt="update owner and pet" src="https://github.com/user-attachments/assets/21b5d292-b15d-4e26-b141-b091fb172e51" />

* view scheduled requests
* <img width="333" height="746" alt="view scheduled request" src="https://github.com/user-attachments/assets/04fc59dd-59a8-4890-afaa-d10fd5168191" />

* Create visit entry
* <img width="333" height="746" alt="create visit entry" src="https://github.com/user-attachments/assets/2755c00d-a561-4dad-84be-11af190103a5" />

  

## 3. Administrator Screens

* Administrator console
* <img width="333" height="746" alt="admin console" src="https://github.com/user-attachments/assets/0593190a-de10-44cb-80d1-3bce68deb72b" />

* Request management
* <img width="333" height="746" alt="manage requests" src="https://github.com/user-attachments/assets/71efaa0d-bf4b-49a4-93da-5f1ff3ba1828" />

* Expired map view
* <img width="333" height="746" alt="expired map view" src="https://github.com/user-attachments/assets/8a07ba62-93a9-4835-bf62-0431cb48653a" />

* Map picker
* <img width="333" height="746" alt="map picker" src="https://github.com/user-attachments/assets/636be824-074b-4d26-a45b-4329ca2c7bac" />

* Scheduling & Notifications
* <img width="333" height="746" alt="scheduling   Notifications" src="https://github.com/user-attachments/assets/696c9141-ccb3-4f7e-8eb9-bd9a383f12ed" />

* Outreach outcome statistics
* <img width="333" height="746" alt="outreach outcomes" src="https://github.com/user-attachments/assets/5114112e-5525-4eda-babe-56ec43121993" />

* visit entry details
* <img width="333" height="746" alt="visit entry details" src="https://github.com/user-attachments/assets/c6b650bd-98dc-4f2a-8d21-5289101842b7" />

* admin profile
* <img width="333" height="746" alt="admin profile" src="https://github.com/user-attachments/assets/45ad336e-da5c-40f6-a324-e6f98f3293e1" />

* Edit admin profile
* <img width="333" height="746" alt="edit admin profile" src="https://github.com/user-attachments/assets/ac1ef7c1-53f6-42a5-92f2-732bd4b8c256" />



  

---

## Repository Purpose

This repository contains the **Phase 1 Android UI and navigation prototype** for **The Loop (SAID Mobile Outreach App)**. It validates UX flows and functional requirements before database, backend, and CI/CD development begins.
