package ru.mtifany;

import ru.mtifany.nodes.BinaryOperationNode;
import ru.mtifany.nodes.Node;
import ru.mtifany.nodes.NumberNode;
import ru.mtifany.nodes.VariableNode;

public class ExpressionParser {
  private final String expression;
  private int position = 0;

  public ExpressionParser(String expression) {
    if (!isValidExpression(expression)) {
      throw new IllegalArgumentException("Expression contains invalid characters.");
    }
    this.expression = expression;
  }

  private boolean isValidExpression(String expression) {
    return expression.matches("[a-zA-Z0-9+\\-*/() ]+");
  }

  public Node parse() {
    Node result = parseExpression();
    if (position < expression.length()) {
      throw new IllegalArgumentException("Unexpected character at position " + position);
    }
    return result;
  }

  private Node parseExpression() {
    Node leftNode = parseTerm();
    while (position < expression.length()) {
      char op = expression.charAt(position);
      if (op == '+' || op == '-') {
        position++;
        Node rightNode = parseTerm();
        leftNode = new BinaryOperationNode(leftNode, rightNode, op);
      } else {
        break;
      }
    }
    return leftNode;
  }

  private Node parseTerm() {
    Node leftNode = parseFactor();
    while (position < expression.length()) {
      char op = expression.charAt(position);
      if (op == '*' || op == '/') {
        position++;
        Node rightNode = parseFactor();
        leftNode = new BinaryOperationNode(leftNode, rightNode, op);
      } else {
        break;
      }
    }
    return leftNode;
  }

  private Node parseFactor() {
    skipWhitespace();
    if (position >= expression.length()) {
      throw new ParsingException("Unexpected end of expression");
    }
    char ch = expression.charAt(position);
    if (Character.isDigit(ch)) {
      int start = position;
      while (position < expression.length() && Character.isDigit(expression.charAt(position))) {
        position++;
      }
      return new NumberNode(Integer.parseInt(expression.substring(start, position)));
    } else if (Character.isLetter(ch)) {
      int start = position;
      while (position < expression.length() && (Character.isLetter(expression.charAt(position)) ||
          Character.isDigit(expression.charAt(position)))) {
        position++;
      }
      return new VariableNode(expression.substring(start, position));
    } else if (ch == '(') {
      position++;
      Node node = parseExpression();
      if (position >= expression.length() || expression.charAt(position) != ')') {
        throw new ParsingException("Mismatched parentheses");
      }
      position++;
      return node;
    } else {
      throw new ParsingException("Unexpected character: " + ch);
    }
  }

  private void skipWhitespace() {
    while (position < expression.length() && Character.isWhitespace(expression.charAt(position))) {
      position++;
    }
  }
}
