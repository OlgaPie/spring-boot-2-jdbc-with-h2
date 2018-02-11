package springboot.jdbc.h2.example.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class StudentJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> findAll() {
        return jdbcTemplate.query("select * from student", new StudentRowMapper());
    }

    public Student findById(long id) {
        return jdbcTemplate.queryForObject("select * from student where id=?", new Object[]{id},
                new BeanPropertyRowMapper<Student>(Student.class));
    }

    public List<Map<String, Object>> findIdByFirstName(String firstName) {
        return jdbcTemplate.queryForList("select id from student where first_name= ? ", new Object[]{firstName});
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("delete from student where id=?", new Object[]{id});
    }

    public int insert(Student student) {
        return jdbcTemplate.update("insert into student (id, first_name, last_name) " + "values(?,  ?, ?)",
                new Object[]{student.getId(), student.getFirstName(), student.getLastName()});
    }

    public int update(Student student) {
        return jdbcTemplate.update("update student " + " set first_name = ?, last_name = ? " + " where id = ?",
                new Object[]{student.getFirstName(), student.getLastName(), student.getId()});
    }

    class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            return student;
        }
    }

}
