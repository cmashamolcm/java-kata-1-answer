import docker.JenkinsMain;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JenkinsMain.class)
public class Test {
    @org.junit.jupiter.api.Test
    public void test(){
        System.out.println("Test");
    }
}
