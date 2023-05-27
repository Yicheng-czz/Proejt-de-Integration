import gui.MainGUI;



public class Main {
    public static void main(String[] args){
        MainGUI simulation = new MainGUI("Explorateur");
        Thread guiTread = new Thread(simulation);
        guiTread.start();
    }
}