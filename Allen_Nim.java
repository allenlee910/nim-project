/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Used Netbeans
 */
package nim;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
/**
 *
 * @author AllenLee1
 */
public class Nim {

    /**
     * @param args the command line arguments
     */

    //Do not want these values to ever change
   	final int Piles[] = {3,5,8};
    final Color PilesColor[] = {Color.RED,Color.BLUE,Color.YELLOW};
    final String SetsColor[] = {"Red","Blue","Yellow"};
    
    int Sets[] = {0,0,0};
    String Player="Player1";
    int CurrentMenu=1; 
    String Player1="Player1";
    String Player2="Player2";
    int gameOver = 0;
    JFrame myframe; //Wanted to provide a window for users to play
    JTextArea txtarea = new JTextArea(12,26); 
    Buttons barray[][] = new Buttons[3][8];
    Buttons menu[] = new Buttons[3];

    //Constructor for the game, initializes the game with the user interface
    public Nim() 
    {
	BlockDetail myPanel = new BlockDetail();
	InitialPage gamePanel = new InitialPage();

        myframe = new JFrame();

	JPanel content = new JPanel(new BorderLayout());
	content.add(new Panel(),BorderLayout.SOUTH);

	content.setBorder(BorderFactory.createTitledBorder(""));

	myframe.getContentPane().add(content, BorderLayout.CENTER);
	myframe.getContentPane().add(myPanel, BorderLayout.WEST);
	myframe.getContentPane().add(gamePanel, BorderLayout.NORTH);
	myframe.setTitle("Nim Game");
	myframe.pack();
	myframe.setLocationRelativeTo(null);
	myframe.setVisible(true);

    }

    //Keeps track of how many moves have been made
    static class Buttons extends JButton {
	int value1=0;
	int value2=0;
    }

    //Things I looked up on StackOverflow to see how to edit JPanel to make it look nice
    public class Panel extends JPanel {
	private Panel() {	
	    JScrollPane scroll = new JScrollPane (txtarea);
	    GridBagConstraints cons=new GridBagConstraints();
	    cons.weightx=1.0;
	    cons.weighty=1.0;
	    add(scroll, cons);
	    txtarea.setEditable(false);
	    txtarea.setForeground(Color.BLUE);
	    txtarea.setLineWrap(true);
	}
    }

    //Initialize the game
    public void initGame() {
	Sets = Piles.clone();

	for (int i=0; i<Piles.length; i++) {
	    for (int j=0; j<Piles[i]; j++ ){
		barray[i][j].setEnabled(true);
		barray[i][j].setBackground(PilesColor[i]);
                barray[i][j].setOpaque(true);
		barray[i][j].setContentAreaFilled(true);
	    }
	}
	txtarea.setText(null);
	txtarea.append("=== "+Player1+" vs " + Player2+" ===\n");
	txtarea.append("Players movement details:\n");
	gameOver = 0;
    }

    public void checkWin() {
	int count=0;

	if (gameOver == 1) {
	    return;
	}

	for (int i=0; i<Sets.length; i++) {
	    count += Sets[i];
	}


	if (count == 0) {
	    
	    txtarea.append("Game over: "+Player+" lose.\n");
	    gameOver=1;
	}
	else if (count == 1) {
	    String ThePlayer=Player1;
	    if (Player.equals(Player1)) {
		ThePlayer=Player2;
	    }
	   
	    txtarea.append("Game over: "+ThePlayer+" lose.\n");
	    gameOver=1;
	}
    }

    //
    public class InitialPage extends JPanel {
	
    //first page user sees
	public InitialPage() {
	    setLayout(new BorderLayout());
	    setBorder(BorderFactory.createTitledBorder(""));
	    GridBagLayout gbl = new GridBagLayout();
	    setLayout(gbl);
	    GridBagConstraints c = new GridBagConstraints();

	    //Creating the 'menu' with different options for user to choose
	    for (int i=0; i<menu.length; i++) {
	        if (i == 0) {
			menu[i] = new Buttons();
			menu[i].setText("Player vs Player");
		}
                
		else if (i == 1) { 
			menu[i] = new Buttons();
			menu[i].setText("Instruction");
		}
		else if (i == 2) {
			menu[i] = new Buttons();
			menu[i].setText("Quit");
		}
             	
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady=1;

		c.gridx=i % 3;
		c.gridy=i / 3;

		c.insets = new Insets(2,2,0,0);
		c.weightx = 1;
		c.weighty = 1;
		menu[i].value1=0; //menu pile
		menu[i].value2=i; //menu item
		menu[i].addActionListener(clickedmenu());

		gbl.setConstraints(menu[i], c);
		add(menu[i]);
	    }
	}

	//Based off of what user clicked, will print with respect to selection
	public void process_menu(int value1, int value2) {

	    if (value2 == 0) {
		    // Player vs Player
		    Player="Player1";
		    Player1="Player1";
		    Player2="Player2";
		    initGame();
	    }
            
	    else if (value2 == 1) {
		    // Instruction
		    JOptionPane.showMessageDialog(null,
			  "Rules of playing Nim:\n"+
			  "One person picks any number of stones at a time\n"+
			  "as they like from any of the 3 piles. This keeps happening\n" +
                          "until there is only one stone left, or nothing left.\n" +
			  "The person who takes the last stone, or leaves no stones loses");
	    }
	    else if (value2 == 2) {
		    // Quit
		    System.exit(0);
	    }

	    //change color for play buttons, if selected
	    if (value2 < 3) {
		    Color myc = menu[value2].getBackground();
		    menu[CurrentMenu].setBackground(myc);
		    CurrentMenu = value2;
		    menu[CurrentMenu].setBackground(Color.GREEN);
                    menu[CurrentMenu].setOpaque(true);
	    }
	}
	public ActionListener clickedmenu() {
	    return new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			//String actionCommand = e.getActionCommand();
			//System.out.println("actionCommand is: " + actionCommand);

			Object s = e.getSource();

			Buttons b = (Buttons) s;
			process_menu(b.value1, b.value2);

		    } };
	}
    }

    public class BlockDetail extends JPanel {

   	// Sets up how we keep track of how many stones are in each of the three piles and
   	// creates images for user to see instead of displaying numbers
	public BlockDetail() {
	    setLayout(new BorderLayout());
	    setBorder(BorderFactory.createTitledBorder(" Click on piece "));
	    GridBagLayout gbl = new GridBagLayout();
	    setLayout(gbl);
	    GridBagConstraints c = new GridBagConstraints();

	    // find the max of the piles
	    int max_count = Piles[0];
	    for (int i=1; i< Piles.length; i++) {
		if (max_count < Piles[i]) {
		    max_count = Piles[i];
		}
	    }

	    for (int i=0; i< Piles.length; i++) {
		for (int j=0; j< Piles[i] ; j++) {
		    barray[i][j] = new Buttons();

		    barray[i][j].value1= i+1;
		    barray[i][j].value2= j+1;
		    barray[i][j].setBackground(PilesColor[i]);
		    barray[i][j].addActionListener(clickedbutton());
		    c.fill = GridBagConstraints.VERTICAL;
		    c.ipady=1;
		    c.gridx=i;
		    c.gridy= max_count - j - 1; //rows
		    c.insets = new Insets(2,10,0,0);

		    c.weightx = 1;
		    c.weighty = 1;
		    //c.anchor = GridBagConstraints.SOUTH;
		    //c.gridwidth = 1;
					
		    gbl.setConstraints(barray[i][j], c);
		    add(barray[i][j]);
		}
	    }
	    initGame();
	}

	public int askMessage(String msg, String tle, int op)   {
	    return JOptionPane.showConfirmDialog(null, msg, tle, op);
	}

	//Teacher recommended to use this class for help, referenced StackOverflow and Java API to get the functions I wanted
	public ActionListener clickedbutton() {
	    return new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			//String actionCommand = e.getActionCommand();
			//System.out.println("actionCommand is: " + actionCommand);

			Object s = e.getSource();

			Buttons b = (Buttons) s;
			markPiece(b.value1, b.value2);

			checkWin();
			if (gameOver == 0) {
			    switch_player();
			    if (Player.equals("CPU")) {
				checkWin();
				switch_player();
			    }
			}
		    } };
	}
    } //end  Detailed Panel

    public void switch_player() {
	if (Player.equals(Player1)) {
	    Player=Player2;
	}
	else {
	    Player=Player1;
	}
    }

    //Takes in the user input and makes adjustments to reevaluate stone counts
    public void markPiece(int value1, int value2) {
	int m=value1 -1;
	int n=value2 -1;
	int c=0;


	for (int i=n; i <Sets[m]; i++) {
	    c++;
	    barray[m][i].setEnabled(false);
	    barray[m][i].setBackground(Color.WHITE);
	}
	txtarea.append(Player+ ": "+
		       c + " piece(s) removed from " + SetsColor[m]+ " Pile.\n");
	Sets[m] = n;
    }

    public int largest(int a[])
    {
	int m = a[0];
	for(int i = 1; i < a.length; i++)
	    if (a[i] > m) m = a[i];

	return m;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() 
	    {
		@Override
		    public void run() 
		{
		    Nim n = new Nim();
		}
	    });
    }
    
}
