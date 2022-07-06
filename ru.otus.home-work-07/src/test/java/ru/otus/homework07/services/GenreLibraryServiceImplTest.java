package ru.otus.homework07.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework07.dto.GenreDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@DisplayName("Тестирование класса GenreLibraryServiceImpl")
class GenreLibraryServiceImplTest {

    private final GenreDto crime = new GenreDto().setId(1L).setCode("CRIME");
    private final GenreDto detective = new GenreDto().setId(2L).setCode("DETECTIVE");

    @Autowired
    private GenreLibraryServiceImpl service;

    @Test
    @DisplayName("Проверка получения всех элементов")
    void getAll() {
        List<GenreDto> expectedGenres = List.of(crime, detective);

        var all = service.getAll();

        assertArrayEquals(expectedGenres.toArray(), all.toArray());
    }

    @Test
    @DisplayName("Проверка получения элемента по ID")
    void findById() {
        var expectedGenre = Optional.ofNullable(crime);

        var foundGenre = service.findById(crime.getId());

        assertEquals(expectedGenre, foundGenre);
    }
}