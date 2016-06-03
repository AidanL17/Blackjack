import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Game implements ActionListener {
	
	JFrame frame = new JFrame("Blackjack");
	JButton hit = new JButton("Hit");
	boolean cards[][] = new boolean[13][4];
	int bank = 100;
	int bet = 0;
	final int PLUS = 1;
	final int MINUS = 0;
	Container west = new Container();
	Container east = new Container();
	Container betChange = new Container();
	JButton doubleDown = new JButton("Double Down");
	JButton surrender = new JButton("Surrender");
	JButton stand = new JButton("Stand");
	JButton allIn = new JButton("BET IT ALL");
	JButton plusTen = new JButton("+ $10");
	JButton plusFive = new JButton("+ $5");
	JButton plusOne = new JButton("+ $1");
	JButton minusTen = new JButton("- $10");
	JButton minusFive = new JButton("- $5");
	JButton minusOne = new JButton("- $1");
	JTextArea fund = new JTextArea("Your kid's college fund: $" + bank);
	JTextArea currentBet = new JTextArea("Your bet: $" + bet);

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		frame.setSize(800, 400);
		frame.setLayout(new BorderLayout());
		west.setLayout(new GridLayout(4,1));
		east.setLayout(new GridLayout(4,1));
		betChange.setLayout(new GridLayout(3,2));
		betChange.add(plusTen);
		betChange.add(minusTen);
		betChange.add(plusFive);
		betChange.add(minusFive);
		betChange.add(plusOne);
		betChange.add(minusOne);
		east.add(fund);
		east.add(currentBet);
		east.add(betChange);
		east.add(allIn);
		west.add(hit);
		west.add(doubleDown);
		west.add(surrender);
		west.add(stand);
		hit.addActionListener(this);
		doubleDown.addActionListener(this);
		surrender.addActionListener(this);
		stand.addActionListener(this);
		plusTen.addActionListener(this);
		minusTen.addActionListener(this);
		plusFive.addActionListener(this);
		minusFive.addActionListener(this);
		plusOne.addActionListener(this);
		minusOne.addActionListener(this);
		allIn.addActionListener(this);
		frame.add(west, BorderLayout.WEST);
		frame.add(east, BorderLayout.EAST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		shuffle();
	}
	
	public void deal() {
		ArrayList<Integer> deck = new ArrayList<Integer>();
		for (int x = 0; x < 13; x++) {
			for (int y = 0; y < 4; y++) {
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
		if (event.getSource().equals(hit)) {
			deal();
		}
		if (event.getSource().equals(allIn)) {
			bet += bank;
			bank = 0;
			System.out.println(bet);
			System.out.println(bank);
			fund.setText("Your kid's college fund: $" + bank);
			currentBet.setText("Your bet: $" + bet);
		}
		if (event.getSource().equals(plusTen)) {
			changeBetAmount(10, PLUS);
		}
		if (event.getSource().equals(minusTen)) {
			changeBetAmount(-10, MINUS);
		}
		if (event.getSource().equals(plusFive)) {
			changeBetAmount(5, PLUS);
		}
		if (event.getSource().equals(minusFive)) {
			changeBetAmount(-5, MINUS);
		}
		if (event.getSource().equals(plusOne)) {
			changeBetAmount(1, PLUS);
		}
		if (event.getSource().equals(minusOne)) {
			changeBetAmount(-1, MINUS);
		}
	}
	
	public void shuffle() {
		for (int x = 0; x < 13; x++) {
			for (int y = 0; y < 4; y++) {
				cards[x][y] = true;
			}
		}
	}
	
	public void changeBetAmount(int changeBy, int plusOrMinus) {
		if (plusOrMinus == PLUS) {
			if (bank >= changeBy) {
				bet += changeBy;
				bank -= changeBy;
			}
		}
		if (plusOrMinus == MINUS) {
			if (bet >= -changeBy) {
				bet += changeBy;
				bank -= changeBy;
			}
		}
		System.out.println(bet);
		System.out.println(bank);
		fund.setText("Your kid's college fund: $" + bank);
		currentBet.setText("Your bet: $" + bet);
	}

}
