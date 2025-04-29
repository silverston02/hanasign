-- 계약 테이블 샘플 데이터
INSERT INTO contracts (contract_title, contract_content, contract_document_url, contract_status, contract_created_at, contract_updated_at)
VALUES 
('매매계약서', '부동산 매매계약 내용입니다.', 'http://example.com/doc1.pdf', 'DRAFT', NOW(), NOW()),
('임대차계약서', '사무실 임대차계약 내용입니다.', 'http://example.com/doc2.pdf', 'PENDING', NOW(), NOW()),
('용역계약서', 'IT 용역계약 내용입니다.', 'http://example.com/doc3.pdf', 'COMPLETED', NOW(), NOW());

-- 계약 서명 테이블 샘플 데이터
INSERT INTO contract_signatures (contract_id, signer_id, signature_image_url, signed_at, ip_address, device_info)
VALUES 
(1, 'user1', 'http://example.com/signatures/sig1.png', NOW(), '192.168.1.1', 'Chrome Windows'),
(2, 'user2', 'http://example.com/signatures/sig2.png', NOW(), '192.168.1.2', 'Safari MacOS'),
(3, 'user3', 'http://example.com/signatures/sig3.png', NOW(), '192.168.1.3', 'Firefox Linux'); 