package pl.kiepura.monopoly.manager;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.entity.PlayerDto;
import pl.kiepura.monopoly.repo.PlayerRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerManager {


    private final PlayerRepo playerRepo;


    public void clearPlayers() {
        playerRepo.clearPlayers();
    }


    public List<PlayerDto> getPlayers() {
        return playerRepo.getPlayers();
    }


    public Player getById(Long id){
        return playerRepo.getById(id);
    }

    public Player save(Player player){
        return playerRepo.save(player);
    }


    public Player findByUsername(String username) {
        return playerRepo.findByUsername(username);
    }


    // player 1

    public String getPlayerOne() {
        return playerRepo.getPlayerOne();
    }


    public Integer getPlayerOneCash() {
        return playerRepo.getPlayerOneCash();
    }

    // player 2

    public String getPlayerTwo() {
        return playerRepo.getPlayerTwo();
    }


    public Integer getPlayerTwoCash() {
        return playerRepo.getPlayerTwoCash();
    }

    // player 3

    public String getPlayerThree() {
        return playerRepo.getPlayerThree();
    }


    public Integer getPlayerThreeCash() {
        return playerRepo.getPlayerThreeCash();
    }

    // player 4

    public String getPlayerFour() {
        return playerRepo.getPlayerFour();
    }


    public Integer getPlayerFourCash() {
        return playerRepo.getPlayerFourCash();
    }

}
