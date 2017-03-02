import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Toolkit;
import javax.swing.JTextPane;

public class GUI {

	private JFrame frmStockQuote;
	
	private static String loadedString = ("MSFT KO AAPL WMT AMD NVDA");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmStockQuote.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Load File
		String fileName = ("savedata.txt"); 
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			try {
				String line = br.readLine();
				loadedString = line;
				System.out.println("File Line: " + line);
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");			
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		frmStockQuote = new JFrame();
		frmStockQuote.setResizable(false);
		frmStockQuote.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		frmStockQuote.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmStockQuote.setTitle("Stock Exporter | by Gabriel Kutuzov");
		frmStockQuote.setBounds(100, 100, 526, 300);
		frmStockQuote.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStockQuote.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(173, 10, 327, 92);
		frmStockQuote.getContentPane().add(scrollPane);
		
	    // Add scrollbar to textfield
	    //outputField.add(scroll);
		
		JTextArea inputField = new JTextArea();
		inputField.setWrapStyleWord(true);
		scrollPane.setViewportView(inputField);
		inputField.setLineWrap(true);
		inputField.setText("MSFT KO AAPL WMT AMD NVDA");
		inputField.setText(loadedString);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(173, 113, 327, 122);
		frmStockQuote.getContentPane().add(scrollPane_1);
		
		JTextArea outputField = new JTextArea();
		outputField.setWrapStyleWord(true);
		scrollPane_1.setViewportView(outputField);
		outputField.setText
		("****************\nInstructions\n****************\n "
				+ "Add stock market symbols with spaces after each one "
				+ "to ensure that the quotes are grabbed correctly. \n Example: \n\n"
				+ "MSFT KO AAPL WMT AMD NVDA COST SHP"
				+ "\n\n After you have entered all of the stock symbols, press the button "
				+ "\'Export Quotes\'. \n"
				+ "A list will appear of all "
				+ "the current stock prices, and a "
				+ ".xlsx file will appear in the program path.\n\n"
				+ "Enjoy! \n\n"
				+ "Created by Gabriel Kutuzov");
		
		outputField.setLineWrap(true);
	    
		JButton btnGetQuotes = new JButton("Export Quotes");
		btnGetQuotes.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				StockQuote.symbols.clear();				

				String inputText = inputField.getText();
				String[] inputTextArray = inputText.split(" ");
				 
				for (String entry : inputTextArray) 
				{
					String entryUpperCase = entry.toUpperCase();
					System.out.println("Entry: " + entryUpperCase);
					StockQuote.symbols.add(entryUpperCase);
				}
				
				StockQuote.GetPriceForEachSymbol();												
				
				// set outputField
				outputField.setText("***.xlsx created in file path ***\n\n" + 
				"================================\n\tRESULTS\n================================\n");				
				
				for(String stockSymbol : StockQuote.stockSymbols)
				{
					// Get stockValue
					String stockValue = StockQuote.stockValues.get
							(StockQuote.stockSymbols.indexOf(stockSymbol));
					
					// Print to text field					
					outputField.append("\n" + stockSymbol + " : " + stockValue);
				}
			}
		});
	
		btnGetQuotes.setBounds(10, 11, 125, 23);
		frmStockQuote.getContentPane().add(btnGetQuotes);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					PrintWriter out = new PrintWriter("savedata.txt");
					out.println(inputField.getText());
					out.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(10, 45, 125, 23);
		frmStockQuote.getContentPane().add(btnSave);
	}
}
