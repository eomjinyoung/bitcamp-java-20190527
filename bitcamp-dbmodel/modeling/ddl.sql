SET FOREIGN_KEY_CHECKS = 0;

-- 학생
DROP TABLE IF EXISTS students RESTRICT;

-- 강의
DROP TABLE IF EXISTS lectures RESTRICT;

-- 강사
DROP TABLE IF EXISTS teachers RESTRICT;

-- 매니저
DROP TABLE IF EXISTS managers RESTRICT;

-- 교육센터
DROP TABLE IF EXISTS centers RESTRICT;

-- 교실
DROP TABLE IF EXISTS rooms RESTRICT;

-- 수강신청
DROP TABLE IF EXISTS add_drop RESTRICT;

-- 교실사진
DROP TABLE IF EXISTS room_photos RESTRICT;

-- 강의과목
DROP TABLE IF EXISTS subjects RESTRICT;

-- 강사강의과목
DROP TABLE IF EXISTS teacher_subject RESTRICT;

-- 강사강의배정
DROP TABLE IF EXISTS lecture_teacher RESTRICT;

-- 회원
DROP TABLE IF EXISTS members RESTRICT;

-- 학력
DROP TABLE IF EXISTS school_grade RESTRICT;

-- 학생
CREATE TABLE students (
  student_id INTEGER      NOT NULL COMMENT '학생번호', -- 학생번호
  photo_path VARCHAR(255) NOT NULL COMMENT '사진', -- 사진
  working    INTEGER      NOT NULL COMMENT '재직여부' -- 재직여부
)
COMMENT '학생';

-- 학생
ALTER TABLE students
  ADD CONSTRAINT PK_students -- 학생 기본키
    PRIMARY KEY (
      student_id -- 학생번호
    );

-- 강의
CREATE TABLE lectures (
  lecture_id INTEGER      NOT NULL COMMENT '강의번호', -- 강의번호
  title      VARCHAR(255) NOT NULL COMMENT '과정명', -- 과정명
  start_dt   DATE         NOT NULL COMMENT '시작일', -- 시작일
  end_dt     DATE         NOT NULL COMMENT '종료일', -- 종료일
  capa       INTEGER      NOT NULL COMMENT '모집인원', -- 모집인원
  tot_hr     INTEGER      NOT NULL COMMENT '총강의시간', -- 총강의시간
  day_hr     INTEGER      NOT NULL COMMENT '일강의시간', -- 일강의시간
  price      INTEGER      NOT NULL COMMENT '강의료', -- 강의료
  nation     INTEGER      NOT NULL COMMENT '정부지원여부', -- 정부지원여부
  worker     INTEGER      NOT NULL COMMENT '재직자지원여부', -- 재직자지원여부
  manager_id INTEGER      NULL     COMMENT '매니저번호' -- 매니저번호
)
COMMENT '강의';

-- 강의
ALTER TABLE lectures
  ADD CONSTRAINT PK_lectures -- 강의 기본키
    PRIMARY KEY (
      lecture_id -- 강의번호
    );

-- 강의 인덱스
CREATE INDEX IX_lectures
  ON lectures( -- 강의
    title ASC -- 과정명
  );

ALTER TABLE lectures
  MODIFY COLUMN lecture_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의번호';

-- 강사
CREATE TABLE teachers (
  teacher_id INTEGER      NOT NULL COMMENT '강사번호', -- 강사번호
  photo_path VARCHAR(255) NOT NULL COMMENT '사진', -- 사진
  salary_hr  INTEGER      NULL     COMMENT '시강임금', -- 시강임금
  ncs_score  INTEGER      NULL     COMMENT 'NCS점수' -- NCS점수
)
COMMENT '강사';

-- 강사
ALTER TABLE teachers
  ADD CONSTRAINT PK_teachers -- 강사 기본키
    PRIMARY KEY (
      teacher_id -- 강사번호
    );

-- 매니저
CREATE TABLE managers (
  manager_id INTEGER     NOT NULL COMMENT '매니저번호', -- 매니저번호
  fax        VARCHAR(30) NULL     COMMENT '팩스', -- 팩스
  tel        VARCHAR(30) NOT NULL COMMENT '회사전화', -- 회사전화
  dept       VARCHAR(25) NOT NULL COMMENT '부서', -- 부서
  posi       VARCHAR(25) NOT NULL COMMENT '직위' -- 직위
)
COMMENT '매니저';

-- 매니저
ALTER TABLE managers
  ADD CONSTRAINT PK_managers -- 매니저 기본키
    PRIMARY KEY (
      manager_id -- 매니저번호
    );

-- 교육센터
CREATE TABLE centers (
  center_id INTEGER      NOT NULL COMMENT '교육센터번호', -- 교육센터번호
  name      VARCHAR(25)  NOT NULL COMMENT '지점명', -- 지점명
  post_no   CHAR(6)      NULL     COMMENT '우편번호', -- 우편번호
  bas_addr  VARCHAR(255) NULL     COMMENT '기본주소', -- 기본주소
  det_addr  VARCHAR(255) NOT NULL COMMENT '상세주소' -- 상세주소
)
COMMENT '교육센터';

-- 교육센터
ALTER TABLE centers
  ADD CONSTRAINT PK_centers -- 교육센터 기본키
    PRIMARY KEY (
      center_id -- 교육센터번호
    );

-- 교육센터 유니크 인덱스
CREATE UNIQUE INDEX UIX_centers
  ON centers ( -- 교육센터
    name ASC -- 지점명
  );

ALTER TABLE centers
  MODIFY COLUMN center_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '교육센터번호';

-- 교실
CREATE TABLE rooms (
  room_id   INTEGER     NOT NULL COMMENT '교실번호', -- 교실번호
  name      VARCHAR(25) NOT NULL COMMENT '교실명', -- 교실명
  capa      INTEGER     NOT NULL COMMENT '수용가능인원', -- 수용가능인원
  state     INTEGER     NULL     DEFAULT 1 COMMENT '사용여부', -- 사용여부
  center_id INTEGER     NOT NULL COMMENT '교육센터번호' -- 교육센터번호
)
COMMENT '교실';

-- 교실
ALTER TABLE rooms
  ADD CONSTRAINT PK_rooms -- 교실 기본키
    PRIMARY KEY (
      room_id -- 교실번호
    );

-- 교실 인덱스
CREATE INDEX IX_rooms
  ON rooms( -- 교실
    name ASC -- 교실명
  );

ALTER TABLE rooms
  MODIFY COLUMN room_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '교실번호';

-- 수강신청
CREATE TABLE add_drop (
  ad_id      INTEGER  NOT NULL COMMENT '수강신청번호', -- 수강신청번호
  lecture_id INTEGER  NOT NULL COMMENT '강의번호', -- 강의번호
  student_id INTEGER  NOT NULL COMMENT '학생번호', -- 학생번호
  reg_dt     DATETIME NOT NULL COMMENT '수강신청일', -- 수강신청일
  state      INTEGER  NULL     DEFAULT 0 COMMENT '수강신청상태' -- 수강신청상태
)
COMMENT '수강신청';

-- 수강신청
ALTER TABLE add_drop
  ADD CONSTRAINT PK_add_drop -- 수강신청 기본키
    PRIMARY KEY (
      ad_id -- 수강신청번호
    );

ALTER TABLE add_drop
  MODIFY COLUMN ad_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '수강신청번호';

-- 교실사진
CREATE TABLE room_photos (
  photo_id   INTEGER      NOT NULL COMMENT '교실사진번호', -- 교실사진번호
  photo_path VARCHAR(255) NOT NULL COMMENT '사진', -- 사진
  room_id    INTEGER      NOT NULL COMMENT '교실번호' -- 교실번호
)
COMMENT '교실사진';

-- 교실사진
ALTER TABLE room_photos
  ADD CONSTRAINT PK_room_photos -- 교실사진 기본키
    PRIMARY KEY (
      photo_id -- 교실사진번호
    );

ALTER TABLE room_photos
  MODIFY COLUMN photo_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '교실사진번호';

-- 강의과목
CREATE TABLE subjects (
  subject_id INTEGER     NOT NULL COMMENT '강의과목번호', -- 강의과목번호
  title      VARCHAR(25) NOT NULL COMMENT '과목명' -- 과목명
)
COMMENT '강의과목';

-- 강의과목
ALTER TABLE subjects
  ADD CONSTRAINT PK_subjects -- 강의과목 기본키
    PRIMARY KEY (
      subject_id -- 강의과목번호
    );

-- 강의과목 유니크 인덱스
CREATE UNIQUE INDEX UIX_subjects
  ON subjects ( -- 강의과목
    title ASC -- 과목명
  );

-- 강의과목 인덱스
CREATE INDEX IX_subjects
  ON subjects( -- 강의과목
    title ASC -- 과목명
  );

ALTER TABLE subjects
  MODIFY COLUMN subject_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의과목번호';

-- 강사강의과목
CREATE TABLE teacher_subject (
  subject_id INTEGER NOT NULL COMMENT '강의과목번호', -- 강의과목번호
  teacher_id INTEGER NOT NULL COMMENT '강사번호' -- 강사번호
)
COMMENT '강사강의과목';

-- 강사강의과목
ALTER TABLE teacher_subject
  ADD CONSTRAINT PK_teacher_subject -- 강사강의과목 기본키
    PRIMARY KEY (
      subject_id, -- 강의과목번호
      teacher_id  -- 강사번호
    );

-- 강사강의배정
CREATE TABLE lecture_teacher (
  lecture_id INTEGER NOT NULL COMMENT '강의번호', -- 강의번호
  teacher_id INTEGER NOT NULL COMMENT '강사번호' -- 강사번호
)
COMMENT '강사강의배정';

-- 강사강의배정
ALTER TABLE lecture_teacher
  ADD CONSTRAINT PK_lecture_teacher -- 강사강의배정 기본키
    PRIMARY KEY (
      lecture_id, -- 강의번호
      teacher_id  -- 강사번호
    );

-- 회원
CREATE TABLE members (
  member_id   INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
  name        VARCHAR(25)  NOT NULL COMMENT '이름', -- 이름
  email       VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
  tel         VARCHAR(30)  NOT NULL COMMENT '전화', -- 전화
  post_no     CHAR(6)      NULL     COMMENT '우편번호', -- 우편번호
  bas_addr    VARCHAR(255) NULL     COMMENT '기본주소', -- 기본주소
  det_addr    VARCHAR(255) NULL     COMMENT '상세주소', -- 상세주소
  last_sg_id  INTEGER      NOT NULL COMMENT '최종학력번호', -- 최종학력번호
  last_school VARCHAR(25)  NOT NULL COMMENT '최종학교', -- 최종학교
  major       VARCHAR(25)  NOT NULL COMMENT '전공' -- 전공
)
COMMENT '회원';

-- 회원
ALTER TABLE members
  ADD CONSTRAINT PK_members -- 회원 기본키
    PRIMARY KEY (
      member_id -- 회원번호
    );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_members
  ON members ( -- 회원
    email ASC -- 이메일
  );

-- 회원 인덱스
CREATE INDEX IX_members
  ON members( -- 회원
    name ASC -- 이름
  );

ALTER TABLE members
  MODIFY COLUMN member_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 학력
CREATE TABLE school_grade (
  sg_id INTEGER     NOT NULL COMMENT '학력번호', -- 학력번호
  name  VARCHAR(25) NOT NULL COMMENT '학력명' -- 학력명
)
COMMENT '학력';

-- 학력
ALTER TABLE school_grade
  ADD CONSTRAINT PK_school_grade -- 학력 기본키
    PRIMARY KEY (
      sg_id -- 학력번호
    );

-- 학력 유니크 인덱스
CREATE UNIQUE INDEX UIX_school_grade
  ON school_grade ( -- 학력
    name ASC -- 학력명
  );

ALTER TABLE school_grade
  MODIFY COLUMN sg_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '학력번호';

-- 학생
ALTER TABLE students
  ADD CONSTRAINT FK_members_TO_students -- 회원 -> 학생
    FOREIGN KEY (
      student_id -- 학생번호
    )
    REFERENCES members ( -- 회원
      member_id -- 회원번호
    );

-- 강의
ALTER TABLE lectures
  ADD CONSTRAINT FK_managers_TO_lectures -- 매니저 -> 강의
    FOREIGN KEY (
      manager_id -- 매니저번호
    )
    REFERENCES managers ( -- 매니저
      manager_id -- 매니저번호
    );

-- 강사
ALTER TABLE teachers
  ADD CONSTRAINT FK_members_TO_teachers -- 회원 -> 강사
    FOREIGN KEY (
      teacher_id -- 강사번호
    )
    REFERENCES members ( -- 회원
      member_id -- 회원번호
    );

-- 매니저
ALTER TABLE managers
  ADD CONSTRAINT FK_members_TO_managers -- 회원 -> 매니저
    FOREIGN KEY (
      manager_id -- 매니저번호
    )
    REFERENCES members ( -- 회원
      member_id -- 회원번호
    );

-- 교실
ALTER TABLE rooms
  ADD CONSTRAINT FK_centers_TO_rooms -- 교육센터 -> 교실
    FOREIGN KEY (
      center_id -- 교육센터번호
    )
    REFERENCES centers ( -- 교육센터
      center_id -- 교육센터번호
    );

-- 수강신청
ALTER TABLE add_drop
  ADD CONSTRAINT FK_lectures_TO_add_drop -- 강의 -> 수강신청
    FOREIGN KEY (
      lecture_id -- 강의번호
    )
    REFERENCES lectures ( -- 강의
      lecture_id -- 강의번호
    );

-- 수강신청
ALTER TABLE add_drop
  ADD CONSTRAINT FK_students_TO_add_drop -- 학생 -> 수강신청
    FOREIGN KEY (
      student_id -- 학생번호
    )
    REFERENCES students ( -- 학생
      student_id -- 학생번호
    );

-- 교실사진
ALTER TABLE room_photos
  ADD CONSTRAINT FK_rooms_TO_room_photos -- 교실 -> 교실사진
    FOREIGN KEY (
      room_id -- 교실번호
    )
    REFERENCES rooms ( -- 교실
      room_id -- 교실번호
    );

-- 강사강의과목
ALTER TABLE teacher_subject
  ADD CONSTRAINT FK_subjects_TO_teacher_subject -- 강의과목 -> 강사강의과목
    FOREIGN KEY (
      subject_id -- 강의과목번호
    )
    REFERENCES subjects ( -- 강의과목
      subject_id -- 강의과목번호
    );

-- 강사강의과목
ALTER TABLE teacher_subject
  ADD CONSTRAINT FK_teachers_TO_teacher_subject -- 강사 -> 강사강의과목
    FOREIGN KEY (
      teacher_id -- 강사번호
    )
    REFERENCES teachers ( -- 강사
      teacher_id -- 강사번호
    );

-- 강사강의배정
ALTER TABLE lecture_teacher
  ADD CONSTRAINT FK_teachers_TO_lecture_teacher -- 강사 -> 강사강의배정
    FOREIGN KEY (
      teacher_id -- 강사번호
    )
    REFERENCES teachers ( -- 강사
      teacher_id -- 강사번호
    );

-- 강사강의배정
ALTER TABLE lecture_teacher
  ADD CONSTRAINT FK_lectures_TO_lecture_teacher -- 강의 -> 강사강의배정
    FOREIGN KEY (
      lecture_id -- 강의번호
    )
    REFERENCES lectures ( -- 강의
      lecture_id -- 강의번호
    );

-- 회원
ALTER TABLE members
  ADD CONSTRAINT FK_school_grade_TO_members -- 학력 -> 회원
    FOREIGN KEY (
      last_sg_id -- 최종학력번호
    )
    REFERENCES school_grade ( -- 학력
      sg_id -- 학력번호
    );
    
SET FOREIGN_KEY_CHECKS = 1;    
    








