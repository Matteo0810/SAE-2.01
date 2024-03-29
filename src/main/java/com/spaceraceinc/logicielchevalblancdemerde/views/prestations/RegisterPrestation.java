package com.spaceraceinc.logicielchevalblancdemerde.views.prestations;

import com.spaceraceinc.logicielchevalblancdemerde.MainMenu;
import com.spaceraceinc.logicielchevalblancdemerde.modules.CustomerPrestation;
import com.spaceraceinc.logicielchevalblancdemerde.ui.fields.CustomPriceField;
import com.spaceraceinc.logicielchevalblancdemerde.utils.Utils;
import com.spaceraceinc.logicielchevalblancdemerde.enums.ServiceType;
import com.spaceraceinc.logicielchevalblancdemerde.ui.FormActions;
import com.spaceraceinc.logicielchevalblancdemerde.ui.StageTemplate;
import com.spaceraceinc.logicielchevalblancdemerde.ui.fields.CustomQuantityField;
import com.spaceraceinc.logicielchevalblancdemerde.ui.fields.CustomRadioList;
import com.spaceraceinc.logicielchevalblancdemerde.ui.fields.CustomTextField;
import com.spaceraceinc.logicielchevalblancdemerde.ui.typography.Title;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisterPrestation extends StageTemplate {

    private CustomQuantityField chamberNumber;
    private CustomRadioList list;
    private CustomTextField label;
    private CustomPriceField amountWF;

    private BooleanProperty hasSelectedPressing;

    public RegisterPrestation() {
        super("Enregistrer des prestations à l'accueil", 350, 450);
        this.setResizable(false);
    }

    private void loadServiceTypes() {
        ServiceType[] serviceTypes = ServiceType.values();
        for (int i = 0; i < serviceTypes.length; i++) {
            ServiceType type = serviceTypes[i];
            list.addButton(type.getName(), i == 0);
        }

        list.selectedToggleProperty().addListener((props, oldValue, newValue) -> {
            RadioButton button = (RadioButton) this.list.getSelectedToggle();
            this.hasSelectedPressing.set(button.getText().equals(ServiceType.PRESSING.getName()));
        });
    }

    private void registerService(ActionEvent action) {
        final RadioButton selectedToggle = (RadioButton) this.list.getSelectedToggle();

        final int chamberNumber = this.chamberNumber.getField().getValue();
        double amountWF = 0;
        try {
            amountWF = this.amountWF.getField().getValue();
        } catch(NullPointerException ignored) {}

        final String label = this.label.getField().getText();
        final String choice = selectedToggle.getText();

        final ArrayList<String> labels = new ArrayList<>(List.of("numéro de chambre", "montant HT"));
        final ArrayList<Boolean> values = new ArrayList<>(List.of(Utils.isIntFieldValid(chamberNumber), Utils.isDoubleFieldValid(amountWF)));
        final boolean hasPressing = choice.equals(ServiceType.PRESSING.getName());


        if(hasPressing) {
            labels.add("libellé");
            values.add(Utils.isStringFieldValid(label));
        }


        final Map<String, Boolean> invalidFields = Utils.getInvalidFieldsFrom(labels, values);
        if(invalidFields != null && invalidFields.size() > 0) {
            this.openAlert(Alert.AlertType.ERROR, "Erreur: les champs suivants ne sont pas valides: " + String.join(", ", invalidFields.keySet()));
            return;
        }

        this.close();
        this.openAlert(Alert.AlertType.INFORMATION, "La prestation a été ajouté.");

        CustomerPrestation customerServices = new CustomerPrestation(chamberNumber, choice, amountWF);
        if(hasPressing)
            customerServices.setLabel(label);
        MainMenu.CUSTOMER_PRESTATIONS_LIST.add(customerServices);
    }

    private FlowPane renderFields() {
        final FlowPane group = new FlowPane(Orientation.VERTICAL);
        final ObservableList<Node> children = group.getChildren();

        this.chamberNumber = new CustomQuantityField("Numéro de chambre", "Format: NuméroEtageXX (ex: 201)");
        this.label = new CustomTextField("Libellé");
        this.amountWF = new CustomPriceField("Montant HT");

        this.hasSelectedPressing = new SimpleBooleanProperty(false);

        this.list = new CustomRadioList();
        this.loadServiceTypes();

        children.add(chamberNumber);
        children.addAll(this.list.getButtons());
        this.hasSelectedPressing.addListener((props, oldValue, newValue) -> {
            if(newValue)
                children.add(this.label);
            else
                children.remove(this.label);
        });
        children.add(amountWF);

        group.setVgap(10);
        group.setAlignment(Pos.CENTER);
        return group;
    }

    private FormActions renderActions() {
        final FormActions formActions = new FormActions("Enregistrer", "Quitter");
        formActions.setConfirmCallback(this::registerService);
        formActions.setCancelCallback(event -> this.close());
        return formActions;
    }

    @Override
    public Node renderTopContent() {
        return new Title("Enregistrer un service à l'accueil");
    }

    @Override
    public Node renderMainContent() {
        return this.renderFields();
    }

    @Override
    public Node renderBottomContent() {
        return this.renderActions();
    }

}
