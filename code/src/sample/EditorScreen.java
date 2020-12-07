package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

public class EditorScreen {
    // properties
    private Scene scene;
    GridPane boardPane;

    Text[] playerTexts;
    Text turnText;
    Text diceText;

    Button btnRollDice;
    Button btnEndTurn;
    Button btnBuy;

    GameEngine gameEngine;
    private boolean diceRolled;

    Font font = Font.font("Source Sans Pro", 20);

    // constructors
    public EditorScreen() throws FileNotFoundException {
        //editor = new Editor();
        // boardPane = getTiles(); //CHANGE
        setScene();
    }

    // private methods
    private void setScene() throws FileNotFoundException {
        Group group = new Group();
        int width = 1366;
        int height = 768;

        // group.getChildren().add(boardPane);
        Rectangle[] recs = getTiles();
        for(int i=0; i < 41; i++){
            group.getChildren().add(recs[i]);
        }
        scene = new Scene(group, width, height);
    }

    private Rectangle[] getTiles() throws FileNotFoundException {
        //  GridPane gridPane = new GridPane();
        Rectangle[] recs = new Rectangle[41];
        for (int col = 0; col < 11; col++) {
            for (int row = 0; row < 11; row++) {
                int sum = row + col;
                int pos;

                if ( (row == 0) | (col == 0) | (row == 10) | (col == 10)) {
                    if (col >= row) {
                        pos = sum;
                    } else {
                        pos = 40 - sum;
                    }
                }
                else
                    pos = 40 + row + col;

                if (pos < 40) {
                  /*  StackPane stp = new StackPane();
                    stp.setPadding(new Insets(1, 1, 1, 1));*/

                    // create rectangle of correct color for tile
                    Rectangle tile = new Rectangle();
                    if ( (row == 0 & col == 10) | (col == 0 & row == 10) | (row == col)){
                        tile.setHeight(90);
                        tile.setWidth(90);
                    }
                    else if (row == 10 | row == 0){
                        tile.setHeight(90);
                        tile.setWidth(60);
                    }
                    else{
                        tile.setHeight(60);
                        tile.setWidth(90);
                    }

                    //rect locations
                    if (pos == 0){
                        tile.setX(10);
                        tile.setY(10);
                    }
                    else if (pos <= 10){
                        tile.setX(recs[pos - 1].getX() + recs[pos - 1].getWidth());
                        tile.setY(recs[pos - 1].getY());
                    }
                    else if (pos <= 20){
                        tile.setX(recs[pos - 1].getX());
                        tile.setY(recs[pos - 1].getY() + recs[pos - 1].getHeight());
                    }
                    else if (pos < 30){
                        tile.setX(recs[(pos + 1) % 40].getX() + recs[(pos + 1) % 40].getWidth());
                        tile.setY(recs[(pos + 1) % 40].getY());
                    }
                    else if (pos < 40){
                        tile.setX(recs[(pos + 1) % 40].getX());
                        tile.setY(recs[(pos + 1) % 40].getY() + recs[(pos + 1) % 40].getHeight());
                    }
                    else{
                        tile.setX(recs[0].getX() + recs[0].getHeight());
                        tile.setY(recs[0].getY() + recs[0].getWidth());
                    }

                    //rect colors
                    tile.setStroke(Color.BLACK);
                    tile.setFill(Color.WHITE);
                    tile.setOnMouseClicked(event -> {
                        System.out.println(pos);
                        Font font = new Font("Source Sans Pro", 20);
                        Dialog d = new Dialog();
                        d.getDialogPane().setBackground(new Background(new BackgroundFill(Color.rgb(182, 216, 184), CornerRadii.EMPTY, Insets.EMPTY)));
                        Text header = new Text("Select square type:");
                        header.setFont(font);

                        //group of radio buttons
                        final ToggleGroup group = new ToggleGroup();

                        RadioButton property = new RadioButton("Property");
                        property.setToggleGroup(group);
                        property.setFont(font);
                        property.setSelected(true);

                        RadioButton joker = new RadioButton("Joker");
                        joker.setFont(font);
                        joker.setToggleGroup(group);

                        RadioButton chance = new RadioButton("Chance");
                        chance.setFont(font);
                        chance.setToggleGroup(group);

                        RadioButton communityChest = new RadioButton("Community Chest");
                        communityChest.setFont(font);
                        communityChest.setToggleGroup(group);

                        VBox vbox = new VBox(10);
                        vbox.setPadding(new Insets(10));

                        vbox.getChildren().addAll(header, property, joker, chance, communityChest);

                        d.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.NEXT);
                        ((Button) d.getDialogPane().lookupButton(ButtonType.CANCEL)).setFont(font);
                        ((Button) d.getDialogPane().lookupButton(ButtonType.NEXT)).setFont(font);
                        ((Button) d.getDialogPane().lookupButton(ButtonType.NEXT)).setDefaultButton(false);

                        //todo next buttonu bir sonraki dialog pane e geçmeli
                        d.getDialogPane().lookupButton(ButtonType.NEXT).setOnMouseClicked(event2 -> {
                            System.out.println("clicked");
                            openSecondDialog();
                        });
                        d.getDialogPane().setContent(vbox);

                        Optional<ButtonType> result = d.showAndWait();

                        if (result.get() == ButtonType.NEXT){
                            System.out.println("here");
                            openSecondDialog();
                        }
                    });

                    recs[pos]= tile;
                    /*if ((row == 0) | (col == 0) | (row == 10) | (col == 10)) {
                        stp.getChildren().add(0, tile);
                        stp.getChildren().addAll(tile, text);
                        gridPane.add(stp, col, row);
                    }*/
                }
            }
        }

        /*gridPane.setLayoutX(10);
        gridPane.setLayoutY(300);*/
        Rectangle middleOne = new Rectangle();
        middleOne.setX(recs[0].getX() + recs[0].getWidth());
        middleOne.setY(recs[0].getY() + recs[0].getHeight());
        middleOne.setStroke(Color.BLACK);
        middleOne.setFill(Color.WHITE);
        middleOne.setHeight(540);
        middleOne.setWidth(540);

        /* image yok
        Image image = new Image(new FileInputStream("C:\\Users\\User\\Documents\\cs319-Monopoly-3D\\code\\src\\sample\\ask.jpeg"));

        //Setting the image view
        ImagePattern imagePattern = new ImagePattern(image);

        EventHandler<MouseEvent> eventHandler =
                new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent e) {
                        middleOne.setFill(imagePattern);
                    }
                };

        //Adding the event handler
        middleOne.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
        recs[40]= middleOne;
        return recs;
    }
    */


    /*
    //new updateBoard same as getTiles?
    private void updateTiles() { //board pane vs grid pane?
        boardPane.getChildren().clear();

        for (int col = 0; col < 11; col++) {
            for (int row = 0; row < 11; row++) {
                int sum = row + col;
                int pos;

                if ( (row == 0) | (col == 0) | (row == 10) | (col == 10)) {
                    if (col >= row) {
                        pos = sum;
                    } else {
                        pos = 40 - sum;
                    }
                }
                else
                    pos = 40 + row + col;

                if (pos < 40) {
                    StackPane stp = new StackPane();
                    stp.setPadding(new Insets(1, 1, 1, 1));

                    // create rectangle of correct color for tile
                    Rectangle tile = new Rectangle();
                    if ((row == col) | (row == 0 & col == 10) | (col == 0 & row == 10)){
                        tile.setHeight(60);
                        tile.setWidth(60);
                    }
                    else if (row == 10 | row == 0){
                        tile.setHeight(60);
                        tile.setWidth(40);
                    }
                    else{
                        tile.setHeight(40);
                        tile.setWidth(60);
                    }
                    tile.setX(col * 10);
                    tile.setY(row * 10);
                    tile.setStroke(Color.BLACK);
                    tile.setOnMouseClicked(event -> {
                        System.out.println(pos);
                    });

                    if ((row == 0) | (col == 0) | (row == 10) | (col == 10)) {
                        stp.getChildren().add(0, tile);
                        //stp.getChildren().addAll(tile, text);
                    }
                    boardPane.add(stp, col, row);
                }
            }
        }*/
        //middleOne.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
        recs[40]= middleOne;
        return recs;
    }

    private void openSecondDialog() {
        System.out.println("yes");
        Font font = new Font("Source Sans Pro", 20);
        Dialog d2 = new Dialog();
        d2.getDialogPane().setBackground(new Background(new BackgroundFill(Color.rgb(182, 216, 184), CornerRadii.EMPTY, Insets.EMPTY)));
        Text header = new Text("Joker Square");
        header.setFont(font);

        //group of radio buttons
        final ToggleGroup group2 = new ToggleGroup();

        RadioButton property = new RadioButton("Property");
        property.setToggleGroup(group2);
        property.setFont(font);
        property.setSelected(true);

        RadioButton joker = new RadioButton("Joker");
        joker.setFont(font);
        joker.setToggleGroup(group2);

        RadioButton chance = new RadioButton("Chance");
        chance.setFont(font);
        chance.setToggleGroup(group2);

        RadioButton communityChest = new RadioButton("Community Chest");
        communityChest.setFont(font);
        communityChest.setToggleGroup(group2);

        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(10));

        vbox2.getChildren().addAll(header, property, joker, chance, communityChest);

        d2.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.NEXT);
        ((Button) d2.getDialogPane().lookupButton(ButtonType.CANCEL)).setFont(font);
        ((Button) d2.getDialogPane().lookupButton(ButtonType.NEXT)).setFont(font);
        System.out.println(vbox2.getChildren());
        d2.show();
    }


    // public methods

    public Scene getScene() { return scene; }
}
