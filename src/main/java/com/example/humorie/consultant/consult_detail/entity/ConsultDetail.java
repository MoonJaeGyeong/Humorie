package com.example.humorie.consultant.consult_detail.entity;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.reservation.entity.Reservation;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "consult_detail")
public class ConsultDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String title;

    private String symptomCategory;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String symptomDetail;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Boolean isOnline;

    private String location;

    private LocalDate counselDate;

    private LocalTime counselTime;

    /*@ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;*/

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountDetail account;

    @ManyToOne
    @JoinColumn(name = "counselor_id")
    private Counselor counselor;

    @Column(nullable = false)
    private Boolean status = false;

    public void setStatus(Boolean status) { this.status = status; }

    public String getContent()  { return content; }

    public Long getCounselorId() { return counselor.getId(); }

    public String getCounselorName() {
        return counselor.getName();
    }

    public double getRating() {
        return counselor.getRating();
    }

    /*public Boolean getIsOnline() { return reservation.getIsOnline(); }
    public String getLocation() {
        return reservation.getLocation();
    }

    public LocalDate getCounselDate() {
        return reservation.getCounselDate();
    }

    public LocalTime getCounselTime() { return reservation.getCounselTime(); }

    public String getCounselContent() { return reservation.getCounselContent(); }*/

}
