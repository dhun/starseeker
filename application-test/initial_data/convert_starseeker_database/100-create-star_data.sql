-- 星データ
drop table if exists star_data ;
create table star_data (
    hip_num                     INTEGER not null    -- HIP番号
  , right_ascension             REAL    not null    -- 赤径
  , declination                 REAL    not null    -- 赤緯
  , magnitude                   REAL    not null    -- 等級
  , bayer_code                  TEXT                -- バイエル符号／フラムスティード番号
  , simbad                      TEXT                -- 英名・SIMBAD
  , japanese_name               TEXT                -- 日本語名
  , memo                        TEXT                -- 備考
  , constraint PK_star_data primary key ( hip_num )
);

insert into star_data
    select a.hip_num
         , a.right_ascension
         , a.declination
         , a.magnitude
         , b.bayer_code
         , b.simbad
         , b.japanese_name
         , b.memo
      from nasa_star_data a
      left join wikipedia_star_name b
        on b.hip_num = a.hip_num
    ;

select count(*), count(bayer_code), count(japanese_name) from star_data ;
