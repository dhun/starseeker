-- wikipediaの星座のパスデータ
-- 'γ Cas', 'ツィー'のHIPを-1にねつ造している
drop table if exists wikipedia_constellation ;
create table wikipedia_constellation (
    hip_num_fm                  INTEGER not null    -- HIP番号の起点
  , hip_num_to                  INTEGER not null    -- HIP番号の終点
  , constellation_code          TEXT    not null    -- 略符
  , constraint PK_wikipedia_constellation primary key ( hip_num_fm, hip_num_to )
);

delete from wikipedia_constellation ;

-- insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('

-- カシオペヤ座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('8886', '6686', 'Cas');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('6686', '-1', 'Cas');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('-1', '3179', 'Cas');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('3179', '746', 'Cas');

-- おおぐま座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('67301', '65378', 'UMa');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('65378', '62956', 'UMa');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('62956', '59774', 'UMa');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('59774', '58001', 'UMa');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('59774', '54061', 'UMa');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('58001', '53910', 'UMa');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('53910', '54061', 'UMa');

select count(*) from wikipedia_constellation ;
