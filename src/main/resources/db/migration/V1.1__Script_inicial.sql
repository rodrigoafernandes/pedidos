CREATE TABLE GER_ITEM (
  CD_ITEM BIGINT AUTO_INCREMENT PRIMARY KEY,
  NM_ITEM VARCHAR(60) NOT NULL CHARSET UTF8,
  VL_UNITARIO DECIMAL(18,2) NOT NULL
);