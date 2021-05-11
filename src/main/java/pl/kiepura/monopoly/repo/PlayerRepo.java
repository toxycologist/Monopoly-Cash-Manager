package pl.kiepura.monopoly.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.entity.PlayerDto;

import java.util.List;

@Repository
public interface PlayerRepo extends CrudRepository<Player, Long> {

    Player getById(Long id);

    Player findByUsername(String username);


    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE player", nativeQuery = true)
    void clearPlayers();

    @Query(value = "SELECT username as username, cash as cash FROM player WHERE (id BETWEEN 1 AND 4)", nativeQuery = true)
    List<PlayerDto> getPlayers();



    // player 1

    @Query(value = "SELECT username from player WHERE ID=1", nativeQuery = true)
    String getPlayerOne();

    @Query(value = "SELECT cash from player WHERE ID=1", nativeQuery = true)
    Integer getPlayerOneCash();


    // player 2

    @Query(value = "SELECT username from player WHERE ID=2", nativeQuery = true)
    String getPlayerTwo();

    @Query(value = "SELECT cash from player WHERE ID=2", nativeQuery = true)
    Integer getPlayerTwoCash();

    // player 3


    @Query(value = "SELECT username from player WHERE ID=3", nativeQuery = true)
    String getPlayerThree();

    @Query(value = "SELECT cash from player WHERE ID=3", nativeQuery = true)
    Integer getPlayerThreeCash();

    // player 4

    @Query(value = "SELECT username from player WHERE ID=4", nativeQuery = true)
    String getPlayerFour();

    @Query(value = "SELECT cash from player WHERE ID=4", nativeQuery = true)
    Integer getPlayerFourCash();


}
