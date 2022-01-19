package com.example.memorycard;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class memoryCard_game extends Application {

    public static final int NUM_OF_PAIRS = 8;
    public static final int NUM_PER_ROW = 4;
    public static int numbOfAttempts = 40;
    public static int numbOfCards = 16;


    public Tile selected = null;
    public int clickCount = 2;


    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(600, 600);

        Image[] squirrel = new Image[2];
        Image[] octopus = new Image[2];
        Image[] pig = new Image[2];
        Image[] sloth = new Image[2];
        Image[] narwhal = new Image[2];
        Image[] cat = new Image[2];
        Image[] dog = new Image[2];
        Image[] bunny = new Image[2];

        String squirrelRes =  memoryCard_game.class.getResource("squirrel.png").toString();
        String octopusRes =  memoryCard_game.class.getResource("octopus.png").toString();
        String pigRes =  memoryCard_game.class.getResource("pig.png").toString();
        String slothRes =  memoryCard_game.class.getResource("sloth.jpg").toString();
        String narwhalRes =  memoryCard_game.class.getResource("narwhal.png").toString();
        String catRes =  memoryCard_game.class.getResource("cat.jpg").toString();
        String dogRes =  memoryCard_game.class.getResource("dog.png").toString();
        String bunnyRes =  memoryCard_game.class.getResource("bunny.jpg").toString();

        for (int i = 0; i < 2; i++){
            squirrel [i]= new Image(squirrelRes);
            octopus [i] = new Image (octopusRes);
            pig [i] = new Image (pigRes);
            sloth [i] = new Image (slothRes);
            narwhal [i] = new Image (narwhalRes);
            cat [i] = new Image (catRes);
            dog [i] = new Image (dogRes);
            bunny [i] = new Image (bunnyRes);
        }

        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            ImageView first = new ImageView(squirrel[i]);
            ImageView second = new ImageView(octopus[i]);
            ImageView third = new ImageView(pig[i]);
            ImageView fourth = new ImageView(sloth[i]);
            ImageView fifth = new ImageView(narwhal[i]);
            ImageView sixth = new ImageView(cat[i]);
            ImageView seventh = new ImageView(dog[i]);
            ImageView eight = new ImageView(bunny[i]);

            tiles.add(new Tile(first));
            tiles.add(new Tile(second));
            tiles.add(new Tile (third));
            tiles.add(new Tile (fourth));
            tiles.add(new Tile (fifth));
            tiles.add(new Tile (sixth));
            tiles.add(new Tile (seventh));
            tiles.add(new Tile (eight));
        }

        Collections.shuffle(tiles);

        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setTranslateX(150 * (i % NUM_PER_ROW));
            tile.setTranslateY(150 * (i / NUM_PER_ROW));
            root.getChildren().add(tile);

        }

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private class Tile extends StackPane {
        private ImageView image;

        public Tile(ImageView image) {
            this.image = image;

            Rectangle border = new Rectangle(150, 150);
            border.setFill(Color.rgb(199,223,250));
            border.setStroke(Color.BLACK);

            image.setFitHeight(140);
            image.setFitWidth(140);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, image);

            setOnMouseClicked(this::handleMouseClick);

            close();
        }

        public void handleMouseClick(MouseEvent event) {

            if(isOpen()|| clickCount == 0 || !limit()){
                return;
            }

            if (isOpen() || clickCount == 0)
                return;

            clickCount--;

            if (selected == null) {
                selected = this;
                open(() -> {});
            }
            else {
                open(() -> {
                    if (!hasSameValue(selected)) {
                        selected.close();
                        this.close();
                    }
                    selected = null;
                    clickCount = 2;

                });
            }
        }

        public boolean isOpen() {
            return image.getOpacity() == 1;
        }

        public void open(Runnable action) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), image);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();
        }

        public void close() {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), image);
            ft.setToValue(0);
            ft.play();
        }

        public boolean hasSameValue(Tile other) {
            return image.getImage().getUrl().equals((other.image.getImage().getUrl()));
        }



        public boolean limit(){
            numbOfAttempts--;

            if (numbOfAttempts==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("You lost the game");

                alert.showAndWait();
                System.exit(5);
            }
            else if (numbOfCards==0){
                System.out.println(numbOfCards);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("You win!");

                alert.showAndWait();
                System.exit(5);
            }
            return true;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}