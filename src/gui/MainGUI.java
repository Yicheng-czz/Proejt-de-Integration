package gui;

import config.GameConfiguration;
import engine.map.Map;
import engine.process.ElementManager;
import engine.process.GameBuilder;
import engine.process.PlayerManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainGUI extends JFrame implements Runnable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map map;

    private final static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);

    private ElementManager elementManager;

    private GameDisplay gamedisplay;

    public MainGUI(String title){
        super(title);
        init();
    }

    public void init(){
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        setSize(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        map = GameBuilder.buildMap("src/res/mapInfo/worldmap01.txt");

        List<PlayerManager> playerManagers;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the strategy you want(1, 2 or 3): ");
        int strategy = scanner.nextInt();
        if(strategy < 1 || strategy > 3){
            System.out.println("you should enter a number between 1 and 3");
            System.exit(1);
        }

        elementManager = GameBuilder.buildElement(map);
        gamedisplay = new GameDisplay(map,elementManager);

        gamedisplay.setPreferredSize(preferredSize);
        contentPane.add(BorderLayout.CENTER, gamedisplay);
        pack();
        setVisible(true);
        setPreferredSize(preferredSize);
        setResizable(false);

        playerManagers = elementManager.getPlayerManagers();
        for(int i = 0; i < 4; i++){
            playerManagers.get(i).setSwitchStrategy(strategy);
            new Thread(playerManagers.get(i)).start();
            System.out.println("the "+i+" thread starts");
        }
    }


    @Override
    public void run() {
        while (!elementManager.getTreasureList().isEmpty()) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            gamedisplay.repaint();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
}
