package waitNotify;
import java.util.LinkedList;		//LinkedList存储结构为链表

public class Storage {
	private final int Max_size=3;	//不能被继承
	private LinkedList<Object> list=new LinkedList<Object>();
	
	public void produce(String producer) {
		synchronized(list)			//同步方法，一个时刻只有一个线程可以执行，其他阻塞
		{
			while(list.size()==Max_size){	//一定要用while来判断，不能用if
				System.out.println("仓库已满，【"+producer+"】：暂时不能生产！");
				try {
					list.wait();
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.add(new Object());
			System.out.println("【"+producer+"】：生产了一个产品\t【现仓库容量为】："+list.size());
			list.notifyAll();				//唤醒所有wait线程
		}
	}
	
	public void consume(String consumer) {
		synchronized(list) {
			while(list.size()==0) {
				System.out.println("仓库已空，【"+consumer+"】：暂时不能消费！");
				try {
					list.wait();
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.remove();
			System.out.println("【"+consumer+"】：消费了一个产品\t【现仓库容量为】："+list.size());
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
