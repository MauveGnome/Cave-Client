package clientGUI;

/**
 * @author Sam Beed B0632953
 */

import java.awt.*;
import javax.swing.*;

public class ClientGUI extends JFrame {
    
    MainPanel mainPanel;
    JTextArea informationTA;
    
    public ClientGUI(String title) {
        super(title);
        setSize(500, 500);
        this.setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        setVisible(true);
        
        mainPanel = new MainPanel(getAvailableWidth(), getAvailableHeight());
        informationTA = new JTextArea("Wibble");
        
        mainPanel.add(informationTA);
        
        add(mainPanel, BorderLayout.SOUTH);        
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
     * Inner class describing the main panel.
     */
    private class MainPanel extends JPanel
    {
        JTextArea textArea;
        
        public MainPanel(int width, int height) // given
        {
            setSize(width, height);
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
    
}