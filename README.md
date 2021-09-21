# Marvel

Simple app with a list with some characters from Marvel populated on a recyclerview. Every item show its description, if it have, and a button to see it's detail. On the character's detail screen the user is able to fetch 4 different types of information according each character's information.

Libraries used:
- Dagger Hilt: For dependency injection.
- Coroutines: To retrieve data from API rest outside the UI Thread.
- LiveData: According observer pattern used to get noticed changes from view models on the views.
- Picasso: Retrieve the images from an URL.
- Retrofit: Rest Client for HTTP requests.
- OkHttp3: Used to create a OkHttpClient with some custom properties.
- Moshi: JSON serialization/deserialization
- Architecture Components:
    - Navigation: Used single activity with two fragments. Flow between fragments through navigation.
    - SafeArgs: For passing parameters through fragments using navigation.
    - ViewBinding: Linking the layouts with the views.
    - ViewModels: At the presentation layer.
    
- Used clean architecture with a modularized project.
