package jpastart.common;

import java.math.BigDecimal;

public class Money {
    private BigDecimal value;
    private String currency;

    public Money(BigDecimal value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money(Double value, String currency) {
        this.value = BigDecimal.valueOf(value);
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public String toString() {
        return value.toString() + currency;
    }
}
