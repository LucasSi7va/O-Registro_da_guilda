-- Para funcionar o arquivo sql precisaria esta Intellij Database integrada no Intellij IDEA
-- Aqui mostrarei que as tabelas estao criadas 100%

-- lembrando que nao vai ter nada nas tabelas e so uma mostra que as tabelas estao criadas

-- Aventureiro


-- para o intellij saber onde esta o schema aventureiro;
set search_path to aventura;

-- mostrando as tabelas criadas
select column_name , data_type from information_schema.columns where table_name = 'aventureiro';
select column_name , data_type from information_schema.columns where table_name = 'companheiro';
select column_name , data_type from information_schema.columns where table_name = 'missao';
select column_name , data_type from information_schema.columns where table_name = 'participacao_em_missao';;



select * from aventura.companheiro;



-- aqui esta o select para verificar se as tabelas estao funcionando
select usr.nome , org.nome , org.ativo , av.id as id_aventureiro , av.nome , av.nivel , av.classe , av.ativo ,co.especie , co.nome   , mi.titulo , mi.status , mi.nivel_perigo , mip.papel  , mip.recompensa_ouro , mip.destaque
FROM aventura.aventureiro av
         join aventura.companheiro co on av.id = co.aventureiro_id
         JOIN audit.organizacoes org ON av.organizacao_id = org.id
         JOIN audit.usuarios usr ON av.usuario_id = usr.id
         join aventura.missao mi on org.id = mi.organizacao_id
         join aventura.participacao_missao mip on mi.id = mip.missao_id;




-- aqui eu estava tendo uma ideia de como faria o filtro do aventureiro

-- filtro de aventureiro
select id , nome , classe , nivel from aventura.aventureiro where ativo = true and classe = 'GUERREIRO' and nivel = 10;




