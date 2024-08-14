package ru.mtifany;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.mtifany.nodes.Node;


public class Calculator {
  public static void main(String[] args) {
    Random rand = new Random();
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Enter an expression:");
      String expression = getNewInputLine(scanner);
      Node astNode = getAstNode(expression);
      Map<String, Integer> variables = getVariables(expression, rand);
      System.out.println("Result: " + astNode.evaluate(variables));
      boolean[] keepRunning = {true};
      while (keepRunning[0]) {
        Map<String, Runnable> commands = getCommandsMap(keepRunning, astNode, variables);
        System.out.println(
            "Enter a command ('calc', 'print', 'x=20, y=30' (should be separated with ','), or 'quit')");
        String input = getNewInputLine(scanner);
        Runnable action = commands.getOrDefault(input.toLowerCase(),
            () -> parseVariablesNames(input, variables));
        action.run();
      }
    }
  }

  private static Node getAstNode(String expression) {
    ExpressionParser expressionParser = new ExpressionParser(expression);
    return expressionParser.parse();
  }

  private static Map<String, Runnable> getCommandsMap(boolean[] keepRunning, Node astNode,
                                                      Map<String, Integer> variables) {
    Map<String, Runnable> commands = new HashMap<>();
    commands.put("quit", () -> keepRunning[0] = false);
    commands.put("calc", () -> System.out.println("Result: " + astNode.evaluate(variables)));
    commands.put("print", () -> TreeDrawer.drawTree(astNode));
    commands.put("", () -> System.out.println("Empty input"));
    commands.put(null, () -> System.out.println("Empty input"));
    return commands;
  }

  private static String getNewInputLine(Scanner scanner) {
    return scanner.nextLine().replaceAll("\\s+", "");
  }

  private static void parseVariablesNames(String input, Map<String, Integer> variables) {
    for (String equating : input.split(",")) {
      checkEquating(equating);
      String[] parts = equating.split("=");
      if (parts.length == 2) {
        parseVariables(variables, parts);
      } else {
        throw new ParsingException("Invalid command");
      }
    }
  }

  private static void parseVariables(Map<String, Integer> variables, String[] parts) {
    String name = parts[0];
    if (variables.containsKey(name)) {
      long value = Long.parseLong(parts[1]);
      checkMaxValue(value);
      variables.put(name, Math.toIntExact(value));
    } else {
      throw new ParsingException("Variable " + name + " not found");
    }
  }

  private static void checkMaxValue(long value) {
    if (value > 65536) {
      throw new ParsingException("Max variable value is 65536");
    }
  }

  private static void checkEquating(String equating) {
    if (!equating.matches("^\\s*[a-zA-Z][a-zA-Z0-9]*\\s*=\\s*\\d{1,5}$")) {
      throw new ParsingException("Invalid command");
    }
  }

  private static Map<String, Integer> getVariables(String expression, Random rand) {
    Map<String, Integer> variables = new HashMap<>();
    Pattern variablePattern = Pattern.compile("\\b[a-zA-Z]\\w*\\b");
    Matcher matcher = variablePattern.matcher(expression);
    Set<String> variableNames = new HashSet<>();
    while (matcher.find()) {
      variableNames.add(matcher.group());
    }
    if(variableNames.isEmpty()){
      return variables;
    }
    for (String variable : variableNames) {
      variables.put(variable, rand.nextInt(10) + 1);
    }
      System.out.println("Variables initialized with random values:");

    for (Map.Entry<String, Integer> entry : variables.entrySet()) {
      System.out.println(entry.getKey() + " = " + entry.getValue());
    }
    return variables;
  }
}
