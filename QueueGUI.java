import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueGUI {
    private int maxQueue;
    private Object[] queueArray;
    private int front;
    private int rear;
    private JFrame frame;
    private JTextArea queueDisplay;
    private JTextField inputField;

    public QueueGUI(int size) {
        maxQueue = size;
        queueArray = new Object[maxQueue];
        front = -1;
        rear = -1;

        // ใช้ UIManager เพื่อให้ UI ดูทันสมัยขึ้น
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        frame = new JFrame("Queue GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        // สร้าง Panel ด้านบนสำหรับ Input และปุ่ม
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(200, 220, 240)); // สีฟ้าอ่อน
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton enqueueButton = createStyledButton("Enqueue", new Color(50, 150, 50));
        JButton dequeueButton = createStyledButton("Dequeue", new Color(200, 50, 50));

        queueDisplay = new JTextArea(10, 35);
        queueDisplay.setEditable(false);
        queueDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
        queueDisplay.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 2), // Darker border
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        queueDisplay.setBackground(new Color(245, 245, 245)); // Light gray background
        queueDisplay.setForeground(new Color(50, 50, 50));

        enqueueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enQueue(inputField.getText());
                    inputField.setText("");
                    updateQueueDisplay();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dequeueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deQueue();
                    updateQueueDisplay();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(new JLabel("Enter Value:"));
        panel.add(inputField);
        panel.add(enqueueButton);
        panel.add(dequeueButton);

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
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    public void enQueue(Object theData) throws Exception {
        if ((front == 0 && rear == maxQueue - 1) || (front == rear + 1))
            throw new Exception("Queue Overflow");
        if (front == -1) {
            front = 0;
            rear = 0;
        } else if (rear == maxQueue - 1)
            rear = 0;
        else
            rear = rear + 1;
        queueArray[rear] = theData;
    }

    public Object deQueue() throws Exception {
        if (front == -1)
            throw new Exception("Queue Underflow");
        Object temp = queueArray[front];
        if (front == rear) {
            front = -1;
            rear = -1;
        } else if (front == maxQueue - 1)
            front = 0;
        else
            front = front + 1;
        return temp;
    }

    private void updateQueueDisplay() {
        StringBuilder sb = new StringBuilder("Queue: ");
        if (front != -1) {
            int i = front;
            while (true) {
                sb.append(queueArray[i]).append(" ");
                if (i == rear)
                    break;
                i = (i + 1) % maxQueue;
            }
        }
        queueDisplay.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QueueGUI(10));
    }
}
