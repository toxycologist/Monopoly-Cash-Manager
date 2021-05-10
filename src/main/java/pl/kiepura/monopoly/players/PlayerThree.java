package pl.kiepura.monopoly.players;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.manager.PlayerManager;

import javax.transaction.Transactional;


@Route("player-three")
@PageTitle("Monopoly - Gracz #3")
@RequiredArgsConstructor
public class PlayerThree extends VerticalLayout {

    private final PlayerManager playerManager;
    private int howMuch;

    @Autowired
    public void PlayerThreeGUI() {

        Image imageMonopoly = new Image("https://i.pinimg.com/originals/b0/b2/48/b0b248f91cefb344ec92b272eadd860b.png", "Monopoly");
        imageMonopoly.setHeight("90px");
        imageMonopoly.setWidth("300px");

        Text textPlayerThree = new Text("Gracz " + playerManager.getPlayerThree());
        Label labelCash = new Label("Posiadana gotówka: " + playerManager.getPlayerThreeCash());

        IntegerField integerFieldHowMuch = new IntegerField("Ile chcesz przelać?");
        integerFieldHowMuch.setMin(1);


        Dialog dialogSendMoney = new Dialog();
        Span whoSendMoney = new Span("Komu chcesz wysłać pieniądze?");


        // player 1
        Button buttonPlayerOne = new Button(playerManager.getPlayerOne());
        buttonPlayerOne.addClickListener(ClickEvent -> {
            transferMoneyToPlayerOne(integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
        });

        // player 2
        Button buttonPlayerTwo = new Button(playerManager.getPlayerTwo());
        buttonPlayerTwo.addClickListener(ClickEvent -> {
            transferMoneyToPlayerTwo(integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
        });

        // player 4
        Button buttonPlayerFour = new Button(playerManager.getPlayerFour());
        buttonPlayerFour.addClickListener(ClickEvent -> {
            transferMoneyToPlayerFour(integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
        });

        // bank
        Button buttonBanker = new Button("[Bank]");
        buttonBanker.addClickListener(ClickEvent -> {
            transferMoneyToBank(integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
        });

        ProgressBar progressBarSplitUIDialog = new ProgressBar();
        progressBarSplitUIDialog.setIndeterminate(true);


        dialogSendMoney.add(whoSendMoney, progressBarSplitUIDialog, buttonPlayerOne, buttonPlayerTwo, buttonPlayerFour, buttonBanker);


        Button buttonMainMenu = new Button("Menu główne", new Icon(VaadinIcon.MENU));
        buttonMainMenu.setIconAfterText(true);
        buttonMainMenu.addClickListener(e ->
                buttonMainMenu.getUI().ifPresent(ui ->
                        ui.navigate("main-menu")));


        Button buttonSendMoney = new Button("Wyślij pieniądze!", new Icon(VaadinIcon.MONEY_WITHDRAW));
        buttonSendMoney.setIconAfterText(true);
        buttonSendMoney.addClickListener(ClickEvent -> dialogSendMoney.open());


        add(imageMonopoly, textPlayerThree, labelCash, integerFieldHowMuch, buttonSendMoney, buttonMainMenu, dialogSendMoney);
    }

    private void dialogWarning() {
        Dialog dialogWarning = new Dialog();
        Text textWarning = new Text("Nie możesz wysłać przelewu mniejszego niż 0!!");
        dialogWarning.add(textWarning);
        add(dialogWarning);
        dialogWarning.open();
    }

    // PLAYER TRANSACTIONS

    @Transactional
    private void transferMoneyToBank(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player sourcePlayer = playerManager.getById(3L);
            sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);

            playerManager.save(sourcePlayer);
            UI.getCurrent().getPage().reload();
        }
    }

    @Transactional
    private void transferMoneyToPlayerOne(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player sourcePlayer = playerManager.getById(3L);
            sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
            Player targetPlayer = playerManager.getById(1L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            playerManager.save(sourcePlayer);
            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


    @Transactional
    private void transferMoneyToPlayerTwo(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player sourcePlayer = playerManager.getById(3L);
            sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
            Player targetPlayer = playerManager.getById(2L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            playerManager.save(sourcePlayer);
            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }

    @Transactional
    private void transferMoneyToPlayerFour(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player sourcePlayer = playerManager.getById(3L);
            sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
            Player targetPlayer = playerManager.getById(4L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            playerManager.save(sourcePlayer);
            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


}
