package ru.otus.homework07.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework07.domain.BookComment;
import ru.otus.homework07.dto.BookCommentDto;
import ru.otus.homework07.dto.BookDto;
import ru.otus.homework07.dto.BookTitleDto;
import ru.otus.homework07.repository.BookCommentRepository;
import ru.otus.homework07.repository.BookRepository;
import ru.otus.homework07.domain.Book;

import java.util.*;

@Component
@RequiredArgsConstructor
public class BookLibraryServiceImpl implements LibraryBookService {

    private final BookRepository bookRepository;
    private final BookCommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        if(bookDto.getAuthors() == null) {
            bookDto.setAuthors(Set.of());
        }

        if (bookDto.getBookComments() == null) {
            bookDto.setBookComments(Set.of());
        }

        var book = convertToBookEntity(bookDto);

        var savedBook = bookRepository.save(book);

        return convertToBookDto(savedBook);
    }

    @Override
    public List<BookTitleDto> getAll() {
        List<BookTitleDto> bookTitleDtoList = new ArrayList<>();
        bookRepository.findAll().forEach(book -> bookTitleDtoList.add(convertToBookTitleDto(book)));
        return bookTitleDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(Long id) {
        var book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(convertToBookDto(book.get()));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BookCommentDto> getAllComments(Long bookId) {
        var book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            return Set.of();
        } else {
            var bookDto = convertToBookDto(book.get());
            return bookDto.getBookComments();
        }
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
            var comm = comment.get();
            comm.getBook().getBookComments().remove(comm);
            commentRepository.delete(comm);
        } else {
            throw new LibraryEntitySavingException("Can't find comment. id: " + id);
        }
    }

    private BookDto convertToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    private BookTitleDto convertToBookTitleDto(Book book) {
        return modelMapper.map(book, BookTitleDto.class);
    }

    private Book convertToBookEntity(BookDto bookDto) {
        var book = modelMapper.map(bookDto, Book.class);
        for (BookComment bookComment : book.getBookComments()) {
            bookComment.setBook(book);
        }
        return book;
    }

    private BookCommentDto convertToBookCommentDto(BookComment bookComment) {
        return modelMapper.map(bookComment, BookCommentDto.class);
    }

    private BookComment convertToBookComment(BookCommentDto bookCommentDto) {
        return modelMapper.map(bookCommentDto, BookComment.class);
    }
}