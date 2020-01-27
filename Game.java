
public class Game implements Comparable {
	
	 	private String gameTitle;
	    private int releaseYear;
	    private String rating;

	    //Constructor
	    public Game(String title, int year, String rating) {
	        gameTitle = title;
	        releaseYear = year;
	        this.rating = rating;
	    }

	    //returns the game's title
	    public String getTitle() {
	        return this.gameTitle;
	    }

	  //returns the game's release year
	    public int getReleaseYear() {
	        return this.releaseYear;
	    }

	  //returns the game's ESRB rating
	    public String getRating() {
	        return this.rating;
	    }

	    
	    //compares two games - first by title alphabetically, then by release year
	    @Override
	    public int compareTo(Object other) {
	        Game otherGame = (Game)other;
	        int titleResult = this.getTitle().compareTo(otherGame.getTitle());
	        if (titleResult != 0) {
	            return titleResult;
	        }
	        else {
	            int difference = getReleaseYear() - otherGame.getReleaseYear();
	            if (difference < 0) {
	                return -1;
	            } else if (difference > 0) {
	                return 1;
	            } else {
	                return 0;
	            }
	        }
	    }

	    //returns the game's data in a formatted string
	    public String toString() {
	        return String.format("Title: %s, Year: %s, Rating: %s\n", gameTitle, releaseYear, rating);
	    }

}
