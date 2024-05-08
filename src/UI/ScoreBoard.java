package UI;

import config.CONFIG;
import edu.hitsz.DataIO.Entry;
import edu.hitsz.DataIO.IRankList;
import edu.hitsz.DataIO.TreeMapRankList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ScoreBoard {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JScrollPane tableScrollPanel;
    private JLabel headerLabel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JButton returnButton;

    public ScoreBoard() {
        String[] columnName = {"score", "time"};
        IRankList scoreboard = new TreeMapRankList();
        try {
            scoreboard.load(CONFIG.Game.DATA_PATH);
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

        // 表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // 不可编辑
            }
        };

        // JTable 并不存储自己的数据，而是从表格模型那里获取它的数据
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        // 删除按钮
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                int result = JOptionPane.showConfirmDialog(deleteButton, "是否确定删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                }
            }
        });

        // 返回按钮，保存数据
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.first(Main.cardPanel);
                // 将数据写入文件
                saveModelToCSV(model, CONFIG.Game.DATA_PATH);
            }
        });
    }

    // 将模型数据保存到 CSV 文件
    private void saveModelToCSV(DefaultTableModel model, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
//            // 写入表头
//            for (int col = 0; col < model.getColumnCount(); col++) {
////                writer.append(model.getColumnName(col));
//                if (col < model.getColumnCount() - 1) {
//                    writer.append(',');
//                }
//            }
//            writer.append('\n');

            // 写入每一行数据
            for (int row = 0; row < model.getRowCount(); row++) {
                String first = String.valueOf(model.getValueAt(row, 0));
                String second = String.valueOf(model.getValueAt(row, 1));
                if (first.length() > 0 && second.length() > 0) {
                    String entry = first + "," + second + "\n";
                    writer.append(entry);
                }
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, "无法保存文件：" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
