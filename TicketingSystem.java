import java.util.Scanner;

class TicketQueue {
    private int size;
    private String[] queue;
    private int front;
    private int rear;

    // Constructor
    public TicketQueue(int size) {
        this.size = size;
        this.queue = new String[size];
        this.front = -1;
        this.rear = -1;
    }

    // Check if queue is full
    public boolean isFull() {
        return rear == size - 1;
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return front == -1 || front > rear;
    }

    // Enqueue - Add Ticket Request
    public void enqueue(String ticketId) {
        if (isFull()) {
            System.out.println("Queue is Full! Cannot add more ticket requests.");
            return;
        }

        if (front == -1) {
            front = 0;
        }

        rear++;
        queue[rear] = ticketId;
        System.out.println("Ticket Request Added: " + ticketId);
    }

    // Dequeue - Process Ticket
    public String dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is Empty! No ticket to process.");
            return null;
        }

        String ticket = queue[front];
        System.out.println("Ticket Processed: " + ticket);
        front++;
        return ticket;
    }

    // Display Pending Tickets
    public void display() {
        if (isEmpty()) {
            System.out.println("No pending ticket requests.");
            return;
        }

        System.out.print("Pending Tickets: ");
        for (int i = front; i <= rear; i++) {
            System.out.print(queue[i] + " ");
        }
        System.out.println();
    }
}


// ----------------------------------------------------------
// Main Program (Menu Driven)
// ----------------------------------------------------------

public class TicketingSystem {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TicketQueue q = new TicketQueue(5);  // Queue with capacity 5

        while (true) {
            System.out.println("\n======= Ticketing System =======");
            System.out.println("1. Add Ticket Request (Enqueue)");
            System.out.println("2. Process Ticket (Dequeue)");
            System.out.println("3. Show Pending Tickets");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter Ticket ID: ");
                    String ticketId = sc.nextLine();
                    q.enqueue(ticketId);
                    break;

                case "2":
                    q.dequeue();
                    break;

                case "3":
                    q.display();
                    break;

                case "4":
                    System.out.println("Exiting Ticketing System.");
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a number between 1-4.");
            }
        }
    }
}
