package pl.kiepura.monopoly.entity;

public interface TransactionHistoryDto {

    Long getId();

    String getSource();

    String getTarget();

    Integer getAmount();


}
