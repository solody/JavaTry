create table if not exists users (
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null
);

create table if not exists authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username),
    unique key ix_auth_username (username, authority)
);


/*
IMPORTANT:
    If using PostgreSQL:
        - update ALL columns defined with 'timestamp' to 'timestamptz', to ensure that time instants are stored accurately.
    If using MySQL:
        - add 'preserveInstants=true&connectionTimeZone=UTC&forceConnectionTimeZoneToSession=true' to JDBC connection URL
          to ensure that time instants are stored accurately. See https://dev.mysql.com/doc/connector-j/en/connector-j-time-instants.html
*/
create table if not exists oauth2_registered_client (
    id varchar(100) not null,
    client_id varchar(100) not null,
    client_id_issued_at timestamp default current_timestamp not null,
    client_secret varchar(200) default null,
    client_secret_expires_at timestamp default null,
    client_name varchar(200) not null,
    client_authentication_methods varchar(1000) not null,
    authorization_grant_types varchar(1000) not null,
    redirect_uris varchar(1000) default null,
    post_logout_redirect_uris varchar(1000) default null,
    scopes varchar(1000) not null,
    client_settings varchar(2000) not null,
    token_settings varchar(2000) not null,
    primary key (id)
);
