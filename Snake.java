package fun;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Snake implements KeyListener{
	
	public static String curDir = "right";
	public static String[][] grid = new String[13][29];
	public static int points = 0;
	public static boolean gameOver = false;
	
	public static void main(String[] args) {
		//sets up keylistener
		new Snake();
		
		//sets up snake grid
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = " ";
			}
		}
		
		int[] randStart = getRandomCoords();
		Head head = new Head(randStart[0], randStart[1]);
		genRandApple();
		
		while(!gameOver) {
			printGrid();
			gameOver = head.move();
			wait(200);
			
		}
		
		
		
		
		
	}
	
	public static void genRandApple() {
		int[] randAppleCoords = getRandomCoords();
		grid[randAppleCoords[0]][randAppleCoords[1]] = "A";
	}
	
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	public static int[] getRandomCoords() {
		
		int maxRow = grid.length;
		int maxCol = grid[0].length;
		
		int randRow, randCol;
		do {
			randRow = (int) (maxRow * Math.random());
			randCol = (int) (maxCol * Math.random());
		} while(!grid[randRow][randCol].equals(" "));
		
		return new int[] {randRow, randCol};
	}
	
	public static void printGrid() {
		for(int i = 0; i < 50; i++) System.out.println();
		for(int i = 0; i < grid[0].length; i++) {
			if(i<10)
				System.out.print(" " + i);
			else
				System.out.print(i);
		}
		System.out.println();
		for(int i = 0; i < grid.length*4.4+1; i++) System.out.print("-");
		System.out.println();
		for(int i = 0; i < grid.length; i++) {
			String line = "|";
			for(int j = 0; j < grid[i].length; j++) {
				line += grid[i][j];
				line += "|";
			}
			System.out.println(line + " " + i);
			for(int k = 0; k < grid.length*4.4+1; k++) System.out.print("-");
			System.out.println();
		}
		System.out.println("Points: " + Snake.points);
	}
	
	public Snake() {
		JFrame frame = new JFrame("Key Listener");
        Container contentPane = frame.getContentPane();
        
        JTextField textField = new JTextField();
        textField.addKeyListener(this);
        contentPane.add(textField, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
	}

	@Override
    public void keyPressed(KeyEvent e) {
        String code = KeyEvent.getKeyText(e.getKeyCode()).toLowerCase();
        if(" left right up down ".contains(" " + code + " ")) {
        	changeDirection(code);
        }
        
    }
	
	private void changeDirection(String newDir) {
        if(curDir.equals(newDir) || (curDir.equals("left") && newDir.equals("right"))
        		|| (curDir.equals("right") && newDir.equals("left"))
        		|| (curDir.equals("up") && newDir.equals("down"))
        		|| (curDir.equals("down") && newDir.equals("up"))) {
        	
        } else {
        	curDir = newDir;
        }
    	
    }
	
    @Override
    public void keyReleased(KeyEvent event) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent event) {
        
    }
}

class SnakeBody {
	
	int curRow;
	int curCol;
	int oldRow;
	int oldCol;
	SnakeBody next;
	String character;
	
	public SnakeBody(int row, int col) {
		curRow = row;
		curCol = col;
		character = "#";
		Snake.grid[row][col] = character;
	}
	
	public void move(int newRow, int newCol) {
		Snake.grid[curRow][curCol] = " ";
		Snake.grid[newRow][newCol] = character;
		oldRow = curRow;
		oldCol = curCol;
		curRow = newRow;
		curCol = newCol;
	}
	
}

class Head {
	int curRow;
	int curCol;
	String character;
	SnakeBody next;
	
	public static int length = 1;
	
	
	public Head(int row, int col) {
		curRow = row;
		curCol = col;
		character = "O";
		Snake.grid[row][col] = character;
	}
	
	public void eatApple(SnakeBody tail, SnakeBody prev, int row, int col) {
		int len = length;
		
		if(len == 1) {
			next = new SnakeBody(row, col);
		}
		else {
			prev.next = new SnakeBody(row, col);
		}
		
		length++;
		
		
	}
	
	
	public boolean move() {
		String dir = Snake.curDir;
		int oldRow = curRow;
		int oldCol = curCol;		
		
		//sets grid at current pos to blank
		Snake.grid[curRow][curCol] = " ";
		
		//finds new location of head
		if(dir.equals("right")) {
			curCol = (curCol+1)%Snake.grid[0].length;
		} else if(dir.equals("left")) {
			curCol = (curCol+Snake.grid[0].length-1)%Snake.grid[0].length;
		}else if(dir.equals("up")) {
			curRow = (curRow + Snake.grid.length-1)%Snake.grid.length;
		}else {
			curRow = (curRow+1)%Snake.grid.length;
		}
		
		if(Snake.grid[curRow][curCol].equals("A")) {
			int l = 0;
		}
		//sets curBody equal to tail end of snake and gets pre move position of tail
		SnakeBody curBody = next;
		int prevRow = oldRow;
		int prevCol = oldCol;
		SnakeBody prevBody = curBody;
		while(curBody != null) {
			curBody.move(prevRow, prevCol);
			prevRow = curBody.oldRow;
			prevCol = curBody.oldCol;
			prevBody = curBody;
			curBody = curBody.next;
		}
		
		//if head is going to land on apple
		if(Snake.grid[curRow][curCol].equals("A")) {
			Snake.points++;
			eatApple(curBody, prevBody, prevRow, prevCol);
			Snake.genRandApple();
		}
		else if(Snake.grid[curRow][curCol].equals("A")) {
			System.out.println("Game over!");
			return true;
		}
		
		Snake.grid[curRow][curCol] = character;
		return false;
	}
	
}
