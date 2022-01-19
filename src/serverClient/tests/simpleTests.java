package serverClient.tests;
import org.junit.*;
import junit.framework.TestCase;

public class priorityTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
        // Kod som körs innan den första testmetoden
    }
    
    @Before
    public void setUp() throws Exception {
        // Kod som körs innan varje test
    }
    
    @Test
    public void testServer() {
	Server server = new Server();
	assertEquals(server,null);
    }
    
    @After
    public void tearDown() throws Exception {
        // Kod som körs efter varje test  
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        // Kod som körs efter den sista testmetoden
    }
}
