package ssvv;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ssvv.domain.Nota;
import ssvv.domain.Student;
import ssvv.domain.Tema;
import ssvv.repository.NotaXMLRepo;
import ssvv.repository.StudentXMLRepo;
import ssvv.repository.TemaXMLRepo;
import ssvv.service.Service;
import ssvv.validation.NotaValidator;
import ssvv.validation.StudentValidator;
import ssvv.validation.TemaValidator;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;

public class AddGradeIntegration {
    Service service;
    String filenameStudent = "fisiere/testStudenti.xml";
    String filenameTema = "fisiere/testTeme.xml";
    String filenameNota = "fisiere/testNote.xml";

    @Before
    public void initializeService() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        try {
            new File(filenameStudent).createNewFile();
            new File(filenameTema).createNewFile();
            new File(filenameNota).createNewFile();

            FileWriter fw = new FileWriter(filenameStudent);
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>");
            fw.close();

            fw = new FileWriter(filenameTema);
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>");
            fw.close();

            fw = new FileWriter(filenameNota);
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>");
            fw.close();

        } catch (Exception e) {
        }


        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    }

    @After
    public void  destroyService() {

        new File(filenameStudent).delete();
        new File(filenameTema).delete();
        new File(filenameNota).delete();
    }


    @Test
    public void addStudent() {
        try {
            service.addStudent(new Student("1", "Ion", 937, "ion@gmail.com"));
            assert(true);
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void addAssignment() {
        Tema tema = service.addTema(new Tema("id", "descriere", 1, 3));
        assert(tema == null);
    }

    @Test
    public void addGrade() {
        try {
            service.addNota(new Nota("new", "1", "id", 8.8, LocalDate.now()),
                    "why is feedback not part of nota class?");
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }

    @Test
    public void incrementalIntegration() {
        addStudent();
        addAssignment();
    }

    @Test
    public void bigBangIntegration() {
        addStudent();
        addAssignment();
        try {
            addGrade();
            assert(false);
        } catch (AssertionError e) {
            assert(true);
        }
    }
}
