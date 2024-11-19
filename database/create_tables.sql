CREATE TABLE IF NOT EXISTS project ( 
    id            serial PRIMARY KEY NOT NULL,
    name          varchar(255),
    description   text,
    start_date    timestamp,
    end_date      timestamp,
    status        boolean
);

CREATE TABLE IF NOT EXISTS resource ( 
    id            serial PRIMARY KEY NOT NULL,
    name          varchar(255),
    role          varchar(255)
);

CREATE TABLE IF NOT EXISTS task_status ( 
    id            serial PRIMARY KEY NOT NULL,
    description   varchar(255)
);

CREATE TABLE IF NOT EXISTS task ( 
    id            	serial PRIMARY KEY NOT NULL,
    title         	varchar(255),
    description   	text,
    start_date    	timestamp,
    end_date      	timestamp,
    task_status_id 	integer,
    resource_id    	integer NOT NULL,
    project_id     	integer NOT NULL
);

CREATE TABLE IF NOT EXISTS project_manager ( 
    project_id    integer NOT NULL,
    resource_id   integer NOT NULL,
    PRIMARY KEY (project_id, resource_id)
);

CREATE TABLE IF NOT EXISTS users ( 
    id            serial PRIMARY KEY NOT NULL,
    name          varchar(255),
    email         varchar(255) UNIQUE NOT NULL,
    password      varchar(255) NOT NULL,
    role          varchar(255)
);

ALTER TABLE project_manager ADD CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE project_manager ADD CONSTRAINT fk_resource FOREIGN KEY (resource_id) REFERENCES resource(id);

ALTER TABLE task ADD CONSTRAINT fk_task_status FOREIGN KEY (task_status_id) REFERENCES task_status(id);

ALTER TABLE task ADD CONSTRAINT fk_task_resource FOREIGN KEY (resource_id) REFERENCES resource(id);

ALTER TABLE task ADD CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES project(id);

COMMENT ON COLUMN project.id IS 'Primary key';
COMMENT ON COLUMN project.name IS 'Project name';
COMMENT ON COLUMN project.description IS 'Project description';
COMMENT ON COLUMN project.start_date IS 'Project start date';
COMMENT ON COLUMN project.end_date IS 'Project end date';
COMMENT ON COLUMN project.status IS 'Project status - active|inactive';

COMMENT ON COLUMN resource.id IS 'Primary key';
COMMENT ON COLUMN resource.name IS 'Resource name';
COMMENT ON COLUMN resource.role IS 'Resource role';

COMMENT ON TABLE task_status IS 'Stores possible statuses of a task: pending|in progress|etc';
COMMENT ON COLUMN task_status.id IS 'Primary key';
COMMENT ON COLUMN task_status.description IS 'Status description';

COMMENT ON COLUMN task.title IS 'Task title';
COMMENT ON COLUMN task.description IS 'Task description';
COMMENT ON COLUMN task.start_date IS 'Task start date';
COMMENT ON COLUMN task.end_date IS 'Task end date';
COMMENT ON COLUMN task.task_status_id IS 'Foreign key - task status';
COMMENT ON COLUMN task.resource_id IS 'Foreign key - resource responsible for the task';

COMMENT ON TABLE project_manager IS 'Stores the relationship between manager and project';
COMMENT ON COLUMN project_manager.project_id IS 'Foreign key - project';
COMMENT ON COLUMN project_manager.resource_id IS 'Foreign key - resource';