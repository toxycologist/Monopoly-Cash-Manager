package pl.kiepura.monopoly.players;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.repo.PlayerRepo;

import javax.transaction.Transactional;


@Route(value = "player-one")
@PageTitle("Monopoly - Gracz #1")
public class PlayerOne extends VerticalLayout {
    PlayerRepo playerRepo;
    Integer howMuch;


    public PlayerOne(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;

        Text textPlayerOne = new Text("Gracz " + playerRepo.getPlayerOne());
        Label labelCash = new Label("Posiadana gotówka: " + playerRepo.getPlayerOneCash());
        IntegerField integerFieldHowMuch = new IntegerField("Ile chcesz przelać?");


        Dialog dialogSendMoney = new Dialog();
        Span whoSendMoney = new Span("Komu chcesz wysłać pieniądze?");


        //gracz 2
        Button buttonPlayerTwo = new Button(playerRepo.getPlayerTwo());
        buttonPlayerTwo.addClickListener(ClickEvent -> {
            transferMoneyToPlayerTwo(howMuch, integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        // gracz 3
        Button buttonPlayerThree = new Button(playerRepo.getPlayerThree());
        buttonPlayerThree.addClickListener(ClickEvent -> {
            transferMoneyToPlayerThree(howMuch, integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        // gracz 4
        Button buttonPlayerFour = new Button(playerRepo.getPlayerFour());
        buttonPlayerFour.addClickListener(ClickEvent -> {
            transferMoneyToPlayerFour(howMuch, integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        // bank
        Button buttonBanker = new Button("[Bank]");
        buttonBanker.addClickListener(ClickEvent -> {
            transferMoneyToBank(integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
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
        //----------------------------------MIEJSCE BANKU----------------------------------
        //---------------------------------------------------------------------------------


        ProgressBar progressBarSplitUI = new ProgressBar();


        IntegerField integerFieldHowMuchFromBank = new IntegerField("Ile chcesz przelać? [BANK]");
        integerFieldHowMuchFromBank.setWidth("200px");

        Dialog dialogSendMoneyFromBank = new Dialog();
        Span whoSendMoneyFromBank = new Span("Komu chcesz wysłać pieniądze?  [BANK]");

        Button buttonBank = new Button("Wyślij jako bank!", new Icon(VaadinIcon.MONEY_EXCHANGE));
        buttonBank.setIconAfterText(true);
        buttonBank.addClickListener(ClickEvent -> dialogSendMoneyFromBank.open());


        //  gracz 1

        Button buttonPlayerOneBank = new Button(playerRepo.getPlayerOne());
        buttonPlayerOneBank.addClickListener(ClickEvent -> {
            transferMoneyFromBankToPlayerOne(howMuch, integerFieldHowMuchFromBank);
            integerFieldHowMuchFromBank.clear();
            dialogSendMoneyFromBank.close();
            UI.getCurrent().getPage().reload();
        });


        //  gracz 2

        Button buttonPlayerTwoBank = new Button(playerRepo.getPlayerTwo());
        buttonPlayerTwoBank.addClickListener(ClickEvent -> {
            transferMoneyFromBankToPlayerTwo(howMuch, integerFieldHowMuchFromBank);
            integerFieldHowMuchFromBank.clear();
            dialogSendMoneyFromBank.close();
            UI.getCurrent().getPage().reload();
        });


        //  gracz 3

        Button buttonPlayerThreeBank = new Button(playerRepo.getPlayerThree());
        buttonPlayerThreeBank.addClickListener(ClickEvent -> {
            transferMoneyFromBankToPlayerThree(howMuch, integerFieldHowMuchFromBank);
            integerFieldHowMuchFromBank.clear();
            dialogSendMoneyFromBank.close();
            UI.getCurrent().getPage().reload();
        });


        //  gracz 4

        Button buttonPlayerFourBank = new Button(playerRepo.getPlayerFour());
        buttonPlayerFourBank.addClickListener(ClickEvent -> {
            transferMoneyFromBankToPlayerFour(howMuch, integerFieldHowMuchFromBank);
            integerFieldHowMuchFromBank.clear();
            dialogSendMoneyFromBank.close();
            UI.getCurrent().getPage().reload();
        });

        ProgressBar progressBarSplitUIBank = new ProgressBar();
        progressBarSplitUIBank.setIndeterminate(true);


        dialogSendMoneyFromBank.add(whoSendMoneyFromBank, progressBarSplitUIBank, buttonPlayerOneBank, buttonPlayerTwoBank, buttonPlayerThreeBank, buttonPlayerFourBank);


        add(textPlayerOne, labelCash, integerFieldHowMuch, buttonSendMoney, buttonMainMenu, dialogSendMoney, progressBarSplitUI, integerFieldHowMuchFromBank, buttonBank);
    }

    @Transactional
    private void transferMoneyToBank(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(1L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);

        playerRepo.save(sourcePlayer);
    }

    @Transactional
    private void transferMoneyToPlayerTwo(Integer howMuch, IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(1L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(2L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }


    @Transactional
    private void transferMoneyToPlayerThree(Integer howMuch, IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(1L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(3L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }

    @Transactional
    private void transferMoneyToPlayerFour(Integer howMuch, IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(1L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(4L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }


    // STERFA BANKU


    @Transactional
    private void transferMoneyFromBankToPlayerOne(Integer howMuch, IntegerField integerFieldHowMuchFromBank) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuchFromBank.getValue()));
        Player targetPlayer = playerRepo.getById(1L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(targetPlayer);
    }


    @Transactional
    private void transferMoneyFromBankToPlayerTwo(Integer howMuch, IntegerField integerFieldHowMuchFromBank) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuchFromBank.getValue()));
        Player targetPlayer = playerRepo.getById(2L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(targetPlayer);
    }


    @Transactional
    private void transferMoneyFromBankToPlayerThree(Integer howMuch, IntegerField integerFieldHowMuchFromBank) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuchFromBank.getValue()));
        Player targetPlayer = playerRepo.getById(3L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(targetPlayer);
    }


    @Transactional
    private void transferMoneyFromBankToPlayerFour(Integer howMuch, IntegerField integerFieldHowMuchFromBank) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuchFromBank.getValue()));
        Player targetPlayer = playerRepo.getById(4L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(targetPlayer);
    }


}
