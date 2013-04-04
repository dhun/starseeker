-- 星座データ
drop table if exists horoscope_data ;
create table horoscope_data (
    horoscope_id                INTEGER not null    -- 星座ID
  , horoscope_code              TEXT    not null    -- 略符
  , horoscope_name              TEXT    not null    -- 学名
  , abbreviated_name            TEXT    not null    -- 星座名(日本語)
  , right_ascension             REAL    not null    -- 赤経
  , declination                 REAL    not null    -- 赤緯
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
