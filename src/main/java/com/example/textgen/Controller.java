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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;   

import java.util.*;
import java.lang.IllegalArgumentException;

@RestController
public class Controller {

  @GetMapping(path="/sample") // Map ONLY POST Requests
  public @ResponseBody String sample () {
    return "Sample Connextion";
  }

}