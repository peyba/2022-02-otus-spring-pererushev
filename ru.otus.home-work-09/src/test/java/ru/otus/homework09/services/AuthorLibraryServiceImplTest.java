package ru.otus.homework09.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework09.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@DisplayName("Тестирование класса AuthorLibraryService")
class AuthorLibraryServiceImplTest {

    private final AuthorDto author1 = new AuthorDto().setId(1L).setFirstName("Author1").setSecondName("Author1");
    private final AuthorDto author2 = new AuthorDto().setId(2L).setFirstName("Author2").setSecondName("Author2");

    @Autowired
    private AuthorLibraryServiceImpl service;

    @Test
    @DisplayName("Проверка получения всех элементов")
    void getAll() {
        List<AuthorDto> expectedAuthors = List.of(author1, author2);

        var all = service.getAll();

        assertArrayEquals(expectedAuthors.toArray(), all.toArray());
    }

    @Test
    @DisplayName("Проверка получения элемента по ID")
    void findById() {
        var expectedGenre = Optional.ofNullable(author1);

        var foundGenre = service.findById(author1.getId());

        assertEquals(expectedGenre, foundGenre);
    }
}