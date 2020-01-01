package pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.IOException;

@Component
public class JsonFormatter  {
    public Employee getFormat() throws IOException {
            return new ObjectMapper().readValue(ResourceUtils.getFile("classpath:json/Format.json"), Employee.class);
    }
}
