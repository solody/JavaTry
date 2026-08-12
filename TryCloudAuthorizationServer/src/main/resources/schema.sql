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

create table if not exists oauth2_authorization (
    id varchar(100) not null,
    registered_client_id varchar(100) not null,
    principal_name varchar(200) not null,
    authorization_grant_type varchar(100) not null,
    authorized_scopes varchar(1000) default null,
    attributes blob default null,
    state varchar(500) default null,
    authorization_code_value blob default null,
    authorization_code_issued_at timestamp default null,
    authorization_code_expires_at timestamp default null,
    authorization_code_metadata blob default null,
    access_token_value blob default null,
    access_token_issued_at timestamp default null,
    access_token_expires_at timestamp default null,
    access_token_metadata blob default null,
    access_token_type varchar(100) default null,
    access_token_scopes varchar(1000) default null,
    oidc_id_token_value blob default null,
    oidc_id_token_issued_at timestamp default null,
    oidc_id_token_expires_at timestamp default null,
    oidc_id_token_metadata blob default null,
    refresh_token_value blob default null,
    refresh_token_issued_at timestamp default null,
    refresh_token_expires_at timestamp default null,
    refresh_token_metadata blob default null,
    user_code_value blob default null,
    user_code_issued_at timestamp default null,
    user_code_expires_at timestamp default null,
    user_code_metadata blob default null,
    device_code_value blob default null,
    device_code_issued_at timestamp default null,
    device_code_expires_at timestamp default null,
    device_code_metadata blob default null,
    primary key (id)
);

create table if not exists oauth2_authorization_consent (
    registered_client_id varchar(100) not null,
    principal_name varchar(200) not null,
    authorities varchar(1000) not null,
    primary key (registered_client_id, principal_name)
);
