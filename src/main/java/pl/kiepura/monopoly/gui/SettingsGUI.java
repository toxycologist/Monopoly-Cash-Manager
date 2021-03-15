package pl.kiepura.monopoly.gui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.repo.PlayerRepo;


@Route("settings")
public class SettingsGUI extends VerticalLayout {

    PlayerRepo playerRepo;


    @Autowired
    public SettingsGUI(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;

        Text textAddPlayerSection = new Text("USTAWIENIA GRY");
        TextField textFieldName = new TextField("Wpisz imię gracza");
        Text textWarning = new Text("UWAGA! Pierwszy dodany gracz przejmie rolę bankiera!");
        IntegerField integerFieldStartCash = new IntegerField("Wpisz początkową ilość pieniędzy");
        integerFieldStartCash.setWidth("300px");

        Dialog dialogAddedPlayer = new Dialog();
        dialogAddedPlayer.setCloseOnEsc(false);
        dialogAddedPlayer.setCloseOnOutsideClick(true);
        Span messageOK = new Span("Dodano nowego gracza!   ");
        Button confirmButton = new Button("OK!", event -> {
            dialogAddedPlayer.close();
        });
        dialogAddedPlayer.add(messageOK, confirmButton);


        Button buttonSavePlayer = new Button("Dodaj gracza!", new Icon(VaadinIcon.THUMBS_UP));
        buttonSavePlayer.setIconAfterText(true);
        buttonSavePlayer.addClickListener(ClickEvent -> {
            addPlayer(textFieldName, integerFieldStartCash);
            textFieldName.clear();
            integerFieldStartCash.clear();
            dialogAddedPlayer.open();
        });

        Button buttonMainMenu = new Button("Menu główne", new Icon(VaadinIcon.MENU));
        buttonMainMenu.setIconAfterText(true);
        buttonMainMenu.addClickListener(e ->
                buttonMainMenu.getUI().ifPresent(ui ->
                        ui.navigate("main-menu")));


        ProgressBar progressBarSplitUIDialog = new ProgressBar();
        ProgressBar progressBarSplitUIDialog2 = new ProgressBar();
        ProgressBar progressBarSplitUIDialogConfirm = new ProgressBar();
        progressBarSplitUIDialogConfirm.setIndeterminate(true);

        Button buttonDeleteAllPlayers = new Button("Usuń wszystkich graczy", new Icon(VaadinIcon.ERASER));
        buttonDeleteAllPlayers.setIconAfterText(true);


        Button buttonConfirmDeleteAllPlayers = new Button("Tak, usuń wszyskich graczy!");
        Button buttonCancelDeleting = new Button("Anuluj!");


        Dialog dialogDeleteAllPlayerConfirmation = new Dialog();
        Span spanConfirm = new Span("Czy na pewno chcesz usunąć wszystkich graczy?");

        buttonDeleteAllPlayers.addClickListener(ClickEvent -> dialogDeleteAllPlayerConfirmation.open());

        dialogDeleteAllPlayerConfirmation.add(spanConfirm, progressBarSplitUIDialogConfirm, buttonConfirmDeleteAllPlayers, buttonCancelDeleting);

        buttonCancelDeleting.addClickListener(ClickEvent -> dialogDeleteAllPlayerConfirmation.close());

        buttonConfirmDeleteAllPlayers.addClickListener(ClickEvent -> {
            playerRepo.clearPlayers();
            dialogDeleteAllPlayerConfirmation.close();
        });


        add(textAddPlayerSection, textFieldName, textWarning, integerFieldStartCash,
                buttonSavePlayer, progressBarSplitUIDialog, buttonDeleteAllPlayers, progressBarSplitUIDialog2, dialogAddedPlayer,  buttonMainMenu);
    }


    public void addPlayer(TextField textFieldName, IntegerField integerFieldStartCash) {
        Player player = new Player();
        player.setName(String.valueOf(textFieldName.getValue()));
        player.setCash(Integer.parseInt(String.valueOf(integerFieldStartCash.getValue())));
        playerRepo.save(player);
    }

}
