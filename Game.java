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
	JButton dealerHand[][] = new JButton[1][11];
	JButton userHand[][] = new JButton[1][11];
	boolean cards[][] = new boolean[13][4];
	boolean dealerBust = false;
	boolean playerBust = false;
	int bank = 100;
	int bet = 0;
	int won = 0;
	final int DEALER = 0;
	final int USER = 1;
	final int PLUS = 1;
	final int MINUS = 0;
	int dealerScore = 0;
	int playerScore = 0;
	int dealerAces = 0;
	int playerAces = 0;
	Container west = new Container();
	Container east = new Container();
	Container dealerCards = new Container();
	Container playerCards = new Container();
	Container center = new Container();
	Container betChange = new Container();
	Container nextHand = new Container();
	JButton next = new JButton("Start New Hand");
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
	JTextArea handResult = new JTextArea("This is where it says what you've won or lost.");

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		frame.setSize(800, 400);
		frame.setLayout(new BorderLayout());
		west.setLayout(new GridLayout(4,1));
		east.setLayout(new GridLayout(4,1));
		nextHand.setLayout(new GridLayout(1,2));
		playerCards.setLayout(new GridLayout(1,11));
		dealerCards.setLayout(new GridLayout(1,11));
		betChange.setLayout(new GridLayout(3,2));
		center.setLayout(new GridLayout(3,1));
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
		for (int i = 0; i < 11; i++) {
			userHand[0][i] = new JButton("");
		}
		for (int i = 0; i < 11; i++) {
			dealerHand[0][i] = new JButton("");
		}
		center.add(dealerCards);
		center.add(nextHand);
		nextHand.add(handResult);
		center.add(playerCards);
		next.addActionListener(this);
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
		frame.add(center, BorderLayout.CENTER);
		frame.add(west, BorderLayout.WEST);
		frame.add(east, BorderLayout.EAST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//shuffle();
		handOver();
	}
	
	public void deal(int player) {
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
        String face = "";
        if ((deck.get(choice) / 10) == 0) {
			face = "ace";
		}
		if ((deck.get(choice) / 10) < 10 && (deck.get(choice) / 10) > 0) {
			face += (deck.get(choice) / 10 + 1);
		}
		if ((deck.get(choice) / 10) == 10) {
			face = "jack";
		}
		if ((deck.get(choice) / 10) == 11) {
			face = "queen";
		}
		if ((deck.get(choice) / 10) == 12) {
			face = "king";
		}
        String suit = "";
        if ((deck.get(choice) % 10) == 0) {
        	suit = "_of_spades";
        }
        if ((deck.get(choice) % 10) == 1) {
        	suit = "_of_diamonds";
        }
        if ((deck.get(choice) % 10) == 2) {
        	suit = "_of_clubs";
        }
        if ((deck.get(choice) % 10) == 3) {
        	suit = "_of_hearts";
        }
        String cardName = face + suit + ".png";
        System.out.println(cardName);
        if (player == USER) {
        	for (int i = 0; i < 11; i++) {
				if (userHand[0][i].isEnabled()) {
					playerCards.add(userHand[0][i]);
					userHand[0][i].setText((deck.get(choice) / 10) + "," + (deck.get(choice) % 10));
					userHand[0][i].setEnabled(false);
					if ((deck.get(choice) / 10) == 0) {
						playerAces++;
						if (playerAces < 1) {
							playerScore += 10;
						}
						playerScore +=1;
					}
					if ((deck.get(choice) / 10) < 9 && (deck.get(choice) / 10) > 0) {
						playerScore += (deck.get(choice) / 10 + 1);
					}
					if ((deck.get(choice) / 10) > 8) {
						playerScore += 10;
					}
					if (playerScore > 21) {
						if (playerAces > 0) {
							playerScore -= 10;
						}
						if (playerScore > 21) {
							playerBust = true;
						}
					}
					System.out.println(playerScore);
					return;
				}
			}
        }
        if (player == DEALER) {
        	for (int i = 0; i < 11; i++) {
				if (dealerHand[0][i].isEnabled()) {
					dealerCards.add(dealerHand[0][i]);
					dealerHand[0][i].setText((deck.get(choice) / 10) + "," + (deck.get(choice) % 10));
					dealerHand[0][i].setEnabled(false);
					if ((deck.get(choice) / 10) == 0) {
						dealerAces++;
						if (dealerAces < 1) {
							dealerScore += 10;
						}
						dealerScore +=1;
					}
					if ((deck.get(choice) / 10) < 9 && (deck.get(choice) / 10) > 0) {
						dealerScore += (deck.get(choice) / 10 + 1);
					}
					if ((deck.get(choice) / 10) > 8) {
						dealerScore += 10;
					}
					if (dealerScore > 21) {
						if (dealerAces > 0) {
							dealerScore -= 10;
						}
						if (dealerScore > 21) {
							dealerBust = true;
						}
					}
					System.out.println(dealerScore);
					return;
				}
			}
        }
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource().equals(hit)) {
			deal(USER);
			surrender.setEnabled(false);
		}
		if (event.getSource().equals(allIn)) {
			bet += bank;
			bank = 0;
			System.out.println(bet);
			System.out.println(bank);
			fund.setText("Your kid's college fund: $" + bank);
			currentBet.setText("Your bet: $" + bet);
		}
		if (event.getSource().equals(doubleDown)) {
			deal(USER);
			bank -= bet;
			bet += bet;
			fund.setText("Your kid's college fund: $" + bank);
			currentBet.setText("Your bet: $" + bet);
			hit.setEnabled(false);
			surrender.setEnabled(false);
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
		if (event.getSource().equals(next)) {
			handStart();
		}
	}
	
	public void handStart() {
		nextHand.remove(next);
		for (int i = 0; i < 11; i++) {
			dealerCards.remove(dealerHand[0][i]);
			dealerHand[0][i].setText("");
			dealerHand[0][i].setEnabled(true);
		}
		for (int i = 0; i < 11; i++) {
			playerCards.remove(userHand[0][i]);
			userHand[0][i].setText("");
			userHand[0][i].setEnabled(true);
		}
		shuffle();
		deal(USER);
		deal(USER);
		deal(DEALER);
		deal(DEALER);
		hit.setEnabled(true);
		stand.setEnabled(true);
		doubleDown.setEnabled(false);
		surrender.setEnabled(true);
		allIn.setEnabled(false);
		plusTen.setEnabled(false);
		plusFive.setEnabled(false);
		plusOne.setEnabled(false);
		minusTen.setEnabled(false);
		minusFive.setEnabled(false);
		minusOne.setEnabled(false);
		if (bet <= bank) {
			doubleDown.setEnabled(true);
		}
	}
	
	public void handOver() {
		nextHand.add(next);
		hit.setEnabled(false);
		stand.setEnabled(false);
		doubleDown.setEnabled(false);
		surrender.setEnabled(false);
		bank += won;
		bet = 0;
		allIn.setEnabled(true);
		plusTen.setEnabled(true);
		plusFive.setEnabled(true);
		plusOne.setEnabled(true);
		minusTen.setEnabled(true);
		minusFive.setEnabled(true);
		minusOne.setEnabled(true);
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
