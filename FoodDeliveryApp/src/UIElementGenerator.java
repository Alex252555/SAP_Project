import javax.swing.*;
import java.awt.*;

public class UIElementGenerator {

    public JPanel createPanel(GridBagLayout borderLayout, Color color){
        JPanel panel=new JPanel();
        if(color!=null) panel.setBackground(color);
        if(borderLayout!=null) panel.setLayout(borderLayout);
        return panel;
    }

    public JButton createSquareButton(JPanel header) {
        JButton button = new JButton();
        int headerHeight = 60; // Ориентировъчна височина (можеш да вземеш динамично ако искаш)
        int buttonSize = (int) (headerHeight * 0.8); // 80% от header височината за всеки бутон

        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
        button.setMinimumSize(new Dimension(buttonSize, buttonSize));
        button.setMaximumSize(new Dimension(buttonSize, buttonSize));
        return button;
    }
    public GridBagConstraints createGBC(Integer gridX, Integer gridY, Double weightX, Integer anchor, Integer fill, Integer inset1, Integer inset2, Integer inset3, Integer inset4) {
        GridBagConstraints gbc = new GridBagConstraints();

        if (gridX != null) gbc.gridx = gridX;
        if (gridY != null) gbc.gridy = gridY;
        if (weightX != null) gbc.weightx = weightX;
        if (anchor != null) gbc.anchor = anchor;
        if (fill != null) gbc.fill = fill;
        if (inset1 != null && inset2 != null && inset3 != null && inset4 != null) gbc.insets = new Insets(inset1, inset2, inset3, inset4);

        return gbc;
    }
}
