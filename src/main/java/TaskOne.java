/*Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
(порядок – ABСABСABС). Используйте wait/notify/notifyAll./*
 */
public class TaskOne {

    private static final Object monitor = new Object();
    private static char letter = 'C';

    public static void main(String[] args) {

        TaskOne taskThread= new TaskOne();

        new Thread(() -> taskThread.print('A')).start();
        new Thread(() -> taskThread.print('B')).start();
        new Thread(() -> taskThread.print('C')).start();
    }

    public void print(char c) {
        char c1 = nextChar(c);

        try {
            for (int i = 0; i < 15; i++) {
                synchronized (monitor) {
                    if (letter != c)
                        monitor.wait();
                    System.out.print(c1);
                    letter = c1;
                    monitor.notify();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public char nextChar(char c) {
        if (c == 'A') return 'B';
        if (c == 'B') return 'C';
        return 'A';
    }
}
