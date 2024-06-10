package com.mounwan.moudules;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity @Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account { //계정관리

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified; //이메일로 인증된 계정인지 여부

    private String emailCheckToken; //이메일 검증시 사용할 토큰 값

    private LocalDateTime joinTime; //가입일시

    private LocalDateTime emailCheckTokenTime; //이메일 토큰발행 일시

    private String selfIntroduction; //자기소개

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean gatheringCreatedByEmail;

    private boolean gatheringCreatedByWeb = true;

    private boolean gatheringEnrollmentResultByEmail;

    private boolean gatheringEnrollmentResultByWeb = true;

    private boolean gatheringUpdatedByEmail;

    private boolean gatheringUpdatedByWeb = true;

    @ManyToMany
    private Set<Categories> categories = new HashSet<>();


    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenTime = LocalDateTime.now();
    }
}
