package signal;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    // �ֿ����洢��
    private final int MAX_SIZE = 3;

    // �ֿ�洢������
    private LinkedList<Object> list = new LinkedList<Object>();
    // ��
    private final Lock lock = new ReentrantLock();

    // �ֿ�������������
    private final Condition full = lock.newCondition();

    // �ֿ�յ���������
    private final Condition empty = lock.newCondition();

    // ������Ʒ
    public void produce(String producer) {
        lock.lock();
        // ����ֿ�����
        while (list.size() == MAX_SIZE) {
            System.out.println("�ֿ���������" + producer + "���� ��ʱ����ִ����������!");
            try {
                // �������������㣬��������
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // ������Ʒ
        list.add(new Object());

        System.out.println("��" + producer + "����������һ����Ʒ\t���ֲִ���Ϊ��:" + list.size());

        empty.signalAll();

        // �ͷ���
        lock.unlock();

    }

    // ���Ѳ�Ʒ
    public void consume(String consumer) {
        // �����
        lock.lock();

        // ����ֿ�洢������
        while (list.size() == 0) {
            System.out.println("�ֿ��ѿգ���" + consumer + "���� ��ʱ����ִ����������!");
            try {
                // �������������㣬��������
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.remove();
        System.out.println("��" + consumer + "����������һ����Ʒ\t���ֲִ���Ϊ��:" + list.size());
        full.signalAll();
        
        // �ͷ���
        lock.unlock();

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