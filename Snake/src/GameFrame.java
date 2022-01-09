import javax.swing.*;

public class GameFrame extends JFrame {

//tworzę konstruktory
    GameFrame(){

        this.add(new GamePanel()) ; //ramka
        this.setTitle("Snake"); //tytuł
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //domyślna operacja zamykania JFrame
        this.setResizable(false); //false - nie możemy zmienić wielkości ramki
        this.pack(); //dopasowuje rozmiar ramki tak, aby cała jej zawartość miała lub przekraczała preferowane rozmiary
        this.setVisible(true); //dzięki niej widzimy ramkę na ekranie, bez niej by jej nie było
        this.setLocationRelativeTo(null); //lokalizacja okna



    }
}
