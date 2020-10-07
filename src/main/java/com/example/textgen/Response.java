package com.example.textgen;
public class Response {
  private String word;
  private String sentence;
  private Integer length;
  
  public Response() {}
  public String getWord() { return this.word; }
  public String getSentence() { return this.sentence; }
  public Integer getLength() { return this.length; }
  public void setWord(String word) { this.word = word; }
  public void setSentence(String sentence) { this.sentence = sentence; }
  public void setLength(Integer length) { this.length = length; }
}