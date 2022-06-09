package ru.otus.homework06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != null) {
            return em.merge(book);
        } else {
            em.persist(book);
            return book;
        }
    }


    @Override
    public List<Book> saveAll(Iterable<Book> books) {
        List<Book> newBooks = new ArrayList<>();
        for (var book : books) {
            newBooks.add(save(book));
        }
        return newBooks;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT DISTINCT b FROM Book b JOIN FETCH b.genre g", Book.class)
                .getResultList();
    }

    @Override
    public List<Book> findAllById(Iterable<Long> ids) {
        return em.createQuery("SELECT DISTINCT b FROM Book b JOIN FETCH b.genre g WHERE b.id in(:ids)", Book.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT count(b) FROM Book b", Long.class)
                .getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        deleteAllById(List.of(id));
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public void deleteAllById(Iterable<Long> ids) {
        em.createQuery("DELETE FROM BookComment c where c.book.id IN(:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
        em.createQuery("DELETE FROM Book b where b.id IN(:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(Iterable<Book> books) {
        List<Long> ids = new ArrayList<>();
        books.forEach(book -> ids.add(book.getId()));
        deleteAllById(ids);
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM BookComment ")
                .executeUpdate();
        em.createQuery("DELETE FROM Book ")
                .executeUpdate();
    }
}