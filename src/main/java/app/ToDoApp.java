

package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoApp {
    private JFrame frame;
    private DefaultListModel<Task> taskModel;
    private JList<Task> taskList;
    private JTextField taskInput;

    public ToDoApp() {
        frame = new JFrame("To Do List Mini-App");
        frame.setSize(713, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        taskInput = new JTextField();
        JButton addButton = new JButton("Add");
        topPanel.add(taskInput, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        // Task List with Custom Rendering
        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskList.setCellRenderer(new TaskCellRenderer());
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Add MouseListener to toggle task completion on click
        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Task task = taskModel.getElementAt(index);
                    task.toggleDone();
                    taskList.repaint();
                }
            }
        });

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);

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
                Task task = taskModel.getElementAt(index);
                String newText = JOptionPane.showInputDialog(frame, "Edit Task", task.getText());
                if (newText != null && !newText.trim().isEmpty()) {
                    task.setText(newText.trim());
                    taskModel.set(index, task); // Refresh the model
                    taskList.repaint();
                }
            }
        });

        removeButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                taskModel.remove(index);
            }
        });

        clearButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear all tasks?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                taskModel.clear();
            }
        });

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Custom renderer to show tasks with a checkbox and strikethrough for completed tasks
    private static class TaskCellRenderer extends JPanel implements ListCellRenderer<Task> {
        private JCheckBox checkBox;

        public TaskCellRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            checkBox = new JCheckBox();
            add(checkBox);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index, boolean isSelected, boolean cellHasFocus) {
            // Remove all components and re-add to avoid duplication
            removeAll();
            add(checkBox);

            // Set checkbox state and text
            checkBox.setText(task.getText());
            checkBox.setSelected(task.isDone());

            // Apply strikethrough and gray color for completed tasks
            if (task.isDone()) {
                checkBox.setText("<html><strike>" + task.getText() + "</strike></html>");
                checkBox.setForeground(Color.GRAY);
            } else {
                checkBox.setText(task.getText());
                checkBox.setForeground(Color.BLACK);
            }

            // Handle selection background
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                checkBox.setBackground(list.getSelectionBackground());
            } else {
                setBackground(list.getBackground());
                checkBox.setBackground(list.getBackground());
            }

            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoApp::new);
    }
}