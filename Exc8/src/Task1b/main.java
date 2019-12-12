package Task1b;

import java.util.concurrent.locks.ReentrantLock;

public class main
{
    private ReentrantLock lock = new ReentrantLock(true);
    private int counter;

    public static void main(String[] args) {
        main main = new main();
        main.t1.start();
        main.t2.start();
    }

    public void foo()
    {
        lock.lock();
        counter++;
    }

    public int bar()
    {
        lock.unlock();
        return counter;
    }

    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true) {
                foo();
                int c = bar();
                System.out.println(t1.getName() + "'s count: " + c);
                if(c >= 5)t1.stop();
                try{
                    Thread.sleep(100);
                }catch(Exception e){
                    //unhandled
                }
            }
        }
    });

    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true) {
                foo();
                int c = bar();
                System.out.println(t2.getName() + "'s count: " + c);
                if(c >= 5)t2.stop();
                try{
                    Thread.sleep(100);
                }catch(Exception e){
                    //unhandled
                }
            }
            }
    });
}
