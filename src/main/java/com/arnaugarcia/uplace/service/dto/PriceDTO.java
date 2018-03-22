package com.arnaugarcia.uplace.service.dto;

import com.arnaugarcia.uplace.domain.enumeration.TransactionType;

public class PriceDTO {

    private Double priceSell;
    private Double priceRent;
    private Double priceTransfer;
    private TransactionType transactionType;

    public PriceDTO() { }

    public PriceDTO(Double priceSell, Double priceRent, Double priceTransfer, TransactionType transactionType) {
        this.priceSell = priceSell;
        this.priceRent = priceRent;
        this.priceTransfer = priceTransfer;
        this.transactionType = transactionType;
    }

    public Double getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(Double priceSell) {
        this.priceSell = priceSell;
    }

    public Double getPriceRent() {
        return priceRent;
    }

    public void setPriceRent(Double priceRent) {
        this.priceRent = priceRent;
    }

    public Double getPriceTransfer() {
        return priceTransfer;
    }

    public void setPriceTransfer(Double priceTransfer) {
        this.priceTransfer = priceTransfer;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "PriceDTO{" +
            "priceSell=" + priceSell +
            ", priceRent=" + priceRent +
            ", priceTransfer=" + priceTransfer +
            ", transactionType=" + transactionType +
            '}';
    }
}
