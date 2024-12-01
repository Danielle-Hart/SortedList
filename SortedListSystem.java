import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SortedListSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SortedListGUI());
    }
}

class SortedList {
    private ArrayList<Integer> list;

    public SortedList() {
        list = new ArrayList<>();
    }

    private int binarySearch(int element) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = Integer.compare(element, list.get(mid));

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return low;
    }

    public void add(int element) {
        int index = binarySearch(element);
        list.add(index, element);
    }

    public int search(int element) {
        int index = binarySearch(element);
        if (index < list.size() && list.get(index) == element) {
            return index;
        }
        return -index - 1;
    }

    public String getList() {
        return list.stream().map(String::valueOf).collect(Collectors.joining("\n"));
    }
}

class SortedListGUI {
    private SortedList sortedList;
    private JTextArea textArea;

    public SortedListGUI() {
        sortedList = new SortedList();

        JFrame frame = new JFrame("Sorted List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JTextField inputField = new JTextField(20);
        JButton addButton = new JButton("Add");
        JButton searchButton = new JButton("Search");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Input:"));
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        inputPanel.add(searchButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            if (!input.isEmpty()) {
                try {
                    int number = Integer.parseInt(input);
                    sortedList.add(number);
                    updateTextArea();
                    inputField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
            }
        });

        searchButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            if (!input.isEmpty()) {
                try {
                    int number = Integer.parseInt(input);
                    int result = sortedList.search(number);
                    if (result >= 0) {
                        textArea.append("\nFound: " + number + " at index " + result);
                    } else {
                        int insertionPoint = -result - 1;
                        textArea.append("\nNot Found. Insert at index " + insertionPoint);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void updateTextArea() {
        textArea.setText("Sorted List:\n" + sortedList.getList());
    }
}