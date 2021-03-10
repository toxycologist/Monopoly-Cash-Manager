package pl.monopoly.Monopoly.entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.monopoly.Monopoly.repo.PlayerRepo;

@Service
public class PlayerManager {

    private PlayerRepo playerRepo;


    @Autowired
    public PlayerManager(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    public Player findById(Long id) {
        return playerRepo.findById(id).get();
    }



    public Iterable<Player> findAll() {
        return playerRepo.findAll();
    }

    public Player save(Player player) {
        return playerRepo.save(player);
    }




    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        save(new Player(1L, "Marcin", 6000));
        save(new Player(2L, "Dagmara", 6000));
        save(new Player(3L, "Ola", 6000));
        save(new Player(4L, "Damian", 6000));

    }

}
