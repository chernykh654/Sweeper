import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {
    private Game game;

    private JPanel panel; // обявление панели на которой будем рисовать
    private JLabel label;
    private final int COLS = 9;//колличество столбцов
    private final int ROWS = 9;//Колличество строк
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50; // Размер картинки


    public static void main(String[] args) {
        new JavaSweeper();
    }

    private JavaSweeper () {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();


    }
    private void initLabel() {
        label = new JLabel("Welcome!");
        add( label, BorderLayout.SOUTH);
    }

    private void  initPanel () {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };



        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x* IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE)) ;// Размерность панели
        add (panel);
    }

    private  void initFrame() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// Активирует Действие по умолчанию На нажатие крестика
        setTitle("Java Sweeper"); // Установка заголовка
        setResizable(false);//запрет на изменение окна
        setVisible(true); // видимость формы
        setIconImage(getImage("icon")); // заадем иконку
        pack(); //Изменяет размер чтобы все компаненты входили в размер окна
        setLocationRelativeTo(null);// установка окошка по центру
    }

    private void setImages () {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name) {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon (getClass().getResource(filename));
        return icon.getImage();
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED: return "Think Twitch!";
            case BOMBED: return "YOU LOSE! BIG BA-DA-BOOM!";
            case WINNER: return "YOU WIN!!!!";
            default: return "Welcome";

        }
    }
}
