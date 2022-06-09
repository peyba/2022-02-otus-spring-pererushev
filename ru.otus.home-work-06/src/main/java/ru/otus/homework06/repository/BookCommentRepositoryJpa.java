package ru.otus.homework06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookCommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public BookComment save(BookComment comment) {
        if (comment.getId() != null) {
            return em.merge(comment);
        } else {
            em.persist(comment);
            return comment;
        }
    }

    @Override
    public List<BookComment> saveAll(Iterable<BookComment> comments) {
        List<BookComment> newComments = new ArrayList<>();
        for (var comment : comments) {
            newComments.add(save(comment));
        }
        return newComments;
    }

    @Override
    public Optional<BookComment> findById(Long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<BookComment> findAll() {
        return em.createQuery("SELECT c FROM BookComment c", BookComment.class)
                .getResultList();
    }

    @Override
    public List<BookComment> findAllById(Iterable<Long> ids) {
        return em.createQuery("SELECT c FROM BookComment c WHERE c.id IN(:ids)", BookComment.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT count(c) FROM BookComment c", Long.class)
                .getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        em.createQuery("DELETE FROM BookComment c WHERE c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void delete(BookComment comment) {
        em.remove(comment);
    }

    @Override
    public void deleteAllById(Iterable<Long> ids) {
        em.createQuery("DELETE FROM BookComment c WHERE c.id IN(:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAll(Iterable<BookComment> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(c -> ids.add(c.getId()));

        deleteAllById(ids);
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM BookComment ")
                .executeUpdate();
    }
}
