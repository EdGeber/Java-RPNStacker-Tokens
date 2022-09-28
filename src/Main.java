import stacker.rpn.lexer.Token;
import stacker.rpn.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {
    private static List<Token> tokenize(Scanner scanner) {
        List<Token> tokens = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String thing = scanner.nextLine();
            switch (thing) {
                case "+" -> tokens.add(new Token(TokenType.PLUS,  "+"));
                case "-" -> tokens.add(new Token(TokenType.MINUS, "-"));
                case "*" -> tokens.add(new Token(TokenType.STAR,  "*"));
                case "/" -> tokens.add(new Token(TokenType.SLASH, "/"));
                default -> {
                    try {
                        Integer.parseInt(thing);
                    }
                    catch (Error e){
                        System.out.println("Error: Unexpected character: " + thing.charAt(0));
                        e.printStackTrace();
                    }
                    tokens.add(new Token(TokenType.NUM, thing));
                }
            }
        }
        scanner.close();
        return tokens;
    }
    private static int evaluate(List<Token> tokens) {
        Stack<Integer> S = new Stack<>();
        for(int i=0; i<tokens.size(); i++) {
            Token token = tokens.get(i);
            int a, b;
            switch (token.type) {
                case PLUS -> {
                    b = S.pop();
                    a = S.pop();
                    S.push(a + b);
                }
                case MINUS -> {
                    b = S.pop();
                    a = S.pop();
                    S.push(a - b);
                }
                case STAR -> {
                    b = S.pop();
                    a = S.pop();
                    S.push(a * b);
                }
                case SLASH -> {
                    b = S.pop();
                    a = S.pop();
                    S.push(a / b);
                }
                default -> S.push(Integer.parseInt(token.lexeme));
            }
        }
        int result = S.pop();
        return result;
    }
    public static void main(String[] args) {
        // System.out.println("Hello world!");
        Stack<Integer> S = new Stack<>();
        Scanner scanner;
        try {
            File file = new File(args[0]);
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
            return;
        }
        List<Token> tokens = tokenize(scanner);
        int result = evaluate(tokens);
        System.out.println(result);
    }
}
