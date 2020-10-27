package cz.vse.ruzicka;

import javafx.scene.control.*;
import cz.vse.ruzicka.logika.IHra;
import cz.vse.ruzicka.logika.Prostor;

public class MainController {

    private IHra hra;
    public Label locationName;
    public Label locationDescription;

    public void init(IHra hra) {
        this.hra = hra;
        update();
    }

    private void update() {
        String location = getAktualniProstor().getNazev();
        locationName.setText(location);
        String description = getAktualniProstor().getPopis();
        locationDescription.setText(description);
    }

    private Prostor getAktualniProstor() {
        return hra.getHerniPlan().getAktualniProstor();
    }


//        labelC.setOnMouseClicked();

}
