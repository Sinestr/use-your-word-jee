DROP DATABASE IF EXISTS useyourwords;

CREATE DATABASE IF NOT EXISTS useyourwords;
USE useyourwords;
# -----------------------------------------------------------------------------
#       TABLE : USER
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS USER
 (
   USER_ID INT(8) NOT NULL  ,
   USER_LOGIN VARCHAR(100) NULL  ,
   USER_PASSWORD VARCHAR(100) NULL  ,
   USER_PSEUDO VARCHAR(100) NULL  ,
   USER_ADMIN BOOLEAN NULL
   , PRIMARY KEY (USER_ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : PLAY
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS PLAY
 (
   PLAY_ID INT(12) NOT NULL  ,
   USER_ID INT(8) NULL  ,
   GAME_ID INT(10) NULL  ,
   PLAY_SCORE INT(8) NULL
   , PRIMARY KEY (PLAY_ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : MEDIA
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS MEDIA
 (
   MEDIA_ID INT(4) NOT NULL  ,
   MEDIA_TYPE_ID INT(2) NULL  ,
   MEDIA_PATH VARCHAR(500) NULL  ,
   MEDIA_CONTENT VARCHAR(2500) NULL
   , PRIMARY KEY (MEDIA_ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : GAME
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS GAME
 (
   GAME_ID INT(10) NOT NULL  ,
   GAME_NB_PLAYERS INT(2) NULL  ,
   GAME_ACCESS_CODE VARCHAR(6) NULL  ,
   GAME_CREATED_AT DATE NULL  
   , PRIMARY KEY (GAME_ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : MEDIA_TYPE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS MEDIA_TYPE
 (
   MEDIA_TYPE_ID INT(2) NOT NULL  ,
   MEDIA_TYPE_LIBELLE VARCHAR(100) NULL
   , PRIMARY KEY (MEDIA_TYPE_ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : CONSITUTE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS CONSITUTE
 (
   GAME_ID INT(10) NOT NULL  ,
   MEDIA_ID INT(4) NOT NULL
   , PRIMARY KEY (GAME_ID,MEDIA_ID) 
 ) 
 comment = "";


# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE PLAY 
  ADD FOREIGN KEY FK_PLAY_USER (USER_ID)
      REFERENCES USER (USER_ID) ;


ALTER TABLE PLAY 
  ADD FOREIGN KEY FK_PLAY_GAME (GAME_ID)
      REFERENCES GAME (GAME_ID) ;


ALTER TABLE MEDIA 
  ADD FOREIGN KEY FK_MEDIA_MEDIA_TYPE (MEDIA_TYPE_ID)
      REFERENCES MEDIA_TYPE (MEDIA_TYPE_ID) ;


ALTER TABLE CONSTITUTE
  ADD FOREIGN KEY FK_CONSTITUTE_GAME (GAME_ID)
      REFERENCES GAME (GAME_ID) ;


ALTER TABLE CONSITUTE 
  ADD FOREIGN KEY FK_CONSITUTE_MEDIA (MEDIA_ID)
      REFERENCES MEDIA (MEDIA_ID) ;

