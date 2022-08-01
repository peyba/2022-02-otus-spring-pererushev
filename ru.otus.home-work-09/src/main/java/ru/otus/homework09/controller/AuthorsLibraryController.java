package ru.otus.homework09.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework09.services.AuthorLibraryService;

@Controller
@RequiredArgsConstructor
public class AuthorsLibraryController {

    private final AuthorLibraryService service;

    @GetMapping("/authors")
    public String listPage(Model model) {
        var authors = service.getAll();
        model.addAttribute("authors", authors);
        return "authors";
    }
}