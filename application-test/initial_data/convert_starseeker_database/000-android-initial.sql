-- Androidメタデータ
drop table if exists android_metadata ;
create table android_metadata (  
    locale text default 'ja_JP'  
); 

insert into android_metadata values ('ja_JP') ;

