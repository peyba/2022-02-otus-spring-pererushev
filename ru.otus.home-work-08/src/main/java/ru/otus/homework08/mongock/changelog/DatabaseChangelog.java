package ru.otus.homework08.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.homework08.domain.Author;
import ru.otus.homework08.domain.Book;
import ru.otus.homework08.domain.BookComment;
import ru.otus.homework08.domain.Genre;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.repository.BookCommentRepository;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.GenreRepository;
import ru.otus.homework08.services.BookLibraryService;

import java.util.List;
import java.util.Set;

@ChangeLog(order = "v1.0")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "a.pererushev", runAlways = true/*TODO: убрать при переходе на реальную БД*/)
    public void dropDb(MongoDatabase db) {
        //db.drop();
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "a.pererushev")
    public void addAuthors(AuthorRepository repository) {
        List<Author> authorList = List.of(
                new Author().setId(0L).setFirstName("UNKNOWN").setSecondName("UNKNOWN"),
                new Author().setId(1L).setFirstName("Аркадий").setSecondName("Стругацкий"),
                new Author().setId(2L).setFirstName("Борис").setSecondName("Стругацкий")
        );

        repository.saveAll(authorList);
    }

    @ChangeSet(order = "003", id = "addGenres", author = "a.pererushev")
    public void addGenres(GenreRepository repository) {
        var genres = List.of(
            new Genre().setId(1L).setCode("CRIME").setNameEng("Crime").setNameRus("Криминальная проза, детектив"),
            new Genre().setId(2L).setCode("DETECTIVE").setNameEng("Detective fiction").setNameRus("Детектив"),
            new Genre().setId(3L).setCode("SCIENCE").setNameEng("Science fiction").setNameRus("Научная фантастика"),
            new Genre().setId(4L).setCode("POST_APOC").setNameEng("Post-apocalyptic").setNameRus("Постапокалипсис"),
            new Genre().setId(5L).setCode("DISTOPIA").setNameEng("Distopia").setNameRus("Антиутопия"),
            new Genre().setId(6L).setCode("CYBERPUNK").setNameEng("Cyberpunk").setNameRus("Киберпанк"),
            new Genre().setId(7L).setCode("FANTASY").setNameEng("Fantasy").setNameRus("Фэнтези"),
            new Genre().setId(8L).setCode("ROMANCE").setNameEng("Romance novel, Romantic novel").setNameRus("Любовный роман"),
            new Genre().setId(9L).setCode("WESTERN").setNameEng("Western").setNameRus("Вестерн"),
            new Genre().setId(10L).setCode("HORROR").setNameEng("Horror").setNameRus("Ужасы"),
            new Genre().setId(11L).setCode("CLASSIC").setNameEng("Classic").setNameRus("Классическая литература"),
            new Genre().setId(12L).setCode("FAIRY_TALE").setNameEng("Fairy tale").setNameRus("Сказка"),
            new Genre().setId(13L).setCode("FAN").setNameEng("Fan fiction").setNameRus("Фанфик"),
            new Genre().setId(14L).setCode("FOLKLORE").setNameEng("Folklore").setNameRus("Фольклор"),
            new Genre().setId(15L).setCode("HISTORICAL").setNameEng("Historical fiction").setNameRus("Историческая проза"),
            new Genre().setId(16L).setCode("HUMOR").setNameEng("Humor").setNameRus("Юмористическая проза"),
            new Genre().setId(17L).setCode("MYSTERY").setNameEng("Mystery").setNameRus("Детектив"),
            new Genre().setId(18L).setCode("PICTURE").setNameEng("Picture book").setNameRus("Книга с картинками (детские книжки)"),
            new Genre().setId(19L).setCode("THRILLER").setNameEng("Thriller").setNameRus("Триллер"),
            new Genre().setId(20L).setCode("EROTIC").setNameEng("Erotic fiction").setNameRus("Эротика"),
            new Genre().setId(21L).setCode("BIOGRAPY").setNameEng("Biograpy").setNameRus("Биография"),
            new Genre().setId(22L).setCode("AUTOBIOGRAPHY").setNameEng("Autobiography").setNameRus("Автобиография"),
            new Genre().setId(23L).setCode("ESSAY").setNameEng("Essay").setNameRus("Эссе"),
            new Genre().setId(24L).setCode("MANUAL").setNameEng("Owner''s manual, Instruction manual, User''s guide").setNameRus("Инструкция"),
            new Genre().setId(25L).setCode("JOURNALISM").setNameEng("Journalism").setNameRus("Публицистика"),
            new Genre().setId(26L).setCode("MEMOIR").setNameEng("Memoir").setNameRus("Мемуары"),
            new Genre().setId(27L).setCode("REFERENCE").setNameEng("Reference book").setNameRus("Справочник"),
            new Genre().setId(28L).setCode("HOW_TO").setNameEng("Self-help book, How-to book").setNameRus("Руководство, саморазвитие"),
            new Genre().setId(29L).setCode("TEXTBOOK").setNameEng("Textbook").setNameRus("Учебник"),
            new Genre().setId(30L).setCode("ACADEMIC").setNameEng("Academic paper").setNameRus("Научное исследование"),
            new Genre().setId(31L).setCode("ENCYCLOPEDIA").setNameEng("Encyclopedia").setNameRus("Энциклопедия"),
            new Genre().setId(32L).setCode("DICTIONARY").setNameEng("Dictionary").setNameRus("Словарь"),
            new Genre().setId(33L).setCode("POPULAR").setNameEng("Popular science").setNameRus("Популярная наука"),
            new Genre().setId(34L).setCode("PHOTOGRAPH").setNameEng("Photograph").setNameRus("Фотокнига"),
            new Genre().setId(35L).setCode("TECHNICAL").setNameEng("Technical writing").setNameRus("Техническая литература"),
            new Genre().setId(36L).setCode("COOKBOOK").setNameEng("Cookbook").setNameRus("Кулинарная книга")
        );

        repository.saveAll(genres);
    }

    @ChangeSet(order = "004", id = "addBooks", author = "a.pererusehv")
    public void addBooks(BookRepository bookRepository, GenreRepository genreRepository, AuthorRepository authorRepository) {
        var boos = List.of(
                new Book().setName("Обитаемый остров")
                        .setGenre(genreRepository.findById(5L).orElseThrow())
                        .setAuthors(
                                Set.of(
                                        authorRepository.findById(1L).orElseThrow(),
                                        authorRepository.findById(2L).orElseThrow()
                                )
                        ),
                new Book().setName("Новейшая история России")
                        .setGenre(genreRepository.findById(4L).orElseThrow())
                        .setBookComments(
                                Set.of(new BookComment().setText("qqqq"))
                        )
        );

        bookRepository.saveAll(boos);
    }

    @ChangeSet(order = "005", id = "addBookComments", author = "a.pererusehv")
    public void addBookComments(BookRepository bookRepository, BookCommentRepository bookCommentRepository) {

    }

    //@ChangeSet(order = "002", id = "insertLermontov", author = "ydvorzhetskiy")
    //public void insertLermontov(MongoDatabase db) {
    //    MongoCollection<Document> myCollection = db.getCollection("persons");
    //    var doc = new Document().append("name", "Lermontov");
    //    myCollection.insertOne(doc);
    //}

    //@ChangeSet(order = "003", id = "insertPushkin", author = "stvort")
    //public void insertPushkin(PersonRepository repository) {
    //    repository.save(new Person("Pushkin"));
    //}
}
