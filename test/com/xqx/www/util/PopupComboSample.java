package com.xqx.www.util;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class PopupComboSample {

	public static void main(String args[]) {
	    String labels[] = { "A", "B", "C", "D", "E", "F", "G" };
	    JFrame frame = new JFrame("Popup JComboBox");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JComboBox<String> comboBox = new JComboBox<String>(labels);
	    comboBox.setUI((ComboBoxUI) MyComboBoxUI.createUI(comboBox));
	    frame.add(comboBox, BorderLayout.NORTH);

	    frame.setSize(300, 200);
	    frame.setVisible(true);
	  }

	  static class MyComboBoxUI extends BasicComboBoxUI {
	    public static ComponentUI createUI(JComponent c) {
	      return new MyComboBoxUI();
	    }

	    protected JButton createArrowButton() {
	      JButton btn = new JButton();
	      btn.setVisible(true);
	      btn.setSize(0, 0);
	      return btn;
	    }
	  }
	
}
