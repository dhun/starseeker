-- 星座構成星データ
--drop   table star_data ;
--create table star_data (
--    hip_num         INTEGER not null    -- HIP番号
--  , right_ascension REAL    not null    -- 赤径
--  , declination     REAL    not null    -- 赤緯
--  , magnitude       REAL    not null    -- 等級
--  , name            TEXT                -- 通称
--  , memo            TEXT                -- 備考
--  , constraint PK_fk_data primary key ( hip_num )
--);
--
--insert into star_data
select count(*) from (
    select a.horoscope_code
         , a.hip_num
         , b.horoscope_name
         , b.abbreviated_name
--         , b.right_ascension
--         , b.declination
         , c.right_ascension
         , c.declination
         , c.name
      from fk_name a
      left join horoscope_data b
        on b.horoscope_code = a.horoscope_code
      left join star_data c
        on c.hip_num = a.hip_num
    order by 1, 2
)
    ;
