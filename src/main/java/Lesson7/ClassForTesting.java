package Lesson7;
//набор методов

public class ClassForTesting {

    @Test(priority = 2)
    public static void method2() {
        System.out.println(2);
    }

    @Test(priority = 1)
    public static void method1() {
        System.out.println(1);
    }

    @Test(priority = 1)
    public static void method1_1() {
        System.out.println(1);
    }

    @Test(priority = 8)
    public static void method8() {
        System.out.println(8);
    }

    @Test(priority = 8)
    public static void method8_1() {
        System.out.println(8);
    }

    //дефолтный приорити
    @Test
    public static void methodDef() {
        System.out.println("def =0");
    }

    // метод который не будет тестироваться  = нет аннтоации
    public static void methodNot() {
        System.out.println("not");
    }

    @BeforeSuite
    public static void beforeMethod() {
        System.out.println("before");
    }
//    @BeforeSuite
//    public  static void beforeMethodOneMore(String a) {
//       System.out.println("before");
//    }

    @AfterSuite
    public static void afterMethod() {
        System.out.println("after");
    }


//   @AfterSuite
//    public static void afterMethodOneMore(String a) {
//       System.out.println("after");
//    }

}
