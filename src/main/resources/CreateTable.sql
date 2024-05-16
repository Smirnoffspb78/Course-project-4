--Альпинисты
CREATE TABLE IF NOT EXISTS tb_climbers(
id BIGSERIAL PRIMARY KEY,
number_phone BIGINT NOT NULL UNIQUE CHECK ((number_phone<=99999999999) AND (number_phone>=10000000000)),
first_name VARCHAR(200) NOT NULL,
second_name VARCHAR(200) NOT NULL,
email VARCHAR(200) NOT NULL
);

--Страны
CREATE TABLE IF NOT EXISTS tb_countries(
	country VARCHAR(100) PRIMARY KEY
);

--Горы
CREATE TABLE IF NOT EXISTS tb_mountains(
id SERIAL PRIMARY KEY ,
	mountain_name VARCHAR(150) NOT NULL,
	height REAL NOT NULL CHECK(height>=100)
);

--Связь многи ко многи Горы-Страны
CREATE TABLE IF NOT EXISTS tb_mountain_country(
country_id VARCHAR(100),
mountain_id INTEGER,
PRIMARY KEY (country_id, mountain_id),
FOREIGN KEY(country_id) REFERENCES tb_countries(country),
FOREIGN KEY(mountain_id) REFERENCES tb_mountains(id)
);

--Руководители
CREATE TABLE IF NOT EXISTS tb_supervisors(
id BIGSERIAL PRIMARY KEY,
	first_name VARCHAR(200) NOT NULL,
	second_name VARCHAR(200) NOT NULL,
	surname VARCHAR(200) NOT NULL
);

--Группы альпинистов
CREATE TABLE IF NOT EXISTS tb_groups_climbers(
id BIGSERIAL PRIMARY KEY,
	mountain_id INT NOT NULL,
	start_date DATE NOT NULL CHECK (start_date>CURRENT_DATE),
	finish_date DATE NOT NULL CHECK (finish_date>=start_date),
	max_climbers INTEGER NOT NULL CHECK(max_climbers>0),
	is_open BOOLEAN NOT NULL DEFAULT true,
	price REAL NOT NULL CHECK (price>0),
	supervisor_id BIGINT NOT NULL,
	FOREIGN KEY (mountain_id) REFERENCES tb_mountains(id),
	FOREIGN KEY (supervisor_id) REFERENCES tb_supervisors(id)
);

--Связь многие ко многим альпинисты-группы
CREATE TABLE IF NOT EXISTS tb_climber_group_climbers(
	climber_id BIGINT,
	group_climbers_id BIGINT,
	PRIMARY KEY (climber_id, group_climbers_id),
	FOREIGN KEY (climber_id) REFERENCES tb_climbers(id),
	FOREIGN KEY (group_climbers_id) REFERENCES tb_groups_climbers(id)
);

--Резерв (выражается через связь многие ко многим) - один альпинист не может несколько раз попасть в резерв на одну гору
CREATE TABLE tb_reserve(
        climber_id BIGINT not null,
        mountain_id INT not null,
        primary key (climber_id, mountain_id),
	FOREIGN KEY (climber_id) REFERENCES tb_climbers(id),
	FOREIGN KEY (mountain_id) REFERENCES tb_mountains(id)
)
