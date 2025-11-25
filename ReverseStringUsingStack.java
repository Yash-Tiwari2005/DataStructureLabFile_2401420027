import java.util.Scanner;
import java.util.Stack;

public class ReverseStringUsingStack {

    // Function to reverse string using stack
    public static String reverseString(String str) {
        Stack<Character> stack = new Stack<>();

        // Push all characters into stack
        for (char ch : str.toCharArray()) {
            stack.push(ch);
        }

        // Pop characters to build reversed string
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

        return reversed.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        String result = reverseString(input);
        System.out.println("Reversed String: " + result);
    }
}
