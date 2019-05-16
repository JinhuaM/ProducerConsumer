package await_signal;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    private final int MAX_SIZE = 5;
    private LinkedList<Object> list = new LinkedList<Object>();		
    private final Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();


    public void produce(String producer) {						// 生产产品
        lock.lock();
        while (list.size() == MAX_SIZE) {
            System.out.println("仓库已满，【" + producer + "】： 暂时不能执行生产任务!");
            try {										// 由于条件不满足，生产阻塞
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.add(new Object());
        System.out.println("【" + producer + "】：生产了一个产品\t【现仓储量为】:" + list.size());
        empty.signalAll();
        lock.unlock();								 // 释放锁

    }

    public void consume(String consumer) {						  // 消费产品
        lock.lock();								  // 获得锁
        while (list.size() == 0) {
            System.out.println("仓库已空，【" + consumer + "】： 暂时不能执行消费任务!");
            try {										 // 由于条件不满足，消费阻塞
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.remove();
        System.out.println("【" + consumer + "】：消费了一个产品\t【现仓储量为】:" + list.size());
        full.signalAll();
        lock.unlock();								 // 释放锁

    }

    public LinkedList<Object> getList() {
        return list;
    }

    public void setList(LinkedList<Object> list) {
        this.list = list;
    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }
}