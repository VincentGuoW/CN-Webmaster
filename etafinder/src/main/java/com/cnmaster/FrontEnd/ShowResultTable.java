package com.cnmaster.FrontEnd;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ShowResultTable {
    public static void showResultTable(List<ContainerInfo> dataList) {
        JFrame tableFrame = new JFrame("Container ETA Results");
        tableFrame.setSize(1400, 500); // 加宽加高，给表格更多空间
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setLocationRelativeTo(null); // 居中显示

        String[] columnNames = {
                "Container", "SIZE", "Destination", "ETA",
                "Lot", "Row", "Spot", "Customs Hold", "Free Day"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (ContainerInfo info : dataList) {
            tableModel.addRow(info.toTableRow());
        }

        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 关闭自动列宽调整

        // 手动设置每一列的宽度
        int[] columnWidths = { 120, 100, 200, 150, 50, 50, 50, 150, 100 };
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        tableFrame.add(scrollPane);

        tableFrame.setVisible(true);
    }
}
