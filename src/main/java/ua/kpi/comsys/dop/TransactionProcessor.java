package ua.kpi.comsys.dop;

/**
 * Обробник транзакцій: перетворює дані {@link Transaction} у текстове подання.
 * Містить лише логіку, жодних даних.
 */
public class TransactionProcessor {

    /**
     * Формує текстовий звіт про транзакцію.
     *
     * @param transaction транзакція для обробки
     * @return відформатований Text Block
     * @throws IllegalArgumentException якщо {@code transaction == null}
     */
    public String process(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        // sealed-ієрархія: switch вичерпний, default не потрібен
        return switch (transaction) {
            case Deposit d -> """
                    Transaction Type: DEPOSIT
                    Account: %s
                    Amount: USD %s
                    """.formatted(d.accountId(), d.amount().toPlainString());

            case Withdrawal w -> """
                    Transaction Type: WITHDRAWAL
                    Account: %s
                    Category: %s
                    Amount: USD %s
                    """.formatted(w.accountId(), w.category(), w.amount().toPlainString());

            case Transfer t -> """
                    Transaction Type: TRANSFER
                    From: %s
                    To: %s
                    Amount: USD %s
                    """.formatted(t.fromAccount(), t.toAccount(), t.amount().toPlainString());
        };
    }
}
