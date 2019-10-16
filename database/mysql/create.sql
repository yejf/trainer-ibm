CREATE TABLE IF NOT EXISTS s_customer
(id                         int(7) NOT NULL,
 name                       VARCHAR(50) NOT NULL,
 phone                      VARCHAR(25),
 address                    VARCHAR(400),
 city                       VARCHAR(30),
 state                      VARCHAR(20),
 country                    VARCHAR(30),
 zip_code                   VARCHAR(75),
 credit_rating              VARCHAR(9),
     CONSTRAINT s_customer_id_pk PRIMARY KEY (id),
     CONSTRAINT s_customer_credit_rating_ck
        CHECK (credit_rating IN ('EXCELLENT', 'GOOD', 'POOR')));

INSERT INTO s_customer VALUES (
   201, 'Unisports', '55-2066101',
   '72 Via Bahia', 'Sao Paolo', NULL, 'Brazil', NULL,
   'EXCELLENT');
INSERT INTO s_customer VALUES (
   202, 'OJ Atheletics', '81-20101',
   '6741 Takashi Blvd.', 'Osaka', NULL, 'Japan', NULL,
   'POOR');
INSERT INTO s_customer VALUES (
   203, 'Delhi Sports', '91-10351',
   '11368 Chanakya', 'New Delhi', NULL, 'India', NULL,
   'GOOD');
INSERT INTO s_customer VALUES (
   204, 'Womansport', '1-206-104-0103',
   '281 King Street', 'Seattle', 'Washington', 'USA', NULL,
   'EXCELLENT');
INSERT INTO s_customer VALUES (
   205, 'Kam''s Sporting Goods', '852-3692888',
   '15 Henessey Road', 'Hong Kong', NULL, NULL, NULL,
   'EXCELLENT');
INSERT INTO s_customer VALUES (
   206, 'Sportique', '33-2257201',
   '172 Rue de Rivoli', 'Cannes', NULL, 'France', NULL,
   'EXCELLENT');
INSERT INTO s_customer VALUES (
   207, 'Sweet Rock Sports', '234-6036201',
   '6 Saint Antoine', 'Lagos', NULL, 'Nigeria', NULL,
   'GOOD');
INSERT INTO s_customer VALUES (
   208, 'Muench Sports', '49-527454',
   '435 Gruenestrasse', 'Stuttgart', NULL, 'Germany', NULL,
   'GOOD');
INSERT INTO s_customer VALUES (
   209, 'Beisbol Si!', '809-352689',
   '792 Playa Del Mar', 'San Pedro de Macon''s', NULL, 'Dominican Republic',
   NULL, 'EXCELLENT');
INSERT INTO s_customer VALUES (
   210, 'Futbol Sonora', '52-404562',
   '3 Via Saguaro', 'Nogales', NULL, 'Mexico', NULL,
   'EXCELLENT');
INSERT INTO s_customer VALUES (
   211, 'Kuhn''s Sports', '42-111292',
   '7 Modrany', 'Prague', NULL, 'Czechoslovakia', NULL,
   'EXCELLENT');
INSERT INTO s_customer VALUES (
   212, 'Hamada Sport', '20-1209211',
   '57A Corniche', 'Alexandria', NULL, 'Egypt', NULL,
   'EXCELLENT');
INSERT INTO s_customer VALUES (
   213, 'Big John''s Sports Emporium', '1-415-555-6281',
   '4783 18th Street', 'San Francisco', 'CA', 'USA', NULL,
   'EXCELLENT');
INSERT INTO s_customer VALUES (
   214, 'Ojibway Retail', '1-716-555-7171',
   '415 Main Street', 'Buffalo', 'NY', 'USA', NULL,
   'POOR');
INSERT INTO s_customer VALUES (
   215, 'Sporta Russia', '7-3892456',
   '6000 Yekatamina', 'Saint Petersburg', NULL, 'Russia', NULL,
   'POOR');


CREATE TABLE IF NOT EXISTS s_dept
(id                         int(7) NOT NULL,
 name                       VARCHAR(25) NOT NULL,
 PRIMARY KEY (id),
 UNIQUE (name)
);

INSERT INTO s_dept VALUES (
   10, 'Finance');
INSERT INTO s_dept VALUES (
   31, 'Sales');
INSERT INTO s_dept VALUES (
   41, 'Operations');
INSERT INTO s_dept VALUES (
   50, 'Administration');

CREATE TABLE IF NOT EXISTS s_emp
(id                         int(7) NOT NULL,
 last_name                  VARCHAR(25) NOT NULL,
 first_name                 VARCHAR(25),
 userid                     VARCHAR(8),
 start_date                 DATE,
 comments                   VARCHAR(255),
 manager_id                 int(7),
 title                      VARCHAR(25),
 dept_id                    int(7),
 salary                     float(9),
 commission_pct             float(6),
 PRIMARY KEY (id),
 UNIQUE (userid),
 CHECK (commission_pct IN (10, 12.5, 15, 17.5, 20)));

INSERT INTO s_emp VALUES (
  1, 'Velasquez', 'Carmen', 'cvelasqu',
   '2017-05-12 00:00:00', NULL, NULL, 'President',
   50, 2500, NULL);
   
INSERT INTO s_emp VALUES (
   2, 'Ngao', 'LaDoris', 'lngao',
   '2017-05-08 00:00:00', NULL, 1, 'VP, Operations',
   41, 1450, NULL);
   
INSERT INTO s_emp VALUES (
   3, 'Nagayama', 'Midori', 'mnagayam',
   '2017-01-17 00:00:00', NULL, 1, 'VP, Sales',
   31, 1400, NULL);
   
INSERT INTO s_emp VALUES (
   4, 'Quick-To-See', 'Mark', 'mquickto',
   '2017-06-07 00:00:00', NULL, 1, 'VP, Finance',
   10, 1450, NULL);
   
INSERT INTO s_emp VALUES (
   5, 'Ropeburn', 'Audry', 'aropebur',
   '2017-07-18 00:00:00', NULL, 1, 'VP, Administration',
   50, 1550, NULL);
   
INSERT INTO s_emp VALUES (
   6, 'Urguhart', 'Molly', 'murguhar',
   '2017-01-18 00:00:00', NULL, 2, 'Warehouse Manager',
   41, 1200, NULL);
   
INSERT INTO s_emp VALUES (
   7, 'Menchu', 'Roberta', 'rmenchu',
   '2016-01-12 00:00:00', NULL, 2, 'Warehouse Manager',
   41, 1250, NULL);
   
INSERT INTO s_emp VALUES (
   8, 'Biri', 'Ben', 'bbiri',
   '2016-06-07 00:00:00', NULL, 2, 'Warehouse Manager',
   41, 1100, NULL);
   
INSERT INTO s_emp VALUES (
   9, 'Catchpole', 'Antoinette', 'acatchpo',
   '2017-02-09 00:00:00', NULL, 2, 'Warehouse Manager',
   41, 1300, NULL);
   
INSERT INTO s_emp VALUES (
   10, 'Havel', 'Marta', 'mhavel',
   '2017-02-27 00:00:00', NULL, 2, 'Warehouse Manager',
   41, 1307, NULL);
   
INSERT INTO s_emp VALUES (
   11, 'Magee', 'Colin', 'cmagee',
   '2017-04-14 00:00:00', NULL, 3, 'Sales Representative',
   31, 1400, 10);
   
INSERT INTO s_emp VALUES (
   12, 'Giljum', 'Henry', 'hgiljum',
   '2017-05-12 00:00:00', NULL, 3, 'Sales Representative',
   31, 1490, 12.5);
   
INSERT INTO s_emp VALUES (
   13, 'Sedeghi', 'Yasmin', 'ysedeghi',
   '2017-02-18 00:00:00', NULL, 3, 'Sales Representative',
   31, 1515, 10);
   
INSERT INTO s_emp VALUES (
   14, 'Nguyen', 'Mai', 'mnguyen',
   '2017-03-6 00:00:00', NULL, 3, 'Sales Representative',
   31, 1525, 15);
   
INSERT INTO s_emp VALUES (
   15, 'Dumas', 'Andre', 'adumas',
   '2017-10-09 00:00:00', NULL, 3, 'Sales Representative',
   31, 1450, 17.5);
   
INSERT INTO s_emp VALUES (
   16, 'Maduro', 'Elena', 'emaduro',
   '2017-05-12 00:00:00', NULL, 6, 'Stock Clerk',
   41, 1400, NULL);
   
INSERT INTO s_emp VALUES (
   17, 'Smith', 'George', 'gsmith',
   '2017-05-08 00:00:00', NULL, 6, 'Stock Clerk',
   41, 940, NULL);
   
INSERT INTO s_emp VALUES (
   18, 'Nozaki', 'Akira', 'anozaki',
   '2017-02-09 00:00:00', NULL, 7, 'Stock Clerk',
   41, 1200, NULL);
   
INSERT INTO s_emp VALUES (
   19, 'Patel', 'Vikram', 'vpatel',
   '2017-08-06 00:00:00', NULL, 7, 'Stock Clerk',
   41, 795, NULL);
   
INSERT INTO s_emp VALUES (
   20, 'Newman', 'Chad', 'cnewman',
   '2017-01-09 00:00:00', NULL, 8, 'Stock Clerk',
   41, 750, NULL);
   
INSERT INTO s_emp VALUES (
   21, 'Markarian', 'Alexander', 'amarkari',
   '2017-04-26 00:00:00', NULL, 8, 'Stock Clerk',
   41, 850, NULL);
   
INSERT INTO s_emp VALUES (
   22, 'Chang', 'Eddie', 'echang',
   '2016-11-09 00:00:00', NULL, 9, 'Stock Clerk',
   41, 800, NULL);
   
INSERT INTO s_emp VALUES (
   23, 'Patel', 'Radha', 'rpatel',
   '2017-10-17 00:00:00', NULL, 9, 'Stock Clerk',
   31, 795, NULL);
   
INSERT INTO s_emp VALUES (
   24, 'Dancs', 'Bela', 'bdancs',
   '2017-05-17 00:00:00', NULL, 10, 'Stock Clerk',
   41, 860, NULL);
   
INSERT INTO s_emp VALUES (
   25, 'Schwartz', 'Sylvie', 'sschwart',
   '2017-04-19 00:00:00', NULL, 10, 'Stock Clerk',
   41, 1100, NULL);


ALTER TABLE s_emp
   ADD CONSTRAINT s_emp_manager_id_fk
   FOREIGN KEY (manager_id) REFERENCES s_emp (id);
ALTER TABLE s_emp
   ADD CONSTRAINT s_emp_dept_id_fk
   FOREIGN KEY (dept_id) REFERENCES s_dept (id);