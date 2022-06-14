package com.hqt.happyhostel.dto;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bill {
    int billID;
    int roomID;
    int totalMoney;
    String createdDate;
    String expiredPaymentDate;
    int status;
    Payment payment;
}
