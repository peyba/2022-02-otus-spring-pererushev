package ru.otus.homework06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("SELECT a FROM Author a", Author.class)
                .getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT count(a) FROM Author a", Long.class)
                .getSingleResult();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAllById(List<Long> ids) {
        return em.createQuery("SELECT a FROM Author a WHERE a.id IN(:ids)", Author.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}