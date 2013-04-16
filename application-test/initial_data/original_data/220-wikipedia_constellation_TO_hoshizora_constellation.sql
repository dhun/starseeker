-- wikipediaを見て追加した、星座の基礎データ
insert into hoshizora_constellation (horoscope_id, right_ascension, declination) values ('-1', '0h 1m', '+0°0'''); -- 赤径と赤緯は適当
insert into hoshizora_constellation (horoscope_id, right_ascension, declination) values ('-2', '0h 2m', '+0°0'''); -- 赤径と赤緯は適当

select count(*) from hoshizora_constellation ;
