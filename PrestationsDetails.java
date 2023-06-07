package com.spaceraceinc.logicielchevalblancdemerde.views;

import com.spaceraceinc.logicielchevalblancdemerde.ui.StageTemplate;
import com.spaceraceinc.logicielchevalblancdemerde.ui.fields.CustomButton;
import com.spaceraceinc.logicielchevalblancdemerde.ui.fields.CustomDateField;
import com.spaceraceinc.logicielchevalblancdemerde.ui.fields.CustomTextField;
import com.spaceraceinc.logicielchevalblancdemerde.ui.typography.Title;
import com.spaceraceinc.logicielchevalblancdemerde.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class PrestationsDetails extends StageTemplate {
    private LocalDateTime dateEnregister;
    private Label numberChamber;
    private Label typePrestation;
    private Label libellePrestation;
    private Label montantHT;
    private CustomButton quit;
    private VBox root;
    private FlowPane bottomRoot;

    public PrestationsDetails (){
        super("Récapitulatif d'une préstation");
        this.setResizable(false);
        this.setMinWidth(350);
        this.setMinHeight(400);
        this.getIcons().add(new Image("file:resources/images/icon.png"));
    }


    @Override
    public Node renderTopContent() {

        return new Title("Récapitulatif d'une préstation");
    }

    @Override
    public Node renderMainContent() {
        VBox root = new VBox();
        Label numberChamber = new Label("Numéro de chambre: 2030");
        dateEnregister = LocalDateTime.now();
        Label typePrestation = new Label("Pressing");
        Label libellePrestation = new Label("Machine à laver à 60°");
        Label montantHT = new Label("20 €");
        Text textDate = new Text("Date d'enregistrement: " + Utils.formatDate(this.dateEnregister));

        root.getChildren().addAll(numberChamber, textDate, typePrestation, libellePrestation, montantHT);
        root.setSpacing(15);
        root.setPadding(new Insets((10)));

        return root;
    }

    @Override
    public Node renderBottomContent() {
        FlowPane bottomRoot = new FlowPane();
        quit = new CustomButton("Quitter");
        quit.setOnAction( e -> {
            this.close();
        });
        bottomRoot.getChildren().addAll(quit);
        bottomRoot.setAlignment(Pos.BOTTOM_RIGHT);
        return bottomRoot;
    }
}
