import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SortingVisualizer extends JPanel {
    private static final int SIZE = 50; // Number of elements
    private int[] array = new int[SIZE];
    private int delay = 50; // Sorting speed (ms)

    public SortingVisualizer() {
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.BLACK);
        generateArray();
    }

    private void generateArray() {
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            array[i] = rand.nextInt(300) + 10;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth() / SIZE;
        for (int i = 0; i < SIZE; i++) {
            g.setColor(Color.WHITE);
            g.fillRect(i * width, getHeight() - array[i], width - 2, array[i]);
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        repaint();
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void bubbleSort() {
        new Thread(() -> {
            for (int i = 0; i < SIZE - 1; i++) {
                for (int j = 0; j < SIZE - i - 1; j++) {
                    if (array[j] > array[j + 1]) {
                        swap(j, j + 1);
                    }
                }
            }
        }).start();
    }

    public void selectionSort() {
        new Thread(() -> {
            for (int i = 0; i < SIZE - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < SIZE; j++) {
                    if (array[j] < array[minIndex]) {
                        minIndex = j;
                    }
                }
                swap(i, minIndex);
            }
        }).start();
    }

    public void quickSort(int left, int right) {
        if (left < right) {
            int pivotIndex = partition(left, right);
            quickSort(left, pivotIndex - 1);
            quickSort(pivotIndex + 1, right);
        }
    }

    private int partition(int left, int right) {
        int pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (array[j] < pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, right);
        return i + 1;
    }

    public void startQuickSort() {
        new Thread(() -> quickSort(0, SIZE - 1)).start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualizer");
        SortingVisualizer visualizer = new SortingVisualizer();

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.DARK_GRAY);

        JButton bubbleButton = new JButton("Bubble Sort");
        JButton selectionButton = new JButton("Selection Sort");
        JButton quickButton = new JButton("Quick Sort");
        JButton resetButton = new JButton("Reset");

        bubbleButton.addActionListener(e -> visualizer.bubbleSort());
        selectionButton.addActionListener(e -> visualizer.selectionSort());
        quickButton.addActionListener(e -> visualizer.startQuickSort());
        resetButton.addActionListener(e -> visualizer.generateArray());

        buttons.add(bubbleButton);
        buttons.add(selectionButton);
        buttons.add(quickButton);
        buttons.add(resetButton);

        frame.add(visualizer, BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
