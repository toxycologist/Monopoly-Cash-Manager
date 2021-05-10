package pl.kiepura.monopoly.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kiepura.monopoly.entity.PlayerDto;
import pl.kiepura.monopoly.manager.PlayerManager;

import java.util.List;


@Route("main-menu")
@PageTitle("Monopoly - Menu Główne")
@RequiredArgsConstructor
public class MainMenuGUI extends VerticalLayout {

    private final PlayerManager playerManager;

    @Autowired
    public void MainMenuGUIs() {
        Image imageMonopoly = new Image("https://i.pinimg.com/originals/b0/b2/48/b0b248f91cefb344ec92b272eadd860b.png", "Monopoly");
        imageMonopoly.setHeight("90px");
        imageMonopoly.setWidth("300px");

        Button buttonSettings = new Button("Ustawienia gry", new Icon(VaadinIcon.COGS));
        buttonSettings.addClickListener(event -> UI.getCurrent().getPage().setLocation("settings"));

        Button buttonLogout = new Button("Wyloguj się!", new Icon(VaadinIcon.UNLINK));
        buttonLogout.addClickListener(event -> UI.getCurrent().getPage().setLocation("/logout"));

        ProgressBar progressBarSplit = new ProgressBar();


        List<PlayerDto> playerList = playerManager.getPlayers();
        Grid<PlayerDto> gridPlayers = new Grid<>(PlayerDto.class);
        gridPlayers.setItems(playerList);
        gridPlayers.removeAllColumns();
        gridPlayers.addColumn(PlayerDto::getUsername).setHeader("Imię").setSortable(true);
        gridPlayers.addColumn(PlayerDto::getCash).setHeader("Gotówka").setSortable(true);
        gridPlayers.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        gridPlayers.setHeightByRows(true);


        Button buttonPlayerOne = new Button("Panel gracza " + playerManager.getPlayerOne() + " [Bankier]", new Icon(VaadinIcon.USER));
        Button buttonPlayerTwo = new Button("Panel gracza " + playerManager.getPlayerTwo(), new Icon(VaadinIcon.USER));
        Button buttonPlayerThree = new Button("Panel gracza " + playerManager.getPlayerThree(), new Icon(VaadinIcon.USER));
        Button buttonPlayerFour = new Button("Panel gracza " + playerManager.getPlayerFour(), new Icon(VaadinIcon.USER));


        buttonPlayerOne.addClickListener(event -> UI.getCurrent().getPage().setLocation("player-one"));

        buttonPlayerTwo.addClickListener(event -> UI.getCurrent().getPage().setLocation("player-two"));

        buttonPlayerThree.addClickListener(event -> UI.getCurrent().getPage().setLocation("player-three"));

        buttonPlayerFour.addClickListener(event -> UI.getCurrent().getPage().setLocation("player-four"));

        add(imageMonopoly, buttonSettings, gridPlayers,
                buttonPlayerOne, buttonPlayerTwo, buttonPlayerThree, buttonPlayerFour, progressBarSplit, buttonLogout);
    }


}
