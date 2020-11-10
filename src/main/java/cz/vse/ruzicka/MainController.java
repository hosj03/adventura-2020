package cz.vse.ruzicka;

import cz.vse.ruzicka.logika.Vec;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import cz.vse.ruzicka.logika.IHra;
import cz.vse.ruzicka.logika.Prostor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.security.Key;
import java.util.Collection;

public class MainController {


    private IHra hra;

    public Label locationName;
    public Label locationDescription;
    public VBox vychody;
    public VBox veci;
    public VBox batoh;
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
        updateBatoh();
    }

    private void updateBatoh() {
        Collection<Vec> batohList = hra.getBatoh().getSeznamVeci().values();
        batoh.getChildren().clear();

        projdiList(batoh, batohList, "poloz");
    }



    private void updateItems() {
        Collection<Vec> veciList = getAktualniProstor().getSeznamVeci().values();
        veci.getChildren().clear();

        projdiList(veci, veciList, "seber");
    }

    private void updateExits() {
        Collection<Prostor> vychodyList = getAktualniProstor().getVychody();
        vychody.getChildren().clear();

        for (Prostor prostor : vychodyList) {
            String nazevVychodu = prostor.getNazev();
            Label labelNazevVychodu = new Label(nazevVychodu);
            onClickLabel(labelNazevVychodu, "jdi");
            vychody.getChildren().add(labelNazevVychodu);
        }

    }

    private Prostor getAktualniProstor() {
        return hra.getHerniPlan().getAktualniProstor();
    }


    private void projdiList(VBox box,Collection<Vec> list, String prikaz) {
        for (Vec vec : list) {
            String nazevVeci = vec.getJmeno();
            Label veciLabel = new Label(nazevVeci);

            if(vec.jePrenositelna()){
                onClickLabel(veciLabel, prikaz);
            } else {
                veciLabel.setTooltip(new Tooltip("nelze přenést"));
                setLabelImg(veciLabel);
            }

            box.getChildren().add(veciLabel);
        }
    }

    private void onClickLabel(Label label, String prikaz) {
        setLabelImg(label);
        label.setCursor(Cursor.HAND);
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                executeCommand(prikaz, label.getText());
            }
        });
    }

    private void executeCommand(String prikaz, String kontent) {
        String vysledek = hra.zpracujPrikaz(prikaz + " " + kontent);
        textOutput.appendText(prikaz + " " + kontent + "\n\n");
        textOutput.appendText(vysledek + "\n\n");
        update();
    }

    private void setLabelImg(Label label) {
        Image img = new Image(label.getText() + ".jpg");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setPreserveRatio(true);
        label.setGraphic(view);
    }

    public void onInputKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            String[] parts = textInput.getText().split(" ");
            executeCommand(parts[0], parts[1]);
            textInput.clear();
        }
    }
}
