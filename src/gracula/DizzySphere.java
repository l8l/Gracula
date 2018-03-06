package gracula;

import java.util.concurrent.ThreadLocalRandom;

import processing.core.PApplet;

public class DizzySphere extends GraculaSphere {
	
	private static final int RND_DISTANCE = 2;

	public DizzySphere(PApplet applet, float size, float x, float y, float z) {
		super(applet, size, x, y, z);
		this.colorR = 128;
		this.colorG = 128;
		this.colorB = 128;
	}
	
	@Override
	protected void beforeDraw() {
		final int rnd = getRandomIntUpTo(6);
		
		switch(rnd) {
		case 0:
			this.x += RND_DISTANCE;
			break;
		case 1:
			this.x -= RND_DISTANCE;
			break;
		case 2:
			this.y += RND_DISTANCE;
			break;
		case 3:
			this.y -= RND_DISTANCE;
			break;
		case 4:
			this.z += RND_DISTANCE;
			break;
		case 5:
			this.z -= RND_DISTANCE;
			break;
		}
		
		final int rnd2 = getRandomIntUpTo(3);
		
		if (rnd2 == 1) {
			this.colorR += getRandomIntUpTo(5) - 2;
			if (this.colorR < 0) {
				this.colorR = 0;
			}
			if (this.colorR > 255) {
				this.colorR = 255;
			}
		} else if (rnd2 == 2) {
			this.colorG += getRandomIntUpTo(5) - 2;
			if (this.colorG < 0) {
				this.colorG = 0;
			}
			if (this.colorG > 255) {
				this.colorG = 255;
			}
		} else {
			this.colorB += getRandomIntUpTo(5) - 2;
			if (this.colorB < 0) {
				this.colorB = 0;
			}
			if (this.colorB > 255) {
				this.colorB = 255;
			}
		}
		
		
		
	}
	
	/**
	 * returns a random int between (inclusive) 0 and "upTo" (exclusive)
	 */
	private static int getRandomIntUpTo(int upTo) {
		return ThreadLocalRandom.current().nextInt(upTo);
	}

}
