package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Map;

public class SalesCalendar extends JPanel {
    private Map<String, Integer> salesData;
    private JComboBox<String> monthSelector;
    private JPanel calendarPanel;

    public SalesCalendar(Map<String, Integer> salesData) {
        this.salesData = salesData;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        monthSelector = new JComboBox<>(months);
        monthSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCalendar();
            }
        });
        controlPanel.add(new JLabel("Seleccionar mes:"));
        controlPanel.add(monthSelector);
        add(controlPanel, BorderLayout.NORTH);

        calendarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCalendar(g);
            }
        };
        calendarPanel.setLayout(null);
        add(calendarPanel, BorderLayout.CENTER);

        updateCalendar();
    }

    private void updateCalendar() {
        calendarPanel.repaint();
    }

    private void drawCalendar(Graphics g) {
        int cellSize = 50;
        int padding = 5;
        int startX = 50;
        int startY = 50;

        int selectedMonth = monthSelector.getSelectedIndex();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, selectedMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int row = 0;
        int col = firstDayOfWeek;
        for (int day = 1; day <= daysInMonth; day++) {
            String key = selectedMonth + "-" + row + "-" + col;
            int sales = salesData.getOrDefault(key, 0);
            g.setColor(getColorForSales(sales));
            g.fillRect(startX + (col * (cellSize + padding)), startY + (row * (cellSize + padding)), cellSize, cellSize);

            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(day), startX + (col * (cellSize + padding)) + padding, startY + (row * (cellSize + padding)) + padding + cellSize / 2);

            col++;
            if (col == 7) { 
                col = 0;
                row++;
            }
        }
    }

    private Color getColorForSales(int sales) {
        if (sales == 0) {
            return Color.WHITE;
        } else if (sales < 5) {
            return Color.LIGHT_GRAY;
        } else if (sales < 10) {
            return Color.GRAY;
        } else if (sales < 20) {
            return Color.DARK_GRAY;
        } else {
            return Color.BLACK;
        }
    }
}

