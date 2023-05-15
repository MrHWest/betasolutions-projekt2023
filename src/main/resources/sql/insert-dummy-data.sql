-- Clear all tables
TRUNCATE TABLE beta_solutions_db.tasks;
TRUNCATE TABLE beta_solutions_db.users;
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE beta_solutions_db.projects;
SET FOREIGN_KEY_CHECKS = 1;


-- Insert admin and non-admin user
INSERT INTO beta_solutions_db.users (name, password, is_admin) VALUES ('admin', '1234', true);
INSERT INTO beta_solutions_db.users (name, password, is_admin) VALUES ('udvikler', '1234', false);


-- Insert projects
INSERT INTO beta_solutions_db.projects (name, start_date, end_date)
VALUES ('Projekt 1', STR_TO_DATE('2023-04-11', '%Y-%m-%d'), STR_TO_DATE('2023-07-11', '%Y-%m-%d'));

INSERT INTO beta_solutions_db.projects (name, start_date, end_date)
VALUES ('Projekt 2', STR_TO_DATE('2002-02-02', '%Y-%m-%d'), STR_TO_DATE('2023-05-10', '%Y-%m-%d'));

-- Insert tasks
INSERT INTO beta_solutions_db.tasks (name, start_date, end_date, fk_project_id, fk_tasks_id)
VALUES (
           'Proj1-task1',
           STR_TO_DATE('2023-04-11', '%Y-%m-%d'),
           STR_TO_DATE('2023-04-21', '%Y-%m-%d'),
           true,
           1,
           NULL
       );

INSERT INTO beta_solutions_db.tasks (name, start_date, end_date, fk_project_id, fk_tasks_id)
VALUES (
           'Proj1-task2',
           STR_TO_DATE('2023-04-21', '%Y-%m-%d'),
           STR_TO_DATE('2023-04-25', '%Y-%m-%d'),
           false,
           1,
           NULL
       );

INSERT INTO beta_solutions_db.tasks (name, start_date, end_date, fk_project_id, fk_tasks_id)
VALUES (
           'Proj1-task2-subtask1',
           STR_TO_DATE('2023-04-21', '%Y-%m-%d'),
           STR_TO_DATE('2023-04-22', '%Y-%m-%d'),
           false,
           1,
           2
       );

INSERT INTO beta_solutions_db.tasks (name, start_date, end_date, fk_project_id, fk_tasks_id)
VALUES (
           'Proj1-task2-subtask2',
           STR_TO_DATE('2023-04-23', '%Y-%m-%d'),
           STR_TO_DATE('2023-04-25', '%Y-%m-%d'),
           false,
           1,
           2
       );

INSERT INTO beta_solutions_db.tasks (name, start_date, end_date, fk_project_id, fk_tasks_id)
VALUES (
           'Proj1-task2-subtask3',
           STR_TO_DATE('2023-04-22', '%Y-%m-%d'),
           STR_TO_DATE('2023-04-24', '%Y-%m-%d'),
           1,
           2
       );

INSERT INTO beta_solutions_db.tasks (name, start_date, end_date, fk_project_id, fk_tasks_id)
VALUES (
           'Proj1-task3',
           STR_TO_DATE('2023-04-15', '%Y-%m-%d'),
           STR_TO_DATE('2023-04-17', '%Y-%m-%d'),
           false,
           1,
           NULL
       );