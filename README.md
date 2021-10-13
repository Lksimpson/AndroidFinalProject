# AndroidFinalProject
BY: Tyler Reiser, Lindberg Simpson

Final Project Movie Searching Application.

Overall goal: Allow users to view a list of trending movies and create a shortlist of their favorites.

Functional Design:
(I) MVC Design pattern used
(II) Longterm persistence strategy - on-device SQLite Relational database used to store favorite movies
(III) Mobile sensor - camera used to allow user to upload their reaction to a favorite movie
(IV) Network Component - Homepage uses API to display list of movies from The Movie Database (TMDB) website.

Project Features [Demonstrated in the Demo Video]:
1. Movie list - View trending movies from TMDB dataset once the homepage boots up. The user can return to the movie list by clicking on any of the "movie list" buttons throughout the app.

2. Search - On the homepage the user can search through movies by inputting a string in the search view and clicking the search button.

3. Movie details - Tap on a movie in the movie list on the homepage to bring up the movie details page.

4. Add to favorites - If a movie is tapped on from the homepage the user can click "add to favorites" button to add it to their favorites list. If the "favorites list" button is then clicked on the user can see the movie appear on the list.

5. Favorites list - The user can view the list of their favorite movies by clicking on any of the "favorites list" buttons throughout the application.

6. Watched/unwatched - On the favorites list page the user can tap the blue button to toggle "watched" or "unwatched" for each movie in that list.

7. Favorite movie details - From the favorites list page the user can tap a movie to view its details. This is different from the normal movie details page. The "add to favorites" button is no longer there and the user can now upload their reaction to the movie.

8. Upload reaction - After tapping on a movie from the favorites page a user will see a camera button which they can click on take a picture. The picture is then uploaded to the imageView located beside the camera button.

Potential Issues:
If you cannot get the project to load, please include the database files and try again. 
Also try using the AVD Manager and "wipe data" from the device as sometimes the emulator has issues. Thanks!
