package waitNotify;

public class Test {
    public static void main(String[] args)
    {
        Storage storage=new Storage();

        for(int i=1;i<6;i++)
        {
            int finalI = i;
            new Thread(new Runnable() {		//�̳�Thread��
                @Override
                public void run() {
                    storage.produce(String.format("������%d:", finalI));
                }
            }).start();
        }

        for(int i=1;i<5;i++)
        {
            int finalI = i;
            new Thread(()-> storage.consume(String.format("������%d:", finalI))).start();
        }
    }
}