-- 星データ
drop table if exists star_data ;
create table star_data (
    hip_num                     INTEGER not null    -- HIP番号
  , right_ascension             REAL    not null    -- 赤径
  , declination                 REAL    not null    -- 赤緯
  , magnitude                   REAL    not null    -- 等級
  , name                        TEXT                -- 通称
  , memo                        TEXT                -- 備考
  , constraint PK_fk_data primary key ( hip_num )
);

insert into star_data
    select a.hip_num
         , a.right_ascension
         , a.declination
         , a.magnitude
         , b.name
         , b.memo
      from fk_data a
      left join custom_name b
        on b.hip_num = a.hip_num
    ;

select count(*) from star_data limit 20 ;
