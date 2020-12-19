package sample;

import javafx.scene.paint.Color;
import sample.squares.ChanceAndCommunityChest;
import sample.squares.*;

import java.util.ArrayList;

public class Board {

    // properties
    private String name;
    Square[] squares;
    CardDeck chanceDeck;
    CardDeck chestDeck;
    // TODO: make this a <String, ColorGroup> map?
    ArrayList<ColorGroup> colorGroups;
    String currency;

    int rentRate;
    int mortgageRate;
    /*
    property array -?
        aynı name'de 2 group a izin verme!
        property'i colorGroupa koyarken, property içindeki colorgroup ismine bak

        property(name, ...., string colorgroup)

       if(colorgroup yoksa yarat, varsa içindeki property array e bu property 'i ekle
     */


    // constructors

    // test constructor
    public Board() {
        colorGroups = createGroups();
        squares = getTestSquares();
        name = "default";
        chestDeck = new CardDeck();
        chestDeck.generateChestCardDeck();
        chanceDeck = new CardDeck();
        chanceDeck.generateChanceCardDeck();
        currency = "Meteors";
        rentRate = 50;
        mortgageRate = 120;
    }

    public Board(String name) {
        chestDeck = new CardDeck();
        chestDeck.generateChestCardDeck();
        chanceDeck = new CardDeck();
        chanceDeck.generateChanceCardDeck();
        this.name = name;
    }

    //TODO load board constructor

    public void updatePropertyGroups() {
        for (int i = 0; i < squares.length; i++) {
            if (squares[i].getType() == SquareType.PROPERTY) {
                Property property = (Property) squares[i];
                getColorGroup(property.getGroupName()).addProperty(property);
            }
        }
    }

    public Card drawChanceCard() { return chanceDeck.drawCard(); }
    public Card drawChestCard() { return chestDeck.drawCard(); }

    public SquareType getSquareType(int squareNo) {
        return squares[squareNo].getType();
    }

    public ColorGroup getColorGroup(String groupName) {
        for (ColorGroup colorGroup : colorGroups){
            if (colorGroup.getGroupName().equals(groupName)) {
                return colorGroup;
            }
        }
        System.out.println("ERROR: Could not find ColorGroup in colorGroups: " + groupName);
        return null;
    }

    // getters and setters

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setColorGroups(ArrayList<ColorGroup> colorGroups) { this.colorGroups = colorGroups; }
    public ArrayList<ColorGroup> getColorGroups() { return colorGroups; }

    public void setSquares(Square[] squares) { this.squares = squares; }
    public Square[] getSquares() { return squares; }

    public void setCurrency(String currency) { this.currency = currency; }
    public String getCurrency() { return currency; }

    public void setMortgageRate(int mortgageRate) { this.mortgageRate = mortgageRate; }
    public int getMortgageRate() { return mortgageRate; }

    public void setRentRate(int rentRate) { this.rentRate = rentRate; }
    public int getRentRate() { return rentRate; }

    // TODO: test code, remove later?

    private final String[] propertyNames = {"New York", "Boston", "Paris", "Copenhagen", "Berlin", "İstanbul",
            "Ankara", "Chicago", "Rome", "Milan", "London", "Seul", "Beijing", "Luzern", "Bern", "Oslo",
            "Barcelona", "Madrid", "Amsterdam", "Munich"};

    private Square[] getTestSquares() {
        Square[] squares = new Square[40];
        squares[0] = new Start(20);
        for (int i = 1; i < 40; i++) {
            if(i % 4 == 0)
                squares[i] = new Joker(9, 10, 0,"");
            else if(i % 4 == 1)
                squares[i] = new ChanceAndCommunityChest(true);
            else if (i % 4 == 2) {
                Property newProp = new Property(propertyNames[i % 20], colorGroups.get(0), 100, 80, 50);
                squares[i] = newProp;
                colorGroups.get(0).addProperty(newProp);
            }
            else {
                Property newProp = new Property(propertyNames[i % 20], colorGroups.get(1), 100, 80, 50);
                squares[i] = newProp;
                colorGroups.get(1).addProperty(newProp);
            }
        }
        return squares;
    }

    private ArrayList<ColorGroup> createGroups() {
        ArrayList<ColorGroup> colors = new ArrayList<ColorGroup>();

        ColorGroup red = new ColorGroup("Red");
        red.setColor(Color.NAVAJOWHITE);
        colors.add(red);

        ColorGroup pink = new ColorGroup("Pink");
        pink.setColor(Color.HOTPINK);
        colors.add(pink);

        return colors;
    }
}
