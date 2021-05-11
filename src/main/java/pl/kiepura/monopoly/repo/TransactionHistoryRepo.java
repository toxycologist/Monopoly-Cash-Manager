package pl.kiepura.monopoly.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.kiepura.monopoly.entity.TransactionHistory;
import pl.kiepura.monopoly.entity.TransactionHistoryDto;

import java.util.List;

@Repository
public interface TransactionHistoryRepo extends CrudRepository<TransactionHistory, Long> {

    @Query(value = "SELECT id as Id, source as source, target as target, amount as amount FROM transaction_history ORDER BY id  DESC", nativeQuery = true)
    List<TransactionHistoryDto> getTransactions();

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE transaction_history", nativeQuery = true)
    void clearHistory();

}
