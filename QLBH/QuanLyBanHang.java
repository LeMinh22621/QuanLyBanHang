package QLBH;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class QuanLyBanHang
{
	private JFrame frame;
	private JTextField txtGoodName;
	private JTextField txtPrice;
	private JTable table;
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					QuanLyBanHang window = new QuanLyBanHang();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	public QuanLyBanHang()
	{
		initialize();
	}
	private DefaultTableModel dtb ;
	private List<Loai> listL;
	private int countE = 0;
	private int countI = 0;	
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 599, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtGoodName = new JTextField();
		txtGoodName.setBounds(419, 11, 154, 20);
		frame.getContentPane().add(txtGoodName);
		txtGoodName.setColumns(10);
		txtGoodName.enable(false);
		
		txtPrice = new JTextField();
		txtPrice.setBounds(419, 59, 154, 20);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		txtPrice.enable(false);
		
		JTextArea txtANotice = new JTextArea();
		txtANotice.setBounds(419, 134, 154, 129);
		frame.getContentPane().add(txtANotice);
		txtANotice.enable(false);
		
		listL = new ArrayList<Loai>();
		String query = "Select * from LOAI";
		DefaultTableModel defaultTableModel = null;
		try
		{
			defaultTableModel = DBHelper.getInstance().GetRecords(query);
		}
		catch (ClassNotFoundException | SQLException e2)
		{
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}
		
		for(int i = 0; i < defaultTableModel.getRowCount(); i++)
		{
			Loai l = new Loai();
			
			l.setMaloai(defaultTableModel.getValueAt(i, 0).toString());
			l.setTenloai(defaultTableModel.getValueAt(i, 1).toString());
			l.setMahangsx(defaultTableModel.getValueAt(i, 2).toString());
			listL.add(l);
		}
		JComboBox<Loai> cbbCategory = new JComboBox<Loai>();
		for(Loai i : listL)
			cbbCategory.addItem(i);
		
		cbbCategory.setBounds(419, 101, 154, 22);
		frame.getContentPane().add(cbbCategory);
		cbbCategory.enable(false);
		
		JLabel lbGoodName = new JLabel("Good Name");
		lbGoodName.setBounds(336, 14, 73, 14);
		frame.getContentPane().add(lbGoodName);
		
		JLabel lbPrice = new JLabel("Price");
		lbPrice.setBounds(336, 62, 73, 14);
		frame.getContentPane().add(lbPrice);
		
		JLabel lbCategory = new JLabel("Category");
		lbCategory.setBounds(336, 105, 73, 14);
		frame.getContentPane().add(lbCategory);
		
		JLabel lbNotice = new JLabel("Notice");
		lbNotice.setBounds(336, 203, 46, 14);
		frame.getContentPane().add(lbNotice);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(countI == 0)
				{
					txtGoodName.enable(true);
					txtANotice.enable(true);
					txtPrice.enable(true);
					cbbCategory.enable(true);
					btnInsert.setText("OK");
					countI = 1;
				}
				else
				{
					if(txtGoodName.getText().length() == 0 || txtPrice.getText().length() == 0 || txtANotice.getText().length() == 0)
					{
						JOptionPane.showMessageDialog(null, "Please full fill");
					}
					else
					{
						String mahang = table.getValueAt(table.getRowCount()-1, 0).toString();
						String number = "";number += mahang.charAt(mahang.length() - 1);
						int num = Integer.parseInt(number) + 1;
						mahang = "MH" + num;
						
						try
						{
							DBHelper.getInstance().ExcuteDB("Insert into MATHANG(MAHANGHOA, TENHANGHOA, DONGIA, GHICHU, MALOAI) values ('"
							+ mahang + "','" + txtGoodName.getText() + "','" + txtPrice.getText() + "','" + txtANotice.getText() + "','" + ((Loai)cbbCategory.getSelectedItem()).getMaloai() + "')");
							
							dtb = DBHelper.getInstance().GetRecords("Select * from MATHANG");
							table.setModel(dtb);
						}
						catch (ClassNotFoundException | SQLException e1)
						{
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
						
						txtGoodName.enable(false);
						txtANotice.enable(false);
						txtPrice.enable(false);
						cbbCategory.enable(false);
						btnInsert.setText("Insert");
						countI = 1;
					}
				}
			}
		});
		btnInsert.setBounds(336, 286, 73, 23);
		frame.getContentPane().add(btnInsert);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (table.getSelectedRowCount() == 1)
				{
					int index = table.getSelectedRow();
					try
					{
						DBHelper.getInstance().ExcuteDB("Delete from MATHANG where MAHANGHOA = '" + table.getValueAt(index, 0).toString() + "'");
						dtb = DBHelper.getInstance().GetRecords("Select * from MATHANG");
						table.setModel(dtb);
					}
					catch (ClassNotFoundException | SQLException e1)
					{
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});
		btnDelete.setBounds(499, 286, 74, 23);
		frame.getContentPane().add(btnDelete);
		
		JButton btnEdit = new JButton("Edit");

		btnEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int index = table.getSelectedRow();
				String mahang = table.getValueAt(table.getRowCount()-1, 0).toString();
				String number = "";number += mahang.charAt(mahang.length() - 1);
				int num = Integer.parseInt(number);
				mahang = "MH" + num;
				
				
				if(countE == 0)
				{
					if(table.getSelectedRowCount() != 1)
					{
						JOptionPane.showMessageDialog(null, "Select one row, please!");
						return;
					}
					
					txtGoodName.enable(true);
					txtGoodName.setText(table.getValueAt(index, 1).toString());
					txtANotice.enable(true);
					txtANotice.setText(table.getValueAt(index, 3).toString());
					txtPrice.enable(true);
					txtPrice.setText(table.getValueAt(index, 2).toString());
					cbbCategory.enable(true);
					String ml = table.getValueAt(index, 4).toString();
					Loai l = new Loai();
					for(Loai i: listL)
					{
						if(i.getMaloai().equals(ml))
						{
							l = i;
							break;
						}
							
					}
					cbbCategory.setSelectedItem(l);
					btnEdit.setText("OK");
					countE = 1;
				}
				else
				{
					try
					{
						String maloai = ((Loai)cbbCategory.getSelectedItem()).getMaloai();
						DBHelper.getInstance().ExcuteDB("Update MATHANG set TENHANGHOA = '" +txtGoodName.getText()+ "',GHICHU = '" +txtANotice.getText()+ "',DONGIA = '" + txtPrice.getText() + "',MALOAI = '" + maloai + "' where MAHANGHOA = '" + mahang + "'");
						dtb = DBHelper.getInstance().GetRecords("Select * from MATHANG");
						table.setModel(dtb);
						JOptionPane.showMessageDialog(null, "Edit Success!");
					}
					catch (ClassNotFoundException | SQLException e1)
					{
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					countE = 0;
					
					txtGoodName.enable(false);
					txtANotice.enable(false);
					txtPrice.enable(false);
					cbbCategory.enable(false);
					btnEdit.setText("Edit");
				}
				
			}
		});
		btnEdit.setBounds(432, 286, 57, 23);
		frame.getContentPane().add(btnEdit);
		
		table = new JTable();
		table.setBounds(10, 11, 316, 298);
		try
		{
			dtb = DBHelper.getInstance().GetRecords("Select * from MATHANG");
		}
		catch (ClassNotFoundException | SQLException e1)
		{
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		table.setModel(dtb);
		frame.getContentPane().add(table);
	}

}
