package ua.kpi.comsys.dop;

import java.math.BigDecimal;

/**
 * Переказ між рахунками.
 *
 * @param fromAccount рахунок відправника
 * @param toAccount   рахунок отримувача
 * @param amount      сума переказу (> 0)
 */
public record Transfer(String fromAccount, String toAccount, BigDecimal amount) implements Transaction {

    public Transfer {
        if (fromAccount == null || fromAccount.isBlank()) {
            throw new IllegalArgumentException("fromAccount must not be null or blank");
        }
        if (toAccount == null || toAccount.isBlank()) {
            throw new IllegalArgumentException("toAccount must not be null or blank");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must not be null and must be greater than 0");
        }
    }
}
