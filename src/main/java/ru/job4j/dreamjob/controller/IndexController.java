package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.utility.Utility;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class IndexController {
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        Utility.addUserInModel(model, session);
        return "index";
    }
}
