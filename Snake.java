package fun;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Snake implements KeyListener{
	
	static String curDir = "right";
	public static String[][] grid = new String[13][29];
	
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
		
		while(true) {
			printGrid();
			head.move();
			wait(500);
		}
		
		
		
		
		
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
		for(int i = 0; i < grid.length*4.4+1; i++) System.out.print("-");
		System.out.println();
		for(int i = 0; i < grid.length; i++) {
			String line = "|";
			for(int j = 0; j < grid[i].length; j++) {
				line += grid[i][j];
				line += "|";
			}
			System.out.println(line);
			for(int k = 0; k < grid.length*4.4+1; k++) System.out.print("-");
			System.out.println();
		}

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
        if(!curDir.equals(newDir)) {
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
	SnakeBody next;
	String character;
	
	public SnakeBody(int row, int col, SnakeBody pointer) {
		curRow = row;
		curCol = col;
		next = pointer;
		character = "#";
	}
	
}

class Head {
	int curRow;
	int curCol;
	String character;
	
	public Head(int row, int col) {
		curRow = row;
		curCol = col;
		character = "O";
		Snake.grid[row][col] = character;
	}
	
	public void move() {
		String dir = Snake.curDir;
		
		Snake.grid[curRow][curCol] = " ";
		
		if(dir.equals("right")) {
			curCol = (curCol+1)%Snake.grid[0].length;
		} else if(dir.equals("left")) {
			curCol = (curCol+Snake.grid[0].length-1)%Snake.grid[0].length;
		}else if(dir.equals("up")) {
			curRow = (curRow + Snake.grid.length-1)%Snake.grid.length;
		}else {
			curRow = (curRow+1)%Snake.grid.length;
		}
		
		Snake.grid[curRow][curCol] = character;
	}
	
}
