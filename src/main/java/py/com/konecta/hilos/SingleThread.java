package py.com.konecta.hilos;

import java.util.concurrent.TimeUnit;

import py.com.konecta.Main;
import py.com.konecta.utils.RandomUtils;

public class SingleThread extends Thread {
	
	public SingleThread() {
		super("Hilo sin nombre");
	}
	
	public SingleThread(String name) {
		super(name);
	}
	
	public void run() {
		System.out.println("thread is running : " + getName());
		for (int i = 0; i < 10; i++) {
			int time = RandomUtils.randInt(0, 10);
			try {
				//System.out.println("Dormir " + time + " segundos en Hilo : " + getName());
				TimeUnit.SECONDS.sleep(time);
				if (time < 6) {
					Main.lista.add("test add " + time);
				} else {
					if (Main.lista.size() > 0)
						Main.lista.remove(0);
				}
				System.out.println("Lista : " + Main.lista.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
