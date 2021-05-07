package pl.kiepura.monopoly.entity;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kiepura.monopoly.repo.PlayerRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerManager {

    private final PlayerRepo playerRepo;
    private final PasswordEncoder passwordEncoder;

    public Iterable<Player> findAll() {
        return playerRepo.findAll();
    }

    public Player save(Player player) {
        return playerRepo.save(player);
    }

    public List<PlayerDto> playerList(){
        return playerRepo.getPlayers();
    }



}
