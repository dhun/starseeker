-- 星座名データ
-- 89行目が重複している
drop table if exists hoshizora_constellation_name ;
create table hoshizora_constellation_name (
    horoscope_id                INTEGER not null    -- 星座ID
  , horoscope_code              TEXT    not null    -- 略符
  , horoscope_name              TEXT    not null    -- 学名
  , abbreviated_name            TEXT    not null    -- 星座名(日本語)
  , constraint PK_hoshizora_constellation_name primary key ( horoscope_id )
  , constraint UK_hoshizora_constellation_name unique ( horoscope_code )
);

delete from hoshizora_constellation_name ;

insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('1', 'And', 'Andromeda', 'アンドロメダ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('2', 'Ant', 'Antlia', 'ポンプ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('3', 'Aps', 'Apus', 'ふうちょう');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('4', 'Aql', 'Aquila', 'わし');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('5', 'Aqr', 'Aquarius', 'みずがめ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('6', 'Ara', 'Ara', 'さいだん');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('7', 'Ari', 'Aries', 'おひつじ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('8', 'Aur', 'Auriga', 'ぎょしゃ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('9', 'Boo', 'Bootes', 'うしかい');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('10', 'Cae', 'Caelum', 'ちょうこくぐ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('11', 'Cam', 'Camelopardalis', 'きりん');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('12', 'Cap', 'Capricornus', 'やぎ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('13', 'Car', 'Carina', 'りゅうこつ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('14', 'Cas', 'Cassiopeia', 'カシオペヤ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('15', 'Cen', 'Centaurus', 'ケンタウルス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('16', 'Cep', 'Cepheus', 'ケフェウス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('17', 'Cet', 'Cetus', 'くじら');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('18', 'Cha', 'Chamaeleon', 'カメレオン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('19', 'Cir', 'Circinus', 'コンパス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('20', 'CMa', 'Canis Major', 'おおいぬ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('21', 'CMi', 'Canis Minor', 'こいぬ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('22', 'Cnc', 'Cancer', 'かに');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('23', 'Col', 'Columba', 'はと');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('24', 'Com', 'Coma Berenices', 'かみのけ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('25', 'CrA', 'Corona Australis', 'みなみのかんむり');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('26', 'CrB', 'Corona Borealis', 'かんむり');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('27', 'Crt', 'Crater', 'コップ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('28', 'Cru', 'Crux', 'みなみじゅうじ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('29', 'Crv', 'Corvus', 'からす');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('30', 'CVn', 'Canes Venatici', 'りょうけん');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('31', 'Cyg', 'Cygnus', 'はくちょう');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('32', 'Del', 'Delphinus', 'いるか');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('33', 'Dor', 'Dorado', 'かじき');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('34', 'Dra', 'Draco', 'りゅう');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('35', 'Equ', 'Equuleus', 'こうま');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('36', 'Eri', 'Erhoroscope_idanus', 'エリダヌス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('37', 'For', 'Fornax', 'ろ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('38', 'Gem', 'Gemini', 'ふたご');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('39', 'Gru', 'Grus', 'つる');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('40', 'Her', 'Hercules', 'ヘルクレス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('41', 'Hor', 'Horologium', 'とけい');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('42', 'Hya', 'Hydra', 'うみへび');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('43', 'Hyi', 'Hydrus', 'みずへび');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('44', 'Ind', 'Indus', 'インディアン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('45', 'Lac', 'Lacerta', 'とかげ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('46', 'Leo', 'Leo', 'しし');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('47', 'Lep', 'Lepus', 'うさぎ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('48', 'Lib', 'Libra', 'てんびん');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('49', 'LMi', 'Leo Minor', 'こじし');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('50', 'Lup', 'Lupus', 'おおかみ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('51', 'Lyn', 'Lynx', 'やまねこ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('52', 'Lyr', 'Lyra', 'こと');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('53', 'Men', 'Mensa', 'テーブルさん');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('54', 'Mic', 'Microscopium', 'けんびきょう');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('55', 'Mon', 'Monoceros', 'いっかくじゅう');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('56', 'Mus', 'Musca', 'はえ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('57', 'Nor', 'Norma', 'じょうぎ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('58', 'Oct', 'Octans', 'はちぶんぎ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('59', 'Oph', 'Ophiuchus', 'へびつかい');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('60', 'Ori', 'Orion', 'オリオン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('61', 'Pav', 'Pavo', 'くじゃく');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('62', 'Peg', 'Pegasus', 'ペガスス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('63', 'Per', 'Perseus', 'ペルセウス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('64', 'Phe', 'Phoenix', 'ほうおう');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('65', 'Pic', 'Pictor', 'がか');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('66', 'PsA', 'Piscis Austrinus', 'みなみのうお');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('67', 'Psc', 'Pisces', 'うお');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('68', 'Pup', 'Puppis', 'とも');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('69', 'Pyx', 'Pyxis', 'らしんばん');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('70', 'Ret', 'Reticulum', 'レチクル');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('71', 'Scl', 'Sculptor', 'ちょうこくしつ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('72', 'Sco', 'Scorpius', 'さそり');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('73', 'Sct', 'Scutum', 'たて');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('74', 'Ser', 'Serpens(Caput)', 'へび(頭)');
--insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('75', 'Ser', 'Serpens(Cauda)', 'へび(尾)');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('76', 'Sex', 'Sextans', 'ろくぶんぎ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('77', 'Sge', 'Sagitta', 'や');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('78', 'Sgr', 'Sagittarius', 'いて');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('79', 'Tau', 'Taurus', 'おうし');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('80', 'Tel', 'Telescopium', 'ぼうえんきょう');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('81', 'TrA', 'Triangulum Australe', 'みなみのさんかく');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('82', 'Tri', 'Triangulum', 'さんかく');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('83', 'Tuc', 'Tucana', 'きょしちょう');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('84', 'UMa', 'Ursa Major', 'おおぐま');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('85', 'UMi', 'Ursa Minor', 'こぐま');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('86', 'Vel', 'Vela', 'ほ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('87', 'Vir', 'Virgo', 'おとめ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('88', 'Vol', 'Volans', 'とびうお');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('89', 'Vul', 'Vulpecula', 'こぎつね');


select count(*) from hoshizora_constellation_name ;