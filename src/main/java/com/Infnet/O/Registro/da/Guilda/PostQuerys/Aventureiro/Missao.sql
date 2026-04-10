-- os insert esta em aventureiro

select * from aventura.missao;

select * from aventura.participacao_missao;

-- gerando rank missao
select m.id , m.titulo , m.status , m.nivel_perigo , count(p.aventureiro_id) as total_participacao , sum(p.recompensa_ouro) as total_recompensa from aventura.participacao_missao p
join aventura.missao m on m.id = p.missao_id group by m.id , m.titulo , m.status , m.nivel_perigo;

-- girando rank aventureiro
select p.aventureiro_id , a.nome , count(p.aventureiro_id) as total_participacao , sum(p.recompensa_ouro) as total_recompensa , sum(case when p.destaque = true then 1 else 0 end) as total_mvp from aventura.participacao_missao p join  aventura.aventureiro a
on a.id = p.aventureiro_id group by p.aventureiro_id, a.nome;




