package wait_notify;

public class Test {
    public static void main(String[] args)
    {
        final Storage storage=new Storage();

        for(int i=1;i<15;i++)
        {
            final int finalI = i;
            new Thread(new Runnable() {		//继承Thread类
                @Override
                public void run() {
                    storage.produce(String.format("生产者%d:", finalI));
                }
            }).start();
        }

        for(int i=1;i<10;i++)
        {
            final int finalI = i;
            new Thread(new Runnable(){
            	@Override
            	public void run(){
            		storage.consume(String.format("消费者%d:",finalI));
            	}
            }).start();
        }
    }
}