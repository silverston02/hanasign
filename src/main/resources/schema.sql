CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL COMMENT '이메일',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    user_id VARCHAR(50) UNIQUE NOT NULL COMMENT '아이디',
    registration_number VARCHAR(20) UNIQUE COMMENT '등록번호',
    company_name VARCHAR(100) COMMENT '회사명',
    representative_name VARCHAR(50) COMMENT '대표자명',
    phone_number VARCHAR(20) COMMENT '연락처',
    zip_code VARCHAR(10) COMMENT '우편번호',
    address VARCHAR(255) COMMENT '주소',
    detail_address VARCHAR(255) COMMENT '상세주소',
    is_business BOOLEAN DEFAULT FALSE COMMENT '회원 구분',
    terms_agreed BOOLEAN DEFAULT FALSE COMMENT '이용약관 동의',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
    );