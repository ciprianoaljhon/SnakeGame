import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Snake extends JPanel implements Runnable {
	
	/* TODO!!
	 * 11. Check whether the grid / tile is unoccupied.
	 *   11.1. Only place apple in unoccupied grid.
	 *   11.2. Check if all grid / tiles are occupied (Game Completed).
	 * 12. Add Score
	 * 13. Add Menu; 
	 *   13.1. Pause
	 * 	 13.2. Restart Button
	 * 	 13.3. Start, Exit, High Score;
	 */
	
	private static int TILE_SIZE = 20;
	private static int SCREEN_W = 40 * TILE_SIZE;
	private static int SCREEN_H = 30 * TILE_SIZE;
	private static boolean isRunning = false;
	private int size = 7;
	private int[] posX = new int[SCREEN_W / 2];
	private int[] posY = new int[SCREEN_H / 2];
	private int appleX, appleY;
	boolean w, a, s, d;

	Snake() {
		setPreferredSize(new Dimension(SCREEN_W, SCREEN_H));
		w = a = s = false;
		d = true;
		isRunning = true;
		new Thread(this).start();
		drawApple();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		drawSnake(g2d);
	}

	public void drawSnake(Graphics2D g2d) {
		g2d.setColor(Color.GREEN);

		for (int i = size; i > 0; i--) {
			posX[i] = posX[i - 1];
			posY[i] = posY[i - 1];
		}
		for (int i = 0; i <= size; i++) {
			g2d.fillRect(posX[i], posY[i], TILE_SIZE, TILE_SIZE);

		}

		if (posX[0] == appleX && posY[0] == appleY) {
			size++;
			drawApple();
		}
		g2d.setColor(Color.red);
		g2d.fillOval(appleX, appleY, TILE_SIZE, TILE_SIZE);
	}

	public void move() {
		System.out.println(w + " " + d);
		if (w) {
			posY[0] -= TILE_SIZE;
		}
		if (s) {
			posY[0] += TILE_SIZE;
		}
		if (a) {
			posX[0] -= TILE_SIZE;
		}
		if (d) {
			posX[0] += TILE_SIZE;
		}
		for (int i = 1; i < size; i++) {
			if (posX[0] == posX[i] && posY[0] == posY[i]) {
				isRunning = false;
			}
		}
		if (posX[0] >= SCREEN_W || posY[0] >= SCREEN_H || posY[0] < 0 || posX[0] < 0) {
			isRunning = false;
		}
	}

	public void drawApple() {
		appleX = (int) (Math.random() * TILE_SIZE) * TILE_SIZE;
		appleY = (int) (Math.random() * TILE_SIZE) * TILE_SIZE;
	}

	public void update() {
		move();
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(100);
				update();
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class SnakeGame extends JFrame {
	private Snake snake;

	public static void main(String[] args) {
		SnakeGame frame = new SnakeGame();
		frame.setVisible(true);
	}

	public SnakeGame() {
		snake = new Snake();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		add(snake);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				snake.w = false;
				snake.a = false;
				snake.s = false;
				snake.d = false;

				System.out.println("A");
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					snake.w = true;
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					snake.s = true;
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					snake.a = true;
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					snake.d = true;
					break;
				default:
					break;
				}
			}
		});
		pack();
		setLocationRelativeTo(null);
	}

}
