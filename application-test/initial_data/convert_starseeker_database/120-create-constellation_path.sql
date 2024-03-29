-- 星座パス
drop table if exists constellation_path ;
create table constellation_path (
    constellation_path_id       INTEGER not null    -- 星座パスID
  , constellation_code          TEXT    not null    -- 星座コード(略符)
  , hip_num_fm                  INTEGER not null    -- HIP番号の始点
  , hip_num_to                  INTEGER not null    -- HIP番号の終点
  , constraint PK_constellation_path primary key ( constellation_path_id )
  , constraint UK_constellation_path unique ( constellation_code, hip_num_fm, hip_num_to )
);

insert into constellation_path (
          constellation_code
        , hip_num_fm
        , hip_num_to
    )
    select b.horoscope_code
         , c.hip_num
         , d.hip_num
      from wikipedia_constellation a
      left join  hoshizora_constellation_name b
        on b.horoscope_code = a.constellation_code
      left join star_data c
        on c.hip_num = a.hip_num_fm
      left join star_data d
        on d.hip_num = a.hip_num_to
    order by 1, 2
    ;

select count(*) from constellation_path ;
