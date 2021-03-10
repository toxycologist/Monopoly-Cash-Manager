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

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE PLAYER; ALTER TABLE player ALTER COLUMN id RESTART WITH 1;", nativeQuery = true)
    void clearPlayers();

    @Query(value = "SELECT name as name, cash as cash FROM Player", nativeQuery = true)
    List<PlayerDto> getPlayers();


    @Query(value = "SELECT name as name FROM Player", nativeQuery = true)
    List<PlayerDto> getPlayersNames();


    // gracz 1

    @Query(value = "SELECT NAME from Player WHERE ID=1", nativeQuery = true)
    String getPlayerOne();

    @Query(value = "SELECT CASH from Player WHERE ID=1", nativeQuery = true)
    Integer getPlayerOneCash();


// gracz 2

    @Query(value = "SELECT NAME from Player WHERE ID=2", nativeQuery = true)
    String getPlayerTwo();

    @Query(value = "SELECT CASH from Player WHERE ID=2", nativeQuery = true)
    Integer getPlayerTwoCash();

    // gracz 3


    @Query(value = "SELECT NAME from Player WHERE ID=3", nativeQuery = true)
    String getPlayerThree();

    @Query(value = "SELECT CASH from Player WHERE ID=3", nativeQuery = true)
    Integer getPlayerThreeCash();

    // gracz 4

    @Query(value = "SELECT NAME from Player WHERE ID=4", nativeQuery = true)
    String getPlayerFour();

    @Query(value = "SELECT CASH from Player WHERE ID=4", nativeQuery = true)
    Integer getPlayerFourCash();


}
