package ru.mtifany.nodes;

import java.util.Map;
import ru.mtifany.ParsingException;

public class VariableNode extends Node {
  private final String name;

  public void setValue(Integer value) {
    this.value = value;
  }

  private Integer value;

  public VariableNode(String name) {
    if (!name.matches("[a-zA-Z]\\w*")) {
      throw new ParsingException("Invalid variable name: " + name);
    }
    this.value = 0;
    this.name = name;
  }

  @Override
  public int evaluate(Map<String, Integer> variables) {
    setValue(variables.getOrDefault(name, 0));
    return variables.getOrDefault(name, 0);
  }

  @Override
  public String toString() {
    return  "(" + name + " = " + value + ")";
  }
}