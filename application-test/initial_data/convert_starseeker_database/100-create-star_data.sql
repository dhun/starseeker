-- ���f�[�^
drop   table star_data ;
create table star_data (
    hip_num         INTEGER not null    -- HIP�ԍ�
  , right_ascension REAL    not null    -- �Ԍa
  , declination     REAL    not null    -- �Ԉ�
  , magnitude       REAL    not null    -- ����
  , name            TEXT                -- �ʏ�
  , memo            TEXT                -- ���l
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
