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
import com.vaadin.flow.router.Route;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.repo.PlayerRepo;

import javax.transaction.Transactional;


@Route("player-two")
public class PlayerTwo extends VerticalLayout {
    PlayerRepo playerRepo;
    long fromWho;
    long toWho;
    Integer howMuch;




    public PlayerTwo(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;

        Text textPlayerTwo = new Text("Gracz " + playerRepo.getPlayerTwo());
        Label labelCash = new Label("Posiadana gotówka: " + playerRepo.getPlayerTwoCash());

        IntegerField integerFieldHowMuch = new IntegerField("Ile chcesz przelać?");


        Dialog dialogSendMoney = new Dialog();
        Span whoSendMoney = new Span("Komu chcesz wysłać pieniądze?");


        //gracz 1
        Button buttonPlayerOne = new Button(playerRepo.getPlayerOne());
        buttonPlayerOne.addClickListener(ClickEvent -> {
            transferMoneyToPlayerOne(fromWho, toWho, howMuch, integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        // gracz 3
        Button buttonPlayerThree = new Button(playerRepo.getPlayerThree());
        buttonPlayerThree.addClickListener(ClickEvent -> {
            transferMoneyToPlayerThree(fromWho, toWho, howMuch, integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        // gracz 4
        Button buttonPlayerFour = new Button(playerRepo.getPlayerFour());
        buttonPlayerFour.addClickListener(ClickEvent -> {
            transferMoneyToPlayerFour(fromWho, toWho, howMuch, integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        // bank
        Button buttonBanker = new Button("[Bank]");
        buttonBanker.addClickListener(ClickEvent -> {
            transferMoneyToBank(fromWho, howMuch, integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        ProgressBar progressBarSplitUIDialog = new ProgressBar();
        progressBarSplitUIDialog.setIndeterminate(true);


        dialogSendMoney.add(whoSendMoney, progressBarSplitUIDialog, buttonPlayerOne, buttonPlayerThree, buttonPlayerFour, buttonBanker);


        Button buttonMainMenu = new Button("Menu główne", new Icon(VaadinIcon.MENU));
        buttonMainMenu.setIconAfterText(true);
        buttonMainMenu.addClickListener(e ->
                buttonMainMenu.getUI().ifPresent(ui ->
                        ui.navigate("main-menu")));


        Button buttonSendMoney = new Button("Wyślij pieniądze!", new Icon(VaadinIcon.MONEY_WITHDRAW));
        buttonSendMoney.setIconAfterText(true);
        buttonSendMoney.addClickListener(ClickEvent -> {
            dialogSendMoney.open();
        });


        add(textPlayerTwo, labelCash, integerFieldHowMuch, buttonSendMoney, buttonMainMenu, dialogSendMoney);
    }

    @Transactional
    private void transferMoneyToBank(long fromWho, Integer howMuch, IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(2L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);

        playerRepo.save(sourcePlayer);
    }

    @Transactional
    private void transferMoneyToPlayerOne(Long fromWho, Long toWho, Integer howMuch, IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(2L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(1L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }


    @Transactional
    private void transferMoneyToPlayerThree(Long fromWho, Long toWho, Integer howMuch, IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(2L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(3L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }

    @Transactional
    private void transferMoneyToPlayerFour(Long fromWho, Long toWho, Integer howMuch, IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(2L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(4L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }


}
