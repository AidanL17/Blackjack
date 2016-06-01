import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Game implements ActionListener {
	
	JFrame frame = new JFrame("Blackjack");
	JButton dealTest = new JButton("Pick a card, any card");
	boolean cards[][] = new boolean[4][13];

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		frame.setSize(400, 400);
		frame.setLayout(new GridLayout(1,1));
		frame.add(dealTest);
		dealTest.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 13; y++) {
				cards[x][y] = true;
			}
		}
	}
	
	public void Deal() {
		ArrayList<Integer> deck = new ArrayList<Integer>();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 13; y++) {
				if (cards[x][y] == true) {
					deck.add(x*10+y);
				}
			}
		}
		int choice = (int)(Math.random() * deck.size());
        cards[deck.get(choice) / 10][deck.get(choice) % 10] = false;
        System.out.println((deck.get(choice) / 10) + "," + (deck.get(choice) % 10));
        System.out.println("test");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource().equals(dealTest)) {
			Deal();
		}
	}

}
