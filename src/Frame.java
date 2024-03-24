import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Frame extends JFrame implements ActionListener {

	private String[] thingsHeader = {"Checks", "Things"};
	private DefaultTableModel thingsModel = new DefaultTableModel(thingsHeader, 0);
	private JTable thingsTable = new JTable(thingsModel);;

	private JPanel mainPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JLabel cannotFindNameError = new JLabel("<html>할 일 입력란이 비어 있습니다. <br>할 일을 적으신 후 다시 시도하십시오.</html>");
	private JLabel completeDoesntExistError = new JLabel("<html>완료 항목이 존재하지 않습니다. <br>항목을 완료한 후 다시 시도하십시오.</html>");
	private JLabel selectedDoesntExistError = new JLabel("<html>선택된 항목이 존재하지 않습니다. <br>항목을 선택한 후 다시 시도하십시오.</html>");

	private DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();

	private JScrollPane thingsScrollPane = new JScrollPane(thingsTable);
	private GridLayout buttonLayout = new GridLayout(1, 3);

	private JButton makeButton = new JButton("항목 생성");
	private JButton completeDeleteButton = new JButton("완료 삭제");
	private JButton selectedDeleteButton = new JButton("선택 삭제");

	private JTextField todoTextField = new JTextField();

	private JLabel titleLabel = new JLabel("TODO LIST SECOND");

	Frame() {

		tableRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		thingsTable.setTableHeader(null);
		thingsTable.setBounds(50, (100 + 50 + 10) + 50, 400, 350);
		thingsScrollPane.setBounds(50, (100 + 50 + 10) + 50, 400, 350);
		thingsTable.setFont(new Font("BMDoHyeon-OTF", Font.PLAIN, 20));
		thingsTable.getColumn("Checks").setPreferredWidth(10);
		thingsTable.getColumn("Things").setPreferredWidth(350);
		thingsTable.setRowHeight(30);
		thingsTable.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = thingsTable.getSelectedRow();
				int col = thingsTable.getSelectedColumn();

				if (col == 0) {
					if (thingsTable.getValueAt(row, 0).equals("☐")) {
						thingsTable.setValueAt("☑", row, 0);
					} else {
						thingsTable.setValueAt("☐", row, 0);
					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		todoTextField.setBounds(50, 90, 400, 50);
		todoTextField.setFont(new Font("BMDoHyeon-OTF", Font.PLAIN, 20));
		todoTextField.addActionListener(this);

		titleLabel.setFont(new Font("BMDoHyeon-OTF", Font.PLAIN, 40));
		titleLabel.setBounds(0, 0, 500, 100);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);

		makeButton.setFont(new Font("BMDoHyeon-OTF", Font.PLAIN, 20));
		makeButton.addActionListener(this);

		completeDeleteButton.setFont(new Font("BMDoHyeon-OTF", Font.PLAIN, 20));
		completeDeleteButton.addActionListener(this);

		selectedDeleteButton.setFont(new Font("BMDoHyeon-OTF", Font.PLAIN, 20));
		selectedDeleteButton.addActionListener(this);

		buttonPanel.setLayout(buttonLayout);
		buttonPanel.add(makeButton);
		buttonPanel.add(completeDeleteButton);
		buttonPanel.add(selectedDeleteButton);
		buttonPanel.setBounds(50, 150, 400, 50);

		mainPanel.add(buttonPanel);
		mainPanel.add(todoTextField);
		mainPanel.add(thingsScrollPane);
		mainPanel.add(titleLabel);
		mainPanel.setLayout(null);

		cannotFindNameError.setFont(new Font("BMDoHyeon-OTF", Font.PLAIN, 15));
		completeDoesntExistError.setFont(new Font("BMDoHyeon-OTF", Font.ITALIC, 15));
		selectedDoesntExistError.setFont(new Font("BMDoHyeon-OTF", Font.PLAIN, 15));

		this.add(mainPanel);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 650);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == makeButton) {
			if (!todoTextField.getText().equals("")) {
				String[] tempRow = { "☐", todoTextField.getText() };
				thingsModel.addRow(tempRow);
				todoTextField.setText("");
			} else {
				JOptionPane.showMessageDialog(this, cannotFindNameError, "필숫값 누락 오류", JOptionPane.ERROR_MESSAGE);
			}

		} else if (e.getSource() == selectedDeleteButton) {
			int[] selectedThings = thingsTable.getSelectedRows();
			int counter = 0;
			for (int i = 0; i < selectedThings.length; i++) {
				((DefaultTableModel) thingsTable.getModel()).removeRow(selectedThings[i] - i); // 삭제된 수만큼 행이 앞당겨짐 유의.
				counter++;
			}

			if (counter == 0) {
				JOptionPane.showMessageDialog(this, selectedDoesntExistError, "항목 부재 오류", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == completeDeleteButton) {
			int counter = 0;
			for (int i = thingsTable.getRowCount() - 1; i >= 0; i--) {
				if (((DefaultTableModel) thingsTable.getModel()).getValueAt(i, 0).equals("☑")) {
					((DefaultTableModel) thingsTable.getModel()).removeRow(i);
					counter++;
				}
			}

			if (counter == 0) {
				JOptionPane.showMessageDialog(this, completeDoesntExistError, "항목 부재 오류", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == todoTextField) {
			makeButton.doClick();
		}
	}
}
