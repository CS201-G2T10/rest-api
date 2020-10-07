package com.example.textgen;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.example.textgen.WeightedDirectedGraph;

import org.springframework.beans.factory.annotation.Autowired;   

import java.util.*;
import java.lang.IllegalArgumentException;

@RestController
public class Controller {
  WeightedDirectedGraph graph;

  @Autowired
  public Controller() {
    this.graph = new WeightedDirectedGraph();
    this.graph.readGraph();
  }

  @GetMapping(path="/sample") // Map ONLY POST Requests
  public String sample() {
    return "Sample Connection";
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @RequestMapping(value="/api/model", method = RequestMethod.POST, consumes={"application/x-www-form-urlencoded"})
  public Response getModel(@RequestBody MultiValueMap<String, String> reqBody) {
    System.out.println("POST /api/model | " + reqBody);
    Response res = new Response();
    res.setLength(Integer.parseInt(reqBody.getFirst("length")));
    res.setWord(reqBody.getFirst("string"));
    String sentence = this.graph.predictWord(res.getLength(), res.getWord());
    res.setSentence(sentence);
    return res;
  }


}