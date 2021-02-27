select user0_.id as id1_1_,
       user0_.email as email2_1_,
       user0_.endpoint as endpoint3_1_,
       user0_.name as name4_1_,
       user0_.status as status5_1_
from user user0_
where user0_.email='a.b'



SELECT s.* FROM subscription s, clipuser u, cliptopic t WHERE s.user_id = u.id AND s.cliptopic_id = t.id AND t.name = 'news' AND u.name = 'mark';
