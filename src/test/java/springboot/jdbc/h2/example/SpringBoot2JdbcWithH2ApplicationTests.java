package springboot.jdbc.h2.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.jdbc.h2.example.student.Student;
import springboot.jdbc.h2.example.student.StudentJdbcRepository;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SpringBoot2JdbcWithH2ApplicationTests {

    @Autowired
    StudentJdbcRepository studentJdbcRepository;

    private EmbeddedDatabase db;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql").generateUniqueName(true)
                .build();
    }

    @Test
    public void testFindId() {
        Student student = studentJdbcRepository.findById(10002);

        Assert.assertNotNull(student);
        Assert.assertEquals("Kylie", student.getFirstName());
        Assert.assertEquals("Jenner", student.getLastName());
    }

    @Test
    public void testFindIdsByFirstName() {
        List<Map<String, Object>> ids = studentJdbcRepository.findIdByFirstName("Kim");

        Assert.assertNotNull(ids);
        Assert.assertEquals(ids.size(), 2);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteById() {
        Student student = studentJdbcRepository.findById(10002);
        Assert.assertNotNull(student);

        int k = studentJdbcRepository.deleteById(10002);
        Assert.assertEquals(1, k);

        student = studentJdbcRepository.findById(10002);
    }

    @Test
    public void testInsert() {
        studentJdbcRepository.insert(new Student(10010L, "Justin", "Timberlake"));
        List<Student> list = studentJdbcRepository.findAll();
        Assert.assertEquals(list.size(), 4);
    }

    @Test
    public void testUpdate() {
        Student student = studentJdbcRepository.findById(10001);
        Assert.assertEquals("Kim", student.getFirstName());

        int k = studentJdbcRepository.update(new Student(10001L, "Charlize", "Theron"));
        Assert.assertEquals(1, k);

        student = studentJdbcRepository.findById(10001);
        Assert.assertEquals("Charlize", student.getFirstName());
    }

    @After
    public void tearDown() {
        db.shutdown();
    }
}
