<h1 align="center">Bluffer</h1>

<p align="center">  
Bluffer demonstrates modern Android development with Coroutines, Jetpack Libraries, and Material Design based on MVVM architecture.
</p>


![Bluffer_Poster](https://user-images.githubusercontent.com/81246797/176945231-e672db11-78c7-4a76-bae5-2705f62241d2.png)

## Preview
![Screenshot_20220716-004415](https://user-images.githubusercontent.com/81246797/179296854-fad72aac-8943-4a58-80a1-a0ca0ac1e824.jpg)

![Screenshot_20220716-004422](https://user-images.githubusercontent.com/81246797/179296864-8579e63c-1427-4cbb-8f0b-abb2356825ff.jpg)
![Screenshot_20220716-004439](https://user-images.githubusercontent.com/81246797/179296911-7f190120-bc0c-4b92-9862-01ea8507883a.jpg)
![Screenshot_20220716-004447](https://user-images.githubusercontent.com/81246797/179296956-4c262626-70c5-4aee-aaec-a520302c398e.jpg)
![Screenshot_20220715-195316](https://user-images.githubusercontent.com/81246797/179296975-556001d1-19fd-461c-9e2c-4c5ff43cb06a.jpg)
![Screenshot_20220716-004537](https://user-images.githubusercontent.com/81246797/179296991-2067e0c1-ac46-4c3c-bbc2-47b5a97552bb.jpg)
![Screenshot_20220716-004543](https://user-images.githubusercontent.com/81246797/179296995-c67694ae-946b-452a-b397-2a3e50c887cd.jpg)
![Screenshot_20220716-004549](https://user-images.githubusercontent.com/81246797/179297013-48ec5fa2-6345-4d5a-abb9-5021b44c3b6a.jpg)
![Screenshot_20220716-004603](https://user-images.githubusercontent.com/81246797/179297055-62a6844a-c13a-4228-b871-cbc876995307.jpg)

## Tech stack & Open-source libraries

- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) - Modern, concise and safe programming language
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A concurrency design pattern to execute code asynchronously.
- Jetpack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - Navigation - Implements navigation from simple button clicks to more complex patterns.
  - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - LiveData - LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
  - ViewBinding - Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
- Architecture
  - MVVM Architecture (View - ViewModel - Network)
- [Retrofit2](https://github.com/square/retrofit) - Construct the REST APIs.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building View layouts and Animations.


## Architecture

Bluffer is based on the MVVM architecture pattern.

<img width="500" alt="MVVM Architecture" src="https://user-images.githubusercontent.com/81246797/176947096-e1ba74e8-0bbf-4a8a-a530-363f08d9fd25.png">


## MAD Score

![summary](https://user-images.githubusercontent.com/81246797/179294198-7beb2b9a-773e-4177-930a-b5cf824ab756.png)


## Open API

- Bluffer using the [SubredditApi](https://meme-api.herokuapp.com/gimme/hmmm) for constructing RESTful API.<br>

- This API provides a RESTful API interface for any subreddit. All the data is fetched from [/hmmm](https://www.reddit.com/r/hmmm/) subreddit.

- Github repository of author - [Github](https://github.com/D3vd/Meme_Api)


## Contribution

Contributions are always welcome! You are just a pull request away.


## License

[MIT](https://choosealicense.com/licenses/mit/)
