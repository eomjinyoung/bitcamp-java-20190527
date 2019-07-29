package design_pattern.decorator.test1;

import java.util.ArrayList;

public class Dept extends Node {
  ArrayList<Node> list = new ArrayList<>();
  
  public void add(Node node) {
    list.add(node);
  }
}
