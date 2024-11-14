package com.example.humorie.account.service;

import com.example.humorie.global.config.SecurityConfig;
import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.repository.AccountRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;
    private String senderName = "&DAY";
    private final JavaMailSender javaMailSender;
    private final AccountRepository accountRepository;
    private final SecurityConfig securityConfig;
    private final Map<String, String> verificationCodes = new HashMap<>();
    private final Map<String, Long> verificationExpiry = new HashMap<>();
    private static final long EXPIRY_TIME = 5 * 60 * 1000; // 5 minutes

    public String sendPasswordResetEmail(String sendTo) throws Exception {
        String password = generateRandomCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setFrom(new InternetAddress(sender, senderName));
        helper.setTo(sendTo);
        helper.setSubject("<&DAY> 임시 비밀번호 발급 관련 안내");
        helper.setText("안녕하세요. &DAY 임시 비밀번호 안내 관련 이메일 입니다.\n\n" + "회원님의 임시 비밀번호는 " + password + " 입니다. " + "로그인 후 비밀번호를 변경해 주세요.");

        javaMailSender.send(mimeMessage);
        updatePassword(sendTo, password);

        return "Email sent successfully";
    }

    public String sendVerificationEmail(String sendTo) throws Exception {
        String verificationCode = generateRandomCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setFrom(new InternetAddress(sender, senderName));
        helper.setTo(sendTo);
        helper.setSubject("<&DAY> 이메일 본인 인증 관련 안내");
        helper.setText("안녕하세요. &DAY 이메일 본인 인증 안내 관련 이메일 입니다.\n\n" + "본인 인증을 위해 아래 인증 코드를 입력해 주세요.\n\n" + "인증 코드 : " + verificationCode);

        javaMailSender.send(mimeMessage);
        storeVerificationCode(sendTo, verificationCode);

        return "Verification email sent successfully";
    }

    public String sendConsultationCompletionEmail(String sendTo, String counselDate, String counselorName) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setFrom(new InternetAddress(sender, senderName));
        helper.setTo(sendTo);
        helper.setSubject("<&DAY> 상담 내역 작성 완료 안내");
        helper.setText("안녕하세요. &DAY 상담 내역 작성 완료 안내 관련 이메일 입니다.\n\n" + "회원님께서 " + counselDate + "에 "
                + counselorName + " 상담사님께 받은 상담에 대한 내역이 등록되었습니다.\n\n 상담 내역 페이지에서 확인해 주세요.");

        javaMailSender.send(mimeMessage);

        return "Email sent successfully";
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        Long expiryTime = verificationExpiry.get(email);

        if (storedCode != null && storedCode.equals(code) && System.currentTimeMillis() < expiryTime) {
            verificationCodes.remove(email);
            verificationExpiry.remove(email);
            return true;
        } else {
            return false;
        }
    }

    private String generateRandomCode() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=";
        StringBuilder temporaryPassword = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int idx = (int)(Math.random() * str.length());
            temporaryPassword.append(str.charAt(idx));
        }

        return temporaryPassword.toString();
    }

    private void storeVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
        verificationExpiry.put(email, System.currentTimeMillis() + EXPIRY_TIME);
    }

    @Transactional
    public void updatePassword(String email, String password) {
        AccountDetail accountDetail = accountRepository.findByEmail(email).orElseThrow(
                () -> new ErrorException(ErrorCode.NONE_EXIST_USER));

        String encodedPassword = securityConfig.passwordEncoder().encode(password);
        accountDetail.setPassword(encodedPassword);

        accountRepository.save(accountDetail);
    }

}
