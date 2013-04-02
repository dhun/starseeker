-- 星座の基礎データ
drop   table constellation ;
create table constellation (
    horoscope_id    INTEGER             -- 星座ID
  , right_ascension REAL    not null    -- 赤経
  , declination     REAL    not null    -- 赤緯
  , constraint PK_constellation primary key ( horoscope_id )
);

delete from constellation ;

insert into constellation (horoscope_id, right_ascension, declination) values ('1', '0h 40m', '-38°722''');
insert into constellation (horoscope_id, right_ascension, declination) values ('2', '10h 0m', '+35°239''');
insert into constellation (horoscope_id, right_ascension, declination) values ('3', '16h 0m', '+76°206''');
insert into constellation (horoscope_id, right_ascension, declination) values ('4', '19h 30m', '-2°652''');
insert into constellation (horoscope_id, right_ascension, declination) values ('5', '22h 20m', '+13°980''');
insert into constellation (horoscope_id, right_ascension, declination) values ('6', '17h 10m', '+55°237''');
insert into constellation (horoscope_id, right_ascension, declination) values ('7', '2h 30m', '-20°441''');
insert into constellation (horoscope_id, right_ascension, declination) values ('8', '6h 0m', '-42°657''');
insert into constellation (horoscope_id, right_ascension, declination) values ('9', '14h 35m', '-30°907''');
insert into constellation (horoscope_id, right_ascension, declination) values ('10', '4h 50m', '+38°125''');
insert into constellation (horoscope_id, right_ascension, declination) values ('11', '5h 40m', '-70°757''');
insert into constellation (horoscope_id, right_ascension, declination) values ('12', '20h 50m', '+20°414''');
insert into constellation (horoscope_id, right_ascension, declination) values ('13', '8h 40m', '+62°494''');
insert into constellation (horoscope_id, right_ascension, declination) values ('14', '1h 0m', '-60°598''');
insert into constellation (horoscope_id, right_ascension, declination) values ('15', '13h 20m', '+47°1060''');
insert into constellation (horoscope_id, right_ascension, declination) values ('16', '22h 0m', '-70°588''');
insert into constellation (horoscope_id, right_ascension, declination) values ('17', '1h 45m', '+12°1231''');
insert into constellation (horoscope_id, right_ascension, declination) values ('18', '10h 40m', '+78°132''');
insert into constellation (horoscope_id, right_ascension, declination) values ('19', '14h 50m', '+63°93''');
insert into constellation (horoscope_id, right_ascension, declination) values ('20', '6h 40m', '+24°380''');
insert into constellation (horoscope_id, right_ascension, declination) values ('21', '7h 30m', '-6°183''');
insert into constellation (horoscope_id, right_ascension, declination) values ('22', '8h 30m', '-20°506''');
insert into constellation (horoscope_id, right_ascension, declination) values ('23', '5h 40m', '+34°270''');
insert into constellation (horoscope_id, right_ascension, declination) values ('24', '12h 40m', '-23°386''');
insert into constellation (horoscope_id, right_ascension, declination) values ('25', '18h 30m', '+41°128''');
insert into constellation (horoscope_id, right_ascension, declination) values ('26', '15h 40m', '-30°179''');
insert into constellation (horoscope_id, right_ascension, declination) values ('27', '11h 20m', '+15°282''');
insert into constellation (horoscope_id, right_ascension, declination) values ('28', '12h 20m', '+60°68''');
insert into constellation (horoscope_id, right_ascension, declination) values ('29', '12h 20m', '+18°184''');
insert into constellation (horoscope_id, right_ascension, declination) values ('30', '13h 0m', '-40°465''');
insert into constellation (horoscope_id, right_ascension, declination) values ('31', '20h 30m', '-43°804''');
insert into constellation (horoscope_id, right_ascension, declination) values ('32', '20h 40m', '-12°189''');
insert into constellation (horoscope_id, right_ascension, declination) values ('33', '5h 0m', '+60°179''');
insert into constellation (horoscope_id, right_ascension, declination) values ('34', '17h 0m', '-60°1083''');
insert into constellation (horoscope_id, right_ascension, declination) values ('35', '21h 10m', '-6°72''');
insert into constellation (horoscope_id, right_ascension, declination) values ('36', '3h 50m', '+30°1138''');
insert into constellation (horoscope_id, right_ascension, declination) values ('37', '2h 30m', '+33°398''');
insert into constellation (horoscope_id, right_ascension, declination) values ('38', '7h 0m', '-22°514''');
insert into constellation (horoscope_id, right_ascension, declination) values ('39', '22h 20m', '+47°366''');
insert into constellation (horoscope_id, right_ascension, declination) values ('40', '17h 10m', '-27°1225''');
insert into constellation (horoscope_id, right_ascension, declination) values ('41', '3h 20m', '+52°249''');
insert into constellation (horoscope_id, right_ascension, declination) values ('42', '10h 30m', '+20°1303''');
insert into constellation (horoscope_id, right_ascension, declination) values ('43', '2h 40m', '+70°243''');
insert into constellation (horoscope_id, right_ascension, declination) values ('44', '21h 20m', '+58°294''');
insert into constellation (horoscope_id, right_ascension, declination) values ('45', '22h 25m', '-43°201''');
insert into constellation (horoscope_id, right_ascension, declination) values ('46', '10h 30m', '-15°947''');
insert into constellation (horoscope_id, right_ascension, declination) values ('47', '5h 25m', '+20°290''');
insert into constellation (horoscope_id, right_ascension, declination) values ('48', '15h 10m', '+14°538''');
insert into constellation (horoscope_id, right_ascension, declination) values ('49', '10h 20m', '-33°232''');
insert into constellation (horoscope_id, right_ascension, declination) values ('50', '15h 0m', '+40°334''');
insert into constellation (horoscope_id, right_ascension, declination) values ('51', '7h 50m', '-45°545''');
insert into constellation (horoscope_id, right_ascension, declination) values ('52', '18h 45m', '-36°286''');
insert into constellation (horoscope_id, right_ascension, declination) values ('53', '5h 40m', '+77°153''');
insert into constellation (horoscope_id, right_ascension, declination) values ('54', '20h 50m', '+37°210''');
insert into constellation (horoscope_id, right_ascension, declination) values ('55', '7h 0m', '+3°482''');
insert into constellation (horoscope_id, right_ascension, declination) values ('56', '12h 30m', '+70°138''');
insert into constellation (horoscope_id, right_ascension, declination) values ('57', '16h 0m', '+50°165''');
insert into constellation (horoscope_id, right_ascension, declination) values ('58', '21h 0m', '+87°291''');
insert into constellation (horoscope_id, right_ascension, declination) values ('59', '17h 10m', '+5°948''');
insert into constellation (horoscope_id, right_ascension, declination) values ('60', '5h 20m', '-3°594''');
insert into constellation (horoscope_id, right_ascension, declination) values ('61', '19h 10m', '+65°378''');
insert into constellation (horoscope_id, right_ascension, declination) values ('62', '22h 30m', '-17°1121''');
insert into constellation (horoscope_id, right_ascension, declination) values ('63', '3h 20m', '-42°615''');
insert into constellation (horoscope_id, right_ascension, declination) values ('64', '1h 0m', '+48°469''');
insert into constellation (horoscope_id, right_ascension, declination) values ('65', '5h 30m', '+52°247''');
insert into constellation (horoscope_id, right_ascension, declination) values ('66', '22h 10m', '+32°245''');
insert into constellation (horoscope_id, right_ascension, declination) values ('67', '0h 20m', '-10°889''');
insert into constellation (horoscope_id, right_ascension, declination) values ('68', '7h 40m', '+32°673''');
insert into constellation (horoscope_id, right_ascension, declination) values ('69', '8h 50m', '+28°221''');
insert into constellation (horoscope_id, right_ascension, declination) values ('70', '3h 50m', '+63°114''');
insert into constellation (horoscope_id, right_ascension, declination) values ('71', '0h 30m', '+35°475''');
insert into constellation (horoscope_id, right_ascension, declination) values ('72', '16h 30m', '+26°497''');
insert into constellation (horoscope_id, right_ascension, declination) values ('73', '18h 35m', '+10°109''');
insert into constellation (horoscope_id, right_ascension, declination) values ('74', '15h 35m', '-10°428''');
insert into constellation (horoscope_id, right_ascension, declination) values ('75', '18h 0m', '+5°208''');
insert into constellation (horoscope_id, right_ascension, declination) values ('76', '10h 10m', '+1°314''');
insert into constellation (horoscope_id, right_ascension, declination) values ('77', '19h 40m', '-18°80''');
insert into constellation (horoscope_id, right_ascension, declination) values ('78', '19h 0m', '+25°867''');
insert into constellation (horoscope_id, right_ascension, declination) values ('79', '4h 30m', '-18°797''');
insert into constellation (horoscope_id, right_ascension, declination) values ('80', '19h 0m', '+52°252''');
insert into constellation (horoscope_id, right_ascension, declination) values ('81', '15h 40m', '+65°110''');
insert into constellation (horoscope_id, right_ascension, declination) values ('82', '2h 0m', '-32°132''');
insert into constellation (horoscope_id, right_ascension, declination) values ('83', '23h 45m', '+68°295''');
insert into constellation (horoscope_id, right_ascension, declination) values ('84', '11h 0m', '-58°1280''');
insert into constellation (horoscope_id, right_ascension, declination) values ('85', '15h 40m', '-78°256''');
insert into constellation (horoscope_id, right_ascension, declination) values ('86', '9h 30m', '+45°500''');
insert into constellation (horoscope_id, right_ascension, declination) values ('87', '13h 20m', '+2°1294''');
insert into constellation (horoscope_id, right_ascension, declination) values ('88', '7h 40m', '+69°141''');
insert into constellation (horoscope_id, right_ascension, declination) values ('89', '20h 10m', '-25°268''');

select * from constellation ;
