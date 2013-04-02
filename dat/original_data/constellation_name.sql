-- �P�����f�[�^
-- 89�s�ڂ��d�����Ă���
drop   table constellation_name ;
create table constellation_name (
    horoscope_id        INTEGER not null    -- ����ID
  , horoscope_code      TEXT    not null    -- ����
  , horoscope_name      TEXT    not null    -- �w��
  , abbreviated_name    TEXT    not null    -- ������(���{��)
  , constraint PK_constellation_name primary key ( horoscope_id )
  , constraint UK_constellation_name unique ( horoscope_code )
);

delete from constellation_name ;

insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('1', 'And', 'Andromeda', '�A���h�����_');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('2', 'Ant', 'Antlia', '�|���v');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('3', 'Aps', 'Apus', '�ӂ����傤');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('4', 'Aql', 'Aquila', '�킵');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('5', 'Aqr', 'Aquarius', '�݂�����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('6', 'Ara', 'Ara', '��������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('7', 'Ari', 'Aries', '���Ђ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('8', 'Aur', 'Auriga', '���債��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('9', 'Boo', 'Bootes', '��������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('10', 'Cae', 'Caelum', '���傤������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('11', 'Cam', 'Camelopardalis', '�����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('12', 'Cap', 'Capricornus', '�€');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('13', 'Car', 'Carina', '��イ����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('14', 'Cas', 'Cassiopeia', '�J�V�I�y��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('15', 'Cen', 'Centaurus', '�P���^�E���X');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('16', 'Cep', 'Cepheus', '�P�t�F�E�X');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('17', 'Cet', 'Cetus', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('18', 'Cha', 'Chamaeleon', '�J�����I��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('19', 'Cir', 'Circinus', '�R���p�X');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('20', 'CMa', 'Canis Major', '��������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('21', 'CMi', 'Canis Minor', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('22', 'Cnc', 'Cancer', '����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('23', 'Col', 'Columba', '�͂�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('24', 'Com', 'Coma Berenices', '���݂̂�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('25', 'CrA', 'Corona Australis', '�݂Ȃ݂̂���ނ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('26', 'CrB', 'Corona Borealis', '����ނ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('27', 'Crt', 'Crater', '�R�b�v');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('28', 'Cru', 'Crux', '�݂Ȃ݂��イ��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('29', 'Crv', 'Corvus', '���炷');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('30', 'CVn', 'Canes Venatici', '��傤����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('31', 'Cyg', 'Cygnus', '�͂����傤');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('32', 'Del', 'Delphinus', '���邩');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('33', 'Dor', 'Dorado', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('34', 'Dra', 'Draco', '��イ');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('35', 'Equ', 'Equuleus', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('36', 'Eri', 'Erhoroscope_idanus', '�G���_�k�X');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('37', 'For', 'Fornax', '��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('38', 'Gem', 'Gemini', '�ӂ���');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('39', 'Gru', 'Grus', '��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('40', 'Her', 'Hercules', '�w���N���X');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('41', 'Hor', 'Horologium', '�Ƃ���');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('42', 'Hya', 'Hydra', '���݂ւ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('43', 'Hyi', 'Hydrus', '�݂��ւ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('44', 'Ind', 'Indus', '�C���f�B�A��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('45', 'Lac', 'Lacerta', '�Ƃ���');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('46', 'Leo', 'Leo', '����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('47', 'Lep', 'Lepus', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('48', 'Lib', 'Libra', '�Ă�т�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('49', 'LMi', 'Leo Minor', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('50', 'Lup', 'Lupus', '��������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('51', 'Lyn', 'Lynx', '��܂˂�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('52', 'Lyr', 'Lyra', '����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('53', 'Men', 'Mensa', '�e�[�u������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('54', 'Mic', 'Microscopium', '����т��傤');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('55', 'Mon', 'Monoceros', '�����������イ');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('56', 'Mus', 'Musca', '�͂�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('57', 'Nor', 'Norma', '���傤��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('58', 'Oct', 'Octans', '�͂��Ԃ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('59', 'Oph', 'Ophiuchus', '�ւт���');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('60', 'Ori', 'Orion', '�I���I��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('61', 'Pav', 'Pavo', '�����Ⴍ');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('62', 'Peg', 'Pegasus', '�y�K�X�X');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('63', 'Per', 'Perseus', '�y���Z�E�X');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('64', 'Phe', 'Phoenix', '�ق�����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('65', 'Pic', 'Pictor', '����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('66', 'PsA', 'Piscis Austrinus', '�݂Ȃ݂̂���');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('67', 'Psc', 'Pisces', '����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('68', 'Pup', 'Puppis', '�Ƃ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('69', 'Pyx', 'Pyxis', '�炵��΂�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('70', 'Ret', 'Reticulum', '���`�N��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('71', 'Scl', 'Sculptor', '���傤��������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('72', 'Sco', 'Scorpius', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('73', 'Sct', 'Scutum', '����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('74', 'Ser', 'Serpens(Caput)', '�ւ�(��)');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('75', 'Ser', 'Serpens(Cauda)', '�ւ�(��)');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('76', 'Sex', 'Sextans', '�낭�Ԃ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('77', 'Sge', 'Sagitta', '��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('78', 'Sgr', 'Sagittarius', '����');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('79', 'Tau', 'Taurus', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('80', 'Tel', 'Telescopium', '�ڂ����񂫂傤');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('81', 'TrA', 'Triangulum Australe', '�݂Ȃ݂̂��񂩂�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('82', 'Tri', 'Triangulum', '���񂩂�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('83', 'Tuc', 'Tucana', '���債���傤');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('84', 'UMa', 'Ursa Major', '��������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('85', 'UMi', 'Ursa Minor', '������');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('86', 'Vel', 'Vela', '��');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('87', 'Vir', 'Virgo', '���Ƃ�');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('88', 'Vol', 'Volans', '�Ƃт���');
insert into constellation_name ( horoscope_id, horoscope_code, horoscope_name, abbreviated_name) values ('89', 'Vul', 'Vulpecula', '������');


select count(*) from constellation_name ;
