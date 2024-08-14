package ru.mtifany.nodes;

import java.util.Map;

public abstract class Node {
  public abstract int evaluate(Map<String, Integer> variables);
  public abstract String toString();
}

