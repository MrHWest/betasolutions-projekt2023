DROP DATABASE IF EXISTS beta_solutions_db;
CREATE SCHEMA beta_solutions_db;

DROP TABLE IF EXISTS beta_solutions_db.users;
CREATE TABLE beta_solutions_db.users (
                                         `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         `name` VARCHAR(99) NOT NULL unique,
                                         `password` VARCHAR(45) NOT NULL,
                                         `is_admin` BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS beta_solutions_db.projects;
CREATE TABLE beta_solutions_db.projects (
                                            `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                            `name` VARCHAR(99) NOT NULL,
                                            `start_date` DATE,
                                            `end_date` DATE
);

DROP TABLE IF EXISTS beta_solutions_db.tasks;
CREATE TABLE beta_solutions_db.tasks (
                                         `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         `name` VARCHAR(99) NOT NULL,
                                         `start_date` DATE,
                                         `end_date` DATE,

                                         `is_pending` BOOLEAN NOT NULL,

                                         `fk_project_id` INT NOT NULL,
                                         FOREIGN KEY (`fk_project_id`) REFERENCES projects(id),

                                         `fk_tasks_id` INT(10),
                                         FOREIGN KEY (`fk_tasks_id`) REFERENCES tasks(id)
);