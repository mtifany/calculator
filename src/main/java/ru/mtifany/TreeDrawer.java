package ru.mtifany;

import ru.mtifany.nodes.*;

public class TreeDrawer {
  public static void drawTree(Node node) {
    drawTree(node, "", true);
  }

  private TreeDrawer() {
  }

  private static void drawTree(Node node, String indent, boolean last) {
    if (node instanceof NumberNode || node instanceof VariableNode) {
      System.out.println(indent + (last ? "\\-" : "|-") + node);
    } else if (node instanceof BinaryOperationNode) {
      System.out.println(indent + (last ? "\\-" : "|-") + node);
      indent += last ? "  " : "| ";
      BinaryOperationNode opNode = (BinaryOperationNode) node;
      drawTree(opNode.getLeftNode(), indent, false);
      drawTree(opNode.getRightNode(), indent, true);
    }
  }
}
