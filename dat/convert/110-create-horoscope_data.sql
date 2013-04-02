-- ¯Àf[^
drop   table horoscope_data ;
create table horoscope_data (
    horoscope_id        INTEGER not null    -- ¯ÀID
  , horoscope_code      TEXT    not null    -- ª
  , horoscope_name      TEXT    not null    -- w¼
  , abbreviated_name    TEXT    not null    -- ¯À¼(ú{ê)
  , right_ascension     REAL    not null    -- Ôo
  , declination         REAL    not null    -- ÔÜ
  , constraint PK_constellation_name primary key ( horoscope_id )
  , constraint UK_constellation_name unique ( horoscope_code )
);

insert into horoscope_data
    select a.horoscope_id
         , a.horoscope_code
         , a.horoscope_name
         , a.abbreviated_name
         , b.right_ascension
         , b.declination
      from constellation_name a
      left join constellation b
        on b.horoscope_id = a.horoscope_id
    ;

select count(*) from horoscope_data limit 20 ;
