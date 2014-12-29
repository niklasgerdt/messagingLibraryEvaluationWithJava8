package moma.spike;

import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = TestConfig.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class PrototypeSpikeTest {
    @Autowired
    MyProtoType t1;
    @Autowired
    MyProtoType t2;

    @Test
    public void unequals() {
        assertNotEquals(t1, t2);
    }

    @Configuration
    @ComponentScan(basePackages = "moma.spike")
    static class Config {
    }
}
