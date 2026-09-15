create table url_shortener.url
(
    id         bigint generated always as identity,
    long_url   text                                   not null,
    alias      varchar(100),
    created_at timestamp with time zone default now() not null
);

comment
on table url_shortener.url is 'URL table';

comment
on column url_shortener.url.id is 'Auto increment ID';

comment
on column url_shortener.url.long_url is 'Long URL Column';

comment
on column url_shortener.url.alias is 'Alias from long URL';

comment
on column url_shortener.url.created_at is 'Timestamp for url registration date';

alter table url_shortener.url
    owner to devadmin;
