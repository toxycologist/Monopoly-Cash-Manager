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
import pl.kiepura.monopoly.entity.Transfer;
import pl.kiepura.monopoly.manager.PlayerManager;

import javax.transaction.Transactional;


@Route(value = "player-one")
@PageTitle("Monopoly - Gracz #1")
@RequiredArgsConstructor
public class PlayerOne extends VerticalLayout {

    private final PlayerManager playerManager;
    private int howMuch;
    Transfer transfer;

    @Autowired
    public void PlayerOneGUI() {

        Image imageMonopoly = new Image("https://i.pinimg.com/originals/b0/b2/48/b0b248f91cefb344ec92b272eadd860b.png", "Monopoly");
        imageMonopoly.setHeight("90px");
        imageMonopoly.setWidth("300px");

        Text textPlayerOne = new Text("Gracz " + playerManager.getPlayerOne());
        Label labelCash = new Label("Posiadana gotówka: " + playerManager.getPlayerOneCash());
        IntegerField integerFieldHowMuch = new IntegerField("Ile chcesz przelać?");
        integerFieldHowMuch.setMin(1);


        Dialog dialogSendMoney = new Dialog();
        Span whoSendMoney = new Span("Komu chcesz wysłać pieniądze?");


        // player 2
        Button buttonPlayerTwo = new Button(playerManager.getPlayerTwo());
        buttonPlayerTwo.addClickListener(ClickEvent -> {
            transferMoneyToPlayerTwo(integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();


        });

        // player 3
        Button buttonPlayerThree = new Button(playerManager.getPlayerThree());
        buttonPlayerThree.addClickListener(ClickEvent -> {
            transferMoneyToPlayerThree(integerFieldHowMuch);
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


        dialogSendMoney.add(whoSendMoney, progressBarSplitUIDialog, buttonPlayerTwo, buttonPlayerThree, buttonPlayerFour, buttonBanker);


        Button buttonMainMenu = new Button("Menu główne", new Icon(VaadinIcon.MENU));
        buttonMainMenu.setIconAfterText(true);
        buttonMainMenu.addClickListener(e ->
                buttonMainMenu.getUI().ifPresent(ui ->
                        ui.navigate("main-menu")));


        Button buttonSendMoney = new Button("Wyślij pieniądze!", new Icon(VaadinIcon.MONEY_WITHDRAW));
        buttonSendMoney.setIconAfterText(true);
        buttonSendMoney.addClickListener(ClickEvent -> dialogSendMoney.open());


        //---------------------------------------------------------------------------------
        //-------------------------------------BANK----------------------------------------
        //---------------------------------------------------------------------------------


        ProgressBar progressBarSplitUI = new ProgressBar();


        IntegerField integerFieldHowMuchFromBank = new IntegerField("Ile chcesz przelać? [BANK]");
        integerFieldHowMuchFromBank.setWidth("200px");
        integerFieldHowMuchFromBank.setMin(1);

        Dialog dialogSendMoneyFromBank = new Dialog();
        Span whoSendMoneyFromBank = new Span("Komu chcesz wysłać pieniądze?  [BANK]");

        Button buttonBank = new Button("Wyślij jako bank!", new Icon(VaadinIcon.MONEY_EXCHANGE));
        buttonBank.setIconAfterText(true);
        buttonBank.addClickListener(ClickEvent -> dialogSendMoneyFromBank.open());


        // player 1

        Button buttonPlayerOneBank = new Button(playerManager.getPlayerOne());
        buttonPlayerOneBank.addClickListener(ClickEvent -> {
            transferMoneyFromBankToPlayerOne(integerFieldHowMuchFromBank);
            integerFieldHowMuchFromBank.clear();
            dialogSendMoneyFromBank.close();
        });


        // player 2

        Button buttonPlayerTwoBank = new Button(playerManager.getPlayerTwo());
        buttonPlayerTwoBank.addClickListener(ClickEvent -> {
            transferMoneyFromBankToPlayerTwo(integerFieldHowMuchFromBank);
            integerFieldHowMuchFromBank.clear();
            dialogSendMoneyFromBank.close();

        });


        // player 3

        Button buttonPlayerThreeBank = new Button(playerManager.getPlayerThree());
        buttonPlayerThreeBank.addClickListener(ClickEvent -> {
            transferMoneyFromBankToPlayerThree(integerFieldHowMuchFromBank);
            integerFieldHowMuchFromBank.clear();
            dialogSendMoneyFromBank.close();

        });


        // player 4

        Button buttonPlayerFourBank = new Button(playerManager.getPlayerFour());
        buttonPlayerFourBank.addClickListener(ClickEvent -> {
            transferMoneyFromBankToPlayerFour(integerFieldHowMuchFromBank);
            integerFieldHowMuchFromBank.clear();
            dialogSendMoneyFromBank.close();

        });

        ProgressBar progressBarSplitUIBank = new ProgressBar();
        progressBarSplitUIBank.setIndeterminate(true);


        dialogSendMoneyFromBank.add(whoSendMoneyFromBank, progressBarSplitUIBank, buttonPlayerOneBank, buttonPlayerTwoBank, buttonPlayerThreeBank, buttonPlayerFourBank);

        add(imageMonopoly, textPlayerOne, labelCash, integerFieldHowMuch, buttonSendMoney, buttonMainMenu, dialogSendMoney, progressBarSplitUI, integerFieldHowMuchFromBank, buttonBank);

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
            Player sourcePlayer = playerManager.getById(1L);
            sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
            playerManager.save(sourcePlayer);
            UI.getCurrent().getPage().reload();
        }
    }

    @Transactional
    private void transferMoneyToPlayerTwo(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player sourcePlayer = playerManager.getById(1L);
            sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
            Player targetPlayer = playerManager.getById(2L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            // todo: ostatni przelew ficzer

            transfer.setHowMuchTransfer(howMuch);
            System.out.println(transfer.getHowMuchTransfer());

            playerManager.save(sourcePlayer);
            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


    @Transactional
    private void transferMoneyToPlayerThree(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {

            Player sourcePlayer = playerManager.getById(1L);
            sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
            Player targetPlayer = playerManager.getById(3L);
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
            Player sourcePlayer = playerManager.getById(1L);
            sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
            Player targetPlayer = playerManager.getById(4L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            playerManager.save(sourcePlayer);
            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


    // BANK TRANSACTIONS


    @Transactional
    private void transferMoneyFromBankToPlayerOne(IntegerField integerFieldHowMuchFromBank) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuchFromBank.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player targetPlayer = playerManager.getById(1L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


    @Transactional
    private void transferMoneyFromBankToPlayerTwo(IntegerField integerFieldHowMuchFromBank) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuchFromBank.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player targetPlayer = playerManager.getById(2L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


    @Transactional
    private void transferMoneyFromBankToPlayerThree(IntegerField integerFieldHowMuchFromBank) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuchFromBank.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player targetPlayer = playerManager.getById(3L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


    @Transactional
    private void transferMoneyFromBankToPlayerFour(IntegerField integerFieldHowMuchFromBank) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuchFromBank.getValue()));

        if (howMuch < 1) {
            dialogWarning();
        } else {
            Player targetPlayer = playerManager.getById(4L);
            targetPlayer.setCash(targetPlayer.getCash() + howMuch);

            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


}
