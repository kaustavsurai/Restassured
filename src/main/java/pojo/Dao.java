package pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class Dao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employee> getEmployee(String query){
        List<Employee> rs= jdbcTemplate.query(query,new BeanPropertyRowMapper<>(Employee.class));
        return rs;
    }

    public void setEmployee(String query){
        jdbcTemplate.execute(query);
    }
}
