import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Demo using SwingWorkers do the time consuming task in the other thread, so it
 * won't block the main GUI thread.
 * 
 * @author Xmagic
 *
 */
public class MyFrame extends JFrame {

	private JPanel contentPane;
	private SwingWorker<StringBuilder, String> worker;

	private JTextArea textArea;

	/**
	 * Create the frame.
	 */
	public MyFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(initSubPanels(), BorderLayout.CENTER);
		initSwingWorkers();
	}

	/**
	 * Init the panel with textArea and button.
	 * 
	 * @return
	 */
	private JPanel initSubPanels() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));

		// Add TextArea in the center
		textArea = new JTextArea();
		panel.add(textArea, BorderLayout.CENTER);

		// Add Button at the bottom
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				worker.execute();
			}
		});
		panel.add(btnNewButton, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Init swing workers which will do the task in the other thread
	 */
	private void initSwingWorkers() {
		worker = new SwingWorker<StringBuilder, String>() {

			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
			}

			@Override
			protected void process(List<String> chunks) {
				for (String line : chunks) {
					textArea.append(line);
					textArea.append("\n");
				}

			}

			@Override
			protected StringBuilder doInBackground() throws Exception {
				Scanner scan = null;
				try {
					URL url = new URL("http://www.apache.org");
					scan = new Scanner(url.openConnection().getInputStream());
					String line = null;
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						this.publish(line);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (null != scan)
						scan.close();
				}
				return null;
			}
		};
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
