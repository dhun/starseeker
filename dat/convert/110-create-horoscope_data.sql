-- �����f�[�^
drop   table horoscope_data ;
create table horoscope_data (
    horoscope_id        INTEGER not null    -- ����ID
  , horoscope_code      TEXT    not null    -- ����
  , horoscope_name      TEXT    not null    -- �w��
  , abbreviated_name    TEXT    not null    -- ������(���{��)
  , right_ascension     REAL    not null    -- �Ԍo
  , declination         REAL    not null    -- �Ԉ�
  , constraint PK_constellation_name primary key ( horoscope_id )
  , constraint UK_constellation_name unique ( horoscope_code )
);

insert into horoscope_data
    select a.horoscope_id
         , a.horoscope_code
         , a.horoscope_name
         , a.abbreviated_name
         , b.right_ascension
         , b.declination
      from constellation_name a
      left join constellation b
        on b.horoscope_id = a.horoscope_id
    ;

select count(*) from horoscope_data limit 20 ;
