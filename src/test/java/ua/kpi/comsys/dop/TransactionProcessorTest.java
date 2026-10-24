package ua.kpi.comsys.dop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionProcessorTest {

    private final TransactionProcessor processor = new TransactionProcessor();

    @Test
    @DisplayName("Deposit -> DEPOSIT text block")
    void processDeposit() {
        String result = processor.process(new Deposit("ACC-1", new BigDecimal("100.50")));
        String expected = """
                Transaction Type: DEPOSIT
                Account: ACC-1
                Amount: USD 100.50
                """;
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Withdrawal -> WITHDRAWAL text block")
    void processWithdrawal() {
        String result = processor.process(new Withdrawal("ACC-2", new BigDecimal("25"), "Food"));
        String expected = """
                Transaction Type: WITHDRAWAL
                Account: ACC-2
                Category: Food
                Amount: USD 25
                """;
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Transfer -> TRANSFER text block")
    void processTransfer() {
        String result = processor.process(new Transfer("ACC-3", "ACC-4", new BigDecimal("1000.00")));
        String expected = """
                Transaction Type: TRANSFER
                From: ACC-3
                To: ACC-4
                Amount: USD 1000.00
                """;
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Великі суми без наукової нотації")
    void processLargeAmountIsPlain() {
        String result = processor.process(new Deposit("ACC-1", new BigDecimal("1E+3")));
        assertEquals("""
                Transaction Type: DEPOSIT
                Account: ACC-1
                Amount: USD 1000
                """, result);
    }

    @Test
    @DisplayName("process(null) -> IllegalArgumentException з точним повідомленням")
    void processNull() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> processor.process(null));
        assertEquals("Transaction cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Різні типи дають різні результати")
    void differentTypesDifferentOutput() {
        String d = processor.process(new Deposit("A", BigDecimal.ONE));
        String w = processor.process(new Withdrawal("A", BigDecimal.ONE, "X"));
        String t = processor.process(new Transfer("A", "B", BigDecimal.ONE));
        assertEquals(3, java.util.Set.of(d, w, t).size());
    }

    // ---------------- Deposit validation ----------------

    @Test
    void depositRejectsNullAccount() {
        assertThrows(IllegalArgumentException.class, () -> new Deposit(null, BigDecimal.TEN));
    }

    @Test
    void depositRejectsBlankAccount() {
        assertThrows(IllegalArgumentException.class, () -> new Deposit("   ", BigDecimal.TEN));
    }

    @Test
    void depositRejectsEmptyAccount() {
        assertThrows(IllegalArgumentException.class, () -> new Deposit("", BigDecimal.TEN));
    }

    @Test
    void depositRejectsNullAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Deposit("A", null));
    }

    @Test
    void depositRejectsZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Deposit("A", BigDecimal.ZERO));
    }

    @Test
    void depositRejectsNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Deposit("A", new BigDecimal("-1")));
    }

    // ---------------- Withdrawal validation ----------------

    @Test
    void withdrawalRejectsNullAccount() {
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal(null, BigDecimal.TEN, "Food"));
    }

    @Test
    void withdrawalRejectsBlankAccount() {
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal(" ", BigDecimal.TEN, "Food"));
    }

    @Test
    void withdrawalRejectsNullAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("A", null, "Food"));
    }

    @Test
    void withdrawalRejectsZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("A", BigDecimal.ZERO, "Food"));
    }

    @Test
    void withdrawalRejectsNegativeAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Withdrawal("A", new BigDecimal("-5"), "Food"));
    }

    @Test
    void withdrawalRejectsNullCategory() {
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("A", BigDecimal.TEN, null));
    }

    @Test
    void withdrawalRejectsBlankCategory() {
        assertThrows(IllegalArgumentException.class, () -> new Withdrawal("A", BigDecimal.TEN, "  "));
    }

    // ---------------- Transfer validation ----------------

    @Test
    void transferRejectsNullFrom() {
        assertThrows(IllegalArgumentException.class, () -> new Transfer(null, "B", BigDecimal.TEN));
    }

    @Test
    void transferRejectsBlankFrom() {
        assertThrows(IllegalArgumentException.class, () -> new Transfer(" ", "B", BigDecimal.TEN));
    }

    @Test
    void transferRejectsNullTo() {
        assertThrows(IllegalArgumentException.class, () -> new Transfer("A", null, BigDecimal.TEN));
    }

    @Test
    void transferRejectsBlankTo() {
        assertThrows(IllegalArgumentException.class, () -> new Transfer("A", "", BigDecimal.TEN));
    }

    @Test
    void transferRejectsNullAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Transfer("A", "B", null));
    }

    @Test
    void transferRejectsZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Transfer("A", "B", BigDecimal.ZERO));
    }

    @Test
    void transferRejectsNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Transfer("A", "B", new BigDecimal("-0.01")));
    }

    // ---------------- Immutability / records ----------------

    @Test
    @DisplayName("Records: equals/hashCode за значенням")
    void recordsHaveValueEquality() {
        assertEquals(new Deposit("A", BigDecimal.TEN), new Deposit("A", BigDecimal.TEN));
        assertEquals(new Transfer("A", "B", BigDecimal.TEN).hashCode(),
                new Transfer("A", "B", BigDecimal.TEN).hashCode());
    }
}
