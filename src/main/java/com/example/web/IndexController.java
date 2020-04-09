package com.example.web;

import com.example.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/{id}/{name}")
    public String index(@PathVariable("id") Integer id, @PathVariable("name") String name) {
//        int i = 9/0;
//        String blog = null;
//        if (blog == null) {
//            throw new NotFoundException("blog is null");
//        }

        System.out.println("id: " + id + ", name: " + name);
        return "index";
    }
}
