CREATE TABLE IF NOT EXISTS peoples (
                                       people_id bigint auto_increment
                                           primary key,
                                       age       int          null,
                                       name      varchar(255) null
);