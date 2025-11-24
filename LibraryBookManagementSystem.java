// ===============================================
// Department: CSE
// Session: 2025-26
// Programme: B.Tech (CSE)
// Semester: 3rd
// Course Code: ENCS205
// Course: Data Structures
// Assignment 02: Linear Data Structures – Single Linked List and Stack
// Theme: Library Book Management System
// ===============================================

import java.util.*;

// ---------- Book Record Node (Linked List Node) ----------
class Book {
    int bookID;
    String title;
    String author;
    String status; // "Available" or "Issued"
    Book next;

    public Book(int bookID, String title, String author, String status) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.status = status;
        this.next = null;
    }
}

// ---------- Linked List Class ----------
class BookList {
    private Book head;

    // Insert new book
    public void insertBook(int id, String title, String author) {
        Book newBook = new Book(id, title, author, "Available");
        if (head == null) {
            head = newBook;
        } else {
            Book temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newBook;
        }
        System.out.println("✅ Book inserted successfully!");
    }

    // Delete book by ID
    public void deleteBook(int id) {
        if (head == null) {
            System.out.println("❌ No books to delete.");
            return;
        }

        if (head.bookID == id) {
            head = head.next;
            System.out.println("✅ Book deleted successfully!");
            return;
        }

        Book prev = null, temp = head;
        while (temp != null && temp.bookID != id) {
            prev = temp;
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("❌ Book not found.");
        } else {
            prev.next = temp.next;
            System.out.println("✅ Book deleted successfully!");
        }
    }

    // Search book by ID
    public Book searchBook(int id) {
        Book temp = head;
        while (temp != null) {
            if (temp.bookID == id)
                return temp;
            temp = temp.next;
        }
        return null;
    }

    // Display all books
    public void displayBooks() {
        if (head == null) {
            System.out.println("📚 No books in library.");
            return;
        }
        Book temp = head;
        System.out.println("\n------ Library Book List ------");
        while (temp != null) {
            System.out.println("ID: " + temp.bookID + " | Title: " + temp.title +
                    " | Author: " + temp.author + " | Status: " + temp.status);
            temp = temp.next;
        }
        System.out.println("--------------------------------");
    }

    public Book getHead() {
        return head;
    }
}

// ---------- Transaction Class ----------
class Transaction {
    String type; // "issue" or "return"
    int bookID;

    public Transaction(String type, int bookID) {
        this.type = type;
        this.bookID = bookID;
    }
}

// ---------- Stack-based Transaction Manager ----------
class TransactionManager {
    private BookList bookList;
    private Stack<Transaction> transactionStack;

    public TransactionManager(BookList bookList) {
        this.bookList = bookList;
        this.transactionStack = new Stack<>();
    }

    // Issue book
    public void issueBook(int id) {
        Book book = bookList.searchBook(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (book.status.equals("Issued")) {
            System.out.println(" Book is already issued.");
            return;
        }
        book.status = "Issued";
        transactionStack.push(new Transaction("issue", id));
        System.out.println("Book issued successfully!");
    }

    // Return book
    public void returnBook(int id) {
        Book book = bookList.searchBook(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (book.status.equals("Available")) {
            System.out.println(" Book is already available.");
            return;
        }
        book.status = "Available";
        transactionStack.push(new Transaction("return", id));
        System.out.println(" Book returned successfully!");
    }

    // Undo last transaction
    public void undoTransaction() {
        if (transactionStack.isEmpty()) {
            System.out.println("⚠️ No transactions to undo.");
            return;
        }
        Transaction last = transactionStack.pop();
        Book book = bookList.searchBook(last.bookID);
        if (book == null) return;

        if (last.type.equals("issue")) {
            book.status = "Available";
            System.out.println("↩️ Undo: Book issue reverted!");
        } else if (last.type.equals("return")) {
            book.status = "Issued";
            System.out.println("↩️ Undo: Book return reverted!");
        }
    }

    // View transaction history
    public void viewTransactions() {
        if (transactionStack.isEmpty()) {
            System.out.println("🧾 No transactions yet.");
            return;
        }
        System.out.println("\n------ Transaction History ------");
        for (Transaction t : transactionStack) {
            System.out.println("BookID: " + t.bookID + " | Type: " + t.type);
        }
        System.out.println("----------------------------------");
    }
}

// ---------- Main Class ----------
public class LibraryBookManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookList bookList = new BookList();
        TransactionManager manager = new TransactionManager(bookList);
        int choice;

        do {
            System.out.println("\n====== Library Book Management ======");
            System.out.println("1. Insert Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Search Book");
            System.out.println("4. Display Books");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Undo Last Transaction");
            System.out.println("8. View Transactions");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    bookList.insertBook(id, title, author);
                    break;

                case 2:
                    System.out.print("Enter Book ID to delete: ");
                    id = sc.nextInt();
                    bookList.deleteBook(id);
                    break;

                case 3:
                    System.out.print("Enter Book ID to search: ");
                    id = sc.nextInt();
                    Book b = bookList.searchBook(id);
                    if (b != null)
                        System.out.println(" Found -> Title: " + b.title + ", Author: " + b.author + ", Status: " + b.status);
                    else
                        System.out.println(" Book not found.");
                    break;

                case 4:
                    bookList.displayBooks();
                    break;

                case 5:
                    System.out.print("Enter Book ID to issue: ");
                    id = sc.nextInt();
                    manager.issueBook(id);
                    break;

                case 6:
                    System.out.print("Enter Book ID to return: ");
                    id = sc.nextInt();
                    manager.returnBook(id);
                    break;

                case 7:
                    manager.undoTransaction();
                    break;

                case 8:
                    manager.viewTransactions();
                    break;

                case 9:
                    System.out.println("👋 Exiting... Thank you!");
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
            }

        } while (choice != 9);
        sc.close();
    }
}
