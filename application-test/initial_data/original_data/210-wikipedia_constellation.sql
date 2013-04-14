-- wikipediaの星座のパスデータ
-- 'γ Cas', 'ツィー'のHIPを-1にねつ造している
-- さそり座のパスがあやしい
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

-- オリオン座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('27989', '26727', 'Ori');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('26727', '27366', 'Ori');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('26727', '26311', 'Ori');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('26311', '25930', 'Ori');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('25336', '25930', 'Ori');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('25930', '24436', 'Ori');

-- さそり座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('78820', '80112', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('78401', '80112', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('78265', '80112', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('80112', '80763', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('80763', '81266', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('81266', '82396', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('82396', '82514', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('82514', '82729', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('82729', '84143', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('84143', '86228', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('86228', '87073', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('87073', '86670', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('86670', '85927', 'Sco');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('85927', '85696', 'Sco');

-- おうし座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('26451', '21421', 'Tau');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('21421', '20894', 'Tau');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('20894', '17702', 'Tau');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('17702', '20889', 'Tau');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('20889', '25428', 'Tau');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('25428', '23497', 'Tau');

select count(*) from wikipedia_constellation ;
