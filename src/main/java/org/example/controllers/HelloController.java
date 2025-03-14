package org.example.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
public class HelloController {
    @RequestMapping("/hello")
    @ResponseBody
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}