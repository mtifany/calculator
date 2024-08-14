package ru.mtifany.nodes;

import java.util.Map;
import ru.mtifany.ParsingException;

public class NumberNode extends Node {
  private final int value;

  public NumberNode(int value) {
    if (value < 0 || value > 65535) {
      throw new ParsingException("Number out of range. Must be between 0 and 65535.");
    }
    this.value = value;
  }

  @Override
  public int evaluate(Map<String, Integer> variables) {
    return value;
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }
}


