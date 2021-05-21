# Interview-noitechnologies
csv-reader


Core + Advance Java project to store data from csv file having attributes
first_name , last_name , age, dob (MM-DD-YYYY), email, mobile, addressLine1, addressLine2, city, state, country, pin code (6 digit)

Import this project as existing maven project in eclipse or other supported ide.

Things to be remember before execution
1. Make sure attributes in csv file must be in same order as specified above.
2. Provide file path into FILE_PATH constant in Main class.
3. Default delimiter in ',' but you can change by updating constant DELIMITER
4. In DaoClass you must provide database URL, USERNAME, PASSWORD in constant fields.
5. If a duplicate email or mobile number arrives then existing entry will remians there will be no replacement.
6. MySQL queries to create tables:
	
	create table user(
	email varchar(255) primary key,
	mobile bigint unique not null,
	dob date not null,
	age int not null,
	first_name varchar(20) not null,
	last_name varchar(20) not null
	);

	
	create table address(
	line1 varchar(50) not null,
	line2 varchar(50) not null,
	city varchar(25) not null,
	state varchar(25) not null,
	country varchar(25) not null,
	pincode int not null,
	email varchar(255),
	foreign key(email) references user(email) on delete set null
	);
