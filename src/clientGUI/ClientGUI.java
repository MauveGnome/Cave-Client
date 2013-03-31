package clientGUI;

/**
 * @author Sam Beed B0632953
 */

import client.Client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
        @Override
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
        
        public VotePanel() {
            setLayout(new GridLayout(1,2));
            
            voteList = new JList();
            responseList = new JList();

            voteList.setBorder(BorderFactory.createEtchedBorder());
            responseList.setBorder(BorderFactory.createEtchedBorder());
            voteList.addListSelectionListener(new ListWatcher());
            
            add(voteList);
            add(responseList);
        }
      
        /**
         * 
         */
        public void updateQuestionList() {
            voteList.setListData(myClient.getQuestions().toArray());
        }
        
        /**
         * 
         */
        public void setAnswerList(String question) {
            responseList.setListData(myClient.getAnswers(question).keySet().toArray());
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
            try {
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
                    myClient.sendToServer("GET_VOTES");
                }

                if (source == optionPanel.submitButton) {
                    myClient.sendToServer("SUBMIT");
                }

                if (source == optionPanel.quitButton) {
                    myClient.quit();
                    
                }
            }
            catch (IOException ex) {
                infoPanel.addOutput("IO error: " + ex.getMessage());
            }
            
            optionPanel.repaint();
            votePanel.repaint();
            infoPanel.repaint();
        }
    }
    
    /**
     * 
     */
    private class ListWatcher implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource().equals(votePanel.voteList)) {
                votePanel.setAnswerList((String) votePanel.voteList.getSelectedValue());
            }
        }
        
    }
}