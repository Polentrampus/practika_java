
import java.util.Random;

public class IntDataGenerator {
    private static final Random random = new Random();
    private final int value = random.nextInt(100, 1000);

    public IntDataGenerator() {
        System.out.println("Сгенерировано число: " + value);
    }


    public int maxNumber(){
        int num = -1;
        for (int i = 0; i < 3; i++) {
            if(num < (value/100)) {
                num = value / 100;
            }
            else if (num < (value / 10 - (value / 100) * 10)) {
                num = value / 10 - (value / 100) * 10;
            }
            else if (num < (value - (value/10)*10)) {
                num = value - (value / 10) * 10;
            }
        }

        return num;
    }

    public Integer getValue() {
        return this.value;
    }
}