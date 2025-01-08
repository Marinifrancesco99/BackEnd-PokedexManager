-- Creazione della tabella 'pokemon'
CREATE TABLE pokemon (
    national_number INT NOT NULL,
    gen VARCHAR(3),
    english_name VARCHAR(255),
    primary_type VARCHAR(255),
    secondary_type VARCHAR(255),
    classification VARCHAR(255),
    percent_male NUMERIC(5,2),
    percent_female NUMERIC(5,2),
    height_m NUMERIC(4,1),
    weight_kg NUMERIC(5,1),
    capture_rate INT,
    hp INT,
    attack INT,
    defense INT,
    speed INT,
    abilities_0 VARCHAR(255),
    abilities_1 VARCHAR(255),
    abilities_special VARCHAR(255),
    is_sublegendary INT,
    is_legendary INT,
    is_mythical INT,
    evochain_0 VARCHAR(255),
    evochain_2 VARCHAR(255),
    evochain_4 VARCHAR(255),
    mega_evolution VARCHAR(255),
    description TEXT,
    PRIMARY KEY (national_number)
);

-- Relazioni con altre tabelle
ALTER TABLE pokedex
ADD CONSTRAINT pokedex_national_number_fkey FOREIGN KEY (national_number)
REFERENCES pokemon(national_number)
ON DELETE CASCADE;

ALTER TABLE wishlist
ADD CONSTRAINT wishlist_national_number_fkey FOREIGN KEY (national_number)
REFERENCES pokemon(national_number)
ON DELETE CASCADE;


-- Creazione della tabella 'users'
CREATE TABLE users (
    id_user SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Relazioni con altre tabelle
ALTER TABLE pokedex
ADD CONSTRAINT pokedex_id_user_fkey FOREIGN KEY (id_user)
REFERENCES users(id_user)
ON DELETE CASCADE;

ALTER TABLE wishlist
ADD CONSTRAINT wishlist_id_user_fkey FOREIGN KEY (id_user)
REFERENCES users(id_user)
ON DELETE CASCADE;


-- Creazione della tabella 'pokedex'
CREATE TABLE pokedex (
    id_pokedex SERIAL PRIMARY KEY,
    national_number INT NOT NULL,
    id_user INT NOT NULL
);

-- Relazioni con altre tabelle
ALTER TABLE pokedex
ADD CONSTRAINT pokedex_national_number_fkey FOREIGN KEY (national_number)
REFERENCES pokemon(national_number)
ON DELETE CASCADE;

ALTER TABLE pokedex
ADD CONSTRAINT pokedex_id_user_fkey FOREIGN KEY (id_user)
REFERENCES users(id_user)
ON DELETE CASCADE;



-- Creazione della tabella 'wishlist'
CREATE TABLE wishlist (
    id_wishlist SERIAL PRIMARY KEY,
    national_number INT NOT NULL,
    id_user INT NOT NULL
);


-- Relazioni con altre tabelle
ALTER TABLE wishlist
ADD CONSTRAINT wishlist_national_number_fkey FOREIGN KEY (national_number)
REFERENCES pokemon(national_number)
ON DELETE CASCADE;

ALTER TABLE wishlist
ADD CONSTRAINT wishlist_id_user_fkey FOREIGN KEY (id_user)
REFERENCES users(id_user)
ON DELETE CASCADE;
