create table users (
    id varchar(255) not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    nick_name varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
);

create table baskets (
                         id varchar(255) not null,
                         user_id varchar(255),
                         PRIMARY KEY (id)
);

-- LINK BETWEEN BASKET AND USER
alter table if exists baskets
    add constraint baskets_fk_user
        foreign key (user_id) references users;

create table brands (
    id varchar(255) not null,
    title varchar(255),
    primary key (id)
);

create table categories (
    id varchar(255) not null,
    title varchar(255),
    primary key (id)
);

create table products (
    id varchar(255) not null,
    available boolean not null,
    price float8 not null,
    composition TEXT not null,
    description TEXT not null,
    gender TEXT not null,
    product_code varchar(255) not null,
    title varchar(255) not null,
    brand_id varchar(255),
    category_id varchar(255),
    primary key (id)
 );

 create table products_instances (
    id varchar(255) not null,
    color TEXT not null,
    available boolean not null,
    link_main_picture TEXT not null,
    link_front_picture TEXT not null,
    link_back_picture TEXT not null,
    color_definition TEXT not null,
    date_created DATE not null,
    product_id varchar(255) not null,
    primary key (id)
 );

  create table sizes (
    id varchar(255) not null,
    size varchar(255) not null,
    product_instance_id varchar(255) not null,
    primary key (id)
 );

 alter table if exists products
    drop constraint if exists products_product_code_unique;

 alter table if exists products
    add constraint products_product_code_unique unique (product_code);

alter table if exists products
    add constraint FKa3a4mpsfdf4d2y6r8ra3sc8mv foreign key (brand_id) references brands;

alter table if exists products
    add constraint FKog2rp4qthbtt2lfyhfo32lsw9 foreign key (category_id) references categories;

alter table if exists products_instances
    add constraint FK4e3bsfqh7xro360a8yhigr8ky foreign key (product_id) references products;

alter table if exists size_quantities
    add constraint FKpbyu30xd4s2b8c959nfw9qym9 foreign key (product_instance_id) references products_instances;

