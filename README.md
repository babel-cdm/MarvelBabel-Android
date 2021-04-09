# Marvel Babel - Android
## _MVI_


[![kotlin](https://img.shields.io/badge/Kotlin-1.4.xxx-blue)](https://kotlinlang.org/) ![MVI](https://img.shields.io/badge/Architecture-MVI-brightgreen) [![coroutines](https://img.shields.io/badge/Coroutines-Asynchronous-red)](https://developer.android.com/kotlin/coroutines)  

This app is based on Modulable MVI arch. It implements different tools to keep up with the latest trends.
- Modularization
- Gradle Dependency management
- Kotlin DSL
- Custom Plugin (dependencies with no duplication)
- ViewBinding
- Mockito // Espresso Test 
- Navigation Components
- [Coroutines](https://developer.android.com/kotlin/coroutines) and flows
- [Room Persistence Library](https://developer.android.com/training/data-storage/room "Room Persistence Library")
- Dependency Injection/Service Locator with [Koin](https://github.com/InsertKoinIO/koin "Koin") Library.
- Model View Intent Architecture - MVI
- Repository pattern (NetworkBoundResource)
- Clean Architecture approach.
- Static Code Analytics [Ktlint](https://github.com/jlleitschuh/ktlint-gradle "Ktlint") This plugin creates convenient tasks in your Gradle project that run ktlint checks or do code auto format.

## Features

- Allows request local storage (cache)
- Refresh the cache in the background and update the view with viewBinding
- Crash-safe
- Separate request layer from the visual layer, allowing for greater exception control
- Animated navigations and single activity app
- Automatic data transfer between screens
- Automatic loading screens without the need for extra development. Every time a request is made, loading will be displayed.

## Modules
##### App
Used to declare the rest of the app's modules the start activity.
##### BuildSrc
A module created to centralize the utilities and dependencies for all gradle modules.
##### Cache
This module contains ROOM, which is in charge of caching the desired responses and returning such data when necessary.
##### Remote
Remote module is in charge of making the remote requests.
##### Data
The Data layer is responsible for deciding how the requests are to be made, and whether or not to cache the responses. It has several different implementations for the requests, allowing, among other options, the following actions
- Make a request ignoring cache
- Check if the response is cached, if not, make the request.
- Get the cache, and in the background make the request
- TimeOut and connection errors control in all request included cache
##### Domain
Domain is the connection layer that prepares the necessary use cases to be used by the view. It also provides constants and the states with which the data will be updated.
##### Presentation
It is in charge of receiving and draw data, it consists in only one active activity and the screens are based on fragments. You can distribute the viewModel communication to several screens or do it individually. The navegation is based on a graph that can be modified easily. 
The use of Koin allows managers to be injected in a simple way.

## Development
The development is base on clean code, and the use of Ktlint force always the same strucute in all the code
```sh
 ./gradlew ktlintFormat
```
## License
MIT
