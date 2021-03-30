package ssvv;

import ssvv.domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ssvv.repository.*;
import ssvv.service.Service;
import ssvv.validation.NotaValidator;
import ssvv.validation.StudentValidator;
import ssvv.validation.TemaValidator;
import java.io.File;
import java.io.FileWriter;

public class AddStudentBBT {
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
    public void testAddStudentWithName() {

        try {
            service.addStudent(new Student("1", "Ion", 937, "ion@gmail.com"));
            assert(true);
        } catch (Exception e) {
            assert(false);
        }

        try {
            service.addStudent(new Student("2", "", 937, "ion@gmail.com"));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void testAddStudentWithId() {

        try {

            service.addStudent(new Student("1", "Ion", 937, "ion@gmail.com"));
            assert(true);
        } catch (Exception e) {

            assert(false);
        }

        try {
            service.addStudent(new Student("", "Ion", 937, "ion@gmail.com"));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void testAddStudentWithGroup() {

        try {
            service.addStudent(new Student("1", "Ion", 1, "ion@gmail.com"));
            service.addStudent(new Student("3", "Ion", 0, "ion@gmail.com"));
            assert(true);
        } catch (Exception e) {
            assert(false);
        }



        try {
            service.addStudent(new Student("2", "Ion", -1, "ion@gmail.com"));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }


    @Test
    public void testAddStudentWithEmail() {

        try {
            service.addStudent(new Student("1", "Ion", 1, "ion@gmail.com"));
            assert(true);
        } catch (Exception e) {
            assert(false);
        }



        try {
            service.addStudent(new Student("2", "Ion", 1, ""));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }


    @Test
    public void testAddStudentWithEmailFormat() {

        try {
            service.addStudent(new Student("1", "Ion", 1, "ion@gmail.com"));
            assert(true);
        } catch (Exception e) {
            assert(false);
        }

        try {
            service.addStudent(new Student("2", "Ion", 1, "@gmail.com"));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }

        try {
            service.addStudent(new Student("2", "Ion", 1, "ion@.com"));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }

        try {
            service.addStudent(new Student("2", "Ion", 1, "ion@gmail."));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }

        try {
            service.addStudent(new Student("2", "Ion", 1, "ion@gmail.."));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }

        try {
            service.addStudent(new Student("2", "Ion", 1, "ion@gmailcom"));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }

        try {
            service.addStudent(new Student("2", "Ion", 1, "iongmail.com"));
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }
    
}
