CREATE TABLE IF NOT EXISTS following (
    creatorID VARCHAR(50) NOT NULL,
    followerID VARCHAR(50) NOT NULL,
    creatorName VARCHAR(100),
    followerName VARCHAR(100),
    creatorUsername VARCHAR(50),
    followerUsername VARCHAR(50),
    status VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS log (
    ID INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(256),
    IP VARCHAR(50),
    endpoint VARCHAR(50),
    requestedAt DATETIME DEFAULT NOW(),
    PRIMARY KEY (ID)
);

-- Add any seeding data here, for example:
-- INSERT INTO following (creatorID, followerID, status) VALUES ('1', '2', 'APPROVED');
