import	java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import	db.ConnectionDB;
import model.Author;
import  model.Title;
import service.AuthorService;
import service.AuthorTitleService;
import	service.TitleService;
import repository.AuthorRepository;
import repository.AuthorTitleRepository;
import	repository.TitleRepository;
import service.implementation.AuthorServiceImplement;
import service.implementation.AuthorTitleServiceImplement;
import	service.implementation.TitleServiceImplement;
import repository.implemetation.AuthorRepositoryImplement;
import repository.implemetation.AuthorTitleRepositoryImplement;
import	repository.implemetation.TitleRepositoryImplement;


public class JDBCMain {

    public static void main(String[] args) {

        try {
            Connection connection = ConnectionDB.getConnection();

            AuthorRepository authorRepository =
                new AuthorRepositoryImplement(connection);

            TitleRepository titleRepository =
                new TitleRepositoryImplement(connection);

            AuthorTitleRepository authorTitleRepository =
                new AuthorTitleRepositoryImplement(connection);

            AuthorService authorService =
                new AuthorServiceImplement(authorRepository);

            TitleService titleService =
                new TitleServiceImplement(titleRepository);

            AuthorTitleService authorTitleService =
                new AuthorTitleServiceImplement(
                    connection,
                    authorRepository,
                    titleRepository,
                    authorTitleRepository
                );

            // testAuthorCRUD(authorService);

            // testTitleCRUD(titleService);

            testRelationship(
                authorService,
                titleService,
                authorTitleService
            );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private static void testAuthorCRUD(
    //     AuthorService authorService
    // ) throws Exception {

    //     System.out.println("\n===== AUTHOR TEST =====");

    //     Author author =
    //         new Author(
    //             "Celso",
    //             "Oliveira"
    //         );

    //     authorService.addAuthor(author);

    //     System.out.println("Author inserted");

    //     List<Author> authors =
    //         authorService.getAll();

    //     for (Author current : authors)
    //         System.out.println(current);
    // }

    // private static void testTitleCRUD(
    //     TitleService titleService
    // ) throws Exception {

    //     System.out.println("\n===== TITLE TEST =====");

    //     Title title =
    //         new Title(
    //             "9788543013732",
    //             "C++, como programar",
    //             2,
    //             2006
    //         );

    //     titleService.addTitle(title);

    //     System.out.println("Title inserted");

    //     Title result =
    //         titleService.getById(
    //             "9788543013732"
    //         );

    //     System.out.println(result);
    // }

    private static void testRelationship(
        AuthorService authorService,
        TitleService titleService,
        AuthorTitleService authorTitleService
    ) throws Exception {

        System.out.println(
            "\n===== RELATION TEST ====="
        );

        Title title = new Title(
                "9788534614597",
                "C#: como programar",
                11,
                2003
            );

        // List<Integer>   list = new LinkedList<Integer>(); 
        // list.add( 1 ) ;
        // list.add( 2 );
        // list.add( 3 );
        // authorTitleService.createTitleWithAuthors( title, list );

        List<Author> authors =
            authorTitleService.getAuthorsByTitle( title.getIsbn() );

        System.out.println(
            "\nAuthors of title:"
        );

        for (Author current : authors )
            System.out.println(current);

    }
}