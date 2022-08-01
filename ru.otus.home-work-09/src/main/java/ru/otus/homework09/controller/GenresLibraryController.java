package ru.otus.homework09.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework09.services.GenreLibraryService;

@Controller
@RequiredArgsConstructor
public class GenresLibraryController {

    private final GenreLibraryService service;

    @GetMapping("/genres")
    public String listPage(Model model) {
        var genres = service.getAll();
        model.addAttribute("genres", genres);
        return "genres";
    }
}