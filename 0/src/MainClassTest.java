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

    @Test
    public void testGetClassNumber(){
        MainClass mainClass = new MainClass();
        Assert.assertTrue("Метод getClassNumber вернул число меньше или равное 45\n" +
                        "Результат выполенеия: " + mainClass.getClassNumber() + "\n",
                mainClass.getClassNumber() > 45);
    }

    @Test
    public void testGetClassString(){
        String[] searchedSubStringArray = new String[] {
                "Hello", "hello"
        };
        MainClass mainClass = new MainClass();
        for(String subStr : searchedSubStringArray) {
            Assert.assertTrue("Метод getClassString вернул строку не содержащую нужную подстроку\n" +
                            "Полученная строка: " + mainClass.getClassString() + "\n" +
                            "Поиск строки: " + subStr,
                    mainClass.getClassString().contains(subStr));
        }
    }
}