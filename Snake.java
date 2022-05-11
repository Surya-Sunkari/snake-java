package fun;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Snake implements KeyListener{
	
	String curDir = "right";
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
		
		SnakeBody head = new SnakeBody(6, 4); // maybe do randomized start location?
		
		printGrid();
		
		
		
	}
	
	public static void printGrid() {

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
        	System.out.println("now moving: " + curDir);
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
	
	public SnakeBody(int row, int col) {
		curRow = row;
		curCol = col;
		next = null;
		character = "O";
		Snake.grid[row][col] = character;
	}
	
	public SnakeBody(int row, int col, SnakeBody pointer) {
		curRow = row;
		curCol = col;
		next = pointer;
		character = "#";
	}
	
}
