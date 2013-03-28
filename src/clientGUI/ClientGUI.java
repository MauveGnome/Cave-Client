package clientGUI;

/**
 * @author Sam Beed B0632953
 */

import client.Client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class ClientGUI extends JFrame {
    
    private OptionPanel optionPanel;
    private VotePanel votePanel;
    private InfoPanel infoPanel;
    private Client myClient;
    
    public ClientGUI(String title, Client c) {
        super(title);
        setSize(500, 500);
        this.setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        myClient = c;    
        
        optionPanel = new OptionPanel();
        votePanel = new VotePanel();
        infoPanel = new InfoPanel();
        
        add(optionPanel, BorderLayout.NORTH);
        add(votePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    /**
     * These two methods should only be called after the frame is visible.
     * Note: may also need to adject for menu height.
     */
    private int getAvailableWidth()
    {
        return getWidth() - getInsets().left - getInsets().right;
    }

    private int getAvailableHeight()
    {
        return getHeight() - getInsets().top - getInsets().bottom;
    }
    
    /**
     * Inner class to hold the option button area.
     */
    private class OptionPanel extends JPanel {
        
        JButton connectButton;
        JButton getVotesButton;
        JButton submitButton;
        JButton quitButton;
        
        public OptionPanel() {
            setLayout(new GridLayout(1,4));      
            
            connectButton = new JButton("Connect");
            getVotesButton = new JButton("Get Votes");
            submitButton = new JButton("Submit");
            quitButton = new JButton("Quit");
            
            add(connectButton);
            add(getVotesButton);
            add(submitButton);
            add(quitButton);
            
            connectButton.addActionListener(new ButtonWatcher());
            getVotesButton.addActionListener(new ButtonWatcher());
            submitButton.addActionListener(new ButtonWatcher());
            quitButton.addActionListener(new ButtonWatcher());
        }
      
        /**
         * This method is invoked automatically when repaint occurs in
         * the outer container
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            

            
        }
    }
        
    /**
     * Inner class to hold the voting area.
     */
    private class VotePanel extends JPanel {
        
        JList voteList;
        JList responseList;
        String[] testList = new String[]{"one","two","three","four"};           //REMOVE
        
        public VotePanel() {
            setLayout(new GridLayout(1,2));
            
            voteList = new JList(testList);                                     //REMOVE TESTLIST
            responseList = new JList(testList);                                 //REMOVE TESTLIST

            voteList.setBorder(BorderFactory.createEtchedBorder());
            responseList.setBorder(BorderFactory.createEtchedBorder());
            
            add(voteList);
            add(responseList);
        }
      
        /**
         * This method is invoked automatically when repaint occurs in
         * the outer container
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
        }
    }
    
    /**
     * Inner class describing the main panel.
     */
    private class InfoPanel extends JPanel {
        
        JTextArea textArea;
        
        public InfoPanel() {
            setLayout(new GridLayout(1,1));
                        
            textArea = new JTextArea("Welcome to the Colaboration and "
                                     + "Voting Environment\n", 10, 1);
            JScrollPane scroll = new JScrollPane(textArea);
            textArea.setEditable(false);
            
            add(scroll);
        }
        
        public void addOutput(String newOutput) {
            textArea.append(newOutput + "\n");
        }
                
        /**
         * This method is invoked automatically when repaint occurs in
         * the outer container
         */
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);            
        }

    }
    
    /**
     * Inner class to watch buttons.
     */
    private class ButtonWatcher implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            
            if (source == optionPanel.connectButton) {
                if (!myClient.isConnected()) {
                    if (myClient.connectToServer()) {
                        optionPanel.connectButton.setText("Disconnect");
                        optionPanel.connectButton.setBackground(Color.GREEN);
                        infoPanel.addOutput("Connected");
                    }
                    else {
                        infoPanel.addOutput("Failed to connect");
                    }
                }
            }
            
            if (source == optionPanel.getVotesButton) {
                
            }
            
            if (source == optionPanel.submitButton) {
                
            }
            
            if (source == optionPanel.quitButton) {
                myClient.quit();
            }
            
            optionPanel.repaint();
            votePanel.repaint();
            infoPanel.repaint();
        }
    }
        
}