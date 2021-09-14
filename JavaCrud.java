import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private final JTextField textField = new JTextField();
	private JTextField txtName;
	private JTextField txtEdt;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtBookID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root", "");
		} catch (ClassNotFoundException ex) {

		} catch (SQLException ex) {

		}
	}

	public void table_load() {

		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException ex2) {

			ex2.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		textField.setColumns(10);
		frame = new JFrame();
		frame.setBounds(100, 100, 944, 549);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Book Shop");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel_1.setBounds(322, -21, 277, 99);
		frame.getContentPane().add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Registation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(29, 96, 372, 211);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Book Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 39, 105, 25);
		panel.add(lblNewLabel);

		JLabel lblEdition = new JLabel("Edition");
		lblEdition.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEdition.setBounds(10, 94, 105, 25);
		panel.add(lblEdition);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(10, 153, 105, 25);
		panel.add(lblPrice);

		txtName = new JTextField();
		txtName.setBounds(142, 44, 188, 20);
		panel.add(txtName);
		txtName.setColumns(10);

		txtEdt = new JTextField();
		txtEdt.setColumns(10);
		txtEdt.setBounds(142, 94, 188, 20);
		panel.add(txtEdt);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(142, 153, 188, 20);
		panel.add(txtPrice);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String bName, edt, price;

				bName = txtName.getText();
				edt = txtEdt.getText();
				price = txtPrice.getText();

				try {

					pst = con.prepareStatement("insert into book (name, edition, price) values(?,?,?)");
					pst.setString(1, bName);
					pst.setString(2, edt);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record has been added");
					table_load();
					txtName.setText("");
					txtEdt.setText("");
					txtPrice.setText("");
					txtName.requestFocus();
				}

				catch (SQLException ex1) {

					ex1.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(29, 317, 85, 41);
		frame.getContentPane().add(btnNewButton);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(132, 317, 85, 41);
		frame.getContentPane().add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				txtName.setText("");
				txtEdt.setText("");
				txtPrice.setText("");
				txtName.requestFocus();

			}
		});
		btnClear.setBounds(238, 317, 85, 41);
		frame.getContentPane().add(btnClear);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(428, 86, 477, 272);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(37, 401, 364, 82);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblEdition_1 = new JLabel("Book ID");
		lblEdition_1.setBounds(36, 31, 75, 19);
		lblEdition_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(lblEdition_1);

		txtBookID = new JTextField();
		txtBookID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {

					String id = txtBookID.getText();

					pst = con.prepareStatement("select name,edition,price from book where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();

					if (rs.next() == true) {

						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);

						txtName.setText(name);
						txtEdt.setText(edition);
						txtPrice.setText(price);

					} else {
						txtName.setText("");
						txtEdt.setText("");
						txtPrice.setText("");

					}

				}

				catch (SQLException ex) {

				}

			}

		});
		txtBookID.setBounds(110, 33, 96, 19);
		txtBookID.setColumns(10);
		panel_1.add(txtBookID);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String bName, edt, price, bookID;

				bName = txtName.getText();
				edt = txtEdt.getText();
				price = txtPrice.getText();
				bookID = txtBookID.getText();

				try {

					pst = con.prepareStatement("update book set name= ?, edition= ?, price= ? where id = ?");
					pst.setString(1, bName);
					pst.setString(2, edt);
					pst.setString(3, price);
					pst.setString(4, bookID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record has been updated");
					table_load();
					txtName.setText("");
					txtEdt.setText("");
					txtPrice.setText("");
					txtName.requestFocus();
				}

				catch (SQLException ex1) {

					ex1.printStackTrace();
				}

			}

		});
		btnUpdate.setBounds(579, 368, 85, 41);
		frame.getContentPane().add(btnUpdate);

		JButton btnClear_1_1 = new JButton("Delete");
		btnClear_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String bookID;

				bookID = txtBookID.getText();

				try {

					pst = con.prepareStatement("delete from book where id = ?");
					pst.setString(1, bookID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record has been deleted");
					table_load();
					txtName.setText("");
					txtEdt.setText("");
					txtPrice.setText("");
					txtName.requestFocus();
				}

				catch (SQLException ex1) {

					ex1.printStackTrace();
				}

			}
		});
		btnClear_1_1.setBounds(674, 368, 85, 41);
		frame.getContentPane().add(btnClear_1_1);
	}

	private static class __Tmp {
		private static void __tmp() {
			javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
