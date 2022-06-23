package ru.otus.homework07.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.homework07.domain.Author;
import ru.otus.homework07.dto.AuthorDto;
import ru.otus.homework07.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryAuthorServiceImpl implements LibraryAuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AuthorDto> getAll() {
        List<AuthorDto> authorDtoList = new ArrayList<>();
        authorRepository.findAll().forEach(a -> authorDtoList.add(convertToAuthorDto(a)));
        return authorDtoList;
    }

    @Override
    public Optional<AuthorDto> findById(Long id) {
        var author = authorRepository.findById(id);
        if (author.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(convertToAuthorDto(author.get()));
    }

    private AuthorDto convertToAuthorDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }
}