package com.chcraft.preach.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.chcraft.preach.data.Preach;
import com.chcraft.preach.data.PreachDate;
import com.chcraft.preach.data.PreachSearcher;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PreachSearchUI extends JFrame {
	//class for ObjectOutputStream append mode
	class NoHeaderObjectOutputStream extends ObjectOutputStream {
		public NoHeaderObjectOutputStream(OutputStream out) throws IOException {
			super(out);
		}

		@Override
		protected void writeStreamHeader() throws IOException {
			//아무것도 안적기
		}
	}
	
	private JPanel contentPane;
	private JTextField addTitleTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreachSearchUI frame = new PreachSearchUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public PreachSearchUI() throws FileNotFoundException, IOException {
		//initialize PreacherSearcher
		try {
			PreachSearcher.init();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File data = new File("./data/data");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1201, 830);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//moved from bottom
		JList<Preach> searchResultlist = new JList<Preach>();
		
		JPanel searchTestamentPanel = new JPanel();
		searchTestamentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel searchDatePanel = new JPanel();
		searchDatePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblMonth = new JLabel("월");
		lblMonth.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblYear = new JLabel("년");
		lblYear.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner monthSpinner = new JSpinner();
		monthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		monthSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner yearSpinner = new JSpinner();
		yearSpinner.setModel(new SpinnerNumberModel(2021, 1970, null, 1));
		yearSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JButton btnSearchDate = new JButton("검  색");
		btnSearchDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer)yearSpinner.getValue();
				int month = (Integer)monthSpinner.getValue();
				List<Preach> result = PreachSearcher.search(year, month);
				result.sort(new Comparator<Preach>() {
					@Override
					public int compare(Preach o1, Preach o2) {
						return o1.getDate().compare(o1.getDate(), o2.getDate());
					}
				});
				searchResultlist.setListData(result.toArray(new Preach[result.size()]));
			}
		});
		btnSearchDate.setFont(new Font("굴림", Font.PLAIN, 20));
		
		GroupLayout gl_searchDatePanel = new GroupLayout(searchDatePanel);
		gl_searchDatePanel.setHorizontalGroup(
			gl_searchDatePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchDatePanel.createSequentialGroup()
					.addGap(32)
					.addComponent(yearSpinner, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(monthSpinner, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMonth)
					.addGap(18)
					.addComponent(btnSearchDate)
					.addContainerGap(35, Short.MAX_VALUE))
		);
		gl_searchDatePanel.setVerticalGroup(
			gl_searchDatePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_searchDatePanel.createSequentialGroup()
					.addContainerGap(13, Short.MAX_VALUE)
					.addGroup(gl_searchDatePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(monthSpinner, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMonth)
						.addComponent(btnSearchDate)
						.addComponent(lblYear)
						.addComponent(yearSpinner, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		searchDatePanel.setLayout(gl_searchDatePanel);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel searchYearPanel = new JPanel();
		searchYearPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JSpinner yearSpinner_1 = new JSpinner();
		yearSpinner_1.setModel(new SpinnerNumberModel(new Integer(2021), new Integer(1970), null, new Integer(1)));
		yearSpinner_1.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblYear_1 = new JLabel("년");
		lblYear_1.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JButton btnSearchDate_1 = new JButton("검  색");
		btnSearchDate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer)yearSpinner_1.getValue();
				List<Preach> result = PreachSearcher.search(year);
				result.sort(new Comparator<Preach>() {
					@Override
					public int compare(Preach o1, Preach o2) {
						return o1.getDate().compare(o1.getDate(), o2.getDate());
					}
				});
				searchResultlist.setListData(result.toArray(new Preach[result.size()]));
			}
		});
		btnSearchDate_1.setFont(new Font("굴림", Font.PLAIN, 20));
		GroupLayout gl_searchYearPanel = new GroupLayout(searchYearPanel);
		gl_searchYearPanel.setHorizontalGroup(
			gl_searchYearPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchYearPanel.createSequentialGroup()
					.addGap(21)
					.addComponent(yearSpinner_1, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblYear_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSearchDate_1)
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_searchYearPanel.setVerticalGroup(
			gl_searchYearPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_searchYearPanel.createSequentialGroup()
					.addContainerGap(13, Short.MAX_VALUE)
					.addGroup(gl_searchYearPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(yearSpinner_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblYear_1)
						.addComponent(btnSearchDate_1))
					.addContainerGap())
		);
		searchYearPanel.setLayout(gl_searchYearPanel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(searchTestamentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(searchDatePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(searchYearPanel, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)))
					.addGap(137))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(searchYearPanel, GroupLayout.PREFERRED_SIZE, 54, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(searchDatePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(searchTestamentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(22)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE)
					.addGap(18))
		);
		
		JSpinner addYearSpinner = new JSpinner();
		addYearSpinner.setModel(new SpinnerNumberModel(new Integer(2021), new Integer(1970), null, new Integer(1)));
		addYearSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel = new JLabel("년");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner addMonthSpinner = new JSpinner();
		addMonthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		addMonthSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1 = new JLabel("월");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner addDateSpinner = new JSpinner();
		addDateSpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		addDateSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel_2 = new JLabel("일");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JComboBox addTestamentCombo = new JComboBox(PreachSearcher.TESTAMENTS);
		addTestamentCombo.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner addChapterSpinner = new JSpinner();
		addChapterSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		addChapterSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel_3 = new JLabel("장");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner addStartSpinner = new JSpinner();
		addStartSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		addStartSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel_4 = new JLabel("-");
		lblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner addEndSpinner = new JSpinner();
		addEndSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		addEndSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel_5 = new JLabel("절");
		lblNewLabel_5.setFont(new Font("굴림", Font.PLAIN, 20));
		
		addTitleTextField = new JTextField();
		addTitleTextField.setFont(new Font("굴림", Font.PLAIN, 20));
		addTitleTextField.setColumns(10);
		
		JButton btnAddButton = new JButton("추  가");
		btnAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer)addYearSpinner.getValue();
				int month = (Integer)addMonthSpinner.getValue();
				int date = (Integer)addDateSpinner.getValue();
				String title = addTitleTextField.getText();
				addTitleTextField.setText("");
				String testament = (String)addTestamentCombo.getSelectedItem();
				int chapter = (Integer)addChapterSpinner.getValue(); 
				int start = (Integer)addStartSpinner.getValue();
				int end = (Integer)addEndSpinner.getValue();
				
				if(title.isEmpty()) {
					return;
				}
				
				if(!PreachSearcher.hasData()) {
					try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(data, true));) {
						Preach newItem = new Preach(year,month,date,title,testament,chapter,start,end); 
						out.writeObject(newItem);
						PreachSearcher.add(newItem);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					try(ObjectOutputStream out = new NoHeaderObjectOutputStream(new FileOutputStream(data, true));) {
						Preach newItem = new Preach(year,month,date,title,testament,chapter,start,end); 
						out.writeObject(newItem);
						PreachSearcher.add(newItem);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnAddButton.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblNewLabel_6 = new JLabel("설교 제목");
		lblNewLabel_6.setFont(new Font("굴림", Font.PLAIN, 20));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(addYearSpinner, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addDateSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addTestamentCombo, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(addChapterSpinner, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addStartSpinner, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_4)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addEndSpinner, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_5))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addTitleTextField, GroupLayout.PREFERRED_SIZE, 616, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
							.addComponent(btnAddButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(addYearSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(addMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(addDateSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2)
						.addComponent(addTestamentCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addChapterSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_5)
						.addComponent(addEndSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4)
						.addComponent(addStartSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6)
						.addComponent(addTitleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddButton))
					.addGap(10))
		);
		panel.setLayout(gl_panel);
		
		
		searchResultlist.setFont(new Font("굴림", Font.PLAIN, 20));
		scrollPane.setViewportView(searchResultlist);
		
		JComboBox testamentCombo = new JComboBox(PreachSearcher.TESTAMENTS);
		testamentCombo.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JSpinner chapterSpinner = new JSpinner();
		chapterSpinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
		chapterSpinner.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JLabel lblChapter = new JLabel("장");
		lblChapter.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JButton btnSearchTestament = new JButton("검  색");
		btnSearchTestament.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String testament = (String)testamentCombo.getSelectedItem();
				int chapter = (Integer)chapterSpinner.getValue();
				List<Preach> result = PreachSearcher.search(testament, chapter);
				result.sort(new Comparator<Preach>() {
					@Override
					public int compare(Preach o1, Preach o2) {
						return o1.getDate().compare(o1.getDate(), o2.getDate());
					}
				});
				searchResultlist.setListData(result.toArray(new Preach[result.size()]));
			}
		});
		btnSearchTestament.setFont(new Font("굴림", Font.PLAIN, 20));
		GroupLayout gl_searchTestamentPanel = new GroupLayout(searchTestamentPanel);
		gl_searchTestamentPanel.setHorizontalGroup(
			gl_searchTestamentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_searchTestamentPanel.createSequentialGroup()
					.addContainerGap(19, Short.MAX_VALUE)
					.addComponent(testamentCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(chapterSpinner, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblChapter)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSearchTestament)
					.addContainerGap())
		);
		gl_searchTestamentPanel.setVerticalGroup(
			gl_searchTestamentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_searchTestamentPanel.createSequentialGroup()
					.addGap(9)
					.addComponent(chapterSpinner, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
					.addGap(7))
				.addGroup(Alignment.TRAILING, gl_searchTestamentPanel.createSequentialGroup()
					.addContainerGap(11, Short.MAX_VALUE)
					.addComponent(btnSearchTestament)
					.addGap(8))
				.addGroup(Alignment.TRAILING, gl_searchTestamentPanel.createSequentialGroup()
					.addContainerGap(17, Short.MAX_VALUE)
					.addComponent(lblChapter)
					.addGap(13))
				.addGroup(Alignment.TRAILING, gl_searchTestamentPanel.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addComponent(testamentCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		searchTestamentPanel.setLayout(gl_searchTestamentPanel);
		contentPane.setLayout(gl_contentPane);
	}
}
