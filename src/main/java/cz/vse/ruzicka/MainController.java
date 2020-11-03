package cz.vse.ruzicka;

import cz.vse.ruzicka.logika.Vec;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import cz.vse.ruzicka.logika.IHra;
import cz.vse.ruzicka.logika.Prostor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Collection;

public class MainController {


    private IHra hra;

    public Label locationName;
    public Label locationDescription;
    public VBox vychody;
    public VBox veci;
    public TextArea textOutput;
    public TextField textInput;

    public void init(IHra hra) {
        this.hra = hra;
        update();
    }

    private void update() {
        String location = getAktualniProstor().getNazev();
        locationName.setText(location);
        String description = getAktualniProstor().getPopis();
        locationDescription.setText(description);

        updateExits();
        updateItems();
    }

    private void updateItems() {
        Collection<Vec> veciList = getAktualniProstor().getSeznamVeci().values();
        veci.getChildren().clear();

        for (Vec vec : veciList) {
            String nazevVeci = vec.getJmeno();
            Label veciLabel = new Label(nazevVeci);
            if(vec.jePrenositelna()){
                veciLabel.setCursor(Cursor.HAND);
                veciLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String vysledek = hra.zpracujPrikaz("seber " + nazevVeci);
                        System.out.println(vysledek);
                        textOutput.appendText(vysledek+"\n\n");
                        update();
                    }
                });
            } else {
//                toDO vec neni prenositelna
            }

            veci.getChildren().add(veciLabel);
        }

    }

    private void updateExits() {
        Collection<Prostor> vychodyList = getAktualniProstor().getVychody();
        vychody.getChildren().clear();

        for (Prostor prostor : vychodyList) {
            String nazevVychodu = prostor.getNazev();
            Label labelNazevVychodu = new Label(nazevVychodu);
            labelNazevVychodu.setCursor(Cursor.HAND);
            labelNazevVychodu.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String vysledek = hra.zpracujPrikaz("jdi " + nazevVychodu);
                    System.out.println(vysledek);
                    textOutput.appendText(vysledek+"\n\n");
                    update();
                }
            });
            vychody.getChildren().add(labelNazevVychodu);
        }

    }

    private Prostor getAktualniProstor() {
        return hra.getHerniPlan().getAktualniProstor();
    }


//        labelC.setOnMouseClicked();

}
