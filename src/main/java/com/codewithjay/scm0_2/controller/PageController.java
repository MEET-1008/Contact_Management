package com.codewithjay.scm0_2.controller;

import com.codewithjay.scm0_2.Repo.UserRepo;
import com.codewithjay.scm0_2.Service.EmailServices;
import com.codewithjay.scm0_2.Service.UserService;
import com.codewithjay.scm0_2.entities.User;
import com.codewithjay.scm0_2.forms.UserForms;
import com.codewithjay.scm0_2.helper.MessageHelper;
import com.codewithjay.scm0_2.helper.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailServices emailServices;


    @RequestMapping("/home")

    public String home(Model model) {

        System.out.println("Home is on");
        return "home";
    }

    @RequestMapping("/")

    public String index() {
        return "redirect:/home";
    }

//    about router      

    @RequestMapping("/about")
    public String about() {
        System.out.println("about page");
        return "about";
    }

//    services router

    @RequestMapping("/services")
    public String services() {
        System.out.println("services page");
        return "services";
    }

    @GetMapping("/delete/{id}")
    public void DeleteUser(@PathVariable int id){
        userService.deleteUser(id);



    }

//   contact page

    @RequestMapping("/contact")
    public String contact(Model model) {
        System.out.println("contact page");
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        UserForms userForm = new UserForms();

        model.addAttribute("userForm", userForm);

        return "signup";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String registerProcess(@Valid @ModelAttribute UserForms userForms, HttpSession session, BindingResult result) {


        System.out.println(userForms);

//        fetch the data for userForm and save data in database
        if (result.hasErrors()) {
            session.setAttribute("message", MessageHelper.builder().content("user already signup...! ").type(MessageType.red).build());

            return "redirect:/signup";
        }

        User user2 = userRepo.findByEmail(userForms.getEmail()).orElse(null);

        if (user2 == null) {
            userService.saveUsers(getUser(userForms));
            System.out.println("user saved email id is :" + userForms.getEmail());
            session.setAttribute("message", MessageHelper.builder().content("your contact added successfully").type(MessageType.green).build());
        }

        return "redirect:/signup";
    }


    private static User getUser(UserForms userForms) {
        User user1 = new User();
        user1.setUsername(userForms.getUsername());
        user1.setEmail(userForms.getEmail());
        user1.setPassword(userForms.getPassword());
        user1.setAbout(userForms.getAbout());
        user1.setEnabled(false);
        user1.setPhonenumber(userForms.getPhoneNumber());

        user1.setProfilepic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.m.wikipedia.org%2Fwiki%2FFile%3ADefault_pfp.svg&psig=AOvVaw28VcDklYcHi_KPECHdyFhq&ust=1717472793921000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCNCPtfHCvoYDFQAAAAAdAAAAABAE");
        return user1;
    }
}
