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
	int bank = 100;
	int bet = 0;
	int winnings = 0;
	final int DEALER = 0;
	final int USER = 1;
	final int PLUS = 1;
	final int MINUS = 0;
	final int LOSS = 0;
	final int WIN = 1;
	final int TIE = 2;
	final int SURRENDER = 3;
	final int BLACKJACK = 4;
	String faceDown = "";
	int playerHandSize = 0;
	int dealerHandSize = 0;
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
			userHand[0][i].setEnabled(false);
		}
		for (int i = 0; i < 11; i++) {
			dealerHand[0][i] = new JButton("");
			dealerHand[0][i].setEnabled(false);
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
		handOver(LOSS);
		handResult.setText("Welcome to Blackjack");
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
        if (player == USER) {
        	for (int i = 0; i < 11; i++) {
				if (userHand[0][i].isEnabled() == false) {
					playerCards.add(userHand[0][i]);
					playerHandSize++;
					userHand[0][i].setText(cardName);
					userHand[0][i].setEnabled(true);
					if ((deck.get(choice) / 10) == 0) {
						if (playerAces == 0) {
							playerScore += 10;
						}
						playerAces++;
						playerScore += 1;
					}
					if ((deck.get(choice) / 10) < 9 && (deck.get(choice) / 10) > 0) {
						playerScore += (deck.get(choice) / 10 + 1);
					}
					if ((deck.get(choice) / 10) > 8) {
						playerScore += 10;
					}
					if (playerScore > 21) {
						if (playerAces > 0) {
							playerAces = -3;
							playerScore -= 10;
						}
						if (playerScore > 21) {
							handOver(LOSS);
						}
					}
					System.out.println("P " + playerScore);
					return;
				}
			}
        }
        if (player == DEALER) {
        	for (int i = 0; i < 11; i++) {
				if (dealerHand[0][i].isEnabled() == false) {
					dealerCards.add(dealerHand[0][i]);
					dealerHandSize++;
					if (dealerHandSize == 1) {
						faceDown = cardName;
					}
					if (dealerHandSize > 1) {
						dealerHand[0][i].setText(cardName);
					}
					dealerHand[0][i].setEnabled(true);
					if ((deck.get(choice) / 10) == 0) {
						if (dealerAces == 0) {
							dealerScore += 10;
						}
						dealerAces++;
						dealerScore += 1;
					}
					if ((deck.get(choice) / 10) < 9 && (deck.get(choice) / 10) > 0) {
						dealerScore += (deck.get(choice) / 10 + 1);
					}
					if ((deck.get(choice) / 10) > 8) {
						dealerScore += 10;
					}
					if (dealerHandSize == 2 && dealerScore == 21) {
						dealerHand[0][0].setText(faceDown);
					}
					if (dealerScore > 21) {
						if (dealerAces > 0) {
							dealerAces = -3;
							dealerScore -= 10;
						}
						if (dealerScore > 21) {
							if (playerHandSize == 2 && playerScore == 21) {
								handOver(BLACKJACK);
							}
							else {
								handOver(WIN);
							}
							
						}
					}
					System.out.println("D " + dealerScore);
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
		if (event.getSource().equals(surrender)) {
			handOver(SURRENDER);
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
			bank -= bet;
			bet = bet * 2;
			deal(USER);
			fund.setText("Your kid's college fund: $" + bank);
			currentBet.setText("Your bet: $" + bet);
			doubleDown.setEnabled(false);
			hit.setEnabled(false);
			surrender.setEnabled(false);
			System.out.println(bet);
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
			nextHand.remove(next);
			handStart();
		}
		if (event.getSource().equals(stand)) {
			dealerHand[0][0].setText(faceDown);
			while (dealerScore < 17) {
				deal(DEALER);
			}
			if (dealerScore > playerScore && dealerScore < 22) {
				handOver(LOSS);
			}
			if (playerHandSize == 2 && playerScore == 21) {
				if (dealerHandSize == 2 && dealerScore == 21) {
					handOver(TIE);
				}
				else {
					handOver(BLACKJACK);
				}
			}
			if (playerScore > dealerScore && playerScore < 22) {
				if (playerHandSize == 2 && playerScore == 21) {
					handOver(BLACKJACK);
				}
				handOver(WIN);
			}
			if (playerScore == dealerScore) {
				if (playerScore == 21) {
					if (playerHandSize != dealerHandSize) {
						if (playerHandSize == 2) {
							handOver(BLACKJACK);
						}
						if (dealerHandSize == 2) {
							handOver(LOSS);
						}
					}
				}
				handOver(TIE);
			}
		}
	}
	
	public void handStart() {
		handResult.setText("");
		for (int i = 0; i < 11; i++) {
			dealerCards.remove(dealerHand[0][i]);
			dealerHand[0][i].setText("");
			dealerHand[0][i].setEnabled(false);
		}
		for (int i = 0; i < 11; i++) {
			playerCards.remove(userHand[0][i]);
			userHand[0][i].setText("");
			userHand[0][i].setEnabled(false);
		}
		playerAces = 0;
		dealerAces = 0;
		playerHandSize = 0;
		dealerHandSize = 0;
		playerScore = 0;
		dealerScore = 0;
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
	
	public void handOver(int result) {
		hit.setEnabled(false);
		stand.setEnabled(false);
		doubleDown.setEnabled(false);
		surrender.setEnabled(false);
		if (result == LOSS) {
			winnings = 0;
			handResult.setText("You lost $" + bet + ".");
		}
		if (result == WIN) {
			winnings = 2 * bet;
			handResult.setText("You won $" + winnings + ".");
		}
		if (result == TIE) {
			winnings = bet;
			handResult.setText("You broke even.");
		}
		if (result == SURRENDER) {
			winnings = bet / 2;
			handResult.setText("You lost $" + winnings + ".");
		}
		if (result == BLACKJACK) {
			winnings = 5/2 * bet;
			handResult.setText("Blackjack! You won $" + winnings + ".");
		}
		bank += winnings;
		bet = 0;
		allIn.setEnabled(true);
		plusTen.setEnabled(true);
		plusFive.setEnabled(true);
		plusOne.setEnabled(true);
		minusTen.setEnabled(true);
		minusFive.setEnabled(true);
		minusOne.setEnabled(true);
		fund.setText("Your kid's college fund: $" + bank);
		currentBet.setText("Your bet: $" + bet);
		if (bank > 0) {
			if (bank >= 42000) {
				allIn.setEnabled(false);
				plusTen.setEnabled(false);
				plusFive.setEnabled(false);
				plusOne.setEnabled(false);
				minusTen.setEnabled(false);
				minusFive.setEnabled(false);
				minusOne.setEnabled(false);
				handResult.setText("I think that's enough for college.");
			}
			else {
				nextHand.add(next);
			}
		}
		else {
			handResult.setText("I guess that kid's not going to college, huh?");
			allIn.setEnabled(false);
			plusTen.setEnabled(false);
			plusFive.setEnabled(false);
			plusOne.setEnabled(false);
			minusTen.setEnabled(false);
			minusFive.setEnabled(false);
			minusOne.setEnabled(false);
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
