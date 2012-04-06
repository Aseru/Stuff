package shuf13.gui;

import java.awt.event.ActionListener;

import shuf13.control.GuiHandler;
import shuf13.exception.InvalidStartDirException;


public class Guistd extends javax.swing.JFrame implements Gui {

    /** Creates new form Shuf13Gstd */
    public Guistd(GuiHandler gh) {
    	gHdl = gh;
    	
        initComponents();
        
        setVisible(true);
    }

    private void initComponents() {

        directoryField = new javax.swing.JTextField();
        lastButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        browseButton = new javax.swing.JButton();
        sLabel = new javax.swing.JLabel();
        displayLabel = new javax.swing.JLabel();

        ActionListener listener = new SGuiListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Shuf13");
        setName("Form");
        setResizable(false);

        directoryField.setFont(new java.awt.Font("Tahoma", 0, 12));
        directoryField.setName("directoryField");

        lastButton.setFont(new java.awt.Font("Tahoma", 0, 16));
        lastButton.setText("<");
        lastButton.setName("lastButton");
        lastButton.addActionListener(listener);

        playButton.setFont(new java.awt.Font("Tahoma", 0, 16));
        playButton.setText("|>");
        playButton.setName("playButton");
        playButton.addActionListener(listener);

        nextButton.setFont(new java.awt.Font("Tahoma", 0, 16));
        nextButton.setText(">");
        nextButton.setName("nextButton");
        nextButton.addActionListener(listener);

        browseButton.setFont(new java.awt.Font("Tahoma", 0, 12)); 
        browseButton.setText(":>");
        browseButton.setName("browseButton");
        browseButton.addActionListener(listener);

        sLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        sLabel.setText("$:");
        sLabel.setName("sLabel");

        displayLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        displayLabel.setName("displayLabel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(browseButton)
                        .addGap(18, 18, 18)
                        .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lastButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(playButton)
                        .addGap(18, 18, 18)
                        .addComponent(nextButton)
                        .addGap(88, 88, 88))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                        //.addGap(62, 62, 62)
                        //.addComponent(openDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        //.addContainerGap())))
        ))));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseButton)
                    .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastButton)
                    .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nextButton))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(displayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
        )));

        pack();
    }

    // Code for dispatching events from components to event handlers.
    private class SGuiListener implements java.awt.event.ActionListener {
        SGuiListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == browseButton) {
                Guistd.this.browseButtonActionPerformed(evt);
            }
            else if (evt.getSource() == lastButton) {
                Guistd.this.lastButtonActionPerformed(evt);
            }
            else if (evt.getSource() == playButton) {
                Guistd.this.playButtonActionPerformed(evt);
            }
            else if (evt.getSource() == nextButton) {
                Guistd.this.nextButtonActionPerformed(evt);
            }
        }
    }
    
    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	String dir = gHdl.browse();
    	
    	if(dir!=null)
    		directoryField.setText(dir);
    }

    
    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	final String sdir = directoryField.getText();
    	
    	try {
			gHdl.playHandle(sdir);
		} catch (InvalidStartDirException e) {
			display("Invalid or empty start directory.");
		}
    }

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	gHdl.next();
    }
	private void lastButtonActionPerformed(java.awt.event.ActionEvent evt) {
		gHdl.last();
	}


    
    public void setPlayButton(int mode) {
    	switch(mode) {
    	case 1:
    		//playing
    		playButton.setText("||");
    		break;
    	case 2:
    		//pause
    		playButton.setText("|>");
    		break;
    	}
    }
    
    public void display(String text) {
    	displayLabel.setText(text);
    }
    
    public void setPathEd(boolean editable) {
    	directoryField.setEditable(editable);
    }
    
    
    // Variables declaration
    private GuiHandler gHdl;
    
    private javax.swing.JButton browseButton;
    private javax.swing.JTextField directoryField;
    private javax.swing.JLabel displayLabel;
    private javax.swing.JButton lastButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton playButton;
    private javax.swing.JLabel sLabel;
    // End of variables declaration
}
