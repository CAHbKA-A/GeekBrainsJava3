package Lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassOfTests {
    public static void main(String[] args) {

        ClassForTesting c = new ClassForTesting();
        start(c.getClass());

    }

    public static void start(Class c) {

        //карта < приоритет , список_методов_с_этим_приориттом>
        Map<Integer, ArrayList<Method>> pool = new HashMap<>();
        //выдергиваем список методов
        Method[] methods = c.getDeclaredMethods();

        for (Method method : methods) {
            //  System.out.print(method.getName());
            if (method.getAnnotation(BeforeSuite.class) != null) {
                //   System.out.println("  -   This method for FIRST test");
                if (pool.get(0) != null) throw new RuntimeException("To many @Befors");
                //добавляем метод с приориттом 0
                ArrayList list = new ArrayList<Method>();
                list.add(method);
                pool.put(0, list);

            } else if (method.getAnnotation(Test.class) != null) {
                int prior = method.getAnnotation(Test.class).priority();
                // System.out.println("  -   This method for test. Priority=" + prior);
                if (prior < 1 || prior > 10) throw new RuntimeException("Priority error!");
                else {
                    ArrayList<Method> list = pool.get(methods.length - prior);
                    // если не было методов стаким приоритетом
                    if (list == null) {
                        list = new ArrayList<Method>();
                    }
                    //добавляем метод в список методов с этим приортетом
                    list.add(method);
                    //возвращаем в мапу с приортетом
                    pool.put(methods.length - prior, list);
                }

            } else if (method.getAnnotation(AfterSuite.class) != null) {
                // System.out.println("  -   This method for LAST test");
                if (pool.get(11) != null) throw new RuntimeException("To many @Afters");
                ArrayList list = new ArrayList<Method>();
                list.add(method);
                pool.put(11, list);
            } //else System.out.println("  -  We skip this method");
        }

        //отправляем на тест в пордке приоритета.
        for (int i = 0; i <= 11; i++) {
            if (pool.get(i) != null) {
                ArrayList<Method> list = pool.get(i);
                if (list != null) ;
                for (Method method : list) {
                    tests(method);
                }
            }
        }
    }

    //тут надо описать сами тесты
    public static void tests(Method method) {
        System.out.println("Do test for " + method.getName());
        try {
            method.setAccessible(true);
            method.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}