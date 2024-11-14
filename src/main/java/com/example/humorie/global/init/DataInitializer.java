package com.example.humorie.global.init;

import com.example.humorie.consultant.consult_detail.entity.ConsultDetail;
import com.example.humorie.consultant.consult_detail.repository.ConsultDetailRepository;
import com.example.humorie.consultant.review.entity.Tag;
import com.example.humorie.consultant.review.repository.TagRepository;
import com.example.humorie.global.config.SecurityConfig;
import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.repository.AccountRepository;
import com.example.humorie.consultant.counselor.entity.*;
import com.example.humorie.consultant.counselor.repository.*;
import com.example.humorie.consultant.review.entity.Review;
import com.example.humorie.consultant.review.repository.ReviewRepository;
import com.example.humorie.account.entity.Point;
import com.example.humorie.mypage.repository.PointRepository;
import com.example.humorie.notice.entity.Notice;
import com.example.humorie.notice.repository.NoticeRepository;
import com.example.humorie.payment.entity.Payment;
import com.example.humorie.payment.entity.PaymentStatus;
import com.example.humorie.payment.repository.PaymentRepository;
import com.example.humorie.reservation.entity.Reservation;
import com.example.humorie.reservation.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final CounselorRepository counselorRepository;
    private final CounselingMethodRepository methodRepository;
    private final AffiliationRepository affiliationRepository;
    private final EducationRepository educationRepository;
    private final CareerRepository careerRepository;
    private final SymptomRepository symptomRepository;
    private final ReviewRepository reviewRepository;
    private final PointRepository pointRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final SecurityConfig securityConfig;
    private final ConsultDetailRepository consultDetailRepository;
    private final TagRepository tagRepository;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        AccountDetail accountDetail1 = accountRepository.findByEmail("test1@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "test1@naver.com",
                        securityConfig.passwordEncoder().encode("test1234!"),
                        "test123",
                        "김봄",
                        true)));

        AccountDetail accountDetail2 = accountRepository.findByEmail("test2@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "test2@naver.com",
                        securityConfig.passwordEncoder().encode("test1234!"),
                        "test234",
                        "김여름",
                        false)));

        AccountDetail accountDetail3 = accountRepository.findByEmail("test3@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "test3@naver.com",
                        securityConfig.passwordEncoder().encode("test1234!"),
                        "test345",
                        "김가을",
                        false)));

        AccountDetail accountDetail4 = accountRepository.findByEmail("admin@naver.com")
                .orElseGet(() -> accountRepository.save(AccountDetail.joinAccount(
                        "admin@naver.com",
                        securityConfig.passwordEncoder().encode("admin1234!"),
                        "admin",
                        "관리자",
                        false
                )));

        Counselor counselor1 = Counselor.builder().name("김가을").phoneNumber("01000000000").email("rkdmf@naver.com").gender("여성").region("서울시 강남구").rating(4.8)
                .counselingCount(17).reviewCount(8).introduction("편안한 상담사").build();
        Counselor counselor2 = Counselor.builder().name("김겨울").phoneNumber("01011111111").email("rudnf@naver.com").gender("남성").region("서울시 강남구").rating(4.1)
                .counselingCount(30).reviewCount(19).introduction("든든한 상담사").build();
        Counselor counselor3 = Counselor.builder().name("이우정").phoneNumber("01022222222").email("dnwjd@naver.com").gender("여성").region("서울시 강남구").rating(4.5)
                .counselingCount(22).reviewCount(15).introduction("정직한 상담사").build();
        Counselor counselor4 = Counselor.builder().name("명재현").phoneNumber("0103333333").email("audwogus@naver.com").gender("남성").region("경기도 광명시").rating(4.5)
                .counselingCount(50).reviewCount(40).introduction("성실한 상담사").build();
        Counselor counselor5 = Counselor.builder().name("박윤").phoneNumber("01044444444").email("qkrdbs@gmail.com").gender("여성").region("경기도 광명시").rating(4.2)
                .counselingCount(14).reviewCount(7).introduction("편안한 상담사").build();
        Counselor counselor6 = Counselor.builder().name("허윤지").phoneNumber("01055555555").email("gjdbswl@gmail.com").gender("여성").region("경기도 수원시").rating(3.9)
                .counselingCount(7).reviewCount(1).introduction("편안한 상담사").build();

        CounselingMethod method1 = CounselingMethod.builder().method("온라인").counselor(counselor1).build();
        CounselingMethod method2 = CounselingMethod.builder().method("오프라인").counselor(counselor1).build();
        CounselingMethod method3 = CounselingMethod.builder().method("온라인").counselor(counselor2).build();
        CounselingMethod method4 = CounselingMethod.builder().method("오프라인").counselor(counselor3).build();
        CounselingMethod method5 = CounselingMethod.builder().method("온라인").counselor(counselor4).build();
        CounselingMethod method6 = CounselingMethod.builder().method("오프라인").counselor(counselor4).build();
        CounselingMethod method7 = CounselingMethod.builder().method("온라인").counselor(counselor5).build();
        CounselingMethod method8 = CounselingMethod.builder().method("오프라인").counselor(counselor6).build();

        Symptom symptom1 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("우울").counselor(counselor1).build();
        Symptom symptom2 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("대인관계").counselor(counselor1).build();
        Symptom symptom3 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("우울").counselor(counselor2).build();
        Symptom symptom4 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("자살").counselor(counselor2).build();
        Symptom symptom5 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("사회부적응").counselor(counselor2).build();
        Symptom symptom6 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("우울").counselor(counselor3).build();
        Symptom symptom7 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("우울").counselor(counselor4).build();
        Symptom symptom8 = Symptom.builder().categoryType("가족").issueAreaType("자녀 문제").symptom("대인관계").counselor(counselor4).build();
        Symptom symptom9 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("사회부적응").counselor(counselor4).build();
        Symptom symptom10 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("우울").counselor(counselor5).build();
        Symptom symptom11 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("자살").counselor(counselor6).build();
        Symptom symptom12 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("외상후 스트레스").counselor(counselor1).build();
        Symptom symptom13 = Symptom.builder().categoryType("개인").issueAreaType("개인 문제").symptom("인터넷 중독").counselor(counselor1).build();
        Symptom symptom14 = Symptom.builder().categoryType("가족").issueAreaType("가족 문제").symptom("원가족 갈등").counselor(counselor2).build();
        Symptom symptom15 = Symptom.builder().categoryType("가족").issueAreaType("가족 문제").symptom("친자 갈등").counselor(counselor2).build();
        Symptom symptom16 = Symptom.builder().categoryType("가족").issueAreaType("가족 문제").symptom("종교차이").counselor(counselor3).build();
        Symptom symptom17 = Symptom.builder().categoryType("가족").issueAreaType("가족 문제").symptom("가족 죽음").counselor(counselor3).build();
        Symptom symptom18 = Symptom.builder().categoryType("가족").issueAreaType("부부 문제").symptom("부부관계갈등").counselor(counselor4).build();
        Symptom symptom19 = Symptom.builder().categoryType("가족").issueAreaType("부부 문제").symptom("의사소통").counselor(counselor4).build();
        Symptom symptom20 = Symptom.builder().categoryType("가족").issueAreaType("부부 문제").symptom("성격차이").counselor(counselor5).build();
        Symptom symptom21 = Symptom.builder().categoryType("가족").issueAreaType("부부 문제").symptom("별거").counselor(counselor5).build();
        Symptom symptom22 = Symptom.builder().categoryType("가족").issueAreaType("자녀 문제").symptom("자녀 성격").counselor(counselor6).build();
        Symptom symptom23 = Symptom.builder().categoryType("가족").issueAreaType("자녀 문제").symptom("학업/진로").counselor(counselor6).build();

        Affiliation affiliation1 = Affiliation.builder().societyName("학회1").counselor(counselor1).build();
        Affiliation affiliation2 = Affiliation.builder().societyName("학회2").counselor(counselor2).build();
        Affiliation affiliation3 = Affiliation.builder().societyName("학회1").counselor(counselor3).build();
        Affiliation affiliation4 = Affiliation.builder().societyName("학회1").counselor(counselor4).build();
        Affiliation affiliation5 = Affiliation.builder().societyName("학회2").counselor(counselor4).build();
        Affiliation affiliation6 = Affiliation.builder().societyName("학회3").counselor(counselor4).build();
        Affiliation affiliation7 = Affiliation.builder().societyName("학회1").counselor(counselor5).build();
        Affiliation affiliation8 = Affiliation.builder().societyName("학회3").counselor(counselor5).build();
        Affiliation affiliation9 = Affiliation.builder().societyName("학회2").counselor(counselor6).build();

        Education education1 = Education.builder().content("1대학교 심리학 박사").counselor(counselor1).build();
        Education education2 = Education.builder().content("2대학교 심리학 박사").counselor(counselor2).build();
        Education education3 = Education.builder().content("2대학교 심리학 박사").counselor(counselor3).build();
        Education education4 = Education.builder().content("1대학교 심리학 박사").counselor(counselor4).build();
        Education education5 = Education.builder().content("3대학교 심리학 박사").counselor(counselor5).build();
        Education education6 = Education.builder().content("3대학교 심리학 박사").counselor(counselor6).build();

        Career career1 = Career.builder().content("경력1").counselor(counselor1).build();
        Career career2 = Career.builder().content("경력2").counselor(counselor1).build();
        Career career3 = Career.builder().content("경력1").counselor(counselor2).build();
        Career career4 = Career.builder().content("경력1").counselor(counselor3).build();
        Career career5 = Career.builder().content("경력1").counselor(counselor4).build();
        Career career6 = Career.builder().content("경력2").counselor(counselor4).build();
        Career career7 = Career.builder().content("경력1").counselor(counselor5).build();
        Career career8 = Career.builder().content("경력1").counselor(counselor6).build();

        Point point1 = Point.builder().points(100000).type("earn").title("웰컴 포인트").transactionDate(LocalDateTime.of(2024, 5, 7, 12, 30, 00))
                .account(accountDetail1).build();
        Point point2 = Point.builder().points(50000).type("spend").title("상담 예약").transactionDate(LocalDateTime.of(2024, 5, 10, 12, 30, 00)).
                account(accountDetail1).build();
        Point point3 = Point.builder().points(1000).type("earn").title("리뷰 작성").transactionDate(LocalDateTime.of(2024, 5, 11, 12, 30, 00))
                .account(accountDetail1).build();
        Point point4 = Point.builder().points(30000).type("spend").title("상담 예약").transactionDate(LocalDateTime.of(2024, 6, 2, 12, 30, 00))
                .account(accountDetail1).build();
        Point point5 = Point.builder().points(2000).type("earn").title("리뷰 작성").transactionDate(LocalDateTime.of(2024, 6, 3, 12, 30, 00))
                .account(accountDetail1).build();
        Point point6 = Point.builder().points(100000).type("earn").title("웰컴 포인트").transactionDate(LocalDateTime.of(2024, 5, 9, 12, 30, 00))
                .account(accountDetail2).build();
        Point point7 = Point.builder().points(70000).type("spend").title("상담 예약").transactionDate(LocalDateTime.of(2024, 5, 17, 12, 30, 00))
                .account(accountDetail2).build();
        Point point8 = Point.builder().points(100000).type("earn").title("웰컴 포인트").transactionDate(LocalDateTime.of(2024, 5, 20, 9, 30, 00))
                .account(accountDetail3).build();
        Point point9 = Point.builder().points(5000).type("spend").title("상담 예약").transactionDate(LocalDateTime.of(2024, 8, 20, 12, 00, 00))
                .account(accountDetail3).build();
        Point point10 = Point.builder().points(2000).type("earn").title("리뷰 작성").transactionDate(LocalDateTime.of(2024,8,21,12,00,00))
                .account(accountDetail3).build();

        Payment payment1 = Payment.builder().price(50000).point(0).finalPrice(50000).status(PaymentStatus.READY).build();
        Payment payment2 = Payment.builder().price(50000).point(0).finalPrice(50000).status(PaymentStatus.READY).build();
        Payment payment3 = Payment.builder().price(55000).point(5000).finalPrice(50000).status(PaymentStatus.READY).build();
        Payment payment4 = Payment.builder().price(50000).point(0).finalPrice(50000).status(PaymentStatus.READY).build();

        payment1.changePaymentBySuccess(PaymentStatus.OK, "imp_651256947592");
        payment2.changePaymentBySuccess(PaymentStatus.OK, "imp_617755937144");
        payment3.changePaymentBySuccess(PaymentStatus.OK, "imp_650825655292");

        paymentRepository.saveAll(Arrays.asList(payment1, payment2, payment3, payment4));

        Reservation reservation1 = Reservation.builder().counselDate(LocalDate.of(2024,8,18)).account(accountDetail3).counselTime(LocalTime.of(12,0))
                .isOnline(false).location("서울 강남구").counselor(counselor1).counselContent("학업/진로").reservationUid("d1be41f5-ad55-4060-9584-aee6ff1125ad").payment(payment1).build();
        Reservation reservation2 = Reservation.builder().counselDate(LocalDate.of(2024,8,19)).account(accountDetail2).counselTime(LocalTime.of(15,30))
                .isOnline(false).location("인천 연수구").counselor(counselor2).counselContent("대인관계").reservationUid("692fb669-1305-451d-b3a0-7d463531f215").payment(payment2).build();
        Reservation reservation3 = Reservation.builder().counselDate(LocalDate.of(2024,8,20)).account(accountDetail3).counselTime(LocalTime.of(16,0))
                .isOnline(false).location("서울 은평구").counselor(counselor3).counselContent("정신건강").reservationUid("3834b220-43bd-4f8d-bbb5-15b1ed4b85fb").payment(payment3).build();

        Reservation reservation4 = Reservation.builder().counselDate(LocalDate.of(2024,8,21)).account(accountDetail2).counselTime(LocalTime.of(14,0))
                .isOnline(true).location("Zoom").counselor(counselor2).counselContent("성격").reservationUid(String.valueOf(UUID.randomUUID())).payment(payment4).build();
        Reservation reservation5 = Reservation.builder().counselDate(LocalDate.of(2024,8,21)).account(accountDetail1).counselTime(LocalTime.of(14,0))
                .isOnline(true).location("Google meet").counselor(counselor2).counselContent("성격").reservationUid(String.valueOf(UUID.randomUUID())).build();
        Reservation reservation6 = Reservation.builder().counselDate(LocalDate.of(2024,8,22)).account(accountDetail1).counselTime(LocalTime.of(14,0))
                .isOnline(false).location("인천 미추홀구").counselor(counselor4).counselContent("성격").reservationUid(String.valueOf(UUID.randomUUID())).build();
        Reservation reservation7 = Reservation.builder().counselDate(LocalDate.of(2024,8,23)).account(accountDetail1).counselTime(LocalTime.of(14,0))
                .isOnline(false).location("성남시 분당구").counselor(counselor5).counselContent("성격").reservationUid(String.valueOf(UUID.randomUUID())).build();
        Reservation reservation8 = Reservation.builder().counselDate(LocalDate.of(2024,8,24)).account(accountDetail1).counselTime(LocalTime.of(14,0))
                .isOnline(true).location("전화상담").counselor(counselor6).counselContent("성격").reservationUid(String.valueOf(UUID.randomUUID())).build();
        Reservation reservation9 = Reservation.builder().counselDate(LocalDate.of(2024,8,25)).account(accountDetail1).counselTime(LocalTime.of(14,0))
                .isOnline(true).location("전화상담").counselor(counselor1).counselContent("성격").reservationUid(String.valueOf(UUID.randomUUID())).build();
        Reservation reservation10 = Reservation.builder().counselDate(LocalDate.of(2024,8,26)).account(accountDetail1).counselTime(LocalTime.of(11,0))
                .isOnline(false).location("인천 부평구").counselor(counselor2).counselContent("성격").reservationUid(String.valueOf(UUID.randomUUID())).build();
        Reservation reservation11 = Reservation.builder().counselDate(LocalDate.of(2024,8,26)).account(accountDetail1).counselTime(LocalTime.of(14,0))
                .isOnline(false).location("서울 종로구").counselor(counselor3).counselContent("성격").reservationUid(String.valueOf(UUID.randomUUID())).build();

        ConsultDetail consultDetail1 = ConsultDetail.builder().status(true).account(accountDetail1).counselor(counselor1).content("상담 내용 1").isOnline(true).location("전화 상담")
                .symptomCategory("스트레스").symptomDetail("증상에 대한 설명").title("상담 제목 1").counselDate(LocalDate.of(2024,8,21)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail2 = ConsultDetail.builder().status(true).account(accountDetail1).counselor(counselor2).content("상담 내용 2").isOnline(true).location("전화 상담")
                .symptomCategory("우울").symptomDetail("증상에 대한 설명").title("상담 제목 2").counselDate(LocalDate.of(2024,8,23)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail3 = ConsultDetail.builder().status(false).account(accountDetail1).counselor(counselor3).content("상담 내용 3").isOnline(true).location("전화 상담")
                .symptomCategory("불안").symptomDetail("증상에 대한 설명").title("상담 제목 3").counselDate(LocalDate.of(2024,8,22)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail4 = ConsultDetail.builder().status(false).account(accountDetail1).counselor(counselor4).content("상담 내용 4").isOnline(true).location("전화 상담")
                .symptomCategory("트라우마").symptomDetail("증상에 대한 설명").title("상담 제목 4").counselDate(LocalDate.of(2024,8,27)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail5 = ConsultDetail.builder().status(false).account(accountDetail1).counselor(counselor5).content("상담 내용 5").isOnline(false).location("서울시 종로구")
                .symptomCategory("스트레스").symptomDetail("증상에 대한 설명").title("상담 제목 5").counselDate(LocalDate.of(2024,8,25)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail6 = ConsultDetail.builder().status(false).account(accountDetail1).counselor(counselor6).content("상담 내용 6").isOnline(false).location("서울시 은평구")
                .symptomCategory("우울").symptomDetail("증상에 대한 설명").title("상담 제목 6").counselDate(LocalDate.of(2024,8,26)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail7 = ConsultDetail.builder().status(false).account(accountDetail1).counselor(counselor1).content("상담 내용 7").isOnline(false).location("서울시 강남구")
                .symptomCategory("불안").symptomDetail("증상에 대한 설명").title("상담 제목 7").counselDate(LocalDate.of(2024,8,30)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail8 = ConsultDetail.builder().status(false).account(accountDetail1).counselor(counselor2).content("상담 내용 8").isOnline(false).location("성남시 분당구")
                .symptomCategory("트라우마").symptomDetail("증상에 대한 설명").title("상담 제목 8").counselDate(LocalDate.of(2024,9,15)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail9 = ConsultDetail.builder().status(false).account(accountDetail1).counselor(counselor3).content("상담 내용 9").isOnline(false).location("부천시 원미구")
                .symptomCategory("스트레스").symptomDetail("증상에 대한 설명").title("상담 제목 9").counselDate(LocalDate.of(2024,9,10)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail10 = ConsultDetail.builder().status(false).account(accountDetail2).counselor(counselor4).content("상담 내용 10").isOnline(true).location("전화 상담")
                .symptomCategory("우울").symptomDetail("증상에 대한 설명").title("상담 제목 10").counselDate(LocalDate.of(2024,8,21)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail11 = ConsultDetail.builder().status(false).account(accountDetail2).counselor(counselor5).content("상담 내용 11").isOnline(true).location("전화 상담")
                .symptomCategory("불안").symptomDetail("증상에 대한 설명").title("상담 제목 11").counselDate(LocalDate.of(2024,8,21)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail12 = ConsultDetail.builder().status(false).account(accountDetail2).counselor(counselor6).content("상담 내용 12").isOnline(true).location("전화 상담")
                .symptomCategory("트라우마").symptomDetail("증상에 대한 설명").title("상담 제목 12").counselDate(LocalDate.of(2024,8,21)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail13 = ConsultDetail.builder().status(false).account(accountDetail2).counselor(counselor1).content("상담 내용 13").isOnline(false).location("서울시 종로구")
                .symptomCategory("스트레스").symptomDetail("증상에 대한 설명").title("상담 제목 13").counselDate(LocalDate.of(2024,8,21)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail14 = ConsultDetail.builder().status(false).account(accountDetail2).counselor(counselor2).content("상담 내용 14").isOnline(false).location("서울시 은평구")
                .symptomCategory("우울").symptomDetail("증상에 대한 설명").title("상담 제목 14").counselDate(LocalDate.of(2024,8,21)).counselTime(LocalTime.of(14,0)).build();
        ConsultDetail consultDetail15 = ConsultDetail.builder().status(false).account(accountDetail2).counselor(counselor3).content("상담 내용 15").isOnline(false).location("서울시 종로구")
                .symptomCategory("불안").symptomDetail("증상에 대한 설명").title("상담 제목 15").counselDate(LocalDate.of(2024,8,21)).counselTime(LocalTime.of(14,0)).build();

        Review review1 = Review.builder().title("도움돼요").content("도움돼요").rating(4.0).createdAt(LocalDateTime.of(2024, 5, 15, 13, 00, 00))
                .account(accountDetail1).counselor(consultDetail1.getCounselor()).build();
        Review review2 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00))
                .account(accountDetail1).counselor(consultDetail2.getCounselor()).build();
        Review review3 = Review.builder().title("괜찮아요").content("괜찮아요").rating(4.0).createdAt(LocalDateTime.of(2024, 5, 20, 17, 00, 00))
                .account(accountDetail1).counselor(consultDetail3.getCounselor()).build();
        Review review4 = Review.builder().title("좋아요").content("좋아요").rating(4.0).createdAt(LocalDateTime.of(2024, 5, 7, 12, 30, 00)).
                account(accountDetail1).counselor(consultDetail4.getCounselor()).build();
        Review review5 = Review.builder().title("도움돼요").content("도움돼요").rating(5.0).createdAt(LocalDateTime.of(2024, 5, 15, 13, 00, 00))
                .account(accountDetail1).counselor(consultDetail5.getCounselor()).build();
        Review review6 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00))
                .account(accountDetail1).counselor(consultDetail6.getCounselor()).build();
        Review review7 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00))
                .account(accountDetail1).counselor(consultDetail7.getCounselor()).build();
        Review review8 = Review.builder().title("괜찮아요").content("괜찮아요").rating(4.4).createdAt(LocalDateTime.of(2024, 5, 20, 17, 00, 00))
                .account(accountDetail1).counselor(consultDetail8.getCounselor()).build();
        Review review9 = Review.builder().title("좋아요").content("좋아요").rating(5.0).createdAt(LocalDateTime.of(2024, 5, 7, 12, 30, 00))
                .account(accountDetail1).counselor(consultDetail9.getCounselor()).build();
        Review review10 = Review.builder().title("도움돼요").content("도움돼요").rating(4.0).createdAt(LocalDateTime.of(2024, 5, 15, 13, 00, 00))
                .account(accountDetail2).counselor(consultDetail10.getCounselor()).build();
        Review review11 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00))
                .account(accountDetail2).counselor(consultDetail11.getCounselor()).build();
        Review review12 = Review.builder().title("괜찮아요").content("괜찮아요").rating(4.0).createdAt(LocalDateTime.of(2024, 5, 20, 17, 00, 00))
                .account(accountDetail2).counselor(consultDetail12.getCounselor()).build();
        Review review13 = Review.builder().title("좋아요").content("좋아요").rating(4.0).createdAt(LocalDateTime.of(2024, 5, 7, 12, 30, 00))
                .account(accountDetail2).counselor(consultDetail13.getCounselor()).build();
        Review review14 = Review.builder().title("도움돼요").content("도움돼요").rating(5.0).createdAt(LocalDateTime.of(2024, 5, 15, 13, 00, 00))
                .account(accountDetail2).counselor(consultDetail14.getCounselor()).build();
        Review review15 = Review.builder().title("별로에요").content("별로에요").rating(3.0).createdAt(LocalDateTime.of(2024, 5, 12, 8, 30, 00))
                .account(accountDetail2).counselor(consultDetail15.getCounselor()).build();

        Tag tag1 = Tag.builder().tagName("친절").tagContent("상담사님이 친절하세요.").account(accountDetail1).build();
        Tag tag2 = Tag.builder().tagName("좋음").tagContent("상담이 매우 좋았습니다.").account(accountDetail1).build();
        Tag tag3 = Tag.builder().tagName("아쉬움").tagContent("상담이 아쉬웠습니다.").account(accountDetail1).build();
        Tag tag4 = Tag.builder().tagName("친절").tagContent("상담사님이 친절하세요.").account(accountDetail2).build();
        Tag tag5 = Tag.builder().tagName("좋아요").tagContent("상담이 매우 좋았습니다.").account(accountDetail2).build();
        Tag tag6 = Tag.builder().tagName("아쉬움").tagContent("상담이 아쉬웠습니다.").account(accountDetail2).build();

        Notice notice1 = Notice.builder().title("공지사항 제목 1").content("공지사항 내용 1").
                createdDate(LocalDate.of(2024, 8, 25)).createdTime(LocalTime.of(18,00,00)).viewCount(50).author("관리자").build();
        Notice notice2 = Notice.builder().title("공지사항 제목 2").content("공지사항 내용 2").
                createdDate(LocalDate.of(2024, 8, 25)).createdTime(LocalTime.of(15,00,00)).viewCount(50).author("관리자").build();
        Notice notice3 = Notice.builder().title("공지사항 제목 3").content("공지사항 내용 3").
                createdDate(LocalDate.of(2024, 8, 25)).createdTime(LocalTime.of(12,00,00)).viewCount(40).author("관리자").build();
        Notice notice4 = Notice.builder().title("공지사항 제목 4").content("공지사항 내용 4").
                createdDate(LocalDate.of(2024, 8, 24)).createdTime(LocalTime.of(18,00,00)).viewCount(25).author("관리자").build();
        Notice notice5 = Notice.builder().title("공지사항 제목 5").content("공지사항 내용 5").
                createdDate(LocalDate.of(2024, 8, 23)).createdTime(LocalTime.of(18,00,00)).viewCount(25).author("관리자").build();
        Notice notice6 = Notice.builder().title("공지사항 제목 6").content("공지사항 내용 6").
                createdDate(LocalDate.of(2024, 8, 22)).createdTime(LocalTime.of(18,00,00)).viewCount(25).author("관리자").build();
        Notice notice7 = Notice.builder().title("공지사항 제목 7").content("공지사항 내용 7").
                createdDate(LocalDate.of(2024, 8, 22)).createdTime(LocalTime.of(14,00,00)).viewCount(25).author("관리자").build();
        Notice notice8 = Notice.builder().title("공지사항 제목 8").content("공지사항 내용 8").
                createdDate(LocalDate.of(2024, 8, 21)).createdTime(LocalTime.of(14,00,00)).viewCount(30).author("관리자").build();
        Notice notice9 = Notice.builder().title("공지사항 제목 9").content("공지사항 내용 9").
                createdDate(LocalDate.of(2024, 8, 20)).createdTime(LocalTime.of(14,00,00)).viewCount(35).author("관리자").build();


        counselorRepository.saveAll(Arrays.asList(counselor1, counselor2, counselor3, counselor4, counselor5, counselor6));
        methodRepository.saveAll(Arrays.asList(method1, method2, method3, method4, method5, method6, method7, method8));
        symptomRepository.saveAll(Arrays.asList(symptom1, symptom2, symptom3, symptom4, symptom5, symptom6, symptom7, symptom8, symptom9, symptom10, symptom11, symptom12, symptom13, symptom14, symptom15, symptom16, symptom17, symptom18, symptom19, symptom20, symptom21, symptom22, symptom23));
        reviewRepository.saveAll(Arrays.asList(review1, review2, review3, review4, review5, review6, review7, review8, review9, review10, review11, review12, review13, review14, review15));
        affiliationRepository.saveAll(Arrays.asList(affiliation1, affiliation2, affiliation3, affiliation4, affiliation5, affiliation6, affiliation7, affiliation8, affiliation9));
        educationRepository.saveAll(Arrays.asList(education1, education2, education3, education4, education5, education6));
        careerRepository.saveAll(Arrays.asList(career1, career2, career3, career4, career5, career6, career7, career8));
        pointRepository.saveAll(Arrays.asList(point1, point2, point3, point4, point5, point6, point7, point8, point9, point10));
        reservationRepository.saveAll(Arrays.asList(reservation1, reservation2, reservation3, reservation4, reservation5, reservation6,reservation7, reservation8, reservation9, reservation10, reservation11));
        consultDetailRepository.saveAll(Arrays.asList(consultDetail1, consultDetail2, consultDetail3, consultDetail4, consultDetail5, consultDetail6, consultDetail7, consultDetail8, consultDetail9, consultDetail10, consultDetail1, consultDetail12, consultDetail13, consultDetail14, consultDetail15));
        tagRepository.saveAll(Arrays.asList(tag1, tag2, tag3, tag4, tag5, tag6));
        noticeRepository.saveAll(Arrays.asList(notice1,notice2,notice3,notice4,notice5,notice6,notice7,notice8,notice9));
    }

}
