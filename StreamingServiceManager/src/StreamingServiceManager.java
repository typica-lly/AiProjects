import java.util.Scanner;
import java.util.ArrayList;

interface Playable{
    void play();
    void stop();
}

abstract class MediaContent{
    private String title;
    private int duration;
    private double rating;
    static int totalViews = 0;

    public MediaContent(String title ,
                        int duration ,
                        double rating){
        this.title = title;
        this.duration = duration;
        this.rating = rating;
        totalViews++;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public double getRating() {
        return rating;
    }

    abstract void showDetails();
}

class Movie extends  MediaContent implements Playable{
    private String director;
    public Movie(String title , 
                 int duration ,
                 double rating,
                 String director ){
        super(title, duration, rating);
        this.director = director;
    }

    @Override
    public void play(){
        System.out.printf("[STREAM]: Playing movie: %s by %s\n", getTitle() , director);
        totalViews++;
    }

    @Override
    public void stop(){
        System.out.printf("[STOP]: Movie: %s has been paused.\n" , getTitle());
    }

    @Override
    public void showDetails(){
        System.out.printf("%-20s | %-8.1f | %-10d | Dir: %-20s\n", getTitle(), getRating() , getDuration() , director);
    }
}

class TVSeries extends MediaContent implements Playable{
    private int episodesCount;
    public TVSeries(String title,
                    int duration,
                    double rating,
                    int episodesCount){
        super(title, duration, rating);
        this.episodesCount = episodesCount;
    }

    @Override
    public void play(){
        System.out.printf("[STEAM]: Starting binge-watch of %s. Total: %d episodes.\n", getTitle(), episodesCount);
        totalViews++;
    }

    @Override
    public void stop(){
        System.out.printf("[STOP]: Series %s stopped.\n", getTitle());
    }

    @Override
    public void showDetails(){
        System.out.printf("%-20s | %-8.1f | %-10d | Episodes: %-20d\n", getTitle(), getRating() , getDuration(), episodesCount);
    }
}

public class StreamingServiceManager {
    public static void main(String[] args) {
        ArrayList<MediaContent> library = new ArrayList<>();
        Scanner userInput = new Scanner(System.in);
        int choice = 0;

        while (choice != 6) {
            System.out.println("=== NETFLIX COMMAND CENTER ===");
            System.out.println("1. Upload New Movie.");
            System.out.println("2. Upload New TV Series.");
            System.out.println("3. Content Library(Full Report).");
            System.out.println("4. Play Content(Global Stream).");
            System.out.println("5. Service Analytics(Total Views & Avg Rating).");
            System.out.println("6. Exit.");
            System.out.print("Your choice: ");

            try {
                choice = Integer.parseInt(userInput.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Enter number from 1 to 6.");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter Title: ");
                String title = userInput.nextLine();
                System.out.print("Enter Duration(minutes): ");
                int duration;
                try {
                    duration = Integer.parseInt(userInput.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid input.");
                    continue;
                }
                double rating = 0;
                while (true) {
                    try {
                        System.out.print("Enter Rating: ");
                        rating = Double.parseDouble(userInput.nextLine());

                        if (rating >= 0.0 && rating <= 5.0) {
                            break;
                        } else System.out.println("Error. Rating must be between 0.0 and 5.0");
                    } catch (NumberFormatException e) {
                        System.out.println(" Error: Invalid input. ");
                    }
                }
                System.out.print("Enter Director: ");
                String director = userInput.nextLine();

                library.add(new Movie(title, duration, rating, director));
                System.out.println("Movie added successfully");

            } else if (choice == 2) {
                System.out.print("Enter Title: ");
                String title = userInput.nextLine();
                int duration;
                try {
                    System.out.print("Enter Duration: ");
                    duration = Integer.parseInt(userInput.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid input.");
                    continue;
                }

                double rating = 0;
                while (true) {
                    try {
                        System.out.print("Enter Rating: ");
                        rating = Double.parseDouble(userInput.nextLine());

                        if (rating >= 0.0 && rating <= 5.0) break;
                        else System.out.println("Error. Rating must be between 0.0 and 5.0");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid input.");
                    }
                }
                int episodesCount = 0 ;
                try{
                    System.out.print("Enter Episodes Count: ");
                    episodesCount = Integer.parseInt(userInput.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Error. Invalid input.");
                    continue;
                }
                
                library.add(new TVSeries(title, duration, rating, episodesCount));
            } else if (choice == 3) {
                if (library.isEmpty()) System.out.println("Library is empty. Upload the content first.");
                else {
                    System.out.printf("%-20s | %-8s | %-10s | %-20s\n", "TITLE", "RATING", "DURATION", "EXTRA INFO");
                    System.out.println("---------------------------------------------------");
                    for (MediaContent item : library){
                        item.showDetails();
                    }
                    System.out.println("---------------------------------------------------");

                }
            } else if (choice == 4) {
                if (library.isEmpty()) System.out.println("Nothing to play. Library is empty.");
                else{
                    for (MediaContent item : library){
                        if (item instanceof  Playable playable){
                            playable.play();
                            playable.stop();
                            System.out.println("--------");
                        }
                    }
                    System.out.println("All content processed. Global views update.");
                }
            }else if(choice == 5){
                double sum = 0;
                if (library.isEmpty()) System.out.println("Library is empty.");
                else {
                    for(int i = 0 ; i < library.size() ; i++){
                        sum += library.get(i).getRating();
                    }
                    double avg = sum / library.size();

                    System.out.println("\n=== Platform analytics");
                    System.out.printf("Total Content Views: %d\n", MediaContent.totalViews);
                    System.out.printf("Average User Rating: %.2f / 5.0\n", avg);
                    System.out.println("==========");
                }
            }

        }
    }
}