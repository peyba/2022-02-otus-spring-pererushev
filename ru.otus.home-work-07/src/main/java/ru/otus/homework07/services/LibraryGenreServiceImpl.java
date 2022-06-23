package ru.otus.homework07.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.homework07.domain.Genre;
import ru.otus.homework07.dto.GenreDto;
import ru.otus.homework07.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryGenreServiceImpl implements LibraryGenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GenreDto> getAll() {
        List<GenreDto> genreDtoList = new ArrayList<>();
        genreRepository.findAll().forEach(g -> genreDtoList.add(convertToGenreDto(g)));
        return genreDtoList;
    }

    @Override
    public Optional<GenreDto> findById(Long id) {
        var genre = genreRepository.findById(id);
        if (genre.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(convertToGenreDto(genre.get()));
    }

    private GenreDto convertToGenreDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }
}
