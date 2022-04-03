package com.example.memorygame01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //haendisch eingefuegt, weil ich nicht weiss, wie sonst
    private String[] emoticons = {
            "😎", "😂", "😊", "🤣", "❤", "👌",
            "😒", "👍", "🎉", "😆", "💣", "💍",
            "🌹", "🎂", "🎃", "🤢", "🎶", "👀",
            "✨", "🎁", "🤩", "🤡", "🤠", "👽",
            "💩", "🤓", "🦄", "🧠", "🎱", "👌",
            "🎈", "💰"
    };
    //max 64, auf Emulator 40 darstellbar
    private int numberOfButtons = 36;
    //Liste ueber alle Zeilen
    private ArrayList<TableRow> tableRowArrayList;
    private TableLayout tableLayout;
    //Liste ueber alle Buttons
    private ArrayList<Button> buttonArrayList;
    //Zuordnung Button Id (Schluessel) - Emoji als String (Value)
    //man koennte stattdessen auch eine Klasse von "Button" ableiten,
    //zb "Memory-Button", die ein weiteres Feld hat: String - Emoji
    private HashMap<Integer, String> mapButtonIdText = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        createLayout();
        setRandomPairs();
    }

    /**
     * generiere das Layout und instanziiere die Listen
     */
    private void createLayout() {
        tableLayout = new TableLayout(getApplicationContext());
        tableRowArrayList = new ArrayList<>();
        buttonArrayList = new ArrayList<Button>();
        setTitle("Memory Spiel");
        fillRows(numberOfButtons);
        setContentView(tableLayout);
    }

    /**
     * generiere Zeilen (und Buttons) und fuege sie jeweils ihren Listen (und ihren Zeilen) hinzu.
     * Falls es kompliziert aussieht: es geht auch einfacher, wenn man es fuer eine vorher festgelegte Zahl an Buttons macht.
     * Die Methode funktioniert fuer soviele Button-Paare, wie ich Emojis hinzufuege
     * @param numberOfButtons Anzahl der zu generierenden Buttons. Davon abhaengig werden pro vier Buttons aufgerundet jeweils eine Zeile generiert.
     */
    private void fillRows(int numberOfButtons) {
        for (int i = 0; i < (int) Math.ceil(numberOfButtons/4.0); i++) {
            tableRowArrayList.add(new TableRow(getApplicationContext()));
            tableRowArrayList.get(i).setMinimumHeight(100);
        }
        for (int i = 0; i < numberOfButtons; i++) {
            Button button = new Button(getApplicationContext());
            //nummeriere Button Ids zur vereinfachten Bedienung von 0 ausgehend
            button.setId(i);
            //optische Spielerei
            button.setTextSize(32);
            button.setMinHeight(200);
            button.setHeight(200);
            buttonArrayList.add(button);
            //binde buttonClicked() an Button-Clickevent
            buttonArrayList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClicked(view);
                }
            });
            //fuege Buttons so zu: 0-3 Zeile null, 4-9 Zeile eins usw.
            tableRowArrayList.get((int)Math.floor(i/4.0)).addView(buttonArrayList.get(i));
        }
        for (int i = 0; i < (int) Math.ceil(numberOfButtons/4.0); i++) {
            tableRowArrayList.get(i).setGravity(Gravity.CENTER_HORIZONTAL);
            //fuege Zeilen jeweils dem table layout hinzu
            tableLayout.addView(tableRowArrayList.get(i));
        }
    }

    /**
     * randomisiere die Button-Liste; ordne dann jeweils zwei Buttons das gleiche Emoji zu (in mapButtonIdText).
     */
    private void setRandomPairs() {
        Collections.shuffle(buttonArrayList);
        //tue folgendes so oft, wie es Button-Paare gibt
        for (int i = 0; i < numberOfButtons/2; i++) {
                //paarweise Zuordnung des gleichen Emojis
                //zB bei 20 Buttons: weise 0-ten und 10-ten, 1-ten und 11-ten,... 9-ten und 19-ten
                //Button das gleiche Emoji zu
                mapButtonIdText.put(buttonArrayList.get(i).getId(), emoticons[i]);
                mapButtonIdText.put(buttonArrayList.get(i+numberOfButtons/2).getId(), emoticons[i]);
                //geht auch einfacher, Schnickschnack. ZB mit innerer Schleife
        }

    }

    /**
     * Methode zum umschalten des Button-Inhaltes
     * @param view geklickter Button
     */
    private void buttonClicked(View view) {
        Button button = (Button) view;
        //Fall: Button leer
        if (button.getText().equals("")){
            //lese aus Zuordnung das Value, dass dem Key des ausgewaehlten Buttons zugeordnet wurde
            button.setText(mapButtonIdText.get(button.getId()));
        }
        //Fall: Button gefuellt
        else {
            //leere Button
            button.setText("");
        }
    }
}