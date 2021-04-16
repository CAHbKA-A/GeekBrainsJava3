package Lesson6;

///*3. Написать метод, который проверяет состав массива из чисел 1 и 4.
//Если в нем нет хоть одной четверки или единицы, то метод вернет false;
//Написать набор тестов для этого метода (по 3-4 варианта входных данных).*/
public class MethodForTask3 {


    public static boolean chekOneAndFour(int[] array) {
        boolean flag1 = false;
        boolean flag4 = false;
        for (int j : array) {
            if (j == 1) flag1 = true;
            if (j == 4) flag4 = true;
        }
        return (flag1 && flag4);
    }
}
