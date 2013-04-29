-- wikipediaを見て追加した、星座名データ
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_name, japanese_kana) values ('-1', 'winter-triangle', 'Winter Triangle', 'フユノダイサンカク', '冬の大三角');
insert into hoshizora_constellation_name ( horoscope_id, horoscope_code, horoscope_name, japanese_name, japanese_kana) values ('-2', 'winter-diamond' , 'Winter Diamond' , 'フユノダイアモンド', '冬のダイアモンド');


select count(*) from hoshizora_constellation_name ;
