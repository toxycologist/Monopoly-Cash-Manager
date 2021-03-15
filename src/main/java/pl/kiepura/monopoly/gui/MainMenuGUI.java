package pl.kiepura.monopoly.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kiepura.monopoly.entity.PlayerDto;
import pl.kiepura.monopoly.repo.PlayerRepo;

import java.util.List;


@Route("main-menu")
public class MainMenuGUI extends VerticalLayout {

    private final PlayerRepo playerRepo;


    public MainMenuGUI(PlayerRepo playerRepo) {

        Image imageMonopoly = new Image("https://i.pinimg.com/originals/b0/b2/48/b0b248f91cefb344ec92b272eadd860b.png", "Monopoly");
        imageMonopoly.setHeight("90px");
        imageMonopoly.setWidth("300px");
         
        Button buttonAddPlayer = new Button("Ustawienia gry", new Icon(VaadinIcon.COGS));
        buttonAddPlayer.addClickListener(e ->
                buttonAddPlayer.getUI().ifPresent(ui ->
                        ui.navigate("settings")));


        this.playerRepo = playerRepo;
        List<PlayerDto> playerList = playerRepo.getPlayers();
        Grid<PlayerDto> gridPlayers = new Grid<>(PlayerDto.class);
        gridPlayers.setItems(playerList);
        gridPlayers.removeAllColumns();
        gridPlayers.addColumn(PlayerDto::getName).setHeader("Imię").setSortable(true);
        gridPlayers.addColumn(PlayerDto::getCash).setHeader("Gotówka").setSortable(true);
        gridPlayers.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        gridPlayers.setHeightByRows(true);


        Button buttonPlayerOne = new Button("Panel gracza " + playerRepo.getPlayerOne() + " [Bankier]", new Icon(VaadinIcon.USER));
        Button buttonPlayerTwo = new Button("Panel gracza " + playerRepo.getPlayerTwo(), new Icon(VaadinIcon.USER));
        Button buttonPlayerThree = new Button("Panel gracza " + playerRepo.getPlayerThree(), new Icon(VaadinIcon.USER));
        Button buttonPlayerFour = new Button("Panel gracza " + playerRepo.getPlayerFour(), new Icon(VaadinIcon.USER));



        buttonPlayerOne.addClickListener(e ->
                buttonPlayerOne.getUI().ifPresent(ui ->
                        ui.navigate("player-one")));


        buttonPlayerTwo.addClickListener(e ->
                buttonPlayerTwo.getUI().ifPresent(ui ->
                        ui.navigate("player-two")));


        buttonPlayerThree.addClickListener(e ->
                buttonPlayerThree.getUI().ifPresent(ui ->
                        ui.navigate("player-three")));


        buttonPlayerFour.addClickListener(e ->
                buttonPlayerFour.getUI().ifPresent(ui ->
                        ui.navigate("player-four")));

        add(imageMonopoly, buttonAddPlayer, gridPlayers,
                buttonPlayerOne, buttonPlayerTwo, buttonPlayerThree, buttonPlayerFour);
    }


}
