import java.util.Stack;

/**
* * ----------STUDENT DETAILS ------------ 
* NAME:KANGWA KAPENDA
* SIN: 202410254
* PROGRAM: COMPUTER SCIENCE
/**
 * Infix,prefix,postfix expr: A robust utility for notation conversion.
 * * --- ALGORITHM PSEUDO-CODE ---
 * 1. Initialize an empty Stack for operators and a StringBuilder for the output.
 * 2. Scan the Infix string from left to right:
 * a. If character is an OPERAND: Append to output.
 * b. If character is '(': Push to Stack.
 * c. If character is ')': Pop from Stack to output until '(' is reached. Pop '('.
 * d. If character is an OPERATOR:
 * - While (Stack top has >= precedence AND Stack top is not '('):
 * Pop from Stack to output.
 * - Push current operator to Stack.
 * 3. Pop all remaining operators from Stack to output.
 */
public class InfixExp {

    /**
     * Assigns numeric priority to operators.
     * Uses Java 17 switch expressions for clarity.
     */
    private static int getPrecedence(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^'      -> 3;
            default       -> -1;
        };
    }

    public static String infixToPostfix(String exp) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : exp.toCharArray()) {
            // Skip whitespaces to avoid processing errors
            if (Character.isWhitespace(c)) continue;

            // Case A: Operand (Letter or Digit)
            if (Character.isLetterOrDigit(c)) {
                output.append(c);
            } 
            // Case B: Left Parenthesis
            else if (c == '(') {
                stack.push(c);
            } 
            // Case C: Right Parenthesis
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop(); // Remove the '('
            } 
            // Case D: Operator
            else {
                /*
                 * Logic: We cannot place a lower-precedence operator on top of a 
                 * higher-precedence one. We must pop the higher ones first.
                 */
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    output.append(stack.pop());
                }
                stack.push(c);
            }
        }

        // Final step: Clear the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') return "Invalid Expression: Mismatched Parens";
            output.append(stack.pop());
        }

        return output.toString();
    }

    public static void main(String[] args) {
        String input = "A + B * (C ^ D - E)";
        System.out.println("Infix:   " + input);
        System.out.println("Postfix: " + infixToPostfix(input));
    }
}
