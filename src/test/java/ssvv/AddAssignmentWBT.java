package ssvv;

import ssvv.domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ssvv.domain.Tema;
import ssvv.repository.*;
import ssvv.service.Service;
import ssvv.validation.NotaValidator;
import ssvv.validation.StudentValidator;
import ssvv.validation.TemaValidator;
import java.io.File;
import java.io.FileWriter;

public class AddAssignmentWBT {
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
    public void testAddAssignmentNull() {
        try {
            service.addTema(null);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void testAddAssignmentExisting() {

        Tema tema = new Tema("2", "descriere", 4, 4);

        Tema result = service.addTema(tema);

        assert(result == null);

        Tema tema2 = new Tema("2", "altceva", 4, 4);
        result = service.addTema(tema2);

        assert(result.equals(tema2));

    }

}
