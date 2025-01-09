-- Table users
CREATE TABLE `users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table themes
CREATE TABLE `themes` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL
);

-- Table articles
CREATE TABLE `articles` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `author_id` INT NOT NULL,
    `theme_id` INT NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `content` TEXT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_author` FOREIGN KEY (`author_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_theme` FOREIGN KEY (`theme_id`) REFERENCES `themes`(`id`) ON DELETE CASCADE
);

-- Table theme_subscription
CREATE TABLE `theme_subscription` (
    `theme_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    PRIMARY KEY (`theme_id`, `user_id`),
    CONSTRAINT `fk_subscription_theme` FOREIGN KEY (`theme_id`) REFERENCES `themes`(`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_subscription_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- Table comments
CREATE TABLE `comments` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `article_id` INT NOT NULL,
    `author_id` INT NOT NULL,
    `content` TEXT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_comment_article` FOREIGN KEY (`article_id`) REFERENCES `articles`(`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_comment_author` FOREIGN KEY (`author_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- Table jwt
CREATE TABLE `jwt` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `disable` BOOLEAN NOT NULL,
   `expired` BOOLEAN NOT NULL,
   `bearer` VARCHAR(255) NOT NULL,
   `refresh_token_value` VARCHAR(255),
   `refresh_token_expired` BOOLEAN,
   `refresh_token_creation` TIMESTAMP,
   `refresh_token_expiration` TIMESTAMP,
   `user_id` INT NOT NULL,
   CONSTRAINT `fk_jwt_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);
-- Insérer des utilisateurs
INSERT INTO `users` (`username`, `email`, `password`)
VALUES ('admin', 'admin@example.com', '$2a$10$gF2cB1JXcx.pFk.3nF29dVgjGdTpCpxWvLr7tQngG0XtOpDujn9fG');

-- Insérer des thèmes
INSERT INTO `themes` (`title`, `description`)
VALUES
    ('JavaScript', 'Un langage de programmation orienté objet, utilisé principalement pour le développement web côté client.'),
    ('Java', 'Un langage de programmation orienté objet, très utilisé pour des applications serveur et des applications Android.'),
    ('Python', 'Un langage de programmation polyvalent, très utilisé pour l''intelligence artificielle, le développement web, et la science des données.'),
    ('IA', 'L''intelligence artificielle regroupe des techniques permettant aux machines d''apprendre et d''effectuer des tâches intelligentes.'),
    ('Spring Boot', 'Un framework Java pour créer des applications web, qui simplifie la configuration et le déploiement des projets Spring.'),
    ('Angular', 'Un framework JavaScript pour construire des applications web dynamiques et interactives, développé par Google.'),
    ('Docker', 'Une plateforme permettant de développer, expédier et exécuter des applications dans des containers isolés, afin de faciliter la gestion des dépendances et le déploiement.');

-- Insérer des articles
INSERT INTO `articles` (`author_id`, `theme_id`, `title`, `content`)
VALUES (1, 1, 'Première article', 'Lorem Ipsum');

-- Insérer des abonnements de thèmes
INSERT INTO `theme_subscription` (`theme_id`, `user_id`)
VALUES (1, 1), (2, 1);

-- Insérer des commentaires
INSERT INTO `comments` (`article_id`, `author_id`, `content`)
VALUES (1, 1, 'Cet article est super !');