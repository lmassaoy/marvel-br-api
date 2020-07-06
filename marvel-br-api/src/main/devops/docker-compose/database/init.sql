CREATE TABLE IF NOT EXISTS marvel_db.MarvelCharacters (
    characterId INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    marvelId INT(11) NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    translatedDescription VARCHAR(1000),
    thumbnail VARCHAR(200),
    wikiPage VARCHAR(300)
);