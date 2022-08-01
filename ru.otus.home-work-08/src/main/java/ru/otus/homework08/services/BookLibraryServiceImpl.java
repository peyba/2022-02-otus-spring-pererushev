package ru.otus.homework08.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.domain.BookComment;
import ru.otus.homework08.dto.BookCommentDto;
import ru.otus.homework08.dto.BookDto;
import ru.otus.homework08.dto.BookTitleDto;
import ru.otus.homework08.repository.BookCommentRepository;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.domain.Book;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BookLibraryServiceImpl implements BookLibraryService {

    private final BookRepository bookRepository;
    private final BookCommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookDto save(BookDto bookDto) {
        if(bookDto.getAuthors() == null) {
            bookDto.setAuthors(Set.of());
        }

        if (bookDto.getBookComments() == null) {
            bookDto.setBookComments(Set.of());
        }

        var book = convertToBookEntity(bookDto);

        if (book.getId() == null) {
            book.setId(bookRepository.save(new Book()).getId());
        }

        saveComments(bookDto);

        var savedBook = bookRepository.save(book);

        return convertToBookDto(savedBook);
    }

    private void saveComments(BookDto book) {
        for (var comment : book.getBookComments()) {
            comment.setId(commentRepository.save(convertToBookComment(comment)).getId());
        }
    }

    @Override
    public List<BookTitleDto> getAll() {
        List<BookTitleDto> bookTitleDtoList = new ArrayList<>();
        bookRepository.findAll().forEach(book -> bookTitleDtoList.add(convertToBookTitleDto(book)));
        return bookTitleDtoList;
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        var book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(convertToBookDto(book.get()));
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteByBook_Id(id);
        bookRepository.deleteById(id);
    }

    @Override
    public Set<BookCommentDto> getAllComments(Long bookId) {
        var comments = commentRepository.findByBook_Id(bookId);
        return convertToBookCommentDtoSet(comments);
    }

    @Override
    public Optional<BookCommentDto> findCommentById(Long id) {
        var bookComment = commentRepository.findById(id);
        if (bookComment.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(convertToBookCommentDto(bookComment.get()));
    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        var comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
        } else {
            throw new LibraryEntitySavingException("Can't find comment. id: " + id);
        }
    }

    private BookDto convertToBookDto(Book book) {
        var comments = commentRepository.findByBook_Id(book.getId());
        return modelMapper.map(book, BookDto.class)
                .setBookComments(convertToBookCommentDtoSet(comments));

    }

    private BookTitleDto convertToBookTitleDto(Book book) {
        return modelMapper.map(book, BookTitleDto.class);
    }

    private Book convertToBookEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    private BookCommentDto convertToBookCommentDto(BookComment bookComment) {
        return modelMapper.map(bookComment, BookCommentDto.class);
    }

    private Set<BookCommentDto> convertToBookCommentDtoSet(Iterable<BookComment> bookComments) {
        var bookCommentList = new HashSet<BookCommentDto>();
        for (var bookComment : bookComments) {
            bookCommentList.add(modelMapper.map(bookComment, BookCommentDto.class));
        }
        return bookCommentList;
    }

    private BookComment convertToBookComment(BookCommentDto bookCommentDto) {
        return modelMapper.map(bookCommentDto, BookComment.class);
    }
}