package hello;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleTest {
    @Test
    public void deserializeTest() {
        // create once, reuse
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = mapper.readValue("{\"firstName\":\"Foo\", \"lastName\":\"Bar\"}", User.class);
            Assert.assertEquals("Foo", user.firstName);
            Assert.assertEquals("Bar", user.getLastName());
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void serializeTest() {
        User user = new User();
        user.firstName = "Foo";
        user.setLastName("Bar");
        Address addr = new Address();
        addr.street = "Rd. 1";
        user.address = addr;

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(user);
            Assert.assertEquals("{\"firstName\":\"Foo\",\"lastName\":\"Bar\"}", jsonStr);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
