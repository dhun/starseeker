-- ���f�[�^
drop   table custom_name ;
create table custom_name (
    hip_num         INTEGER not null    -- HIP�ԍ�
  , name            TEXT                -- �ʏ�
  , memo            TEXT                -- ���l
  , constraint PK_custom_name primary key ( hip_num )
);

delete from custom_name ;

insert into custom_name ( hip_num, name, memo ) values ('122', '�V���E�X', null);

select count(*) from custom_name ;
