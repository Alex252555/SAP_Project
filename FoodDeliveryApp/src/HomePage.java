import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public static void main(String[] args) {

        UIElementGenerator uig = new UIElementGenerator();

        SwingUtilities.invokeLater(() -> {

            // Frame
            JFrame frame = new JFrame("Приложение за доставка на храна");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new GridBagLayout());

            // Създаване на GridBagConstrains - кара нареждането на елементите да е като решетка
            GridBagConstraints gbc = uig.createGBC(0, null, 1.0, null, GridBagConstraints.BOTH, null, null, null, null);

            // Header
            JPanel header = uig.createPanel(new GridBagLayout(), Color.PINK);
            gbc.gridy = 0;
            gbc.weighty = 0.1;
            frame.add(header, gbc);

            // Body
            JPanel content = uig.createPanel(null, Color.WHITE);
            gbc.gridy = 1;
            gbc.weighty = 0.85;
            frame.add(content, gbc);

            // Лого

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 10, 0, 0);

            JLabel logoLabel = new JLabel();
            logoLabel.setHorizontalAlignment(JLabel.LEFT);
            header.add(logoLabel, gbc);

            BufferedImage originalLogo = null;
            try {
                originalLogo = ImageIO.read(HomePage.class.getResource("Images\\siteLogo.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            final BufferedImage finalOriginalLogo = originalLogo;

            // Бутони

            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            buttonsPanel.setOpaque(false);

            List<JButton> buttons = new ArrayList<>();
            List<BufferedImage> buttonIcons = new ArrayList<>();

            try {
                buttonIcons.add(ImageIO.read(HomePage.class.getResource("Images\\homePage.jpg")));
                buttonIcons.add(ImageIO.read(HomePage.class.getResource("Images\\checkout.jpg")));
                buttonIcons.add(ImageIO.read(HomePage.class.getResource("Images\\profile.jpg")));
                buttonIcons.add(ImageIO.read(HomePage.class.getResource("Images\\settings.jpg")));
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (BufferedImage iconImage : buttonIcons) {
                JButton button = new JButton(new ImageIcon(iconImage));
                buttons.add(button);
                buttonsPanel.add(button);
            }

            GridBagConstraints headerGbc = new GridBagConstraints();
            headerGbc.gridx = 1;
            headerGbc.gridy = 0;
            headerGbc.weightx = 1.0;
            headerGbc.anchor = GridBagConstraints.EAST;
            headerGbc.insets = new Insets(0, 0, 0, 15);
            header.add(buttonsPanel, headerGbc);

            // BODY PANEL -------------------------------------------------------------------------------------------------------

            // Вътре в content слагаме bodyPanel
            JPanel bodyPanel = new JPanel(new GridBagLayout());
            bodyPanel.setOpaque(false);

            // GridBagConstraints за вътрешното подреждане
            GridBagConstraints bodyGbc = new GridBagConstraints();
            bodyGbc.gridx = 0;
            bodyGbc.gridy = 0;
            bodyGbc.fill = GridBagConstraints.HORIZONTAL;
            bodyGbc.anchor = GridBagConstraints.NORTH;
            bodyGbc.insets = new Insets(20, 20, 0, 20); // отстояние от краищата

            // ТЪРСАЧКА -----------------------------------------------------------------------------------------------------------

            // Панел за иконката и полето
            JPanel searchPanel = new JPanel(new GridBagLayout());
            searchPanel.setOpaque(false);

            // Иконка лупа
            BufferedImage originalSearchIcon = null;
            try {
                originalSearchIcon = ImageIO.read(HomePage.class.getResource("Images\\magnifyingGlass.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image scaledSearchImage = originalSearchIcon.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledSearchIcon = new ImageIcon(scaledSearchImage);

            // JLabel с иконка
            JLabel searchIconLabel = new JLabel(scaledSearchIcon);

            // Текстово поле
            JTextField searchField = new JTextField();
            searchField.setFont(new Font("Arial", Font.PLAIN, 16));
            searchField.setPreferredSize(new Dimension(600, 40)); // минимален размер
            searchField.setMinimumSize(new Dimension(200, 30));

            // Добавяме иконата
            GridBagConstraints spGbc = new GridBagConstraints();
            spGbc.gridx = 0;
            spGbc.gridy = 0;
            spGbc.insets = new Insets(0, 5, 0, 5);
            spGbc.anchor = GridBagConstraints.CENTER;
            searchPanel.add(searchIconLabel, spGbc);

            // Добавяме полето
            spGbc.gridx = 1;
            spGbc.weightx = 1.0;
            spGbc.fill = GridBagConstraints.HORIZONTAL;
            searchPanel.add(searchField, spGbc);

            // searchPanel в bodyPanel
            bodyPanel.add(searchPanel, bodyGbc);

            // bodyPanel в content
            content.setLayout(new BorderLayout());
            content.add(bodyPanel, BorderLayout.NORTH); // само в горната част

            // ПОДСКАЗКА В ПОЛЕТО ---------------------------------------------------------

            // Начален текст
            String placeholderText = "Търсене на храна...";
            searchField.setText(placeholderText);
            searchField.setForeground(Color.GRAY);

            // Когато потребителят кликне в полето
            searchField.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (searchField.getText().equals(placeholderText)) {
                        searchField.setText("");
                        searchField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (searchField.getText().isEmpty()) {
                        searchField.setForeground(Color.GRAY);
                        searchField.setText(placeholderText);
                    }
                }
            });

            // bodyPanel в content
            content.setLayout(new BorderLayout());
            content.add(bodyPanel, BorderLayout.NORTH); // само в горната част

            // ПАНЕЛ С ФИЛТЪР БУТОНИ ----------------------------------------------------------------------------------------------

            JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10)); // 30px между бутоните
            filterPanel.setOpaque(false); // прозрачен панел

            ArrayList<JButton> filterButtons = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
                button.setForeground(Color.BLACK);
                button.setFont(new Font("SansSerif", Font.BOLD, 16));
                button.setMargin(new Insets(10, 20, 10, 20));
                button.setFocusPainted(false);
                filterButtons.add(button);
            }

            filterButtons.get(0).setText("Ястие");
            filterButtons.get(1).setText("Предястие");
            filterButtons.get(2).setText("Десерт");

            filterPanel.add(filterButtons.get(0));
            filterPanel.add(filterButtons.get(1));
            filterPanel.add(filterButtons.get(2));

            // ПОЛЕ С ЯСТИЯ ----------------------------------------------------------------------------------

            JPanel dishesPanel = new JPanel();
            dishesPanel.setLayout(new BoxLayout(dishesPanel, BoxLayout.Y_AXIS));
            dishesPanel.setOpaque(false);

            JScrollPane scrollPane = new JScrollPane(dishesPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); // Падинг отвътре

            JPanel bodyContentPanel = new JPanel();
            bodyContentPanel.setLayout(new GridBagLayout());
            bodyContentPanel.setOpaque(false);

            GridBagConstraints bcGbc = new GridBagConstraints();
            bcGbc.gridx = 0;
            bcGbc.gridy = 0;
            bcGbc.weightx = 1.0;
            bcGbc.fill = GridBagConstraints.HORIZONTAL;
            bcGbc.insets = new Insets(0, 20, 10, 20); // падинг отстрани
            bodyContentPanel.add(filterPanel, bcGbc);

            bcGbc.gridy = 1;
            bodyContentPanel.add(Box.createVerticalStrut(10), bcGbc);

            // скрол панел
            bcGbc.gridy = 2;
            bcGbc.weighty = 1.0;
            bcGbc.fill = GridBagConstraints.BOTH;
            bodyContentPanel.add(scrollPane, bcGbc);

            // Добавяме bodyContentPanel към content
            content.add(bodyContentPanel, BorderLayout.CENTER);

            // Промяна на размери според големината на рамката на приложението

            header.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int headerHeight = header.getHeight();

                    // Оразмеряване на логото
                    if (finalOriginalLogo != null) {
                        int newHeight = (int) (headerHeight * 0.7);
                        int newWidth = (int) (newHeight * 1.942);
                        Image scaledLogo = finalOriginalLogo.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                        logoLabel.setIcon(new ImageIcon(scaledLogo));
                    }

                    // Оразмеряване на бутоните и иконите
                    int buttonSize = (int) (headerHeight * 0.4);

                    for (int i = 0; i < buttons.size(); i++) {
                        JButton button = buttons.get(i);
                        BufferedImage iconImage = buttonIcons.get(i);

                        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
                        button.setMaximumSize(new Dimension(buttonSize, buttonSize));
                        button.setMinimumSize(new Dimension(buttonSize, buttonSize));

                        Image scaledIcon = iconImage.getScaledInstance(buttonSize - 10, buttonSize - 10, Image.SCALE_SMOOTH);
                        button.setIcon(new ImageIcon(scaledIcon));
                    }

                    buttonsPanel.revalidate();
                    buttonsPanel.repaint();
                }
            });

            // Ръчен resize в началото за получаване на правилни размери
            SwingUtilities.invokeLater(() -> {
                header.dispatchEvent(new ComponentEvent(header, ComponentEvent.COMPONENT_RESIZED));
            });

            frame.setVisible(true);
        });
    }
}