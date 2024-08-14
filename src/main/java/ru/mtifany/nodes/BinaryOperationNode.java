package ru.mtifany.nodes;

import java.util.Map;
import ru.mtifany.ParsingException;

public class BinaryOperationNode extends Node {
  private final Node leftNode;
  private final Node rightNode;
  private final char operator;

  public Node getLeftNode() {
    return leftNode;
  }

  public Node getRightNode() {
    return rightNode;
  }

  public BinaryOperationNode(Node leftNode, Node rightNode, char operator) {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
    this.operator = operator;
  }

  @Override
  public int evaluate(Map<String, Integer> variables) {
    int leftValue = leftNode.evaluate(variables);
    int rightValue = rightNode.evaluate(variables);
    switch (operator) {
      case '+': return leftValue + rightValue;
      case '-': return leftValue - rightValue;
      case '*': return leftValue * rightValue;
      case '/':
        if (rightValue == 0) {throw new ParsingException("Division by zero");}
      return leftValue / rightValue;
      default: throw new ParsingException("Unknown operator: " + operator);
    }
  }

  @Override
  public String toString() {
    return "(" + " " + operator + " " + ")";
  }
}
