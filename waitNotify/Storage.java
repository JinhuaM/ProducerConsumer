package waitNotify;
import java.util.LinkedList;		//LinkedList�洢�ṹΪ����

public class Storage {
	private final int Max_size=3;	//���ܱ��̳�
	private LinkedList<Object> list=new LinkedList<Object>();
	
	public void produce(String producer) {
		synchronized(list)			//ͬ��������һ��ʱ��ֻ��һ���߳̿���ִ�У���������
		{
			while(list.size()==Max_size){	//һ��Ҫ��while���жϣ�������if
				System.out.println("�ֿ���������"+producer+"������ʱ����������");
				try {
					list.wait();
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.add(new Object());
			System.out.println("��"+producer+"����������һ����Ʒ\t���ֲֿ�����Ϊ����"+list.size());
			list.notifyAll();				//��������wait�߳�
		}
	}
	
	public void consume(String consumer) {
		synchronized(list) {
			while(list.size()==0) {
				System.out.println("�ֿ��ѿգ���"+consumer+"������ʱ�������ѣ�");
				try {
					list.wait();
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.remove();
			System.out.println("��"+consumer+"����������һ����Ʒ\t���ֲֿ�����Ϊ����"+list.size());
			list.notifyAll();
		}
	}
	
	public LinkedList<Object> getList(){
		return list;
	}
	public void setList(LinkedList<Object>list) {
		this.list=list;
	}
	public int getMax_size() {
		return Max_size;
	}
}
