package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    private final int x, y;
    private boolean soil = false;
    public Plant plant = null;
    public static Plant selectedPlant = null;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (plant != null && !CellPanelHelper.running) {
                    // Show stats in new window
                    JOptionPane.showMessageDialog(null, plant.genpool.toString());
                } else if (!soil) {
                    soil = true;
                    setBackground(new Color(102, 51, 0)); // dark brown soil
                } else if (selectedPlant != null) {
                    plant = new Plant(selectedPlant.genpool);
                    repaint();
                }
            }
        });
    }

    public boolean isSoil() {
        return soil;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (plant != null) {
            g.setColor(plant.getColor());
            g.fillOval(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);
        }
    }

}

