create table if not exists CIPHERKEY(
    id INT,
    value VARCHAR(512)
);

merge into CIPHERKEY KEY(id) values (1, 'thisismyfirstkey');
merge into CIPHERKEY KEY(id) values (2, 'thisismysecondkey');
merge into CIPHERKEY KEY(id) values (3, 'thisismythirdkey');
