package com.example.logicielchevalblancdemerde;

import com.example.logicielchevalblancdemerde.enums.TitleType;
import com.example.logicielchevalblancdemerde.views.RegisterBreakfast;
import com.example.logicielchevalblancdemerde.views.RegisterConsummation;
import com.example.logicielchevalblancdemerde.ui.StageTemplate;
import com.example.logicielchevalblancdemerde.ui.fields.CustomButton;
import com.example.logicielchevalblancdemerde.ui.title.Title;
import com.example.logicielchevalblancdemerde.views.RegisterService;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class MainMenu extends StageTemplate {

    public MainMenu() {
        super("Menu de navigation", 400, 300);
    }

    @Override
    public Node renderTopContent() {
        return new Title("Menu de navigation");
    }

    @Override
    public Node renderBottomContent() {
        return new Title("© ChevalBlanc 2023", TitleType.H4);
    }

    @Override
    public FlowPane renderMainContent() {
        final FlowPane group = new FlowPane(Orientation.VERTICAL);
        final CustomButton feature1 = new CustomButton("Enregistrer des consommations");
        final CustomButton feature2 = new CustomButton("Enregistrer des petits-déjeuners");
        final CustomButton feature3 = new CustomButton("Enregistrer des prestations à l'accueil");
        final CustomButton exit = new CustomButton("Quitter le menu");

        exit.setOnAction(event -> this.close());

        feature1.setOnAction(event -> this.openModal(new RegisterConsummation()));
        feature2.setOnAction(event -> this.openModal(new RegisterBreakfast()));
        feature3.setOnAction(event -> this.openModal(new RegisterService()));

        group.getChildren().addAll(feature1, feature2, feature3, exit);

        return group;
    }

}
