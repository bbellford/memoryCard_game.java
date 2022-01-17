package com.example.memorycard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class memoryCard_game extends Application {

    private static final int NUM_OF_PAIRS = 8;
    private static final int NUM_OF_ROW = 4;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(500, 500);

        List<Tile> tiles = new ArrayList<>();
        char c = 'A';
        for (int i = 0; i < NUM_OF_PAIRS; i++) {
            tiles.add(new Tile(String.valueOf(c)));
            tiles.add(new Tile(String.valueOf(c)));  //2 times 8 because of pairs + increase the char
            c++;
        }
        Collections.shuffle(tiles);

        for (int i = 0; i<tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setTranslateX(50 * (i % NUM_OF_ROW));  //axis
            tile.setTranslateY(50 * (i / NUM_OF_ROW));
            root.getChildren().add(tile);
        }

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(memoryCard_game.class.getResource("game-view.fxml"));
        stage.setScene(new Scene(createContent()));
        stage.setTitle("Memory Game");
        stage.show();
    }

    private class Tile extends StackPane {

        public Tile(String value) {
            Rectangle border = new Rectangle(50, 50);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            Text text = new Text(value);
            text.setFont(Font.font(30));
            setAlignment(Pos.CENTER);

            getChildren().addAll(border);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}