-- 星座名データ
-- 90行目が重複している
drop table if exists hoshizora_constellation_name ;
create table hoshizora_constellation_name (
    horoscope_id                INTEGER not null    -- 星座ID
  , horoscope_code              TEXT    not null    -- 略符
  , horoscope_name              TEXT    not null    -- 学名
  , japanese_name               TEXT    not null    -- 日本語名称
  , japanese_kana               TEXT    not null    -- 日本語カナ
  , constraint PK_hoshizora_constellation_name primary key ( horoscope_id )
  , constraint UK_hoshizora_constellation_name unique ( horoscope_code )
);

delete from hoshizora_constellation_name ;

insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('1', 'And', 'Andromeda', 'アンドロメダ', 'アンドロメダ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('2', 'Ant', 'Antlia', 'ポンプ', 'ポンプ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('3', 'Aps', 'Apus', 'フウチョウ', 'フウチョウ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('4', 'Aql', 'Aquila', 'ワシ', '鷲');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('5', 'Aqr', 'Aquarius', 'ミズガメ', '水瓶');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('6', 'Ara', 'Ara', 'サイダン', 'サイダン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('7', 'Ari', 'Aries', 'オヒツジ', '牡羊');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('8', 'Aur', 'Auriga', 'ギョシャ', 'ギョシャ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('9', 'Boo', 'Bootes', 'ウシカイ', '牛飼い');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('10', 'Cae', 'Caelum', 'チョウコクグ', 'チョウコクグ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('11', 'Cam', 'Camelopardalis', 'キリン', 'キリン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('12', 'Cap', 'Capricornus', 'ヤギ', '山羊');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('13', 'Car', 'Carina', 'リュウコツ', '竜骨');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('14', 'Cas', 'Cassiopeia', 'カシオペヤ', 'カシオペヤ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('15', 'Cen', 'Centaurus', 'ケンタウロス', 'ケンタウロス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('16', 'Cep', 'Cepheus', 'ケフェウス', 'ケフェウス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('17', 'Cet', 'Cetus', 'クジラ', '鯨');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('18', 'Cha', 'Chamaeleon', 'カメレオン', 'カメレオン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('19', 'Cir', 'Circinus', 'コンパス', 'コンパス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('20', 'CMa', 'Canis Major', 'オオイヌ', '大犬');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('21', 'CMi', 'Canis Minor', 'コイヌ', '仔犬');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('22', 'Cnc', 'Cancer', 'カニ', '蟹');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('23', 'Col', 'Columba', 'ハト', '鳩');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('24', 'Com', 'Coma Berenices', 'カミノケ', '髪の毛');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('25', 'CrA', 'Corona Australis', 'ミナミノカンムリ', '南の冠');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('26', 'CrB', 'Corona Borealis', 'カンムリ', '冠');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('27', 'Crt', 'Crater', 'コップ', 'コップ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('28', 'Cru', 'Crux', 'ミナミジュウジ', '南十字');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('29', 'Crv', 'Corvus', 'カラス', '鴉');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('30', 'CVn', 'Canes Venatici', 'リョウケン', '猟犬');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('31', 'Cyg', 'Cygnus', 'ハクチョウ', '白鳥');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('32', 'Del', 'Delphinus', 'イルカ', 'イルカ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('33', 'Dor', 'Dorado', 'カジキ', 'カジキ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('34', 'Dra', 'Draco', 'リュウ', '龍');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('35', 'Equ', 'Equuleus', 'コウマ', '仔馬');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('36', 'Eri', 'Eridanus', 'エリダヌス', 'エリダヌス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('37', 'For', 'Fornax', 'ロ', 'ロ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('38', 'Gem', 'Gemini', 'フタゴ', '双子');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('39', 'Gru', 'Grus', 'ツル', '鶴');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('40', 'Her', 'Hercules', 'ヘラクレス', 'ヘラクレス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('41', 'Hor', 'Horologium', 'トケイ', '時計');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('42', 'Hya', 'Hydra', 'ウミヘビ', '海蛇');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('43', 'Hyi', 'Hydrus', 'ミズヘビ', '水蛇');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('44', 'Ind', 'Indus', 'インディアン', 'インディアン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('45', 'Lac', 'Lacerta', 'トカゲ', 'トカゲ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('46', 'Leo', 'Leo', 'シシ', '獅子');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('47', 'Lep', 'Lepus', 'ウサギ', '兎');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('48', 'Lib', 'Libra', 'テンビン', '天秤');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('49', 'LMi', 'Leo Minor', 'コジシ', '仔獅子');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('50', 'Lup', 'Lupus', 'オオカミ', '狼');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('51', 'Lyn', 'Lynx', 'ヤアネコ', '山猫');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('52', 'Lyr', 'Lyra', 'コト', '琴');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('53', 'Men', 'Mensa', 'テーブルサン', 'テーブルサン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('54', 'Mic', 'Microscopium', 'ケンビキョウ', '顕微鏡');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('55', 'Mon', 'Monoceros', 'イッカクジュウ', '一角獣');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('56', 'Mus', 'Musca', 'ハエ', 'ハエ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('57', 'Nor', 'Norma', 'ジョウギ', '定規');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('58', 'Oct', 'Octans', 'ハチブンギ', 'ハチブンギ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('59', 'Oph', 'Ophiuchus', 'ヘビツカイ', '蛇使い');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('60', 'Ori', 'Orion', 'オリオン', 'オリオン');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('61', 'Pav', 'Pavo', 'クジャク', '孔雀');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('62', 'Peg', 'Pegasus', 'ペガサス', 'ペガサス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('63', 'Per', 'Perseus', 'ペルセウス', 'ペルセウス');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('64', 'Phe', 'Phoenix', 'ホウオウ', '鳳凰');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('65', 'Pic', 'Pictor', 'ガカ', 'ガカ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('66', 'PsA', 'Piscis Austrinus', 'ミナミノウオ', '南の魚');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('67', 'Psc', 'Pisces', 'ウオ', '魚');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('68', 'Pup', 'Puppis', 'トモ', 'トモ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('69', 'Pyx', 'Pyxis', 'ラシンバン', '羅針盤');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('70', 'Ret', 'Reticulum', 'レチクル', 'レチクル');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('71', 'Scl', 'Sculptor', 'チョウコクシツ', 'チョウコクシツ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('72', 'Sco', 'Scorpius', 'サソリ', 'サソリ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('73', 'Sct', 'Scutum', 'タテ', '盾');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('74', 'Ser', 'Serpens(Caput)', 'ヘビ（アタマ）', '蛇（頭）');
--insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('75', 'Ser', 'Serpens(Cauda)', 'ヘビ（オ）', '蛇（尾）');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('76', 'Sex', 'Sextans', 'ログブンギ', 'ログブンギ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('77', 'Sge', 'Sagitta', 'ヤ', '矢');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('78', 'Sgr', 'Sagittarius', 'イテ', '射手');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('79', 'Tau', 'Taurus', 'オウシ', '牡牛');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('80', 'Tel', 'Telescopium', 'ボウエンキョウ', '望遠鏡');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('81', 'TrA', 'Triangulum Australe', 'ミナミノサンカク', '南の三角');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('82', 'Tri', 'Triangulum', 'サンカク', '三角');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('83', 'Tuc', 'Tucana', 'キョシチョウ', 'キョシチョウ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('84', 'UMa', 'Ursa Major', 'オオグマ', '大熊');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('85', 'UMi', 'Ursa Minor', 'コグマ', '仔熊');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('86', 'Vel', 'Vela', 'ホ', '帆');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('87', 'Vir', 'Virgo', 'オトメ', '乙女');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('88', 'Vol', 'Volans', 'トビウオ', 'トビウオ');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_kana, japanese_name) values ('89', 'Vul', 'Vulpecula', 'コギツネ', '仔狐');


select count(*) from hoshizora_constellation_name ;
