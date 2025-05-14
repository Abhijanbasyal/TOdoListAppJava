

package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ToDoApp {
    private JFrame frame;
    private DefaultListModel<Task> taskModel;
    private JList<Task> taskList;
    private JTextField taskInput;

    public ToDoApp() {
        frame = new JFrame("To-Do List App");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        taskInput = new JTextField();
        JButton addButton = createIconButton("Add", "src/main/resources/icons/add.png");
        topPanel.add(taskInput, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        // Task List
        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskList.setCellRenderer(new TaskRenderer());

        JScrollPane scrollPane = new JScrollPane(taskList);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton editButton = createIconButton("Edit", "src/main/resources/icons/edit.png");
        JButton deleteButton = createIconButton("Delete", "src/main/resources/icons/delete.png");
        JButton doneButton = createIconButton("Done", "src/main/resources/icons/done.png");
        JButton clearAllButton = createIconButton("Clear All", "src/main/resources/icons/clear.png");

        buttonPanel.add(editButton);
        buttonPanel.add(doneButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearAllButton);

        // Actions
        addButton.addActionListener(e -> {
            String text = taskInput.getText().trim();
            if (!text.isEmpty()) {
                taskModel.addElement(new Task(text));
                taskInput.setText("");
            }
        });

        editButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                String newText = JOptionPane.showInputDialog(frame, "Edit Task", taskModel.get(index).getText());
                if (newText != null && !newText.trim().isEmpty()) {
                    taskModel.get(index).setText(newText.trim());
                    taskList.repaint();
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                taskModel.remove(index);
            }
        });

        doneButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                taskModel.get(index).toggleDone();
                taskList.repaint();
            }
        });

        clearAllButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove all tasks?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                taskModel.clear();
            }
        });

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JButton createIconButton(String tooltip, String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(40, 40));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoApp::new);
    }

    // Inner Renderer Class
    static class TaskRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Task task = (Task) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(list, task.getText(), index, isSelected, cellHasFocus);
            if (task.isDone()) {
                label.setText("<html><strike>" + task.getText() + "</strike></html>");
                label.setForeground(Color.GRAY);
            } else {
                label.setForeground(Color.BLACK);
            }
            return label;
        }
    }
}

