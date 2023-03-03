import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter the cast's name: ");
        String name = scanner.nextLine();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        ArrayList<String> actors = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String cast = movies.get(i).getCast();
            while(cast.indexOf("|") != -1) {
                String temp = cast.substring(0, cast.indexOf("|"));
                if (temp.contains(name)) {
                    if (!actors.contains(temp)) {
                        actors.add(cast);
                    }
                }
                cast = cast.substring(cast.indexOf("|") + 1);
            }
            if (!actors.contains(cast) && cast.contains(name)) {
                actors.add(cast);
            }
        }
        Collections.sort(actors);
        for (int i = 0; i < actors.size(); i++) {
            int idx = i + 1;
            System.out.println(idx + ". " + actors.get(i));
        }
        System.out.println("Select a cast member to see their movies");
        System.out.print("Enter number: ");
        int choices = scanner.nextInt();
        scanner.nextLine();
        String person = actors.get(choices-1);
        ArrayList<Movie> movies1 = new ArrayList<Movie>();
        for(int i = 0; i<movies.size();i++){
            if (movies.get(i).getCast().contains(person)){
                movies1.add(movies.get(i));
            }
        }
        sortResults(movies1);
        for (int i = 0; i < movies1.size(); i++){
            String title = movies1.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movies1.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n  Press Enter to Return to Main Menu");
        scanner.nextLine();

    }

    private void searchKeywords()
    {
        ArrayList<Movie> movie = new ArrayList<>();
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getKeywords().contains(keyword)) {
                movie.add(movies.get(i));
            }
        }
        sortResults(movie);
        for (int i = 0; i < movie.size(); i++) {
            System.out.println(1 + i + ". " +movie.get(i).getTitle());
        }
    }

    private void listGenres()
    {
        ArrayList<String> genres = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String genre = movies.get(i).getGenres();
            while (genre.indexOf("|") != -1) {
                String temp = genre.substring(0, genre.indexOf("|"));
                if (!genres.contains(temp)) {
                    genres.add(temp);
                }
                genre = genre.substring(genre.indexOf("|") + 1);
            }
            if (!genres.contains(genre)) {
                genres.add(genre);
            }
        }
            Collections.sort(genres);
            for (int a = 0; a < genres.size(); a++) {
                System.out.println(a + 1 + ". " + genres.get(a));
            }
            System.out.println("Which genre would you like to explore?");
            System.out.print("Print a number: ");
            int input = scanner.nextInt();
            int counter = 1;
            for (int b = 0; b < movies.size(); b++) {
                if (movies.get(b).getGenres().contains(genres.get(input - 1))) {
                    System.out.println(counter + ". " + movies.get(b).getTitle());
                    counter++;
                }
            }
        }

    private void listHighestRated()
    {
        ArrayList<Movie> movie = new ArrayList<>();
        movie = movies;
        for (int i = 0; i < movie.size() - 1; i++) {
            int minIdx = i;
            for (int a = i + 1; a < movie.size(); a++) {
                if (movie.get(a).getUserRating() > movie.get(minIdx).getUserRating()) {
                    minIdx = a;
                }
            }
            if (i != minIdx) {
                Movie temp = movie.get(i);
                movie.set(i, movie.get(minIdx));
                movie.set(minIdx, temp);
            }
            for (int y = 0; y < 50; y++) {
                System.out.println(y + 1 + ". " + movie.get(y).getTitle());
            }
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter the number: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            Movie meow = movie.get(choice - 1);
            displayMovieInfo(meow);
            System.out.println("\n  Press Enter to Return to Main Menu");
            scanner.nextLine();
        }
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> temp = movies;
        for (int a = 1; a < movies.size(); a++) {
            Movie movie = movies.get(a);
            int idx = a;
            while (idx > 0 && movie.getRevenue() > movies.get(idx - 1).getRevenue()) {
                temp.set(idx, movies.get(idx - 1));
                idx--;
            }
            temp.set(idx, movie);
        }
        for (int i = 0; i < 50; i++) {
            System.out.println(i + 1 + ". " + temp.get(i).getTitle());
        }
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}