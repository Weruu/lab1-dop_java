package ua.kpi.comsys.dop;

/**
 * Закрита ієрархія фінансових транзакцій (Data-Oriented Programming).
 * Містить лише дані; уся логіка обробки винесена в {@link TransactionProcessor}.
 */
public sealed interface Transaction permits Deposit, Withdrawal, Transfer {
}
