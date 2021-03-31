package pl.kiepura.monopoly.entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kiepura.monopoly.repo.PlayerRepo;

@Service
public class PlayerManager {

    private final PlayerRepo playerRepo;


    @Autowired
    public PlayerManager(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }



    public Iterable<Player> findAll() {
        return playerRepo.findAll();
    }

    public Player save(Player player) {
        return playerRepo.save(player);
    }


}
