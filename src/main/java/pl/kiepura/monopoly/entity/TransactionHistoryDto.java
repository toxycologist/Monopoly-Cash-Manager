package pl.kiepura.monopoly.entity;

public interface TransactionHistoryDto {

    Long getTranactionId();
    String getSource();
    String getTarget();
    Integer getAmount();


}
