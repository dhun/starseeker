-- 星座構成星データ
drop table if exists horoscope_path ;
create table horoscope_path (
    horoscope_code              TEXT    not null
  , hip_num_fm                  INTEGER not null    -- HIP番号の始点
  , hip_num_to                  INTEGER not null    -- HIP番号の終点
  , constraint PK_horoscope_path primary key ( horoscope_code, hip_num_fm, hip_num_to )
);
--
--insert into horoscope_path
--select count(*) from (
    select a.horoscope_code
         , a.hip_num
         , b.horoscope_name
         , b.abbreviated_name
--         , b.right_ascension
--         , b.declination
         , c.right_ascension
         , c.declination
         , c.japanese_name
      from fk_name a
      left join horoscope_data b
        on b.horoscope_code = a.horoscope_code
      left join star_data c
        on c.hip_num = a.hip_num
    order by 1, 2
--)
    ;
