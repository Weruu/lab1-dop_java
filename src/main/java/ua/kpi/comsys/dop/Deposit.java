package ua.kpi.comsys.dop;

import java.math.BigDecimal;

/**
 * Поповнення рахунку.
 *
 * @param accountId ідентифікатор рахунку
 * @param amount    сума поповнення (> 0)
 */
public record Deposit(String accountId, BigDecimal amount) implements Transaction {

    public Deposit {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("accountId must not be null or blank");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must not be null and must be greater than 0");
        }
    }
}
