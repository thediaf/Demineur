package Minesweeper;

class timeThread implements Runnable {
    private Thread t;
    private Game game;

    timeThread(Game game) {
        this.game = game;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                game.timer();
            }
            catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    public void start() {
        if (t==null) {
            t = new Thread(this);
            t.start();
        }
    }
}