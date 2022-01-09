import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;




public class GamePanel extends JPanel implements ActionListener {
    // dekolaruję wszystko czego będę potrzebować dla tego programu
    static final int SCREEN_WIDTH = 600; //szerokość ekranu
    static final int SCREEN_HEIGHT = 600; //wysokość ekranu
    static final int UNIT_SIZE = 25; //jak duże mają być obiekty - im większy numer tym większe okna siatki, a w przy tym będzie ich mniej
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //obliczanie ile faktycznie elementów się zmieści na ekranie
    static final int DELAY = 75; //opóźnienie dla licznika - im wyższa liczba tym wolniejsza gra

    //tworzę tablice  które przechowują współrzędne ciała węża
    final int x[] = new int[GAME_UNITS]; //nie ważne jakie są nasze jednostki w grze bo wąż i tak nie będzie większy
    final int y[] = new int[GAME_UNITS]; //
    int bodyParts = 6; //zaczynam od 6 części ciała węża
    int applesEaten; //deklaruję liczbę całkowitą, początkowo będzie to 0
    int appleX; //współrzędna miejsca gdzie znajduje się jabłko i będzie losowo pojawiać się po zjedzeniu jabłka
    int appleY;
    char direction = 'R'; // kierunek -> R wąż od początku idzie w right prawo
    boolean running = false; //wartość logiczna
    Timer timer; //dekoalracja czasu
    Random random;

//zdeklaruję wszystkie metody których będę potrzebować (public void ...(){ }


    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)); //preferowany rozmiar panelu gry new Dimention -> nowy wymiar
        this.setBackground(Color.black); //kolor tła
        this.setFocusable(true); //ustawianie ostrości
        this.addKeyListener(new MyKeyAdapter()); //odbiornik kluczy
        startGame(); //metoda rozpoczynająca grę
    }
    // metoda uruchamiania gry
    public void startGame(){
        newApple(); //stworzenie nowego jabłka na ekranie
        running = true; // true bo na początku jest false
        timer = new Timer(DELAY,this); // timer jest równy nowemu timerowi któremu przekazujemy wartość opóźnienia,
        // dyktuje jak szybko działa gra. this - bo zamierzamy to przekazać bo używamy interfejsu nasluchiwania akcji
        timer.start();

    }
    //metoda rysowania z parametrem G
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    // metoda sprawdzania jabłka
    public void draw(Graphics g){
        // zamieniam ekran w siatkę, aby było mi łatwiej zobaczyć rzeczy
        //tworzę po prostu pętlę for w metodzie rysowania

        if(running){

            for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){ //dla int = 0 i będę to kontunować tak długo, aż będzie mniejsza od wyokości ekranu
                                                        // podzielonej przez rozmiar obiektu
                // użyję tej pętli w celu uzyskania siatki a potem za każdym razem będę ją zwiększać o 1
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //wzdłuż osi
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            g.setColor(Color.red); //kropka czerwona
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); //kształt owalu, powinien być idealnie dopasowany do kwadratu siatki
                                                //gdy zmienimy UNIT_SIZE, automatycznie powiekszy/zmniejszy sie tez kropka
            // rysuję głowę i ciało węża
            for(int i = 0; i < bodyParts; i++){ // pętla for iteruje części ciała węża
                            // ustawiam na 0 i kontynuuje pętlę tak długo aż i będzie mniejsze
                        // od ilości części ciała, a następnie zwiększam indeks o 1
                if(i == 0){ //jeśli = 0 - głowa węża
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); //filRect metoda prostokąta
                }

            else{ //jeśli nie = 0 - ciało węża
                g.setColor(new Color(45,180,0)); //kolor rgb
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

            }
        else{
            gameOver(g);

        }

    }
    // metoda żeby móc zdobyć punkt
    public void newApple(){
        //generowanie współrzędnych jabłka za każdym razem gdy metoda jest wywoływana, więc za każdym razem gdy od nowa odpalamy grę lub zdobędziemy punkt

        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //współrzędna X równa się lsoowej, zakresem jest szerokość ekranu/ rozmiar obiektu
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    //funkcja ruchu
    public void move(){
        // jakby przesuwanie części ciała węża
        for(int i = bodyParts ; i>0 ; i--){
            //przesuwam wszystkie współrzędne ciała o 1 miejsce
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        //zmiana kierunku ruchu
        switch(direction){
            // 0 to współrzędna głowy węża
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;

            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;


        }

    }
    // wąż zjada jabłka i w ten sposób zdobywam punkt
    public void checkApple(){
        if( (x[0] == appleX) && (y[0] == appleY) ){
            bodyParts++;
            applesEaten++;
            newApple();
        }


    }
    // metoda sprawdzania kolizji
    public void checkCollisions() {
        // sprawdza czy głowa zderza się z ciałem
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //sprawdza czy głowa dotyka lewej krawędzi
        if(x[0] < 0){
            running = false;
        }

        //sprawdza czy głowa dotyka prawej krawędzi
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }

        //sprawdza czy głowa dotyka górnej krawędzi
        if(y[0] < 0){
            running = false;
        }

        //sprawdza czy głowa dotyka dolnej krawędzi
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    // metoda zakończenia gry z parametrem G
    public void gameOver(Graphics g){

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // wywołanie funkcji move, aby zobaczyć ruszającego się węża
        //
        if(running){ //czy nasza gra działa, jesli tak to będzie prawda
            move(); // przeniesienie węża
            checkApple(); //sprawdzenie czy natknął się na jabłko
            checkCollisions(); // sprawdzenie kolizji
        }
        repaint(); //jeśli gra nie działa - metoda odświeżania

    }
    // w akcji tworzę metodę wewnętrzną main która rozszerza KeyAdapter

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
            }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
        }
    }

}}
