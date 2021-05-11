package pl.kiepura.monopoly.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kiepura.monopoly.entity.PlayerDto;
import pl.kiepura.monopoly.entity.TransactionHistory;
import pl.kiepura.monopoly.entity.TransactionHistoryDto;

import java.util.List;

@Repository
public interface TransactionHistoryRepo extends CrudRepository<TransactionHistory, Long> {

    @Query(value = "SELECT transaction_id as TranactionId, source as source, target as target, amount as amount FROM transaction_history  \n" +
            "ORDER BY `TranactionId`  DESC", nativeQuery = true)
    List<TransactionHistoryDto> getTransactions();

}
