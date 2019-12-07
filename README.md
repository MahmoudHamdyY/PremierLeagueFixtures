# Premier League Fixtures
A sample android app that leverages the [football-data.org API](https://www.football-data.org/client/register) to desplays a list of **Fixtures** about **The English Premier League**. It also allows you to watch/favorite Fixtures from the list. See the [football-data.org API documentation](https://www.football-data.org/documentation/quickstart) in case you need more info.

The app is composed of one screen. This screen displays a list of Fixtures sectioned by day, in which, each Fixtures is described by home team name, away team name and game result or the time of the game if it's not played yet. Users allowed to favorite/unfavorite any Fixtures. Also they can filter the list so that it containing only those favorite Fixtures only.

**Fixtures List**

![Imgur](https://funkyimg.com/i/2YsXu.png)

## Overview

The app does the following:

1. Get a list of Fixtures using [Competitions endpoint](https://api.football-data.org/v2/competitions/2021/matches).
2. Check if any item in the list is in user's favorite list.
3. Update favorite list with new details form update Fixtures list.
4. Display the list of Fixtures with their teams' name and details.
5. Get favorite list from local database [Room](https://developer.android.com/jetpack/androidx/releases/room).
6. Add/Delete/Update favorite items in local database.

To achieve this, there are two different components in this app:

1. Data layer that include `repositories` and `Services` for both cash and remote data.
	- `Api` & `ApiService` & `MatchesRemote` - Responsible for executing the API requests and retrieving the JSON.
	- `AppDatabase` &  `MatchDao` & `MatchesCache`- Responsible for 	saving/retrieving data stored in Room.
2. UI layer that include `Adapter` , `ViewModel` and `Fragments`
	- `MatchesAdapter` - Responsible for mapping each `Fixture` to a particular view layout.
	- `AllMatchesFragment` - Responsible for viewing all matches retrieved from the API.
	- `FavoriteMatchesFragment` - Responsible for viewing all favorite matches retrieved from cach.
	- `HomeActivity` - Responsible for switching between `AllMatchesFragment` and `FavoriteMatchesFragment`.
	- `HomeViewModel` - Responsible for handling the scenarios.

## Running the tests
The app designed using **MVVM** architectural pattern, facilitate automated unit testing.

The app tests five different components:

1. `get all matches with successful response then build cards` by mocking the request and check every thing is clear.
2. `get all matches with successful response then build cards and set favorite items` by mocking the request and setting favorite flag according to favorite items cached.
3. `get all matches with successful response then update favorite list` by mocking the request and updating each cached favorite item detail to the update one retrieved from the API.
4. `get all matches with successful response then verify loading state` by requesting matches and check that loading started then ended.
5. `build list of match items then get list of card item sectioned by date` by giving a list of matches and expected output then check that builder worked fine.

## Libraries
Entirely written in [Kotlin](https://kotlinlang.org).
This app leverages seven third-party libraries:

* [Gson](https://github.com/google/gson) - Java library that can be used to convert Java Objects into their JSON representation.
* [Retrofit](http://square.github.io/retrofit/) - Type-safe HTTP client for Android and Java by Square.
* [RxJava](https://github.com/ReactiveX/RxJava) - Java VM implementation of [Reactive Extensions](http://reactivex.io/): a library for composing asynchronous and event-based programs by using observable sequences.
* [Okhttp 3](https://github.com/square/okhttp) - An HTTP+HTTP/2 client for Android and Java applications.
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - Library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Mockito](https://github.com/mockito/mockito) - framework for unit tests written in Java.
* [Joda-Time](https://github.com/dlew/joda-time-android) - Library for handling date and time allows for multiple calendar systems.