package project20280.stacksqueues;

class BracketChecker {
    private final String input;

    public BracketChecker(String in) {
        input = in;
    }

    public void check() {
        ArrayStack<Character> stack = new ArrayStack<>();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }
            else if (ch == ')' || ch == ']' || ch == '}') {
                Character top = stack.pop();
                if (top == null) {
                    System.out.println("ERROR: '" + ch + "' at index " + i + " has no match");
                    return;
                }
                if (!bracketsMatch(top, ch)) {
                    System.out.println("ERROR: '" + ch + "' at index " + i + " does not match '" + top + "'");
                    return;
                }
            }
        }

        if (!stack.isEmpty()) {
            System.out.println("ERROR: unmatched brackets");
        }
        else {
            System.out.println("OK");
        }
    }

    private boolean bracketsMatch(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '[' && close == ']') ||
                (open == '{' && close == '}');
    }

    public static void main(String[] args) {
        String[] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct\n" +
                "a{b[c]d}e", // correct\n" +
                "a{b(c]d}e", // not correct; ] doesn't match (\n" +
                "a[b{c}d]e}", // not correct; nothing matches final }\n" +
                "a{b(c) ", // // not correct; Nothing matches opening {
        };

        for (String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            checker.check();
        }
    }
}