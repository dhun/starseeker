-- �����\�����f�[�^
--drop   table star_data ;
--create table star_data (
--    hip_num         INTEGER not null    -- HIP�ԍ�
--  , right_ascension REAL    not null    -- �Ԍa
--  , declination     REAL    not null    -- �Ԉ�
--  , magnitude       REAL    not null    -- ����
--  , name            TEXT                -- �ʏ�
--  , memo            TEXT                -- ���l
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
