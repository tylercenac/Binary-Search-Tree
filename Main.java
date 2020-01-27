import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    private static boolean prettyPrint;
    private static String sortOrder;

    // Opens the file located at the provided directory and returns a Scanner for that file.

    public static Scanner GetInputFile(String UserPrompt) throws FileNotFoundException {

        Scanner fileScanner = null;                        // Scanner to read input file.
        boolean needInputFile = true;


        // Prompts the user for a file location and loops until a file that can be opened by fileScanner is provided.
        // If the user chooses not to continue, stop looping and end the program.
        do {
            System.out.print(UserPrompt);
            Scanner fileLocationScanner = new Scanner(System.in);
            String fileName = fileLocationScanner.nextLine();
            System.out.println();

            try {
                fileScanner = new Scanner(new File(fileName));
                needInputFile = false;
            } catch (FileNotFoundException ex) {
                System.out.printf("File specified <%s> does not exist. Would you like to continue? <Y/N> ", fileName);
                if (fileLocationScanner.nextLine().toLowerCase().equals("n")) {
                    throw ex;
                }
                System.out.println();
            }
        }
        while (needInputFile);

        return fileScanner;
    }

    // Opens the file located at the provided directory and returns a PrintWriter to write to that file.

    public static PrintWriter GetOutputFile(String userPrompt) throws FileNotFoundException {

        PrintWriter fileWriter = null;                     // PrintWriter to write results to a file.
        boolean needOutputFile = true;

        // Prompts the user for a file location and loops until a file that can be opened by fileWriter is provided.
        // If the user chooses not to continue, stop looping and end the program.
        // Also gives user the option to chose print format
        do {
            System.out.print("Enter output filename: ");
            Scanner fileLocationScanner = new Scanner(System.in);
            String fileName = fileLocationScanner.nextLine();
            System.out.println();
            sortOrder = null;
            try {
                fileWriter = new PrintWriter(fileName);
                needOutputFile = false;
                System.out.print("Would you like to output in the Pretty Print format? <Y/N> ");
                    if (fileLocationScanner.hasNext() && fileLocationScanner.next().equals("Y")) {
                        prettyPrint = true;
                    } else {
                        prettyPrint = false;
                    }
                if(prettyPrint) {
                	do {
                    System.out.print("Please input the sorting method for the file: <Inorder, Preorder, Postorder> ");
                    if (fileLocationScanner.hasNext()) {
                        String input = fileLocationScanner.next();
                        if (input.equals("Inorder") || input.equals("Postorder") || input.equals("Preorder")) {
                            sortOrder = input;
                        }
                    }
                	} while (sortOrder == null);
                }

            } //try
            catch (FileNotFoundException ex) {
                System.out.printf("File specified <%s> does not exist. Would you like to continue? <Y/N> ", fileName);
                if (fileLocationScanner.nextLine().toLowerCase().equals("n"))
                    throw ex;
                System.out.println();
            }//catch

        } while (needOutputFile);

        return fileWriter;

    }//GetOutputFile

    public static void main(String[] args) {

        Scanner fileScanner;                        // Scanner to read input file.
        PrintWriter fileWriter;                     // PrintWriter to write results to a file.
        bstOrderedList list = new bstOrderedList();     // Ordered list to hold sorted elements.


        //open input file
        try {

            fileScanner = GetInputFile("Enter input filename: ");

            // Goes through each line of the input file and performs operations on the ordered list.
            while (fileScanner.hasNextLine()) {
                String[] elements = fileScanner.nextLine().split(",");
                Game newGame;
                if (elements[0].equals("A")) {
                    newGame = new Game(elements[1], Integer.parseInt(elements[2]), elements[3]);
                    // Add item
                    list.add(newGame);
                }
                else if (elements[0].equals("D")) {
                    newGame = new Game(elements[1], Integer.parseInt(elements[2]), "");
                    // Delete item
                    list.remove(newGame);
                }
            }

            System.out.println();

            boolean moreOutput = false;
            do {
                //open output file
                fileWriter = GetOutputFile("Enter output filename: ");

                if (prettyPrint) {
                    // Writes the contents of the ordered list to an output file.
                    fileWriter.append(String.format("Number of games: %d\n", list.size()));
                  
                    Comparable[] contents = list.toArray(sortOrder);
                    
                    for(int i = 0; i < contents.length; i++) {
                        Game game = (Game)contents[i];
                        fileWriter.append(String.format("\nTitle:%10s%-20s\nYear:%11s%-20d\nRating:%9s%-20s\n", "",
                                game.getTitle(), "", game.getReleaseYear(), "", game.getRating()));
                    }
                } else {
                    fileWriter.append(list.toString());
                }

                fileWriter.close();

                Scanner scanner = new Scanner(System.in);
                System.out.print("Would you like to add more output files? <Y/N> ");
                if (scanner.hasNext() && scanner.next().equals("Y")) {
                    moreOutput = true;
                }
                else
                	moreOutput = false;

            } while (moreOutput);
        }//try
        catch (FileNotFoundException ex) {
            System.out.println("User terminated program");
        }//catch

    }
}
