-- PASSWORD: pass123
INSERT INTO CUSTOMERS (ID, USERNAME, PASSWORD, ROLE) VALUES (default, 'customer1', '$2a$10$65nJrdce9HTLCHPg6RtgLei87oby/hE4YwPpes0juUXzocySLD8Ou', 'USER');

-- PASSWORD: pass123456
INSERT INTO CUSTOMERS (ID, USERNAME, PASSWORD, ROLE) VALUES (default, 'customer2', '$2a$10$WGoXvPW9rTdaamUL0g7xN.4TTlt10bVs5J08U1RHsR6GtV1LXFvuC', 'USER');

INSERT INTO ASSETS (ID, CUSTOMER_ID, ASSET_NAME, SIZE, USABLE_SIZE, VERSION) VALUES (default, 1, 'Stock 1', 10, 10, 1);

INSERT INTO ASSETS (ID, CUSTOMER_ID, ASSET_NAME, SIZE, USABLE_SIZE, VERSION) VALUES (default, 1, 'Stock 2', 20, 20, 1);

INSERT INTO ASSETS (ID, CUSTOMER_ID, ASSET_NAME, SIZE, USABLE_SIZE, VERSION) VALUES (default, 2, 'Stock 1', 4, 4, 1);

