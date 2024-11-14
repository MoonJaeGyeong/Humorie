package com.example.humorie.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "account_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    //id: 고유 식별자, accountName: 아이디 값
    private String accountName;

    private String password;

    private String name;

    private LocalDate joinDate;

    private Boolean emailSubscription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> pointTransactions;

    public static AccountDetail joinAccount(String email, String encodedPassword, String accountName, String name, Boolean emailSubscription) {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.email = email;
        accountDetail.password = encodedPassword;
        accountDetail.accountName = accountName;
        accountDetail.name = name;
        accountDetail.emailSubscription = emailSubscription;
        accountDetail.role = AccountRole.USER;
        accountDetail.loginType = LoginType.JWT;
        accountDetail.joinDate = LocalDate.now();

        return accountDetail;
    }

}
