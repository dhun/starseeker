-- wikipediaの星座のパスデータ
-- 'γ Cas', 'ツィー'のHIPを-1にねつ造している
-- おうし座のパスがあやしい
drop table if exists wikipedia_constellation ;
create table wikipedia_constellation (
    hip_num_fm                  INTEGER not null    -- HIP番号の起点
  , hip_num_to                  INTEGER not null    -- HIP番号の終点
  , constellation_code          TEXT    not null    -- 略符
  , constraint PK_wikipedia_constellation primary key ( hip_num_fm, hip_num_to, constellation_code )
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
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('20894', '20205', 'Tau');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('20205', '20889', 'Tau');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('20889', '25428', 'Tau');

-- はくちょう座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('102098', '100453', 'Cyg');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('100453', '102488', 'Cyg');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('100453', '97165', 'Cyg');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('100453', '98110', 'Cyg');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('98110', '95947', 'Cyg');

-- おとめ座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('65474 ', '64238', 'Vir');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('64238', '66249', 'Vir');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('64238', '61941', 'Vir');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('61941', '63090', 'Vir');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('61941', '60129', 'Vir');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('63090', '63608', 'Vir');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('60129', '57757', 'Vir');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('57757', '57380', 'Vir');

-- ふたご座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('30343', '32246', 'Gem');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('32246', '36850', 'Gem');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('36850', '37826', 'Gem');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('37826', '35550', 'Gem');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('35550', '31681', 'Gem');

-- みずがめ座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('102618', '106278', 'Aqr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('106278', '109074', 'Aqr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('109074', '110395', 'Aqr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('112961', '113136', 'Aqr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('113136', '114341', 'Aqr');

-- しし座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('49669', '49583', 'Leo');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('49583', '50583', 'Leo');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('50583', '50335', 'Leo');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('50583', '54872', 'Leo');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('50335', '48455', 'Leo');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('48455', '47908', 'Leo');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('54872', '57632', 'Leo');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('57632', '54879', 'Leo');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('54879', '49669', 'Leo');

-- おひつじ座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('9884', '8903', 'Ari');

-- かに座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('43103', '42806', 'Cnc');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('42806', '42911', 'Cnc');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('42911', '40526', 'Cnc');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('42911', '44066', 'Cnc');

-- てんびん座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('76333', '74785', 'Lib');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('74785', '72622', 'Lib');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('72622', '73714', 'Lib');

-- いて座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('93085', '94141', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('94141', '92855', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('92855', '93864', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('93864', '93506', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('92855', '92041', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('92041', '90496', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('90496', '89931', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('89931', '88635', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('89931', '90185', 'Sgr');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('90185', '89642', 'Sgr');

-- やぎ座（適当）
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('100064', '100345', 'Cap');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('100345', '104139', 'Cap');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('104139', '106985', 'Cap');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('106985', '107556', 'Cap');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('107556', '105881', 'Cap');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('105881', '100345', 'Cap');

-- うお座
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('7097', '9487', 'Psc');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('9487', '114971', 'Psc');


-- 冬の大三角
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('32349', '37279', 'winter-triangle');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('37279', '27989', 'winter-triangle');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('27989', '32349', 'winter-triangle');

-- 冬のダイアモンド
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('32349', '37279', 'winter-diamond');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('37279', '37826', 'winter-diamond');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('37826', '24608', 'winter-diamond');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('24608', '21421', 'winter-diamond');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('21421', '24436', 'winter-diamond');
insert into wikipedia_constellation (hip_num_fm, hip_num_to, constellation_code) values ('24436', '32349', 'winter-diamond');

-- 春の大三角
-- 春のダイヤモンド
-- 夏の大三角
-- 秋の四辺形(ペガサス)

select count(*) from wikipedia_constellation ;
