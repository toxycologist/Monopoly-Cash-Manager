package pl.kiepura.monopoly.gui;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.repo.PlayerRepo;

@Route("register")
@PageTitle("Monopoly - Rejestracja")
public class RegisterGUI extends VerticalLayout {

    PlayerRepo playerRepo;


    @Autowired
    public void init(PlayerRepo playerRepo, PasswordEncoder passwordEncoder) {
        this.playerRepo = playerRepo;

        addPlayer(passwordEncoder);
        mainMenu();


    }

    private void mainMenu() {
        ProgressBar progressBarSplitUI = new ProgressBar();
        Button button = new Button("Menu główne", new Icon(VaadinIcon.MENU));
        button.setIconAfterText(true);
        button.addClickListener(event -> UI.getCurrent().getPage().setLocation("main-menu"));

        add(button, progressBarSplitUI);
    }

    private void addPlayer(PasswordEncoder passwordEncoder) {
        ProgressBar progressBarSplitUI = new ProgressBar();
        TextField textFieldName = new TextField("Wpisz imię (login) :");
        TextField textFieldPassword = new TextField("Wpisz hasło:");
        IntegerField integerFieldStartCash = new IntegerField("Wpisz gotówkę początkową");
        integerFieldStartCash.setWidth("200px");
        ComboBox comboBoxRole = new ComboBox("Wybierz rolę gracza");
        comboBoxRole.setItems("Gracz 1 + Bank", "Gracz 2", "Gracz 3", "Gracz 4");


        Dialog dialogAddedPlayer = new Dialog();
        dialogAddedPlayer.setCloseOnEsc(false);
        dialogAddedPlayer.setCloseOnOutsideClick(true);
        Span messageOK = new Span("Dodano nowego gracza!  ");
        Button confirmButton = new Button("OK!", event -> dialogAddedPlayer.close());
        dialogAddedPlayer.add(messageOK, confirmButton);


        Button buttonAdd = new Button("Dodaj gracza!", new Icon(VaadinIcon.THUMBS_UP));
        buttonAdd.setIconAfterText(true);
        buttonAdd.addClickListener(ClickEvent -> {
            addPlayerMethod(textFieldName, integerFieldStartCash, textFieldPassword,
                    comboBoxRole, passwordEncoder);
            textFieldName.clear();
            textFieldPassword.clear();
            integerFieldStartCash.clear();
            comboBoxRole.clear();
            dialogAddedPlayer.open();
        });

        add(textFieldName, textFieldPassword, integerFieldStartCash, comboBoxRole, buttonAdd, progressBarSplitUI);

    }

    private void addPlayerMethod(TextField textFieldName, IntegerField integerFieldStartCash,
                                 TextField textFieldPassword, ComboBox comboBoxRole,
                                 PasswordEncoder passwordEncoder) {

        Player player = new Player();
        player.setUsername(String.valueOf(textFieldName.getValue()));
        player.setCash(Integer.parseInt(String.valueOf(integerFieldStartCash.getValue())));
        player.setPassword(passwordEncoder.encode(String.valueOf(textFieldPassword.getValue())));
        player.setRole("ROLE_" + comboBoxRole.getValue());
        playerRepo.save(player);
    }
}
