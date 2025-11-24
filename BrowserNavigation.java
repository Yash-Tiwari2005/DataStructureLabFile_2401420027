import java.util.Scanner;
import java.util.Stack;

public class BrowserNavigation {

    // Two stacks: back and forward
    static Stack<String> backStack = new Stack<>();
    static Stack<String> forwardStack = new Stack<>();

    // Current page
    static String currentPage = "Home";

    // Visit a new page
    static void visitPage(String url) {
        backStack.push(currentPage);   // Move current page to backStack
        currentPage = url;             // Navigate to new page
        forwardStack.clear();          // Clear forward history
        System.out.println("Visited: " + currentPage);
    }

    // Go Back
    static void goBack() {
        if (backStack.isEmpty()) {
            System.out.println("No pages in Back History!");
            return;
        }

        forwardStack.push(currentPage);    // Move current to forward
        currentPage = backStack.pop();     // Get last visited
        System.out.println("Moved Back to: " + currentPage);
    }

    // Go Forward
    static void goForward() {
        if (forwardStack.isEmpty()) {
            System.out.println("No pages in Forward History!");
            return;
        }

        backStack.push(currentPage);    // Move current to back
        currentPage = forwardStack.pop(); // Get next page
        System.out.println("Moved Forward to: " + currentPage);
    }

    // Display All History
    static void displayHistory() {
        System.out.println("\n------ Browser History ------");
        System.out.println("Back Stack: " + backStack);
        System.out.println("Current Page: " + currentPage);
        System.out.println("Forward Stack: " + forwardStack);
        System.out.println("------------------------------\n");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("===== Browser Navigation Menu =====");
            System.out.println("1. Visit New Page");
            System.out.println("2. Go Back");
            System.out.println("3. Go Forward");
            System.out.println("4. Show History");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter URL to visit: ");
                    String url = sc.nextLine();
                    visitPage(url);
                    break;

                case 2:
                    goBack();
                    break;

                case 3:
                    goForward();
                    break;

                case 4:
                    displayHistory();
                    break;

                case 5:
                    System.out.println("Exiting Browser...");
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
