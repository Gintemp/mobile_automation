import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
    @Test
    public void testGetLocalNumber() {
        MainClass mainClass = new MainClass();
        Assert.assertEquals("getLocalNumber вернул число, отличное от 14\n" +
                        "Ожидание: 14\n" +
                        "Результат: " + mainClass.getLocalNumber() + "\n",
                14, mainClass.getLocalNumber());
    }
}