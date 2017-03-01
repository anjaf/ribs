package uk.com.ebi.biostudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ehsan on 23/02/2017.
 */
@Controller
public class PageController {
    @RequestMapping("/test")
    public ModelAndView helloWorld() {

        String message = "Hello World, Spring 3.0!";
        return new ModelAndView("detail");
    }

}
