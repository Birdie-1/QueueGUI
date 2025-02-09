import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class QueueGUI {
    private int maxQueue;
    private Object[] queueArray;
    private int front;
    private int rear;
    private int size;
    private JFrame frame;
    private JTextArea queueDisplay;
    private JTextField inputField;

    public QueueGUI(int size) {
        maxQueue = size;
        queueArray = new Object[maxQueue];
        front = -1;
        rear = -1;
        this.size = 0;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        frame = new JFrame("Queue GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(255, 228, 225)); // Pastel pink

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(255, 239, 213)); // Pastel peach
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Enter Value:");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);

        inputField = new JTextField(12);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(inputField);

        JButton enqueueButton = createStyledButton("Enqueue", new Color(152, 251, 152)); // Pastel green
        enqueueButton.setForeground(Color.BLACK); // Ensure black text for better contrast

        JButton dequeueButton = createStyledButton("Dequeue", new Color(135, 206, 250)); // Pastel blue
        dequeueButton.setForeground(Color.BLACK); // Ensure black text for better contrast

        panel.add(enqueueButton);
        panel.add(dequeueButton);

        queueDisplay = new JTextArea(10, 40);
        queueDisplay.setEditable(false);
        queueDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
        queueDisplay.setBorder(BorderFactory.createLineBorder(new Color(176, 224, 230), 2)); // Pastel blue border
        queueDisplay.setBackground(new Color(240, 248, 255)); // Pastel baby blue
        queueDisplay.setForeground(Color.DARK_GRAY);

        enqueueButton.addActionListener(e -> {
            try {
                enQueue(inputField.getText());
                inputField.setText("");
                updateQueueDisplay();
            } catch (Exception ex) {
                showErrorDialog(ex.getMessage());
            }
        });

        dequeueButton.addActionListener(e -> {
            try {
                deQueue();
                updateQueueDisplay();
            } catch (Exception ex) {
                showErrorDialog(ex.getMessage());
            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(queueDisplay), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(7, 15, 7, 15));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enQueue(Object theData) throws Exception {
        if (size == maxQueue)
            throw new Exception("Queue Overflow");
        if (front == -1) {
            front = 0;
            rear = 0;
        } else {
            rear = (rear + 1) % maxQueue;
        }
        queueArray[rear] = theData;
        size++;
    }

    public Object deQueue() throws Exception {
        if (size == 0)
            throw new Exception("Queue Underflow");
        Object temp = queueArray[front];
        front = (front + 1) % maxQueue;
        size--;
        if (size == 0) {
            front = -1;
            rear = -1;
        }
        return temp;
    }

    private void updateQueueDisplay() {
        StringBuilder sb = new StringBuilder("Queue: ");
        if (size > 0) {
            int i = front;
            for (int count = 0; count < size; count++) {
                sb.append(queueArray[i]).append(" ");
                i = (i + 1) % maxQueue;
            }
        }
        queueDisplay.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QueueGUI(5));
    }
}