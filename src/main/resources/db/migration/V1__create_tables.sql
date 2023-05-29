create table roles (
    id serial primary key,
    name varchar(64)
);

create table users (
    id uuid primary key,
    username varchar(100),
    image_url text,
    email varchar(200),
    phone_number varchar(11),
    status varchar(8),
    last_updated timestamp,
    password varchar(200),
    user_role int not null,
    foreign key(user_role) references roles(id)
);