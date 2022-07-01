<h1 align="center">Bluffer</h1>

<p align="center">  
Bluffer demonstrates modern Android development with Coroutines, Jetpack Libraries, and Material Design based on MVVM architecture.
</p>


![Bluffer_Poster](https://user-images.githubusercontent.com/81246797/176945231-e672db11-78c7-4a76-bae5-2705f62241d2.png)


## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) - Modern, concise and safe programming language
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A concurrency design pattern to execute code asynchronously.
- Jetpack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - LiveData - LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
  - ViewBinding - Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
- Architecture
  - MVVM Architecture (View - ViewModel - Network)
- [Retrofit2](https://github.com/square/retrofit) - Construct the REST APIs.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines
- [SoundPool](https://developer.android.com/reference/android/media/SoundPool) - Manages and plays audio resources for applications.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building View layouts and Animations.


## Architecture
Bluffer is based on the MVVM architecture pattern.

<img width="500" alt="MVVM Architecture" src="https://user-images.githubusercontent.com/81246797/176947096-e1ba74e8-0bbf-4a8a-a530-363f08d9fd25.png">


## MAD Score

![summary](https://user-images.githubusercontent.com/81246797/175074332-21e64a45-88b6-4ec0-a005-92e7969e6035.png)


## Open API

- Bluffer using the [SubredditApi](https://meme-api.herokuapp.com/gimme/hmmm) for constructing RESTful API.<br>

- This API provides a RESTful API interface for any subreddit. All the data is fetched from [/hmmm](https://www.reddit.com/r/hmmm/) subreddit.

- Github repository of author - [Github](https://github.com/D3vd/Meme_Api)


## Contribution

Contributions are always welcome! You are just a pull request away.


## License

[MIT](https://choosealicense.com/licenses/mit/)
