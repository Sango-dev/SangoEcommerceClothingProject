create table users (
    id varchar(255) not null,
    email varchar(255) unique not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    nick_name varchar(255) unique not null,
    password varchar(255) not null,
    phone varchar(255) unique not null,
    role varchar(255) not null,
    primary key (id)
);

create table baskets (
    id varchar(255) not null,
    user_id varchar(255) not null,
    PRIMARY KEY (id)
);

create table brands (
    id varchar(255) not null,
    title varchar(255) unique not null,
    primary key (id)
);

create table categories (
    id varchar(255) not null,
    title varchar(255) unique not null,
    primary key (id)
);

 create table orders (
    id varchar(255) not null,
    address varchar(255) not null,
    created DATE not null,
    delivery varchar(255) not null,
    email varchar(255) not null,
    payment varchar(255) not null,
    phone varchar(255) not null,
    recipient varchar(255) not null,
    status varchar(255) not null,
    sum float8 not null,
    updated DATE not null,
    user_id varchar(255) not null,
    primary key (id)
 );

 create table orders_details (
     id varchar(255) not null,
     amount int8 not null,
     link_of_main_picture varchar(255) not null,
     price float8 not null,
     product_instance_id varchar(255) not null,
     product_instance_title varchar(255) not null,
     size varchar(255) not null,
     order_id varchar(255) not null,
     primary key (id)
 );

create table products (
    id varchar(255) not null,
    underwear boolean not null,
    price float8 not null,
    composition TEXT not null,
    description TEXT not null,
    gender TEXT not null,
    product_code varchar(255) not null,
    title varchar(255) not null,
    brand_id varchar(255) not null,
    category_id varchar(255) not null,
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

 create table basket_units (
    id varchar(255) not null,
    quantity int8 not null,
    size varchar(255) not null,
    basket_id varchar(255) not null,
    product_id varchar(255) not null,
    primary key (id)
);

  create table sizes (
    id varchar(255) not null,
    size varchar(255) not null,
    product_instance_id varchar(255) not null,
    primary key (id)
 );

 create table reviews (
    id varchar(255) not null,
    product_id varchar(255) not null,
    user_id varchar(255) not null,
    rate integer not null,
    title varchar(255) not null,
    picture TEXT not null,
    comment TEXT not null,
    updated DATE not null,
    primary key (id)
);

alter table
    if exists products
        add constraint products_product_code_unique
        unique (product_code);

alter table
    if exists basket_units
        add constraint FK233fo9if46qecwwpceymecgto foreign key (basket_id)
        references baskets ON delete CASCADE;

alter table
    if exists basket_units
        add constraint FKmk5ci55bolunhjcsblx6ls0i9 foreign key (product_id)
        references products_instances ON delete CASCADE;

alter table
    if exists baskets
        add constraint baskets_fk_user foreign key (user_id)
        references users(id) ON delete CASCADE;

alter table
    if exists orders
        add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id)
        references users(id) ON delete CASCADE;

alter table
    if exists orders_details
        add constraint FK5o977kj2vptwo70fu7w7so9fe foreign key (order_id)
        references orders(id) ON delete CASCADE;

alter table
    if exists products
        add constraint FKa3a4mpsfdf4d2y6r8ra3sc8mv foreign key (brand_id)
        references brands(id) ON delete CASCADE;

alter table
    if exists products
        add constraint FKog2rp4qthbtt2lfyhfo32lsw9 foreign key (category_id)
        references categories(id) ON delete CASCADE;

alter table
    if exists products_instances
        add constraint FK4e3bsfqh7xro360a8yhigr8ky foreign key (product_id)
        references products(id) ON delete CASCADE;

alter table
    if exists reviews
        add constraint fk_product_clothes foreign key (product_id)
        references products(id) ON delete CASCADE;

alter table
    if exists reviews
        add constraint fk_users foreign key (user_id)
        references users(id) ON delete CASCADE;

alter table
    if exists sizes
        add constraint FKh8riqimoc7xltsx92knq6enb6 foreign key (product_instance_id)
        references products_instances(id) ON delete CASCADE;
