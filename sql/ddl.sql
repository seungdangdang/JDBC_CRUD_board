/*post TABLE 생성*/
create table post
(
    id BIGINT generated by default as identity,
    name VARCHAR(255),
    title VARCHAR(255),
    content VARCHAR(1000)
)

/*post-name COLUMN 수정*/
alter table post alter column name VARCHAR(10)
