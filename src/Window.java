import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends Canvas implements ActionListener{

	//JFrame frame = new JFrame("Gabe");
	//JPanel panel = new JPanel(new FlowLayout());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Window()
	{
		JFrame frame = new JFrame("Gabriel Kutuzov | Stock Quotes Excel");	
		JPanel panel = new JPanel(new FlowLayout());
		frame.setContentPane(panel);
		
		JTextField textField = new JTextField("Field", 64);
		
		JButton button1 = new JButton("Test");
		
		frame.add(button1);
		frame.add(textField);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(500, 250);

		frame.setVisible(true);	
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{		
		
	}
}