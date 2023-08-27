package com.example.kaushop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionPhase {
    TRY("try"),
    CONFIRM("confirm"),
    CANCEL("cancel");

    private String type;
}
