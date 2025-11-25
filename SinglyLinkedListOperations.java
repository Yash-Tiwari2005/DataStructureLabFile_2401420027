import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class SinglyLinkedList {

    Node head = null;

    // Insert at beginning
    void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        System.out.println("Inserted at beginning.");
    }

    // Insert at end
    void insertAtEnd(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            System.out.println("Inserted at end.");
            return;
        }

        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
        System.out.println("Inserted at end.");
    }

    // Insert at position
    void insertAtPosition(int data, int pos) {
        Node newNode = new Node(data);

        if (pos == 1) {
            newNode.next = head;
            head = newNode;
            System.out.println("Inserted at position " + pos);
            return;
        }

        Node temp = head;
        for (int i = 1; i < pos - 1 && temp != null; i++) {
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("Position out of range!");
            return;
        }

        newNode.next = temp.next;
        temp.next = newNode;

        System.out.println("Inserted at position " + pos);
    }

    // Delete first node
    void deleteFirst() {
        if (head == null) {
            System.out.println("List is empty!");
            return;
        }

        head = head.next;
        System.out.println("First node deleted.");
    }

    // Delete last node
    void deleteLast() {
        if (head == null) {
            System.out.println("List is empty!");
            return;
        }

        if (head.next == null) {
            head = null;
            System.out.println("Last node deleted.");
            return;
        }

        Node temp = head;
        while (temp.next.next != null) {
            temp = temp.next;
        }

        temp.next = null;
        System.out.println("Last node deleted.");
    }

    // Delete by value
    void deleteByValue(int value) {
        if (head == null) {
            System.out.println("List is empty!");
            return;
        }

        if (head.data == value) {
            head = head.next;
            System.out.println("Node with value " + value + " deleted.");
            return;
        }

        Node temp = head;
        while (temp.next != null && temp.next.data != value) {
            temp = temp.next;
        }

        if (temp.next == null) {
            System.out.println("Value not found!");
        } else {
            temp.next = temp.next.next;
            System.out.println("Node with value " + value + " deleted.");
        }
    }

    // Search value
    void search(int value) {
        Node temp = head;
        int pos = 1;

        while (temp != null) {
            if (temp.data == value) {
                System.out.println("Value " + value + " found at position " + pos);
                return;
            }
            temp = temp.next;
            pos++;
        }

        System.out.println("Value not found!");
    }

    // Sort list (Ascending)
    void sortList() {
        if (head == null) {
            System.out.println("List is empty!");
            return;
        }

        Node current = head;
        Node index = null;

        while (current != null) {
            index = current.next;

            while (index != null) {
                if (current.data > index.data) {
                    // swap
                    int temp = current.data;
                    current.data = index.data;
                    index.data = temp;
                }
                index = index.next;
            }
            current = current.next;
        }

        System.out.println("List sorted successfully.");
    }

    // Display list
    void display() {
        if (head == null) {
            System.out.println("List is empty!");
            return;
        }

        Node temp = head;
        System.out.print("Linked List: ");

        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }

        System.out.println("NULL");
    }

    // Main function / Menu-driven program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SinglyLinkedList list = new SinglyLinkedList();

        while (true) {
            System.out.println("\n===== Singly Linked List Menu =====");
            System.out.println("1. Insert at Beginning");
            System.out.println("2. Insert at End");
            System.out.println("3. Insert at Position");
            System.out.println("4. Delete First");
            System.out.println("5. Delete Last");
            System.out.println("6. Delete by Value");
            System.out.println("7. Search");
            System.out.println("8. Sort List");
            System.out.println("9. Display List");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value: ");
                    list.insertAtBeginning(sc.nextInt());
                    break;

                case 2:
                    System.out.print("Enter value: ");
                    list.insertAtEnd(sc.nextInt());
                    break;
                case 3:
                    System.out.print("Enter value: ");
                    int val = sc.nextInt();
                    System.out.print("Enter position: ");
                    int pos = sc.nextInt();
                    list.insertAtPosition(val, pos);
                    break;
                case 4:
                    list.deleteFirst();
                    break;
                case 5:
                    list.deleteLast();
                    break;
                case 6:
                    System.out.print("Enter value to delete: ");
                    list.deleteByValue(sc.nextInt());
                    break;
                case 7:
                    System.out.print("Enter value to search: ");
                    list.search(sc.nextInt());
                    break;
                case 8:
                    list.sortList();
                    break;
                case 9:
                    list.display();
                    break;
                case 10:
                    System.out.println("Exiting program...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
