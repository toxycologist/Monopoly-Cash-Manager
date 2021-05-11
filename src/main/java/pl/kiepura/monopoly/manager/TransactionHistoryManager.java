package pl.kiepura.monopoly.manager;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kiepura.monopoly.entity.Player;
import pl.kiepura.monopoly.entity.PlayerDto;
import pl.kiepura.monopoly.entity.TransactionHistory;
import pl.kiepura.monopoly.entity.TransactionHistoryDto;
import pl.kiepura.monopoly.repo.PlayerRepo;
import pl.kiepura.monopoly.repo.TransactionHistoryRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionHistoryManager {


    private final TransactionHistoryRepo transactionHistoryRepo;




    public TransactionHistory save(TransactionHistory transactionHistory) {
        return transactionHistoryRepo.save(transactionHistory);
    }


    public List<TransactionHistoryDto> getTransactions() {
        return transactionHistoryRepo.getTransactions();
    }
}
