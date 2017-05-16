package main;

import static java.lang.System.out;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import model.Card;
import model.CardButton;
import model.Deck;
import model.Player;
import rmi.IPlayer;
import rmi.PlayerImplementation;
import rmi.RegistryManager;
import rmi.ServerDb;

public class GameBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, top, mid, bot, right;
	private boolean isStart = true;
	private Deck cardDeck = new Deck();
	private CardButton lastButtonPressed;
	private List<CardButton> pressedCards;
	private List<CardButton> posibleCardCombinations;
	private ButtonGroup playerCardsGroup;
	private Player player;
	private int counter;
	private boolean onMove;
	private boolean isDealer;
	private IPlayer me;
	private List<Card> cardsOnTableStart;
	private List<Card> cardsInPlayer2Hand;

	private ServerDb serverDb;

	private static final int NUMBER_OF_CARDS_ON_TALON = 4;
	private static final int NUMBER_OF_CARDS_TO_DEAL = 12;

	private JPanel midPlayable;
	private JPanel midPlayableTop;
	private JPanel midPlayableBot;
	private JPanel rightTop;
	private JPanel rightBot;
	private JLabel usernameTop;
	private JLabel tableBot;
	private JLabel scoreBot;
	private JLabel usernameBot;
	private JLabel scoreTop;
	private JLabel tableTop;
	private JPanel rightCenter;

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { GameBoard frame = new GameBoard();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 * }); }
	 */

	public GameBoard(String username, String dbHost) {
		// player = new Player(username, "localhost", true, true);
		try {
			serverDb = (ServerDb) RegistryManager.get(dbHost).lookup(ServerDb.RMI_NAME);
			initalizationOfPlayer(username);
		} catch (RemoteException | NotBoundException ex) {
			String poruka = "Greska pri inicijalizaciji!\nIgra ce se sada zavrsiti.";
			JOptionPane.showMessageDialog(this, poruka, "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (me != null) {
					try {
						IPlayer p = getPlayer(isDealer ? serverDb.getPlayer1() : serverDb.getPlayer2());
						p.connectionLost();
					} catch (RemoteException | NotBoundException ex) {
						JOptionPane.showMessageDialog(GameBoard.this, "Greska u konekciji!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		playerCardsGroup = new ButtonGroup();
		cardsOnTableStart = new LinkedList<>();
		cardsInPlayer2Hand = new LinkedList<>();
		pressedCards = new LinkedList<>();
		counter = 0;
		posibleCardCombinations = new LinkedList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 900);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(700, 820));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(34, 139, 34));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// >>>>>> TOP PANEL <<<<<<

		top = new JPanel();
		top.setAlignmentY(Component.TOP_ALIGNMENT);
		top.setAlignmentX(Component.LEFT_ALIGNMENT);
		top.setBackground(new Color(34, 139, 34));
		contentPane.add(top, BorderLayout.NORTH);
		top.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// >>>>>> RIGHT PANEL <<<<<<

		right = new JPanel();
		right.setBackground(new Color(34, 139, 34));
		contentPane.add(right, BorderLayout.EAST);

		// >>>>>> BOT PANEL <<<<<<

		bot = new JPanel();
		bot.setBackground(new Color(34, 139, 34));
		contentPane.add(bot, BorderLayout.SOUTH);

		// >>>>>> MID PANEL <<<<<<

		mid = new JPanel();
		mid.setAlignmentX(Component.LEFT_ALIGNMENT);
		mid.setBackground(new Color(34, 139, 34));
		contentPane.add(mid, BorderLayout.CENTER);
		FlowLayout fl_mid = new FlowLayout(FlowLayout.CENTER, 180, 180);
		mid.setLayout(fl_mid);

		midPlayable = new JPanel();
		midPlayable.setBackground(new Color(34, 139, 34));
		mid.add(midPlayable);
		midPlayable.setLayout(new BorderLayout(0, 0));

		midPlayableTop = new JPanel();
		midPlayableTop.setBackground(new Color(34, 139, 3));
		midPlayable.add(midPlayableTop, BorderLayout.NORTH);

		midPlayableBot = new JPanel();
		midPlayableBot.setBackground(new Color(34, 139, 34));
		midPlayable.add(midPlayableBot, BorderLayout.SOUTH);
		right.setLayout(new BorderLayout(0, 0));

		rightTop = new JPanel();
		rightTop.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		rightTop.setBackground(new Color(34, 139, 34));
		right.add(rightTop, BorderLayout.NORTH);
		rightTop.setLayout(new BoxLayout(rightTop, BoxLayout.Y_AXIS));

		usernameTop = new JLabel("UsernameTop");
		usernameTop.setAlignmentX(Component.CENTER_ALIGNMENT);
		usernameTop.setFont(new Font("Times New Roman", Font.BOLD, 15));
		rightTop.add(usernameTop);

		scoreTop = new JLabel("Score: 0");
		scoreTop.setVerticalAlignment(SwingConstants.TOP);
		scoreTop.setFont(new Font("Times New Roman", Font.BOLD, 15));
		scoreTop.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightTop.add(scoreTop);

		tableTop = new JLabel("Table: 0");
		tableTop.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		tableTop.setVerticalAlignment(SwingConstants.TOP);
		tableTop.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tableTop.setAlignmentX(0.5f);
		rightTop.add(tableTop);

		rightBot = new JPanel();
		rightBot.setAlignmentY(Component.TOP_ALIGNMENT);
		rightBot.setBackground(new Color(34, 139, 34));
		right.add(rightBot, BorderLayout.SOUTH);
		rightBot.setLayout(new BoxLayout(rightBot, BoxLayout.Y_AXIS));

		tableBot = new JLabel("Table: 0");
		tableBot.setVerticalAlignment(SwingConstants.TOP);
		tableBot.setAlignmentX(Component.CENTER_ALIGNMENT);
		tableBot.setAlignmentY(Component.TOP_ALIGNMENT);
		tableBot.setFont(new Font("Times New Roman", Font.BOLD, 15));
		rightBot.add(tableBot);

		scoreBot = new JLabel("Score: 0");
		scoreBot.setVerticalAlignment(SwingConstants.TOP);
		scoreBot.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreBot.setFont(new Font("Times New Roman", Font.BOLD, 15));
		rightBot.add(scoreBot);

		usernameBot = new JLabel(username);
		usernameBot.setVerticalAlignment(SwingConstants.TOP);
		usernameBot.setAlignmentX(Component.CENTER_ALIGNMENT);
		usernameBot.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		usernameBot.setFont(new Font("Times New Roman", Font.BOLD, 15));
		rightBot.add(usernameBot);

		rightCenter = new JPanel();
		rightCenter.setBackground(new Color(34, 139, 34));
		right.add(rightCenter, BorderLayout.CENTER);
		GridBagLayout gbl_rightCenter = new GridBagLayout();
		gbl_rightCenter.columnWidths = new int[] { 20, 0 };
		gbl_rightCenter.rowHeights = new int[] { 377, 9, 0, 0 };
		gbl_rightCenter.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_rightCenter.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		rightCenter.setLayout(gbl_rightCenter);

		Timer timer = new Timer(30000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (player.isOnMove() && bot.getComponentCount() != 0) {
					int cardsInHand = bot.getComponentCount();
					Random rand = new Random();
					int randomNumber = rand.nextInt(cardsInHand - 1);
					CardButton randomCard = (CardButton) bot.getComponent(randomNumber);
					if (counter % 2 == 1) {
						midPlayableTop.add(randomCard);
						counter++;
					} else {
						midPlayableBot.add(randomCard);
						counter++;
					}
					bot.remove(randomCard);
					player.setOnMove(false);
					repaint();
					revalidate();
				}
			}
		});
		timer.setRepeats(true);

		JButton deck = new JButton("");
		GridBagConstraints gbc_deck = new GridBagConstraints();
		gbc_deck.gridwidth = 0;
		gbc_deck.gridheight = 0;
		gbc_deck.gridx = 0;
		gbc_deck.gridy = 0;
		rightCenter.add(deck, gbc_deck);
		deck.setMargin(new Insets(0, 0, 0, 0));
		deck.setBackground(new Color(34, 139, 34));
		deck.setIcon(resize("Cards/Deck.png", 80, 120));
		deck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (player.isDealer())
					dealCards();
				timer.start();
			}
		});
		deck.setMinimumSize(new Dimension(20, 9));
		deck.setMaximumSize(new Dimension(20, 9));

		/*
		 * new java.util.Timer().schedule(new java.util.TimerTask() {
		 * 
		 * @Override public void run() { if (player.isOnMove() &&
		 * bot.getComponentCount() != 0) { int cardsInHand =
		 * bot.getComponentCount(); Random rand = new Random(); int randomNumber
		 * = rand.nextInt(cardsInHand - 1); CardButton randomCard = (CardButton)
		 * bot.getComponent(randomNumber); if (counter % 2 == 1) {
		 * midPlayableTop.add(randomCard); } else {
		 * midPlayableBot.add(randomCard); } bot.remove(randomCard); } } },
		 * 10000);
		 */

		if (bot.getComponentCount() == 0 && top.getComponentCount() == 0 && cardDeck.getDeck().isEmpty()) {
			if (midPlayableTop.getComponentCount() != 0) {
				CardButton[] cardButtonsTop = (CardButton[]) midPlayableTop.getComponents();
				player.setNumberOfTakenCards(player.getNumberOfTakenCards() + cardButtonsTop.length);
				for (CardButton card : cardButtonsTop) {
					player.setScore(player.getScore() + card.getCard().getValue());
				}
				midPlayable.remove(midPlayableTop);
			}
			if (midPlayableBot.getComponentCount() != 0) {
				CardButton[] cardButtonsBot = (CardButton[]) midPlayableBot.getComponents();
				player.setNumberOfTakenCards(player.getNumberOfTakenCards() + cardButtonsBot.length);
				for (CardButton card : cardButtonsBot) {
					player.setScore(player.getScore() + card.getCard().getValue());
				}
				midPlayable.remove(midPlayableBot);
			}
		}
	}

	private ImageIcon resize(String path, int width, int height) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}

	public void dealCards() {
		if (player.isDealer() && !cardDeck.getDeck().isEmpty()) {
			cardDeck.shuffleDeck();
			int sizeOfDeck = cardDeck.getDeck().size();
			System.out.println("Size of deck: " + sizeOfDeck);
			if (isStart) {
				for (int i = sizeOfDeck - 1; i >= sizeOfDeck - NUMBER_OF_CARDS_ON_TALON; i--) {
					cardDeck.getDeck().get(i).setPlayerCard(false);
					cardsOnTableStart.add(cardDeck.getDeck().get(i));
					if (counter % 2 == 1) {
						midPlayableTop.add(setCard(cardDeck.getDeck().get(i)));
						counter++;
					} else {
						midPlayableBot.add(setCard(cardDeck.getDeck().get(i)));
						counter++;
					}
					cardDeck.getDeck().remove(i);
				}
				sizeOfDeck = sizeOfDeck - NUMBER_OF_CARDS_ON_TALON;
				isStart = false;
			} else {
				cardsOnTableStart.clear();
			}
			if (bot.getComponentCount() == 0 && top.getComponentCount() == 0) {
				boolean nextPlayer = false;
				for (int i = sizeOfDeck - 1; i >= sizeOfDeck - NUMBER_OF_CARDS_TO_DEAL; i--) {
					if (!nextPlayer) {
						cardDeck.getDeck().get(i).setPlayerCard(true);
						cardsInPlayer2Hand.add(cardDeck.getDeck().get(i));
						cardDeck.getDeck().remove(i);
					} else {
						cardDeck.getDeck().get(i).setPlayerCard(true);
						CardButton current = setCard(cardDeck.getDeck().get(i));
						playerCardsGroup.add(current);
						bot.add(current);
						cardDeck.getDeck().remove(i);
					}
					if (i % 3 == 0) {
						nextPlayer = nextPlayer ? false : true;
					}
				}
				try {
					IPlayer p = getPlayer(player.isDealer() ? serverDb.getPlayer1() : serverDb.getPlayer2());
					p.newDeal(cardsInPlayer2Hand, cardsOnTableStart, serverDb.getPlayer2().getUsername());
					usernameTop.setText(serverDb.getPlayer1().getUsername());

				} catch (RemoteException | NotBoundException ex) {
					JOptionPane.showMessageDialog(this, "Greska pri slanju!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			enemyCards();
			repaint();
			revalidate();
		}
	}

	private void enemyCards() {
		for (int i = 0; i < NUMBER_OF_CARDS_TO_DEAL / 2; i++) {
			JButton enemyCard = new JButton("");
			enemyCard.setMargin(new Insets(0, 0, 0, 0));
			enemyCard.setBackground(new Color(34, 139, 34));
			enemyCard.setIcon(resize("Cards/Deck.png", 80, 120));
			enemyCard.setMinimumSize(new Dimension(20, 9));
			enemyCard.setMaximumSize(new Dimension(20, 9));
			top.add(enemyCard);
		}
	}

	public void recieveDeal(List<Card> handCards, List<Card> tableCards, String username) {
		for (Card card : handCards) {
			CardButton current = setCard(card);
			out.println(card.toString());
			playerCardsGroup.add(current);
			bot.add(current);
		}
		if (!tableCards.isEmpty()) {
			for (Card card : tableCards) {
				if (counter % 2 == 1) {
					midPlayableTop.add(setCard(card));
					counter++;
				} else {
					midPlayableBot.add(setCard(card));
					counter++;
				}
			}
		}
		usernameTop.setText(username);
		enemyCards();
		repaint();
		revalidate();
	}

	public void sendMove(List<CardButton> cards, int score, int tables, boolean addedOnTable, boolean endOfMove) {
		try {

			IPlayer p = getPlayer(isDealer ? serverDb.getPlayer1() : serverDb.getPlayer2());

			p.receiveMove(cards, score, tables, addedOnTable, endOfMove);
			
			if (endOfMove) {

				onMove = false;
				/*promocijaDame(doPolje);
				if (pobedio()) {
					JOptionPane.showMessageDialog(frame, "Pobedili ste u igri!");
					return;
				}
				if (izgubio()) {
					JOptionPane.showMessageDialog(frame, "Izgubili ste u igri!");
					return;
				}*/
			}

		} catch (RemoteException | NotBoundException ex) {
			JOptionPane.showMessageDialog(this, "Greska pri slanju!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void reciveMove(List<CardButton> cards, int score, int tables, boolean addedOnTable, boolean endOfMove) {
		if(top.getComponentCount() != 0) {
			top.remove(0);
		}
		if(addedOnTable) {
			
			if(counter % 2 == 1) {
				midPlayableTop.add(cards.get(0));
				cards.get(0).setSelected(false);
			} else {
				midPlayableBot.add(cards.get(0));
				cards.get(0).setSelected(false);
			}
		} else {
			for(CardButton card : cards) {
				midPlayableTop.remove(card);
				midPlayableBot.remove(card);
			}
		}
		scoreTop.setText("Score: " + score);
		tableTop.setText("Tables: " + tables);
		if(endOfMove) {
			onMove = true;
		}
		repaint();
		revalidate();
	}

	private CardButton setCard(Card card) {
		CardButton cardButton = new CardButton(card);
		cardButton.setMargin(new Insets(0, 0, 0, 0));
		cardButton.setBackground(new Color(255, 255, 102));
		cardButton.setIcon(resize(card.getIconPath(), 80, 120));
		cardButton.setContentAreaFilled(true);
		cardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (onMove) {
					CardButton buttonPressed = (CardButton) event.getSource();
					CardButton result = null;
					if (buttonPressed.getCard().isPlayerCard()) {
						if (lastButtonPressed == buttonPressed) {
							// The same button was clicked two+ times in a row
							buttonPressed.getCard().setPlayerCard(false);
							buttonPressed.setSelected(false);
							List<CardButton> tmpList = new LinkedList<>();
							tmpList.add(buttonPressed);
							sendMove(tmpList, player.getScore(), player.getTableCounter(), true, true);
							bot.remove(buttonPressed);
							if (counter % 2 == 0) {
								midPlayableTop.add(setCard(card));
								counter++;
							} else {
								midPlayableBot.add(setCard(card));
								counter++;
							}
						}
						lastButtonPressed = buttonPressed;
						if (buttonPressed.isSelected()) {
							result = buttonPressed;
						} else {
							result = null;
						}
					} else {
						if (buttonPressed.isSelected()) {
							pressedCards.add(buttonPressed);
						} else {
							pressedCards.remove(buttonPressed);
						}
						lastButtonPressed = buttonPressed;
					}

					// if (checkResult(result, pressedCards)) {
					if (result != null && !pressedCards.isEmpty()) {
						sum_up(pressedCards, result.getCard().getCardNumber());
						if (result.getCard().getCardNumber() == 1) {
							result.getCard().setCardNumber(11);
							sum_up(pressedCards, result.getCard().getCardNumber());
							for (CardButton card : pressedCards) {
								if (card.getCard().getCardNumber() == 1) {
									card.getCard().setCardNumber(11);
									break;
								}
							}
							sum_up(pressedCards, result.getCard().getCardNumber());
							result.getCard().setCardNumber(1);
						}
						for (CardButton card : pressedCards) {
							if (card.getCard().getCardNumber() == 1) {
								card.getCard().setCardNumber(11);
								break;
							}
						}
						sum_up(pressedCards, result.getCard().getCardNumber());
						for (CardButton card : pressedCards) {
							if (card.getCard().getCardNumber() == 11) {
								card.getCard().setCardNumber(1);
								break;
							}
						}
						if (posibleCardCombinations.containsAll(pressedCards)) {
							if (result.getCard().getCardNumber() == 11) {
								result.getCard().setCardNumber(1);
							}
							player.setNumberOfTakenCards(player.getNumberOfTakenCards() + pressedCards.size() + 1);
							for (CardButton card : pressedCards) {
								player.setScore(player.getScore() + card.getCard().getValue());
								if (isComponentInMidPlayableTop(card)) {
									midPlayableTop.remove(card);
								} else {
									midPlayableBot.remove(card);
								}
							}
							sendMove(pressedCards, player.getScore(), player.getTableCounter(), false, true);
							pressedCards.clear();
							posibleCardCombinations.clear();
							player.setScore(player.getScore() + result.getCard().getValue());
							bot.remove(result);
							top.remove(result);
							scoreBot.setText("Score: " + player.getScore());
						} else {
							for (CardButton card : posibleCardCombinations) {
								card.getCard().setUsedCard(false);
							}
						}
					}

					if (midPlayableTop.getComponentCount() == 0 && midPlayableBot.getComponentCount() == 0
							&& cardDeck.getDeck().size() != 0) {
						player.incrementTableCounter();
						tableBot.setText("Table: " + player.getTableCounter());
					}
					repaint();
					revalidate();
				}
			}
		});
		return cardButton;

	}

	public void sum_up(List<CardButton> pressedCards, int result) {
		sum_up_recursive(pressedCards, result, new LinkedList<CardButton>());
	}

	private void sum_up_recursive(List<CardButton> pressedCards, int result, LinkedList<CardButton> partial) {
		int s = 0;
		for (CardButton x : partial) {
			if (!x.getCard().isUsedCard()) {
				s += x.getCard().getCardNumber();
			}
		}
		if (s == result) {
			for (CardButton tmp : partial) {
				tmp.getCard().setUsedCard(true);
				posibleCardCombinations.add(tmp);
			}
		}
		if (s >= result) {
			return;
		}
		for (int i = 0; i < pressedCards.size(); i++) {
			LinkedList<CardButton> remaining = new LinkedList<CardButton>();
			CardButton n = pressedCards.get(i);
			for (int j = i + 1; j < pressedCards.size(); j++) {
				remaining.add(pressedCards.get(j));
			}
			LinkedList<CardButton> partial_rec = new LinkedList<CardButton>(partial);
			partial_rec.add(n);
			sum_up_recursive(remaining, result, partial_rec);
		}
	}

	boolean isComponentInMidPlayableTop(Component component) {
		return java.util.Arrays.asList(midPlayableTop.getComponents()).contains(component);
	}

	private void initalizationOfPlayer(String username) throws RemoteException, NotBoundException {
		try {
			String host = System.getProperty("java.rmi.server.hostname");
			if (host == null) {
				host = "localhost";

			}
			if (serverDb.getPlayer1() == null) {
				onMove = true;
				isDealer = false;

			} else if (serverDb.getPlayer2() == null) {
				onMove = false;
				isDealer = true;

			} else {
				String poruka = "Nije moguce kreiranje klijenta!\nMaksimalno igraca: 2.";

				JOptionPane.showMessageDialog(this, poruka, "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}

			Registry reg = RegistryManager.get();

			me = new PlayerImplementation(GameBoard.this);

			reg.rebind(username, me);

			player = new Player(username, host, isDealer, onMove);

			serverDb.addPlayer(player);

		} catch (RemoteException ex) {
			String poruka = "Nije moguce kreiranje klijenta!\nIgra ce se sada zavrsiti.";
			JOptionPane.showMessageDialog(this, poruka, "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	private IPlayer getPlayer(Player player) throws RemoteException, NotBoundException {
		Registry reg = RegistryManager.get(player.getHost());
		return (IPlayer) reg.lookup(player.getUsername());
	}

	public void connectionLost() {
		JOptionPane.showMessageDialog(GameBoard.this, "Greska u konekciji!\nKraj igre.", "Error",
				JOptionPane.ERROR_MESSAGE);
		try {
			serverDb.removePlayers();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.exit(-1);
	}

}