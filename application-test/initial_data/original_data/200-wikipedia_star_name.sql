-- wikipediaの星データ
-- 'γ Cas', 'ツィー'のHIPを-1にねつ造している
drop table if exists wikipedia_star_name ;
CREATE TABLE wikipedia_star_name (
    hip_num                     INTEGER not null    -- HIP番号
  , bayer_code                  TEXT    not null    -- バイエル符号／フラムスティード番号
  , japanese_name               TEXT    not null    -- 日本語名
  , simbad                      TEXT    not null    -- 英名・SIMBAD
  , memo                        TEXT        null    -- 備考
  , constraint PK_wikipedia_star_name primary key ( hip_num )
);

insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('32349', 'α CMa A', 'シリウス', 'Sirius', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('30438', 'α Car', 'カノープス', 'Canopus', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('71683', 'α Cen', 'リギル・ケンタウルス', 'Rigil Kentaurus', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('69673', 'α Boo', 'アルクトゥルス', 'Arcturus', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('91262', 'α Lyr', 'ベガ', 'Vega', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('24608', 'α Aur', 'カペラ', 'Capella', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('24436', 'β Ori', 'リゲル', 'Rigel', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('37279', 'α CMi A', 'プロキオン', 'Procyon', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('27989', 'α Ori', 'ベテルギウス', 'Betelgeuse', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('7588', 'α Eri', 'アケルナル', 'Achernar', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('68702', 'β Cen', 'ハダル', 'Hadar (Agena)', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('97649', 'α Aql', 'アルタイル', 'Altair', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('60718', 'α Cru', 'アクルックス', 'Acrux', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('21421', 'α Tau', 'アルデバラン', 'Aldebaran', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('65474', 'α Vir', 'スピカ', 'Spica', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('80763', 'α Sco', 'アンタレス', 'Antares', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('37826', 'β Gem', 'ポルックス', 'Pollux', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('113368', 'α PsA', 'フォーマルハウト', 'Fomalhaut', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('102098', 'α Cyg', 'デネブ', 'Deneb', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('62434', 'β Cru', 'ミモザ', 'Mimosa', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('49669', 'α Leo A', 'レグルス', 'Regulus', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('33579', 'ε CMa', 'アダーラ', 'Adara', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('36850', 'α Gem', 'カストル', 'Castor', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('85927', 'λ Sco', 'シャウラ', 'Shaula', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('61084', 'γ Cru A', 'ガクルックス', 'Gacrux', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('25336', 'γ Ori', 'ベラトリックス', 'Bellatrix', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('25428', 'β Tau', 'エルナト', 'El Nath', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('45238', 'β Car', 'ミアプラキドゥス', 'Miaplacidus', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('26311', 'ε Ori', 'アルニラム', 'Alnilam', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('109268', 'α Gru', 'アルナイル', 'Al Na''ir', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('62956', 'ε UMa', 'アリオト', 'Alioth', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('26727', 'ζ Ori', 'アルニタク', 'Alnitak', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('54061', 'α UMa', 'ドゥーベ', 'Dubhe', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('90185', 'ε Sgr', 'カウス・アウストラリス', 'Kaus Australis', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('39953', 'γ2 Vel', 'スハイル・ムーリフ', 'Al Suhail al-Muhlif', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('15863', 'α Per', 'ミルファク', 'Mirfak', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('34444', 'δ CMa', 'ウェズン', 'Wezen', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('67301', 'η UMa', 'ベネトナシュ', 'Benetnasch (Alkaid)', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('86228', 'θ Sco', 'サルガス', 'Sargas', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('28360', 'β Aur', 'メンカリナン', 'Menkalinan', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('31681', 'γ Gem', 'アルヘナ', 'Alhena', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('100751', 'α Pav', 'ピーコック', 'Peacock', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('82273', 'α TrA', 'アトリア', 'Atria', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('42913', 'δ Vel', 'クー･シー', 'Koo She', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('41037', 'ε Car', 'アヴィオール', 'Avior', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('50583', 'γ Leo', 'アルギエバ', 'Algieba', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('9884', 'α Ari', 'ハマル', 'Hamal', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('30324', 'β CMa', 'ムルジム', 'Murzim', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('46390', 'α Hya', 'アルファルド', 'Alphard', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('11767', 'α UMi', 'ポラリス', 'Polaris', '北極星');
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('3419', 'β Cet', 'デネブ・カイトス', 'Deneb Kaitos', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('27366', 'κ Ori', 'サイフ', 'Saiph', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('92855', 'σ Sgr', 'ヌンキ', 'Nunki', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('677', 'α And', 'アルフェラッツ', 'Alpheratz', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('68933', 'θ Cen', 'メンケント', 'Menkent', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('5447', 'β And', 'ミラク', 'Mirach', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('72607', 'β UMi', 'コカブ', 'Kochab', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('86032', 'α Oph', 'ラス・アルハゲ', 'Ras Alhague', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('9640', 'γ And', 'アルマク', 'Almach', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('14576', 'β Per', 'アルゴル', 'Algol', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('112122', 'β Gru', 'グライド', 'Gruid', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('57632', 'β Leo', 'デネボラ', 'Denebola', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('61932', 'γ Cen', 'ムリファイン', 'Muhlifain', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('76267', 'α CrB', 'ゲンマ（アルフェッカ）', 'Gemma(Alphecca)', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('44816', 'λ Vel', 'スハイル・ワズン', 'Al Suhail Al Wazn', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('87833', 'γ Dra', 'エルタニン', 'Etamin', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('100453', 'γ Cyg', 'サドル', 'Sadr', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('45556', 'ι Car', 'アスピディスケ', 'Aspidiske', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('39429', 'ζ Pup', 'ナオス', 'Naos', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('3179', 'α Cas', 'シェダル', 'Schedar', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('66657', 'ε Cen', 'バーダン', 'Birdun', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('65378', 'ζ UMa', 'ミザール', 'Mizar', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('746', 'β Cas', 'カフ', 'Caph', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('71860', 'α Lup', 'カッカブ', 'Kakkab', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('82396', 'ε Sco', 'ウェイ', 'Wei', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('78401', 'δ Sco', 'ジュバ', 'Dschubba', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('71352', 'η Cen', 'ケンタウルス座η星', '[1]', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('53910', 'β UMa', 'メラク', 'Merak', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('2081', 'α Phe', 'アンカ', 'Ankaa', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('86670', 'κ Sco', 'ギルタブ', 'Girtab', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('72105', 'ε Boo', 'イザール（プリケルマ）', 'Izar(Pulcherrima)', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('35904', 'η CMa', 'アルドラ', 'Aludra', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('107315', 'ε Peg', 'エニフ', 'Enif', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('25930', 'δ Ori A', 'ミンタカ', 'Mintaka', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('113881', 'β Peg', 'シェアト', 'Scheat', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('84012', 'η Oph', 'サビク', 'Sabik', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('58001', 'γ UMa', 'フェクダ', 'Phecda', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('105199', 'α Cep', 'アルデラミン', 'Alderamin', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('45941', 'κ Vel', 'マルカブ', 'Markab', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values (-1/*ねつ造*/, 'γ Cas', 'ツィー', 'Tsih', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('102488', 'ε Cyg A', 'ギェナー', 'Gienah', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('113963', 'α Peg', 'マルカブ', 'Markab', null);
insert into wikipedia_star_name (hip_num, bayer_code, japanese_name, simbad, memo) values ('78820', 'β Sco', 'アクラブ', 'Acrab', null);

select count(*) from  wikipedia_star_name ;
