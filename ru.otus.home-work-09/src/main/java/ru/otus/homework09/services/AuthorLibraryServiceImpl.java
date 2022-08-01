package ru.otus.homework09.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.homework09.domain.Author;
import ru.otus.homework09.dto.AuthorDto;
import ru.otus.homework09.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorLibraryServiceImpl implements AuthorLibraryService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AuthorDto> getAll() {
        return convertAllToAuthorDto(authorRepository.findAll());
    }

    @Override
    public List<AuthorDto> findAllById(List<Long> ids) {
        return convertAllToAuthorDto(authorRepository.findAllById(ids));
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

    private List<AuthorDto> convertAllToAuthorDto(List<Author> authors) {
        List<AuthorDto> authorDtoList = new ArrayList<>();
        authors.forEach(a -> authorDtoList.add(convertToAuthorDto(a)));
        return authorDtoList;
    }
}