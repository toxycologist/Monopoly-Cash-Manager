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
import lombok.Getter;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.repo.PlayerRepo;

import javax.transaction.Transactional;

@Getter
@Route("player-four")
@PageTitle("Monopoly - Gracz #4")
public class PlayerFour extends VerticalLayout {
    PlayerRepo playerRepo;
    private int howMuch;





    public PlayerFour(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;

        Text textPlayerFour = new Text("Gracz " + playerRepo.getPlayerFour());
        Label labelCash = new Label("Posiadana gotówka: " + playerRepo.getPlayerFourCash());

        IntegerField integerFieldHowMuch = new IntegerField("Ile chcesz przelać?");


        Dialog dialogSendMoney = new Dialog();
        Span whoSendMoney = new Span("Komu chcesz wysłać pieniądze?");


        //gracz 1
        Button buttonPlayerOne = new Button(playerRepo.getPlayerOne());
        buttonPlayerOne.addClickListener(ClickEvent -> {
            transferMoneyToPlayerOne(integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        // gracz 2
        Button buttonPlayerTwo = new Button(playerRepo.getPlayerTwo());
        buttonPlayerTwo.addClickListener(ClickEvent -> {
            transferMoneyToPlayerTwo(integerFieldHowMuch);
            integerFieldHowMuch.clear();
            dialogSendMoney.close();
            UI.getCurrent().getPage().reload();
        });

        // gracz 3
        Button buttonPlayerThree = new Button(playerRepo.getPlayerThree());
        buttonPlayerThree.addClickListener(ClickEvent -> {
            transferMoneyToPlayerThree(integerFieldHowMuch);
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

        dialogSendMoney.add(whoSendMoney, progressBarSplitUIDialog, buttonPlayerOne, buttonPlayerTwo, buttonPlayerThree, buttonBanker);


        Button buttonMainMenu = new Button("Menu główne", new Icon(VaadinIcon.MENU));
        buttonMainMenu.setIconAfterText(true);
        buttonMainMenu.addClickListener(e ->
                buttonMainMenu.getUI().ifPresent(ui ->
                        ui.navigate("main-menu")));


        Button buttonSendMoney = new Button("Wyślij pieniądze!", new Icon(VaadinIcon.MONEY_WITHDRAW));
        buttonSendMoney.setIconAfterText(true);
        buttonSendMoney.addClickListener(ClickEvent -> dialogSendMoney.open());


        add(textPlayerFour, labelCash, integerFieldHowMuch, buttonSendMoney, buttonMainMenu, dialogSendMoney);
    }

    @Transactional
    private void transferMoneyToBank(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(4L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);

        playerRepo.save(sourcePlayer);
    }

    @Transactional
    private void transferMoneyToPlayerOne(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(4L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(1L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }


    @Transactional
    private void transferMoneyToPlayerTwo(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(4L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(2L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }

    @Transactional
    private void transferMoneyToPlayerThree(IntegerField integerFieldHowMuch) {
        howMuch = Integer.parseInt(String.valueOf(integerFieldHowMuch.getValue()));
        Player sourcePlayer = playerRepo.getById(4L);
        sourcePlayer.setCash(sourcePlayer.getCash() - howMuch);
        Player targetPlayer = playerRepo.getById(3L);
        targetPlayer.setCash(targetPlayer.getCash() + howMuch);

        playerRepo.save(sourcePlayer);
        playerRepo.save(targetPlayer);
    }


}
