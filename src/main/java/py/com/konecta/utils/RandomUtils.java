package py.com.konecta.utils;

import java.util.Random;

public class RandomUtils {

	public static int randInt(int min, int max) {

		Random rand = new Random(System.currentTimeMillis());
		int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}
