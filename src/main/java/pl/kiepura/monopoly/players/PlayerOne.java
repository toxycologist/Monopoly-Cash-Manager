package pl.kiepura.monopoly.players;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
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
import pl.kiepura.monopoly.entity.TransactionHistory;
import pl.kiepura.monopoly.entity.TransactionHistoryDto;
import pl.kiepura.monopoly.manager.PlayerManager;
import pl.kiepura.monopoly.manager.TransactionHistoryManager;

import javax.transaction.Transactional;
import java.util.List;


@Route(value = "player-one")
@PageTitle("Monopoly - Gracz #1")
@RequiredArgsConstructor
public class PlayerOne extends VerticalLayout {

    private final PlayerManager playerManager;
    private final TransactionHistoryManager transactionHistoryManager;
    TransactionHistory transactionHistory = new TransactionHistory();
    private int howMuch;

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

        transactionHistory();
    }

    private void dialogWarning() {
        Dialog dialogWarning = new Dialog();
        Text textWarning = new Text("Nie możesz wysłać przelewu mniejszego niż 0!!");
        dialogWarning.add(textWarning);
        add(dialogWarning);
        dialogWarning.open();
    }


    private void transactionHistory() {
        List<TransactionHistoryDto> transactionHistoryDtoList = transactionHistoryManager.getTransactions();
        Grid<TransactionHistoryDto> gridHistory = new Grid<>(TransactionHistoryDto.class);
        gridHistory.setItems(transactionHistoryDtoList);
        gridHistory.removeAllColumns();
        gridHistory.setWidth("350px");
        gridHistory.setHeight("300px");
        gridHistory.setVisible(true);
        gridHistory.addColumn(TransactionHistoryDto::getId).setHeader("#").setAutoWidth(true);
        gridHistory.addColumn(TransactionHistoryDto::getSource).setHeader("Kto?").setAutoWidth(true);
        gridHistory.addColumn(TransactionHistoryDto::getAmount).setHeader("Ile?").setAutoWidth(true);
        gridHistory.addColumn(TransactionHistoryDto::getTarget).setHeader("Komu?").setAutoWidth(true);
        gridHistory.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        gridHistory.setHeightByRows(true);


        Button buttonShowGrid = new Button("Pokaż/ukryj historię transakcji.", new Icon(VaadinIcon.SCALE));
        buttonShowGrid.setWidth("290px");
        buttonShowGrid.setIconAfterText(true);
        buttonShowGrid.addClickListener(ClickEvent ->
                gridHistory.setVisible(!gridHistory.isVisible()));


        add(buttonShowGrid, gridHistory);
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

            transactionHistory.setSource(playerManager.getPlayerOne());
            transactionHistory.setTarget("Bank");
            transactionHistory.setAmount(howMuch);
            transactionHistoryManager.save(transactionHistory);

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

            transactionHistory.setSource(playerManager.getPlayerOne());
            transactionHistory.setTarget(playerManager.getPlayerTwo());
            transactionHistory.setAmount(howMuch);
            transactionHistoryManager.save(transactionHistory);

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

            transactionHistory.setSource(playerManager.getPlayerOne());
            transactionHistory.setTarget(playerManager.getPlayerThree());
            transactionHistory.setAmount(howMuch);
            transactionHistoryManager.save(transactionHistory);

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

            transactionHistory.setSource(playerManager.getPlayerOne());
            transactionHistory.setTarget(playerManager.getPlayerFour());
            transactionHistory.setAmount(howMuch);
            transactionHistoryManager.save(transactionHistory);

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

            transactionHistory.setSource("Bank");
            transactionHistory.setTarget(playerManager.getPlayerOne());
            transactionHistory.setAmount(howMuch);
            transactionHistoryManager.save(transactionHistory);

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

            transactionHistory.setSource("Bank");
            transactionHistory.setTarget(playerManager.getPlayerTwo());
            transactionHistory.setAmount(howMuch);
            transactionHistoryManager.save(transactionHistory);


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

            transactionHistory.setSource("Bank");
            transactionHistory.setTarget(playerManager.getPlayerThree());
            transactionHistory.setAmount(howMuch);
            transactionHistoryManager.save(transactionHistory);


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

            transactionHistory.setSource("Bank");
            transactionHistory.setTarget(playerManager.getPlayerFour());
            transactionHistory.setAmount(howMuch);
            transactionHistoryManager.save(transactionHistory);


            playerManager.save(targetPlayer);
            UI.getCurrent().getPage().reload();
        }
    }


}
