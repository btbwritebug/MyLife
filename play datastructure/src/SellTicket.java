public class SellTicket {
    public static void main(String[] args) {

        //测试
//        SellTicket01 sellTicket01 = new SellTicket01();
//        SellTicket01 sellTicket011 = new SellTicket01();
//        SellTicket01 sellTicket012 = new SellTicket01();
//
//        sellTicket01.start();
//        sellTicket011.start();
//        sellTicket012.start();
        SellTicket02 sellTicket02 = new SellTicket02();

        new Thread(sellTicket02).start();
        new Thread(sellTicket02).start();
        new Thread(sellTicket02).start();

    }
}

class SellTicket01 extends Thread {

    private static int ticketNum = 100;

    @Override
    public void run() {
        while (true) {
            if (ticketNum <= 0) {
                System.out.println("售票结束");
                break;
            }

            //休眠50mm
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("窗口 " + Thread.currentThread().getName() + " 售出1张票," +
                    " 剩余票数=" + (--ticketNum));
        }
    }
}

class SellTicket02 implements Runnable{
    private  int ticketNum = 100;

    @Override
    public void run() {
        while (true) {
            if (ticketNum <= 0) {
                System.out.println("售票结束");
                break;
            }

            //休眠50mm
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("窗口 " + Thread.currentThread().getName() + " 售出1张票," +
                    " 剩余票数=" + (--ticketNum));
        }
    }
}