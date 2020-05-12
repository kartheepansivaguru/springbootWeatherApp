package com.hackerrank.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    /*@GetMapping("/greeting")
    public String greeting(@RequestParam(name="name",required=false,defaultValue="World")String name,Model model) {
		return name;
    	
    }
    
    @Value("#{'${num}'.split(',')[1]}")
    private Integer num;*/
}
