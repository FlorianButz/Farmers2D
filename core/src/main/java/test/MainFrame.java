package test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private final int gridSize = 5;
    private final Cell[][] grid = new Cell[gridSize][gridSize];
    private final Timer timer;
    private int tickCounter = 0;
    private boolean running = false;

    public MainFrame() {
        setTitle("Plant Testing Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(gridSize, gridSize));
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Cell cell = new Cell(x, y);
                grid[x][y] = cell;
                gridPanel.add(cell);
            }
        }

        // Sidebar for plant selection
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JButton plantButton1 = new JButton("Plant 1");
        JButton plantButton2 = new JButton("Plant 2");
        sidebar.add(plantButton1);
        sidebar.add(plantButton2);

        plantButton1.addActionListener(e -> Cell.selectedPlant = new Plant(new GenPool("Plant 1", Color.GREEN, 50, 70)));
        plantButton2.addActionListener(e -> Cell.selectedPlant = new Plant(new GenPool("Plant 2", Color.ORANGE, 10, 100)));

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        sidebar.add(startButton);
        sidebar.add(stopButton);

        startButton.addActionListener(e -> running = true);
        stopButton.addActionListener(e -> running = false);

        add(gridPanel, BorderLayout.CENTER);
        add(sidebar, BorderLayout.EAST);

        timer = new Timer(50, e -> gameLoop());
        timer.start();

        setSize(800, 800);
        setVisible(true);
    }

    private void gameLoop() {
        if (running) {
            tickCounter++;
            if (tickCounter % 10 == 0) {
                // every 10 ticks -> breeding
                for (int x = 0; x < gridSize; x++) {
                    for (int y = 0; y < gridSize; y++) {
                        Cell cell = grid[x][y];
                        if (cell.isSoil() && cell.plant == null) {
                            ArrayList<Plant> neighbors = getNeighborPlants(x, y);
                            if (neighbors.size() >= 2) {
                                Plant parent0 = neighbors.get(0);
                                Plant parent1 = neighbors.get(1);
                                cell.plant = new Plant(parent0.genpool.mix(parent1.genpool));
                                cell.repaint();
                            }
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Plant> getNeighborPlants(int x, int y) {
        ArrayList<Plant> neighbors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && ny >= 0 && nx < gridSize && ny < gridSize) {
                Plant p = grid[nx][ny].plant;
                if (p != null) neighbors.add(p);
            }
        }
        return neighbors;
    }
}
