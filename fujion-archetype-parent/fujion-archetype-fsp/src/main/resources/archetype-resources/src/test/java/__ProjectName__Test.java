package ${package};

import static org.junit.Assert.*;

import org.fujion.common.Logger;
import org.fujion.test.MockTest;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for ${displayName}
 */
public class ${ProjectName}Test extends MockTest {
    
    private static final Logger log = Logger.create(${ProjectName}Test.class);
    
    /**
     * Unit Test initialization
     */
    @Before
    public final void init() {
        log.info("Initializing Test Class");
    }
    
    /**
     * Performs unit test
     */
    @Test
    public void test() {
    }
    
}
