-- 星の名前
drop table if exists custom_name ;
create table custom_name (
    hip_num                     INTEGER not null    -- HIP番号
  , name                        TEXT                -- 通称
  , memo                        TEXT                -- 備考
  , constraint PK_custom_name primary key ( hip_num )
);

delete from custom_name ;

-- insert into custom_name ( hip_num, name, memo ) values ('', '', null);
insert into custom_name ( hip_num, name, memo ) values ('11767', 'ポラリス', '北極星');
insert into custom_name ( hip_num, name, memo ) values ('32349', 'シリウス', null);
insert into custom_name ( hip_num, name, memo ) values ('30438', 'カノープス', null);

select count(*) from custom_name ;
