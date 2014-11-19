-- Users
CREATE TABLE Users (
  id      CHAR(24)     PRIMARY KEY,
  name    VARCHAR(100) NOT NULL,
  created TIMESTAMP NOT NULL
) ENGINE=InnoDB;

-- Ways of authenticating a user
CREATE TABLE Identities (
  id           CHAR(24) PRIMARY KEY,  -- User.id
  idType       VARCHAR(10) NOT NULL,  -- Password/Facebook/Google etc
  idCredential VARCHAR(100) NOT NULL, -- password/fbid/gmail etc
  created      TIMESTAMP NOT NULL
) ENGINE=InnoDB;

