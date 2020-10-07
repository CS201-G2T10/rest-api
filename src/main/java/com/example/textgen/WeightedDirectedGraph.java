package com.example.textgen;
import java.util.*;
import java.lang.*;
import java.io.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class WeightedDirectedGraph {
  private List<Node> words;

  public static class Edge {
    private Node node;
    private int weight;
    
    public Edge(Node node) {
      this.node = node;
      this.weight = 1;
    }
    public Node getNode() { return this.node; }
    public int getWeight() { return this.weight; }
    public void setWeight() { this.weight++; }
    public void setWeight(int weight) { this.weight = weight; }
  }
  public static class Node {
    private String word;
    private List<Edge> edges;

    public Node() { this.edges = new ArrayList<>(); }
    public Node(String word) {
      this.word = word;
      this.edges = new ArrayList<>();
    }
    public String toString() { 
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      sb.append(this.word);
      // sb.append(" Edges: ");
      // for (Edge edge : this.edges) {
      //   sb.append(edge.getNode());
      //   sb.append(edge.getWeight());
      // }
      sb.append("]");

      return sb.toString();
    }

    public String getWord() { return this.word; }
    public void setWord() { this.word = word; }
    public List<Edge> getEdge() { return this.edges; }
    public Edge setEdge(Node node) {
      Edge edge = this.findEdge(node.getWord());
      if (edge == null) {
        edge = new Edge(node);
        this.edges.add(edge);
      } else {
        edge.setWeight();
      }
      return edge;
    }

    public Edge findEdge(String word) {
      for (Edge edge : this.edges) {
        if (edge.getNode().getWord().equals(word)) return edge;
      }
      return null;
    }

  }

  public WeightedDirectedGraph() { this.words = new ArrayList<>(); }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node word : this.words) {
      sb.append(word);
    }
    return sb.toString();
  }

  public void addNode(String prevWord, String currentWord) {
    if (currentWord.equals("")) return;
    Node currentNode = this.findNode(currentWord);
    Node prevNode = prevWord == null ? null : this.findNode(prevWord);
    if (currentNode == null) {
      currentNode = new Node(currentWord.toLowerCase());
      this.words.add(currentNode);
    }
    if (prevNode == null) return;
    prevNode.setEdge(currentNode);
  }

  public Node findNode(String currentWord) {
    for (Node word : this.words) {
      if (word.getWord().equals(currentWord.toLowerCase())) return word;
    }
    return null;
  }

  public String predictWord(int number, String startingWord) {
    Node startingNode = this.findNode(startingWord);
    if (startingNode == null) {
      return "Error";
    }

    StringBuilder sb = new StringBuilder();
    sb.append(startingWord + " ");
    Node nextPredictedNode = new Node();

    for (int i = 0; i < number; i++) {
      int totalWeight = 0;
      double random = Math.random();
      int j = 0;
      int k = 0;
      Map<Integer, Edge> edgeMap = new HashMap<>();
      for (Edge edge : startingNode.getEdge()) {
        // System.out.println(edge.getNode());
        totalWeight += edge.getWeight();
        while (j < edge.getWeight()) {
          edgeMap.put(k, edge);
          j++;
          k++;
        }
        j = 0;
      }
      int randomPointer = (int)(random * totalWeight);

      if (edgeMap.get(randomPointer) == null) {
        // sb.append("!@#$%^&*()");
        return sb.toString();
      }

      nextPredictedNode = edgeMap.get(randomPointer).getNode();
      sb.append(nextPredictedNode.word + " ");
      startingNode = nextPredictedNode;
      // System.out.println("----------------------------------------------------");
    }
    return sb.toString();
  }


  public void saveGraph() {
    
    try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {

      StringBuilder sb = new StringBuilder();
      for (Node node : words) {
        sb.append(node.getWord() + ",");
        for (Edge edge : node.getEdge()) {
          sb.append(edge.getNode().getWord() + " ");
          sb.append(edge.getWeight() + ",");
        }
        sb.append('\n');
      }
      writer.write(sb.toString());
      System.out.println("done!");

    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

  }

  public void readGraph() {
    String csvFile = "test.csv";
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";

    try {
      // File file = ResourceUtils.getFile("classpath:application.properties");
      br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:test.csv")));
      while ((line = br.readLine()) != null) {
        // use comma as separator
        String[] word = line.split(cvsSplitBy);

        Node node = this.findNode(word[0]);
        if (node == null) {
          node = new Node(word[0]);
          this.words.add(node);
        }
        
        for (int i = 1; i < word.length; i++) {
          String[] wordArr = word[i].split(" ");
          
          Node nodeEdge = this.findNode(wordArr[0]);
          if (nodeEdge == null) {
            nodeEdge = new Node(wordArr[0]);
            this.words.add(nodeEdge);
            
          }
          Edge edge = node.setEdge(nodeEdge);

          edge.setWeight(Integer.parseInt(wordArr[1]));
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }

}