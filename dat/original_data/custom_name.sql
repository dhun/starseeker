-- 星データ
drop   table custom_name ;
create table custom_name (
    hip_num         INTEGER not null    -- HIP番号
  , name            TEXT                -- 通称
  , memo            TEXT                -- 備考
  , constraint PK_custom_name primary key ( hip_num )
);

delete from custom_name ;

insert into custom_name ( hip_num, name, memo ) values ('122', 'シリウス', null);

select count(*) from custom_name ;
