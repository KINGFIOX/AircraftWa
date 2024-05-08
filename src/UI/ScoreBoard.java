package UI;

import UI.Main;
import edu.hitsz.DataIO.Entry;
import edu.hitsz.DataIO.IRankList;
import edu.hitsz.DataIO.TreeMapRankList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class SimpleTable {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JScrollPane tableScrollPanel;
    private JLabel headerLabel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JButton returnButton;

    public SimpleTable() {

        String[] columnName = {"score", "time"};
        IRankList scoreboard = new TreeMapRankList();
        try {
            scoreboard.load("data/ScoreBoard.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Entry> entryList = scoreboard.getAllEntries();
        String[][] tableData = new String[entryList.size()][2];
        int i = 0;
        for (Entry e : entryList) {
            tableData[i][0] = String.valueOf(e.score);
            tableData[i][1] = String.valueOf(e.time);
            i++;
        }


        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.first(Main.cardPanel);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SimpleTable");
        frame.setContentPane(new SimpleTable().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
