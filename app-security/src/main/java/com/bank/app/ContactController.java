package com.bank.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/contacts/")
public class ContactController {

    @GetMapping("/get-all")
    public String getContacts() {
        return "Returning all contacts";
    }

   // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addContact() {
        return "New contact added!";
    }

   // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteContact(@PathVariable int id) {
        return "Contact " + id + " deleted!";
    }

    @GetMapping("/public/info")
    public String publicInfo() {
        return "This is a public endpoint";
    }
}
