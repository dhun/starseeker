-- 星座データ
drop table if exists constellation_data ;
create table constellation_data (
    constellation_id            INTEGER not null    -- 星座ID
  , constellation_code          TEXT    not null    -- 星座コード(略符)
  , constellation_name          TEXT    not null    -- 星座名(学名)
  , japanese_name               TEXT    not null    -- 星座名(日本語)
  , right_ascension             REAL    not null    -- 赤経
  , declination                 REAL    not null    -- 赤緯
  , constraint PK_constellation_name primary key ( constellation_id )
  , constraint UK_constellation_name unique ( constellation_code )
);

insert into constellation_data
    select a.horoscope_id
         , a.horoscope_code
         , a.horoscope_name
         , a.abbreviated_name
         , b.right_ascension
         , b.declination
      from hoshizora_constellation_name a
      left join hoshizora_constellation b
        on b.horoscope_id = a.horoscope_id
    ;

select count(*) from constellation_data limit 20 ;
